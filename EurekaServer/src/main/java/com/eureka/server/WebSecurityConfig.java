//package com.eureka.server;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable()
//                .authorizeRequests().anyRequest().authenticated()
//                .and().httpBasic()
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////        .and().formLogin();
//    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .passwordEncoder(passwordEncoder())
//                .withUser("admin")
//                .password("password")
//                .roles("ADMIN")
//                .and()
//                .withUser("manager")
//                .password("password")
//                .credentialsExpired(true)
//                .accountExpired(true)
//                .accountLocked(true)
//                .authorities("WRITE_PRIVILEGES", "READ_PRIVILEGES")
//                .roles("MANAGER");
//    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
