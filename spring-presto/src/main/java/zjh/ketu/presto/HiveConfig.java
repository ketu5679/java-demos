package zjh.ketu.presto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @Author: zjh
 * @Date: 2020/11/17 10:48
 */
@Configuration
public class HiveConfig {
    private static Logger LOG = LoggerFactory.getLogger(HiveConfig.class);

    @Bean(name = "hiveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hive")
    public DataSource prestoDataSource() {
        LOG.info("-------------------- hive init ---------------------");
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "hiveTemplate")
    public JdbcTemplate hiveJdbcTemplate(@Qualifier("hiveDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
