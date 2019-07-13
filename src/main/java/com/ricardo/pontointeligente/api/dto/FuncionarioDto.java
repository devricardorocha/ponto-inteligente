package com.ricardo.pontointeligente.api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class FuncionarioDto {
	
	private Long id;

	@NotEmpty(message = "Nome não pode estar vazio.")
	@Length(min = 3, max = 200, message = "O nome deve ter entre 3 e 200 caracteres.")
	private String nome;
	
	@NotEmpty(message = "Email não pode estar vazio.")
	@Length(min = 5, max = 200, message = "O email deve ter entre 5 e 200 caracteres.")
	@Email(message = "Email inválido.")
	private String email;
	
	@Length(min = 8, max = 20, message = "A senha deve ter entre 8 e 20 caracteres.")
	private String senha;
	
	private Double valorHora;
	
	private Integer qtdHorasTrabalhoDia;
	
	private Integer qtdHorasAlmoco;

}
