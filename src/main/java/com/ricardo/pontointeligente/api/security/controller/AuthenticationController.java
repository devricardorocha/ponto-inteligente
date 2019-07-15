package com.ricardo.pontointeligente.api.security.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ricardo.pontointeligente.api.response.Response;
import com.ricardo.pontointeligente.api.security.dto.JwtAuthenticationDto;
import com.ricardo.pontointeligente.api.security.dto.TokenDto;
import com.ricardo.pontointeligente.api.security.utils.JwtTokenUtils;

@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
	private static final String TOKEN_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer";

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JwtTokenUtils jwtTokenUtils;

	@Autowired
	private UserDetailsService userDetailsService;

	@PostMapping("/auth")
	public ResponseEntity<Response<TokenDto>> gerarTokenJwt(@Valid @RequestBody JwtAuthenticationDto dto, BindingResult result) {

		Response<TokenDto> response = new Response<TokenDto>();
		if (result.hasErrors()) {
			log.error("Erro validando geração de token: {}", result.getAllErrors());
			result.getAllErrors().forEach(erro -> response.getErrors().add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		log.info("Gerando token para email {}.", dto.getEmail());
		
		UserDetails user = this.userDetailsService.loadUserByUsername(dto.getEmail());
		log.info("Usuario: {}", user);
		
		Authentication authentication = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetails details = userDetailsService.loadUserByUsername(dto.getEmail());
		String token = jwtTokenUtils.obterToken(details);
		response.setData(new TokenDto(token));

		return ResponseEntity.ok(response);
	}

	@PostMapping("/auth/refresh")
	public ResponseEntity<Response<TokenDto>> gerarRefreshTokenJwt(HttpServletRequest request) {

		log.info("Gerando refresh token JWT.");
		Response<TokenDto> response = new Response<TokenDto>();
		Optional<String> token = Optional.ofNullable(request.getHeader(TOKEN_HEADER));
		if (token.isPresent() && token.get().startsWith(BEARER_PREFIX)) {
			token = Optional.of(token.get().substring(7));
		}

		if (!token.isPresent()) {
			response.getErrors().add("Token não informado.");
		} else if (!jwtTokenUtils.tokenValido(token.get())) {
			response.getErrors().add("Token inválido ou expirado.");
		}

		if (!response.getErrors().isEmpty()) {
			return ResponseEntity.badRequest().body(response);
		}

		String refreshedToken = jwtTokenUtils.refreshToken(token.get());
		response.setData(new TokenDto(refreshedToken));
		return ResponseEntity.ok(response);

	}

}
