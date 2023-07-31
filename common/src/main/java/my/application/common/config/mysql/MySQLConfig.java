package my.application.common.config.mysql;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"my.application.myApp.repositories.mysql"})
public class MySQLConfig {

    @Bean
    @Qualifier("mysqlMyApp")
    @ConfigurationProperties(prefix = "application.db.mysql.my-app")
    public DataSourceProperties hikariConfig() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "application.db.mysql.exam.hikari")
    public HikariDataSource hikariDataSource(@Qualifier("mysqlMyApp") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }


}
