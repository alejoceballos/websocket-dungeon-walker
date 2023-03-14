package com.momo2x.dungeon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        return new InMemoryUserDetailsManager(
                withDefaultPasswordEncoder()
                        .username("he")
                        .password("hepwd")
                        .roles("USER")
                        .build(),
                withDefaultPasswordEncoder()
                        .username("she")
                        .password("shepwd")
                        .roles("USER")
                        .build()
        );
    }

}
