package com.ricardo.pontointeligente.api.servicos.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ricardo.pontointeligente.api.modelos.Lancamento;
import com.ricardo.pontointeligente.api.repositorios.LancamentoRepository;
import com.ricardo.pontointeligente.api.servicos.LancamentoService;

@Service(value="lancamentoService")
public class LancamentoServiceImpl implements LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	public Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
		return this.lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
	}
	
	public Optional<Lancamento> buscarPorId(Long id) {
		return this.lancamentoRepository.findById(id);
	}
	
	public Lancamento persistir(Lancamento lancamento) {
		return this.lancamentoRepository.save(lancamento);
	}
	
	public void remover(Long id) {
		this.lancamentoRepository.deleteById(id);
	}

}