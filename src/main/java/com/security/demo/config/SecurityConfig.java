package com.security.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		 http
         .csrf(csrf -> csrf.disable()) // Disable CSRF (only if stateless)
         .authorizeHttpRequests(auth -> auth
             .requestMatchers("/api/v1/auth/**").permitAll()  // Public access
             .requestMatchers("/api/v1/admin/**").hasRole("ADMIN") // Admin only
             .requestMatchers("/api/v1/user/**").hasRole("USER") // User role required
             .anyRequest().authenticated() // All other requests require authentication
         );
		
		return http.build();
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
}
