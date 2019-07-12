package com.ricardo.pontointeligente.api.servicos.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricardo.pontointeligente.api.modelos.Funcionario;
import com.ricardo.pontointeligente.api.repositorios.FuncionarioRepository;
import com.ricardo.pontointeligente.api.servicos.FuncionarioService;

@Service(value="funcionarioService")
public class FuncionarioServiceImpl implements FuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Override
	public Funcionario persistir(Funcionario funcionario) {
		return funcionarioRepository.save(funcionario);
	}

	@Override
	public Optional<Funcionario> buscarPorCPF(String cpf) {
		return Optional.ofNullable(funcionarioRepository.findByCpf(cpf));
	}

	@Override
	public Optional<Funcionario> buscarPorEmail(String email) {
		return Optional.ofNullable(funcionarioRepository.findByEmail(email));
	}

	@Override
	public Optional<Funcionario> buscarPorID(Long id) {
		return funcionarioRepository.findById(id);
	}

}
