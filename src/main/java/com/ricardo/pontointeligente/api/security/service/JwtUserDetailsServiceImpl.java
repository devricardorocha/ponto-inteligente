package com.ricardo.pontointeligente.api.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ricardo.pontointeligente.api.modelos.Usuario;
import com.ricardo.pontointeligente.api.security.JwtUserFactory;
import com.ricardo.pontointeligente.api.servicos.UsuarioService;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioService.buscarPorEmail(username);
		if (usuario.isPresent()) {
			return JwtUserFactory.create(usuario.get());
		}

		throw new UsernameNotFoundException("Email não encontrado.");
	}

}
