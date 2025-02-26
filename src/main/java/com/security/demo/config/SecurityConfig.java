package com.security.demo.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.security.demo.jwt.JwtAuthEntryPoint;
import com.security.demo.jwt.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthFilter jwtAuthenticationFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		 http
         .csrf(csrf -> csrf.disable()) // Disable CSRF (only if stateless)
         .authorizeHttpRequests(auth -> auth
             .requestMatchers("/api/v1/auth/**").permitAll()  // Public access
             .requestMatchers("/api/v1/admin/**").hasRole("ADMIN") // Admin only
             .requestMatchers("/api/v1/user/**").hasRole("USER") // User role required
             .anyRequest().authenticated())
         .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
         .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		 
		 
		 http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	  UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	  CorsConfiguration config = new CorsConfiguration();
	  config.setAllowedOrigins(List.of("*"));  // Replace with your allowed origin(s)
	  config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	  config.setAllowedHeaders(List.of("*"));  // You can restrict allowed headers as needed
	  config.setAllowCredentials(false);  // Set to true if your frontend sends cookies

	  source.registerCorsConfiguration("/**", config);
	  return source;
	}
}
