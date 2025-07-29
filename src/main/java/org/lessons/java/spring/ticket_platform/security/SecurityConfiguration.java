package org.lessons.java.spring.ticket_platform.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                .requestMatchers("/operator/**").hasAnyAuthority("OPERATOR", "ADMIN")

                // ! SHARED ROUTES
                .requestMatchers("/tickets", "/tickets/**").hasAnyAuthority("OPERATOR", "ADMIN")

                // ! PUBLIC ROUTES
                .requestMatchers("/login").permitAll()

                .anyRequest().authenticated())

                // ! OTHERS
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
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
