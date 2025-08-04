package org.lessons.java.spring.ticket_platform.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                // ! STATIC RESOURCES
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()

                // ! ADMIN ROUTES
                .requestMatchers("/admin/**").hasAuthority("ADMIN")

                // ! OPERATOR ROUTES
                .requestMatchers("/operators/**").hasAnyAuthority("ADMIN")

                // ! SHARED ROUTES
                .requestMatchers("/tickets", "/tickets/**").hasAnyAuthority("ADMIN", "OPERATOR")
                .requestMatchers(HttpMethod.POST, "/tickets/**").hasAuthority("ADMIN")

                // ! PUBLIC ROUTES
                .requestMatchers("/login").permitAll()

                .anyRequest().authenticated())

                // ! LOGIN CONFIG
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())

                // ! LOGOUT CONFIG
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());

        return http.build();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }

}
