package com.example.sqlparse;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.sun.istack.internal.NotNull;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: zjh
 * @Date: 2021/6/24 19:00
 */
public class SQLParser {
    private Map<String, Set<String>> tableFieldsMap = new HashMap<>();

    public Map<String, Set<String>> parseSql(String sql) {
        return parseSql("", sql);
//        return parseSql("default", sql);
    }

    public Map<String, Set<String>> parseSql(String defaultDb, String sql) {
        SQLStatementParser sqlStatementParser = SQLParserUtils.createSQLStatementParser(sql, DbType.presto);
        SQLSelectStatement sqlStatement = (SQLSelectStatement) sqlStatementParser.parseSelect();
        parseQuery(sqlStatement.getSelect().getQuery());
        Map<String, Set<String>> res = new HashMap<>();
        tableFieldsMap.forEach((table, cur) -> {
            if (table.contains("\\.") || !StringUtils.hasText(defaultDb)) {
                res.put(table, cur);
            } else {
                String fullTableName = defaultDb + "." + table;
                Set<String> oldFieldSets = res.get(fullTableName);
                if (oldFieldSets == null) {
                    res.put(fullTableName, cur);
                } else {
                    oldFieldSets.addAll(cur);
                }
            }
        });
        tableFieldsMap = new HashMap<>();
        return res;
    }

    public void parseQuery(SQLSelectQuery query) {
        if (query instanceof SQLSelectQueryBlock) {
            List<String> whereFieldsList = parseSqlExpr(((SQLSelectQueryBlock) query).getWhere());
            // todo parse group/order/having/selectList
            // group by 1,with
            parseSource(((SQLSelectQueryBlock) query).getFrom(), whereFieldsList);
        } else if (query instanceof SQLSubqueryTableSource) {
            parseQuery(((SQLSubqueryTableSource) query).getSelect().getQuery());
        } else if (query instanceof SQLUnionQuery) {
            ((SQLUnionQuery) query).getRelations().forEach(this::parseQuery);
        } else {
            System.out.println(query);
        }
    }

    public void parseSource(SQLTableSource source) {
        parseSource(source, new ArrayList<>());
    }

    public void parseSource(SQLTableSource source, List<String> selectList) {
        if (source instanceof SQLExprTableSource) {
            // 数据源直接是表的
            String tableName = parseSqlExpr(((SQLExprTableSource) source).getExpr()).get(0);
            List<String> tableFields = getTableFields(tableName);
            List<String> sourceSelectList = getSourceSelectList(source);
            sourceSelectList.addAll(selectList);
            Set<String> fieldUsedList = sourceSelectList.stream()
                    .filter(p -> tableFields.contains(p) || p.startsWith(source.getAlias() + ".") || "*".equals(p))
                    .map(p -> p.contains(".") ? p.split("\\.")[1] : p)
                    .collect(Collectors.toSet());
            Set<String> alreadyExistsFields = tableFieldsMap.get(tableName);
            if (Objects.isNull(alreadyExistsFields)) {
                tableFieldsMap.put(tableName, fieldUsedList);
            } else {
                alreadyExistsFields.addAll(fieldUsedList);
            }
        } else if (source instanceof SQLUnionQueryTableSource) {
            // union all 查询 可以忽略上面使用的selectList
            List<SQLSelectQuery> relations = ((SQLUnionQueryTableSource) source).getUnion().getRelations();
            relations.forEach(this::parseQuery);
        } else if (source instanceof SQLJoinTableSource) {
            // 表关联查询
            SQLTableSource left = ((SQLJoinTableSource) source).getLeft();
            SQLTableSource right = ((SQLJoinTableSource) source).getRight();
            SQLExpr condition = ((SQLJoinTableSource) source).getCondition();
            if (condition instanceof SQLBinaryOpExpr) {
                List<String> conditionFieldList = parseSqlExpr(condition);
                conditionFieldList.addAll(selectList);
                parseSource(left, conditionFieldList);
                parseSource(right, conditionFieldList);
            }
            parseSource(left);
            parseSource(right);
        } else if (source instanceof SQLSubqueryTableSource) {
            parseQuery(((SQLSubqueryTableSource) source).getSelect().getQuery());
        } else {
            System.out.println(source);
        }
    }

    private List<String> parseSqlExpr(SQLExpr sqlExpr) {
        List<String> res = new ArrayList<>();
        if (sqlExpr == null) {
            return res;
        }
        if (sqlExpr instanceof SQLPropertyExpr || sqlExpr instanceof SQLIdentifierExpr) {
            res.add(sqlExpr.toString());
        } else if (sqlExpr instanceof SQLBinaryOpExpr) {
            SQLBinaryOpExpr condition = (SQLBinaryOpExpr) sqlExpr;
            Stream.of(condition.getLeft(), condition.getRight()).forEach(p -> res.addAll(parseSqlExpr(p)));
        } else if (sqlExpr instanceof SQLMethodInvokeExpr) {
            // method
            ((SQLMethodInvokeExpr) sqlExpr).getArguments().forEach(p -> res.addAll(parseSqlExpr(p)));
        } else if (sqlExpr instanceof SQLValuableExpr) {
            // skip sqlValuableExpr
        } else if (sqlExpr instanceof SQLInListExpr) {
            // in
            res.add(((SQLInListExpr) sqlExpr).getExpr().toString());
        } else if (sqlExpr instanceof SQLInSubQueryExpr) {
            // in sub query
            res.add(((SQLInSubQueryExpr) sqlExpr).getExpr().toString());
            parseQuery(((SQLInSubQueryExpr) sqlExpr).getSubQuery().getQuery());
        } else {
            System.out.println("sqlExpr======= " + sqlExpr.getClass().toString() + " val= " + sqlExpr.toString());
        }
        return res;
    }

    private List<String> getSourceSelectList(SQLTableSource source) {
        SQLObject parent = source.getParent();
        while (!(parent instanceof SQLSelectQueryBlock)) {
            parent = parent.getParent();
        }
        return ((SQLSelectQueryBlock) parent).getSelectList().stream().map(p -> p.getExpr().toString()).collect(Collectors.toList());
    }

    public List<String> getTableFields(String db, String table) {
        // todo getFrom hivemeta
        return new ArrayList<>();
    }

    private @NotNull
    List<String> getTableFields(String tableName) {
        return new ArrayList<>();
    }
}
