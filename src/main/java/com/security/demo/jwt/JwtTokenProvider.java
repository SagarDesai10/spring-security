package com.security.demo.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.demo.dto.JwtDTO;
import com.security.demo.entity.User;
import com.security.demo.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	@Autowired
	private UserRepository userRepo;

	@Value("${JWT_SECRET_KEY}")
	private String jwtSecret;
	@Value("${app-jwt-expiration-milliseconds}")
	private long jwtExpirationDate;

	public String generateToken(Authentication authentication) throws InvalidKeyException, JsonProcessingException {
		
		String username = authentication.getName();
		
		User user = userRepo.findByEmail(username)
				.orElseThrow(() -> new RuntimeException("User not found"));

		JwtDTO jwtDto = JwtDTO.builder().id(user.getId()).username(user.getEmail()).role(user.getRole()).build();
		Date currentDate=new Date();
		Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
		
		
		return Jwts.builder().setSubject(new ObjectMapper().writeValueAsString(jwtDto))
				.setIssuedAt(currentDate)
				.setExpiration(expireDate)
				.signWith(getKey(),SignatureAlgorithm.HS256)
				.compact();
	}
	
	public JwtDTO getJwtDTO(String token) throws JsonMappingException, JsonProcessingException
	{
		Claims claims=Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
		
		return new ObjectMapper().readValue(claims.getSubject(),JwtDTO.class);
	}

	public boolean validateToken(String token)
	{
		try {
			Jwts.parserBuilder().setSigningKey(getKey()).build().parse(token);
			return true;
		}catch (Exception e) {
	      }
	        return false;
	}
	
	private Key getKey()
	{
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
}
