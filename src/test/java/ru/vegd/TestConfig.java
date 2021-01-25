package ru.vegd;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@TestConfiguration
@ComponentScan(basePackages = { "ru.vegd.dao", "ru.vegd.util" })
@PropertySource("database.properties")
@EnableTransactionManagement
@EnableAutoConfiguration
public class TestConfig {
    @Value("${db.url}")
    private String dbUrl;
    @Value("${db.username}")
    private String dbUsername;
    @Value("${db.password}")
    private String dbPassword;
    @Value("${db.driver}")
    private String dbDriver;
    @Value("${db.schema}")
    private String dbSchema;
    @Value("${db.poolsize}")
    private String DB_POOL_MAX_SIZE;

    @Bean(name = "dataSource")
    public DataSource dataSource()
    {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(dbUrl);
        hikariConfig.setUsername(dbUsername);
        hikariConfig.setPassword(dbPassword);
        hikariConfig.setDriverClassName(dbDriver);
        hikariConfig.setMaximumPoolSize(Integer.parseInt(DB_POOL_MAX_SIZE));
        hikariConfig.setSchema(dbSchema);

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public DatabaseConfigBean dbUnitDatabaseConfig() {
        final DatabaseConfigBean dbConfig = new DatabaseConfigBean();
        dbConfig.setDatatypeFactory(new PostgresqlDataTypeFactory());
        dbConfig.setCaseSensitiveTableNames(false);
        return dbConfig;
    }

    @Bean
    public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection(final DataSource dataSource){
        final DatabaseDataSourceConnectionFactoryBean connectionFactory = new DatabaseDataSourceConnectionFactoryBean();
        connectionFactory.setDataSource(dataSource);
        connectionFactory.setDatabaseConfig(this.dbUnitDatabaseConfig());
        connectionFactory.setSchema(dbSchema);
        return connectionFactory;
    }
}
