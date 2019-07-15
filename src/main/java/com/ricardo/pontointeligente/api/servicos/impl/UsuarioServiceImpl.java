package com.ricardo.pontointeligente.api.servicos.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricardo.pontointeligente.api.modelos.Usuario;
import com.ricardo.pontointeligente.api.repositorios.UsuarioRepository;
import com.ricardo.pontointeligente.api.servicos.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public Optional<Usuario> buscarPorEmail(String email) {
		return Optional.ofNullable(usuarioRepository.findByEmail(email));
	}

}
