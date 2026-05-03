package com.turalabdullayev.document_flow.config;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

	public String extractUsername(String token) {
		return extractClaim(token, claims -> claims.getSubject());
	}

	public String generateToken(UserDetails userDetails) {
		return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);

	}

	private boolean isTokenExpired(String token) {
		return extractClaim(token, claims -> claims.getExpiration()).before(new Date());

	}

	private <T> T extractClaim(String token, Function<io.jsonwebtoken.Claims, T> claimsResolver) {
		final io.jsonwebtoken.Claims claims = Jwts.parserBuilder().setSigningKey(getSignInKey()).build()
				.parseClaimsJws(token).getBody();
		return claimsResolver.apply(claims);

	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

}
