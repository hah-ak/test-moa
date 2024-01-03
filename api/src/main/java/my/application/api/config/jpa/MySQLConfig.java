package my.application.api.config.jpa;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.dialect.MariaDBDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.HashMap;

@EnableJpaRepositories()
public class MySQLConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(HikariDataSource hikariDataSource) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "validate");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
        properties.put("hibernate.default_batch_fetch_size", 1000);
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        properties.put("hibernate.use_sql_comments", true);

        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setGenerateDdl(false);
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQLInnoDBDialect");

        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(hikariDataSource);
        localContainerEntityManagerFactoryBean.setJpaPropertyMap(properties);
        localContainerEntityManagerFactoryBean.setPackagesToScan();
        return localContainerEntityManagerFactoryBean;
    }
}
