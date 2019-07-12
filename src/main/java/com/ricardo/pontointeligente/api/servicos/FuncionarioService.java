package com.ricardo.pontointeligente.api.servicos;

import java.util.Optional;

import com.ricardo.pontointeligente.api.modelos.Funcionario;

public interface FuncionarioService {
	
	/**
	 * Persiste as informações do funcionário na base de dados.
	 * 
	 * @param funcionario Funcionário a ser persistido.
	 * @return {@link Funcionario} Funcionario persistido.
	 */
	Funcionario persistir(Funcionario funcionario);
	
	/**
	 * Retorna o funcionário que possua o CPF informado.
	 * 
	 * @param cpf CPF do funcionário desejado.
	 * @return {@link Funcionario} Funcionario portador do CPF informado.
	 */
	Optional<Funcionario> buscarPorCPF(String cpf);
	
	/**
	 * Retorna o funcionário que possua o email informado.
	 * 
	 * @param email Email do funcionário desejado.
	 * @return {@link Funcionario} Funcionario portador do email informado.
	 */
	Optional<Funcionario> buscarPorEmail(String email);
	
	/**
	 * Retorna o funcionário que possua o ID informado.
	 * 
	 * @param id ID do funcionário desejado.
	 * @return {@link Funcionario} Funcionario portador do ID informado.
	 */
	Optional<Funcionario> buscarPorID(Long id);

}
