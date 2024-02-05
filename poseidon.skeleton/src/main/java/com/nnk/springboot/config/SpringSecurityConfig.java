package com.nnk.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Configures security filter chain for the application.
     *
     * @param http the HttpSecurity object to configure security
     * @return the configured security filter chain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/home/**").hasAnyRole("USER", "ADMIN");
            auth.requestMatchers("/curvePoint/**").hasAnyRole("USER", "ADMIN");
            auth.requestMatchers("/bidList/**").hasAnyRole("USER", "ADMIN");
            auth.requestMatchers("/secure/**").hasAnyRole("USER", "ADMIN");
            auth.requestMatchers("/rating/**").hasAnyRole("USER", "ADMIN");
            auth.requestMatchers("/ruleName/**").hasAnyRole("USER", "ADMIN");
            auth.requestMatchers("/trade/**").hasAnyRole("USER", "ADMIN");
            auth.requestMatchers("/admin/**").hasRole("ADMIN");
            auth.anyRequest().authenticated();
        }).formLogin(Customizer.withDefaults()).build();
    }

    /**
     * Creates a BCryptPasswordEncoder bean.
     *
     * @return a BCryptPasswordEncoder object
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates an AuthenticationManager bean.
     *
     * @param http the HttpSecurity object to configure security
     * @param bCryptPasswordEncoder the BCryptPasswordEncoder object for password encoding
     * @return an AuthenticationManager object
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder)
            throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }
}
