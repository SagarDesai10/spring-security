package com.security.demo.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.security.demo.dto.JwtDTO;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		 String token = getTokenFromRequest(request);
	        String requestURI = request.getRequestURI();
	        if(!(requestURI.startsWith("/swagger-ui") || requestURI.startsWith("/v3/api-docs") || requestURI.contains("/swagger-config"))) {
	            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {

	                JwtDTO jwtToken = jwtTokenProvider.getJwtDTO(token);

	                UserDetails userDetails = userDetailsService.loadUserByUsername(jwtToken.getUsername());

	                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
	                        userDetails,
	                        null,
	                        userDetails.getAuthorities()
	                );

	                authenticationToken.setDetails(authenticationToken);

	                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

	            } else {
	                response.setStatus(HttpStatus.UNAUTHORIZED.value());
	            }
	        }

	        filterChain.doFilter(request, response);
		
	}
	
	private String getTokenFromRequest(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }

        return null;
    }

}
