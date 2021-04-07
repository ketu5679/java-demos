package zjh.ketu.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;



@Configuration
@MapperScan(basePackages = TestConfig.PACKAGE, sqlSessionFactoryRef = "dqSqlSessionFactory")
public class TestConfig {

    static final String PACKAGE = "zjh.ketu.mybatis.dao";
    static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClass;

    @Bean(name = "dqDataSource")
    @Primary
    public DataSource dqDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setName("dqDataSource");
        dataSource.setInitialSize(5);
        dataSource.setMaxActive(100);
        dataSource.setConnectionErrorRetryAttempts(6);
        dataSource.setTimeBetweenConnectErrorMillis(5000);
        dataSource.setTestWhileIdle(true);
//        dataSource.setValidationQuery("SELECT 1 FROM DUAL");
        dataSource.setValidationQueryTimeout(10);
        dataSource.setTestOnReturn(false);
        dataSource.setTestOnBorrow(false);
        return dataSource;
    }

    @Bean(name = "dqTransactionManager")
    @Primary
    public DataSourceTransactionManager dqTransactionManager() {
        return new DataSourceTransactionManager(dqDataSource());
    }

    @Bean(name = "dqSqlSessionFactory")
    @Primary
    public SqlSessionFactory dqSqlSessionFactory(@Qualifier("dqDataSource") DataSource dqDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dqDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(TestConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
