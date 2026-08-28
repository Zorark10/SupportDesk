package com.project.SupportDesk.service;

import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Service
public class JwtService {
	
	
	@Value("${jwt.secret}")
	String secret;
	
	SecretKey secretKey;
	
	@PostConstruct
	public void init() {
		secretKey = Keys.hmacShaKeyFor(secret.getBytes());
	}
	public String extractUsername(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token)
					.getPayload().getSubject();
	}
	
	public String generateToken(Authentication auth) {
		String name = auth.getName();
		return Jwts.builder().setSubject(name).issuedAt(new Date()).
					expiration(Date.from(Instant.now().plusSeconds(3600)))
					.signWith(secretKey).compact();
	}
	
	public Date extractExpiration(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token)
					.getPayload().getExpiration();
	}
	
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public boolean validateToken(String token, UserDetails userDetails) {
		String usernameString = extractUsername(token);
		return usernameString.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}
}
