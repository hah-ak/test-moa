package my.domain.mysql.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("my.domain.mysql.entities")
@PropertySource("classpath:domain-mysql-${spring.profiles.active}.properties")
@EnableJpaRepositories(basePackages = {"my.domain.mysql.repositories"})
public class MySQLConfig {

    @Bean
    @Qualifier("mysqlMyApp")
    @ConfigurationProperties(prefix = "application.db.mysql.my-app")
    public DataSourceProperties hikariConfig() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "application.db.mysql.my-app.hikari")
    public HikariDataSource hikariDataSource(@Qualifier("mysqlMyApp") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
}
