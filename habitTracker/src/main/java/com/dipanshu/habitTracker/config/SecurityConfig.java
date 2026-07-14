package com.dipanshu.habitTracker.config;

import com.dipanshu.habitTracker.Jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. Inject your custom filter
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for REST APIs
                .csrf(csrf -> csrf.disable())

                // 2. JWTs are stateless. We tell Spring NOT to save sessions in memory.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 3. Configure who is allowed to access what
                .authorizeHttpRequests(auth -> auth
                        // Note: I added /User/login here too, because users need to be able
                        // to log in to GET their token in the first place!
                        .requestMatchers("/User/signup", "/User/login").permitAll()

                        // Locks down EVERY other endpoint. Requires a valid JWT.
                        .anyRequest().authenticated()
                )

                // 4. Put your JWT filter at the front of the line
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}