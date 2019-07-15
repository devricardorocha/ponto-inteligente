package com.ricardo.pontointeligente.api.servicos;

import java.util.Optional;

import com.ricardo.pontointeligente.api.modelos.Usuario;

public interface UsuarioService {
	
	Optional<Usuario> buscarPorEmail(String email);

}
