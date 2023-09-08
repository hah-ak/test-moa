package my.servers.oauth2server.config;

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
@EntityScan(basePackages = {"my.application.security.entities.mysql"})
@PropertySource("classpath:domain-mysql-${spring.profiles.active}.properties")
@EnableJpaRepositories(basePackages = {"my.application.security.repositories.mysql"})
public class SecurityMysqlConfig {

    @Bean("mysqlSecurity")
    @ConfigurationProperties(prefix = "application.db.mysql.security")
    public DataSourceProperties hikariConfig() {
        return new DataSourceProperties();
    }

    @Bean("mysqlSecurityDatasource")
    @ConfigurationProperties(prefix = "application.db.mysql.security.hikari")
    public HikariDataSource hikariDataSource(@Qualifier("mysqlSecurity") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
}
