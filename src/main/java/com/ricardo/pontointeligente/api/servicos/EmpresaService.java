package com.ricardo.pontointeligente.api.servicos;

import java.util.Optional;

import com.ricardo.pontointeligente.api.modelos.Empresa;

public interface EmpresaService {

	/**
	 * Busca a empresa pelo código do CNPJ.
	 * 
	 * @param cnpj CNPJ da empresa.
	 * @return {@link Optional<Empresa>} Empresa com o CNPJ informado.
	 */
	Optional<Empresa> buscaPorCNPJ(String cnpj);
	
	/**
	 * Salva as alterações ou cria uma empresa.
	 * 
	 * @param empresa Empresa a ser persistida.
	 * @return {@link Empresa} Empresa persistida.
	 */
	Empresa persistir(Empresa empresa);
	
}
