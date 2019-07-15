package com.ricardo.pontointeligente.api.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.ricardo.pontointeligente.api.enums.PerfilEnum;
import com.ricardo.pontointeligente.api.modelos.Usuario;

public abstract class JwtUserFactory {

	public static JwtUser create(Usuario usuario) {
		return new JwtUser(usuario.getId(), usuario.getEmail(), usuario.getSenha(),
				mapToGrantAuthorities(usuario.getPerfilEnum()));
	}

	private static Collection<? extends GrantedAuthority> mapToGrantAuthorities(PerfilEnum perfil) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(perfil.toString()));
		return authorities;
	}

}
