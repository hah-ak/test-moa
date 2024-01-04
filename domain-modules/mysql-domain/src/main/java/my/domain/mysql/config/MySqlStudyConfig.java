package my.domain.mysql.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:domain-mysql-${spring.profiles.active}.properties")
public class MySqlStudyConfig {
    @Value("spring.profiles.active")
    private String profile;
    @Bean("mysqlStudy")
    @ConfigurationProperties(prefix = "application.db.mysql.study")
    public DataSourceProperties hikariConfigStudy() {
        return new DataSourceProperties();
    }

    @Bean("mysqlStudySource")
    @ConfigurationProperties(prefix = "application.db.mysql.my-app.study")
    public HikariDataSource hikariDataSourceStudy(@Qualifier("mysqlStudy") DataSourceProperties dataSourceProperties) {
        return profile.equals("local") ? dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build() : null;
    }
}
