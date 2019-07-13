package com.ricardo.pontointeligente.api.controllers;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ricardo.pontointeligente.api.dto.FuncionarioDto;
import com.ricardo.pontointeligente.api.modelos.Funcionario;
import com.ricardo.pontointeligente.api.response.Response;
import com.ricardo.pontointeligente.api.servicos.FuncionarioService;
import com.ricardo.pontointeligente.api.utils.PasswordUtils;

@RestController
@CrossOrigin(origins = "*")
public class FuncionarioController {

	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PutMapping("/api/funcionarios/{id}")
	public ResponseEntity<Response<FuncionarioDto>> atualiza(@PathVariable Long id,
			@Valid @RequestBody FuncionarioDto dto, BindingResult result) {

		Response<FuncionarioDto> response = new Response<FuncionarioDto>();
		Optional<Funcionario> funcionario = funcionarioService.buscarPorID(id);
		
		if (!funcionario.isPresent()) {
			response.getErrors().add("Funcionário não encontrado.");
			return ResponseEntity.badRequest().body(response);
		}
		
		atualizarFuncionario(funcionario.get(), dto, result);
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(erro -> response.getErrors().add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		funcionarioService.persistir(funcionario.get());
		response.setData(convertToDTO(funcionario.get()));
		return ResponseEntity.ok(response);
	}

	private FuncionarioDto convertToDTO(Funcionario funcionario) {
		return modelMapper.map(funcionario, FuncionarioDto.class);
	}

	private void atualizarFuncionario(Funcionario funcionario, @Valid FuncionarioDto dto, BindingResult result) {
		
		funcionario.setNome(dto.getNome());
		funcionario.setDataAtualizacao(new Date());
		funcionario.setQtdHorasAlmoco(dto.getQtdHorasAlmoco());
		funcionario.setQtdHorasTrabalhoDia(dto.getQtdHorasTrabalhoDia());
		funcionario.setValorHora(dto.getValorHora());
		if (!funcionario.getEmail().equalsIgnoreCase(dto.getEmail())) {
			funcionarioService.buscarPorEmail(dto.getEmail()).ifPresent(
					func -> result.getAllErrors().add(new ObjectError("funcionario", "Email já cadastrado.")));
			
			funcionario.setEmail(dto.getEmail());
		}
		if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
			funcionario.setSenha(PasswordUtils.gerarBCrypt(dto.getSenha()));
		}
	}
	
}
