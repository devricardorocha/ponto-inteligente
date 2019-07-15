package com.ricardo.pontointeligente.api.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ricardo.pontointeligente.api.security.utils.JwtTokenUtils;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	private static final String AUTH_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer";

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtils jwtTokenUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = request.getHeader(AUTH_HEADER);
		if (token != null && token.startsWith(BEARER_PREFIX)) {
			token = token.substring(7);
		}

		String username = jwtTokenUtils.getUsernameFromToken(token);
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails details = userDetailsService.loadUserByUsername(username);

			if (jwtTokenUtils.tokenValido(token)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(details,
						null, details.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}

		}

		filterChain.doFilter(request, response);
	}

}
