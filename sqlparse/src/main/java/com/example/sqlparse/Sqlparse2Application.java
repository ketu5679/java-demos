package com.example.sqlparse;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.sun.istack.internal.NotNull;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.PublicKey;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@RestController
//@SpringBootApplication
public class Sqlparse2Application {

    @GetMapping("/parseSql")
    public Map<String, Set<String>> parseSql(@RequestParam("sql") String sql) {
        return new SQLParser().parseSql(sql);
    }

    public static void parseAndPrintResult(String sql) {
        System.out.println(new SQLParser().parseSql(sql));
    }

    public static void main(String[] args) {
//        SpringApplication.run(SqlparseApplication.class, args);
//        app.parseSql("select * from (select id from dm.dmfasdf)a");
//        app.parseSql("select * from (select id from dm.dmfasdf union all select * from ccc)a");
//        app.parseSql("select * from (select a.id, b.name from dm.dmfasdf a left join aa b on a.id=b.id left join cccc c on a.id=c.id and a.name='ketu' and a.age is null union all select * from af)a");
//        parseAndPrintResult("select * from (select a.id from dm.dmfasdf a left join (select * from ke1 union all select * from f2) b on a.id=b.id union all select * from af where id in (select name from xxx))a");
//        parseAndPrintResult("select * from af a where a.name = 1 and a.id in (select a.name from xxx a)");
        parseAndPrintResult("select catnew, case when cate_1_nm like '%生产%' then '生产' ELSE '市场' end as cate_1_nm, shpp_country_nm , case when cat = '是' then weave_nm else 'other' end as weave_nm, case when ROUND(price,0) <5 then '0.[0-5)' when ROUND(price,0) <7 then '01.[5-7)' when ROUND(price,0) <9 then '02.[7-9)' when ROUND(price,0) <11 then '03.[9-11)' when ROUND(price,0) <13 then '04.[11-13)' when ROUND(price,0) <15 then '05.[13-15)' when ROUND(price,0) <17 then '06.[15-17)' when ROUND(price,0) <19 then '07.[17-19)' when ROUND(price,0) <21 then '08.[19-21)' when ROUND(price,0) <23 then '09.[21-23)' when ROUND(price,0) <25 then '10.[23-25)' when ROUND(price,0) <27 then '11.[25-27)' when ROUND(price,0) <29 then '12.[27-29)' else '13.>=29' end AS price_section, sum(sale_cnt-not_t_cnt) as six_m_sale_cnt, sum(if(regexp_like(return_tp,'\\b0\\b|\\b1\\b|\\b2\\b|\\b3\\b|\\b5\\b|\\b6\\b|\\b9\\b|\\b10\\b|\\b11\\b'),1,0 ) ) as six_m_quality_cnt from dm.dm_supplychain_quality_saleandreturn_detail_d a left join ( select a.*, b.catnew, b.returnpolicy, cat from ( select goods_sn, max(cate_1_nm) as cate_1_nm, max(price) as price, max(real_onsale_time) as real_onsale_time, new_cate_1_nm, new_cate_2_nm, new_cate_3_nm, new_cate_4_nm From dm.dm_pub_gds_attr_td where site_tp = 'xx' group by goods_sn, new_cate_1_nm, new_cate_2_nm, new_cate_3_nm, new_cate_4_nm ) a left join tmp.pinleihuafen b on a.new_cate_1_nm = b.new_cate_1_nm and a.new_cate_2_nm = b.new_cate_2_nm and a.new_cate_3_nm = b.new_cate_3_nm and a.new_cate_4_nm = b.new_cate_4_nm ) b on a.goods_sn = b.goods_sn left join ( select goods_sn, case when weave_nm = '毛织' then '针织' when weave_nm = '梭织' then '梭织' else '针织' end as weave_nm from dm.dm_dim_supplychain_sku_d )d on d.goods_sn = a.goods_sn where a.site_tp = 'xx' and dt >= date_format(current_date - interval '120' day ,'%Y%m%d') and dt <= date_format(current_date - interval '30' day ,'%Y%m%d') and shpp_country_nm in ('United States','France','United Kingdom','Germany') group by 1,2,3,4,5 having sum(sale_cnt-not_t_cnt)>= 5000 order by catnew desc ,cate_1_nm,shpp_country_nm,weave_nm,price_section\n");
    }

}
