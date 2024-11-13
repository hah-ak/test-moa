package my.application.user.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Objects;

@Configuration
@EnableJpaRepositories(
        basePackages = {"my.application.user.repositories.mysql"},
        entityManagerFactoryRef = "localContainerEntityManagerFactoryBean",
        transactionManagerRef = "platformTransactionManager"
)
public class MySQLConfig {
    @Bean("localContainerEntityManagerFactoryBean")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(HikariDataSource hikariDataSource) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.ddl.auto", "create");
        properties.put("hibernate.default_batch_fetch_size", 1000);
        properties.put("hibernate.format_sql", true);
        properties.put("hibernate.use_sql_comments", true);

        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setGenerateDdl(false);
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");

        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(hikariDataSource);
        localContainerEntityManagerFactoryBean.setJpaPropertyMap(properties);
        localContainerEntityManagerFactoryBean.setPackagesToScan("my.application.user.entities.mysql");
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        localContainerEntityManagerFactoryBean.setJpaDialect(new HibernateJpaDialect());
        return localContainerEntityManagerFactoryBean;
    }

    @Bean("platformTransactionManager")
    @Primary
    public PlatformTransactionManager pubTransactionManager(LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
        return new JpaTransactionManager(Objects.requireNonNull(localContainerEntityManagerFactoryBean.getObject()));
    }
}
