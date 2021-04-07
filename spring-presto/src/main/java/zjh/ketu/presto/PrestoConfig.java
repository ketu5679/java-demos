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
public class PrestoConfig {
    private static Logger LOG = LoggerFactory.getLogger(PrestoConfig.class);

    @Bean(name = "prestoDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.presto")
    public DataSource prestoDataSource() {
        LOG.info("-------------------- presto init ---------------------");
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "prestoTemplate")
    public JdbcTemplate prestoJdbcTemplate(@Qualifier("prestoDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
