package com.example.sqlparse;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

//@RestController
//@SpringBootApplication
public class SqlparseApplication {

    public static void main(String[] args) {
//        SpringApplication.run(SqlparseApplication.class, args);
        SqlparseApplication app = new SqlparseApplication();
//        app.parseSql("select * from (select id from dm.dmfasdf)a");
        app.parseSql("select * from (select id from dm.dmfasdf union all select * from ccc)a");
        app.parseSql("select * from (select a.id from dm.dmfasdf a left join aa b on a.id=b.id union all select * from af)a");
        app.parseSql("select * from (select a.id from dm.dmfasdf a left join (select * from ke1 union all select * from f2) b on a.id=b.id union all select * from af)a");
    }

    public List<String> parseSql(@RequestParam("sql") String sql) {
        SQLStatementParser sqlStatementParser = SQLParserUtils.createSQLStatementParser(sql, DbType.mysql);
        SQLSelectStatement sqlStatement = (SQLSelectStatement) sqlStatementParser.parseSelect();
        MySqlSelectQueryBlock query = (MySqlSelectQueryBlock) sqlStatement.getSelect().getQuery();
        parseQuery(query);
        List<String> strings = new ArrayList<>(tables);
        tables.clear();
        return strings;
    }

    private List<String> tables = new ArrayList<>();

    public void parseQuery(SQLSelectQuery query) {
        if (query instanceof MySqlSelectQueryBlock) {
            parseSource(((MySqlSelectQueryBlock) query).getFrom());
        } else if (query instanceof SQLSubqueryTableSource) {
            parseQuery(((SQLSubqueryTableSource) query).getSelect().getQuery());
        } else if (query instanceof SQLUnionQuery) {
            ((SQLUnionQuery) query).getRelations().forEach(this::parseQuery);
        } else {
            System.out.println(query);
        }
    }

    public void parseSource(SQLTableSource source) {
        if (source instanceof SQLExprTableSource) {
            parseSqlExpr(((SQLExprTableSource) source).getExpr());
        } else if (source instanceof SQLUnionQueryTableSource) {
            List<SQLSelectQuery> relations = ((SQLUnionQueryTableSource) source).getUnion().getRelations();
            relations.forEach(this::parseQuery);
        } else if (source instanceof SQLJoinTableSource) {
            parseSource(((SQLJoinTableSource) source).getLeft());
            parseSource(((SQLJoinTableSource) source).getRight());
        } else if (source instanceof SQLSubqueryTableSource) {
            parseQuery(((SQLSubqueryTableSource) source).getSelect().getQuery());
        } else {
            System.out.println(source);
        }
    }

    public void parseSqlExpr(SQLExpr expr) {
        if (expr instanceof SQLPropertyExpr) {
            tables.add(((SQLPropertyExpr) expr).getName());
        } else if (expr instanceof SQLIdentifierExpr) {
            tables.add(((SQLIdentifierExpr) expr).getName());
        } else {
            System.out.println(expr);
        }
    }

}
