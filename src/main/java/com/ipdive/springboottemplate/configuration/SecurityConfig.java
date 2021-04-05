package com.ipdive.springboottemplate.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/static/**", "/templates/**").permitAll()
                        .antMatchers("/premium/**").hasAnyAuthority("ADMIN","PREMIUM")
                        .antMatchers("/user/**").hasAnyAuthority("ADMIN","PREMIUM","USER")
                )
                .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login-error")
                    .defaultSuccessUrl("/index")
                    .permitAll()
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/index")
                    .permitAll();
    }
}