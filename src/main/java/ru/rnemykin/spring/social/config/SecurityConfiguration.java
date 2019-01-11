package ru.rnemykin.spring.social.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Value("${server.servlet.session.cookie.name")
    private String cookieSessionName;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                    .loginPage("/signin")
                    .loginProcessingUrl("/signin/authenticate")
                    .failureUrl("/signin?param.error=bad_credentials")
                .and()
                    .logout()
                    .logoutUrl("/signout")
                    .deleteCookies(cookieSessionName)
                .and()
                    .authorizeRequests()
                    .antMatchers("/**").authenticated()
                .and()
                    .rememberMe()
                .and()
                    .apply(new SpringSocialConfigurer());
    }
}
