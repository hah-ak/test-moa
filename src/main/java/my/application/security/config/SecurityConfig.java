package my.application.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    // 다중설정시
//    @Bean
//    public LdapContextSource contextSource(ApplicationContext applicationContext) {
//        Environment environment = applicationContext.getEnvironment();
//        LdapContextSource ldapContextSource = new LdapContextSource();
//        ldapContextSource.setUrl(environment.getRequiredProperty("ldap.url"));
//        return ldapContextSource;
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize)-> authorize.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN").anyRequest().fullyAuthenticated()).formLogin(httpSecurityFormLoginConfigurer -> {});
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/login").permitAll().anyRequest().authenticated()).formLogin(formLogin -> formLogin.loginProcessingUrl("/login-process"));
        return http.build();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(BaseLdapPathContextSource contextSource) {
//        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(contextSource);
//        factory.setUserSearchBase("ou=people");
//        factory.setUserSearchFilter("(uid={0})");
//        return factory.createAuthenticationManager();
//    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups")
                .contextSource()
                .url("ldap://localhost:8389/dc=springframework,dc=org")
                .and()
                .passwordCompare()
                .passwordEncoder(new BCryptPasswordEncoder())
                .passwordAttribute("userPassword");
    }
}
