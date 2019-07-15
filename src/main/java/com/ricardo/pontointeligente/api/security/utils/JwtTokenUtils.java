package com.ricardo.pontointeligente.api.security.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtils {

	public static String CLAIM_KEY_USERNAME = "sub";
	public static String CLAIM_KEY_ROLE = "role";
	public static String CLAIM_KEY_CREATED = "created";

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	public String getUsernameFromToken(String token) {
		Optional<Claims> claims = getClaimsFromToken(token);
		return claims.isPresent() ? claims.get().getSubject() : null;
	}

	public Date getExpirationDateFromToken(String token) {
		Optional<Claims> claims = getClaimsFromToken(token);
		return claims.isPresent() ? claims.get().getExpiration() : null;
	}

	public String refreshToken(String token) {
		Optional<Claims> claims = getClaimsFromToken(token);
		if (claims.isPresent()) {
			claims.get().put(CLAIM_KEY_CREATED, new Date());
			return gerarToken(claims.get());
		}

		return null;
	}

	public Boolean tokenValido(String token) {
		return !tokenExpirado(token);
	}

	public String obterToken(UserDetails details) {

		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, details.getUsername());
		details.getAuthorities().forEach(auth -> claims.put(CLAIM_KEY_ROLE, auth.getAuthority()));
		claims.put(CLAIM_KEY_CREATED, new Date());
		return gerarToken(claims);
	}

	private boolean tokenExpirado(String token) {

		Date dataExpiracao = getExpirationDateFromToken(token);
		if (dataExpiracao == null) {
			return false;
		}

		return dataExpiracao.before(new Date());
	}

	private String gerarToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(gerarDataExpiracao())
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	private Date gerarDataExpiracao() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

	private Optional<Claims> getClaimsFromToken(String token) {

		Optional<Claims> claims = Optional.empty();
		try {
			claims = Optional.ofNullable(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody());
		} catch (Exception e) {
			claims = Optional.empty();
		}

		return claims;
	}

}
