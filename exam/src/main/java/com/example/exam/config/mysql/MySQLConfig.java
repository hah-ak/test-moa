package com.example.exam.config.mysql;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
public class MySQLConfig {

    @Bean
    @Qualifier("mysqlExam")
    @ConfigurationProperties(prefix = "exams.db.mysql.exam")
    public DataSourceProperties hikariConfig() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "exams.db.mysql.exam.hikari")
    public HikariDataSource hikariDataSource(@Qualifier("mysqlExam") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }


}
