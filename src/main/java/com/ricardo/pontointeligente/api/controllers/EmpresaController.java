package com.ricardo.pontointeligente.api.controllers;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ricardo.pontointeligente.api.dto.EmpresaDto;
import com.ricardo.pontointeligente.api.modelos.Empresa;
import com.ricardo.pontointeligente.api.response.Response;
import com.ricardo.pontointeligente.api.servicos.EmpresaService;

@RestController
@CrossOrigin(origins = "*")
public class EmpresaController {

	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/api/empresas/{cnpj}")
	public ResponseEntity<Response<EmpresaDto>> consulta(@PathVariable String cnpj) {

		Response<EmpresaDto> response = new Response<EmpresaDto>();
		Optional<Empresa> empresa = empresaService.buscaPorCNPJ(cnpj);

		if (!empresa.isPresent()) {
			response.getErrors().add("Empresa com CNPJ " + cnpj + " não foi encontrado.");
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(convertToDTO(empresa.get()));
		return ResponseEntity.ok(response);
	}

	private EmpresaDto convertToDTO(Empresa empresa) {
		return modelMapper.map(empresa, EmpresaDto.class);
	}

}
