package com.bb166.foruminternetowe.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
    private DriverManagerDataSource driverManagerDataSource;

    @Autowired
    SecurityConfiguration(DriverManagerDataSource driverManagerDataSource){
        this.driverManagerDataSource = driverManagerDataSource;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()
                .authorizeRequests()
                .antMatchers("/deleteSection")
                .hasAnyRole("ADMIN")
                .antMatchers("/deleteTopic")
                .hasAnyRole("ADMIN")
                .antMatchers("/createSection")
                .hasAnyRole("ADMIN")
                .antMatchers("/deletePost")
                .hasAnyRole("ADMIN")
                .antMatchers("/createTopic")
                .authenticated()
                .antMatchers("/messages")
                .authenticated()
                .antMatchers("/editPost")
                .authenticated()
                .antMatchers("/search")
                .authenticated()
                .antMatchers("/login")
                .anonymous()
                .antMatchers("/register")
                .anonymous()
                .anyRequest()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/")
                .and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(driverManagerDataSource)
               .usersByUsernameQuery("select username,password,active from user where username = ?")
               .authoritiesByUsernameQuery("select username,role from user where username= ?");
    }
}
