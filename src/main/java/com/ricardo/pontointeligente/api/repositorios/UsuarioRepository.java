package com.ricardo.pontointeligente.api.repositorios;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ricardo.pontointeligente.api.modelos.Usuario;

@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Usuario findByEmail(String email);
	
}
