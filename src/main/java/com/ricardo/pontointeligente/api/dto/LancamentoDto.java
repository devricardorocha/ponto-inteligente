package com.ricardo.pontointeligente.api.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class LancamentoDto {

	private Long id;

	@NotEmpty(message = "Data não pode ser vazio.")
	private String data;
	
	@NotEmpty(message="Tipo não pode ser vazio.")
	private String tipo;
	
	private String descricao;
	
	private String localizacao;
	
	private Long funcionarioId;

}
