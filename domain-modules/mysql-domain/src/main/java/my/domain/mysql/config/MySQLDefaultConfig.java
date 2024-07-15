package my.domain.mysql.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:domain-mysql-${spring.profiles.active}.properties")
public class MySQLDefaultConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "application.db.mysql.my-app")
    public DataSourceProperties hikariConfig() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "application.db.mysql.my-app.hikari")
    public HikariDataSource hikariDataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

}
