package com.ricardo.pontointeligente.api.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ricardo.pontointeligente.api.dto.LancamentoDto;
import com.ricardo.pontointeligente.api.modelos.Lancamento;
import com.ricardo.pontointeligente.api.response.Response;
import com.ricardo.pontointeligente.api.servicos.FuncionarioService;
import com.ricardo.pontointeligente.api.servicos.LancamentoService;

@RestController
@CrossOrigin(origins = "*")
public class LancamentoController {

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private ModelMapper modelMapper;

	@Value("${paginacao.qtd_por_pagina}")
	private Integer qtdPorPagina;

	@GetMapping("/api/funcionarios/{id}/lancamentos")
	public ResponseEntity<Response<Page<LancamentoDto>>> lista(@PathVariable(name = "id") Long funcionarioId,
			@RequestParam(defaultValue = "0") Integer pag, @RequestParam(defaultValue = "id") String ord,
			@RequestParam(defaultValue = "DESC") String dir) {

		Response<Page<LancamentoDto>> response = new Response<Page<LancamentoDto>>();

		PageRequest page = PageRequest.of(pag, qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Lancamento> lancamentos = lancamentoService.buscarPorFuncionarioId(funcionarioId, page);

		Page<LancamentoDto> lancamentosDto = lancamentos.map(lancamento -> convertToDTO(lancamento));

		response.setData(lancamentosDto);
		return ResponseEntity.ok(response);
	}

	private LancamentoDto convertToDTO(Lancamento entity) {
		return modelMapper.map(entity, LancamentoDto.class);
	}

	@PostMapping("/api/lancamentos")
	public ResponseEntity<Response<LancamentoDto>> adiciona(@Valid @RequestBody LancamentoDto dto,
			BindingResult result) {

		Response<LancamentoDto> response = new Response<LancamentoDto>();
		validaLancamento(dto, result);

		if (result.hasErrors()) {
			result.getAllErrors().forEach(erro -> response.getErrors().add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Lancamento lancamento = convertToEntity(dto);
		lancamentoService.persistir(lancamento);

		response.setData(convertToDTO(lancamento));
		return ResponseEntity.ok(response);
	}

	private Lancamento convertToEntity(LancamentoDto dto) {
		return modelMapper.map(dto, Lancamento.class);
	}

	private void validaLancamento(LancamentoDto dto, BindingResult result) {

		if (!funcionarioService.buscarPorID(dto.getFuncionarioId()).isPresent()) {
			result.getAllErrors().add(new ObjectError("funcionario", "Funcionário não encontrado."));
		}
		
	}
}
