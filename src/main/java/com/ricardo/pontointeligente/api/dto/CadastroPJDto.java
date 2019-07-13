package com.ricardo.pontointeligente.api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;

@Data
public class CadastroPJDto {

	private Long id;

	@NotEmpty(message = "Nome não pode estar vazio.")
	@Length(min = 3, max = 200, message = "O nome deve ter entre 3 e 200 caracteres.")
	private String nome;

	@NotEmpty(message = "Email não pode estar vazio.")
	@Length(min = 5, max = 200, message = "O email deve ter entre 5 e 200 caracteres.")
	@Email(message = "Email inválido.")
	private String email;

	@NotEmpty(message = "Senha não pode estar vazio.")
	private String senha;

	@NotEmpty(message = "CPF não pode estar vazio.")
	@CPF(message = "CPF inválido.")
	private String cpf;

	@NotEmpty(message = "Razão social não pode estar vazio.")
	@Length(min = 5, max = 200, message = "A razão social deve ter entre 5 e 200 caracteres.")
	private String razaoSocial;

	@NotEmpty(message = "CNPJ não pode estar vazio.")
	@CNPJ(message = "CNPJ inválido.")
	private String cnpj;

}
