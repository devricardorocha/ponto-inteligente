package com.ricardo.pontointeligente.api.modelos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ricardo.pontointeligente.api.enums.PerfilEnum;

import lombok.Data;

@Data
@Entity
public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1086015944112052449L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String senha;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PerfilEnum perfilEnum;

}
