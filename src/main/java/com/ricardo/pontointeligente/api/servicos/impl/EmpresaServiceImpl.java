package com.ricardo.pontointeligente.api.servicos.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricardo.pontointeligente.api.modelos.Empresa;
import com.ricardo.pontointeligente.api.repositorios.EmpresaRepository;
import com.ricardo.pontointeligente.api.servicos.EmpresaService;

@Service(value="empresaService")
public class EmpresaServiceImpl implements EmpresaService {
	
	@Autowired
	private EmpresaRepository empresaRepository;

	@Override
	public Optional<Empresa> buscaPorCNPJ(String cnpj) {
		return Optional.ofNullable(empresaRepository.findByCnpj(cnpj));
	}

	@Override
	public Empresa persistir(Empresa empresa) {
		return empresaRepository.save(empresa);
	}

}
