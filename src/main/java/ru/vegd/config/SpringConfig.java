package ru.vegd.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource("database.properties")
@EnableTransactionManagement
public class SpringConfig {
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

    @Bean
    public DataSource dataSource () {
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
}
