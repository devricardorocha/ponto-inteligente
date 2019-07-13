package com.ricardo.pontointeligente.api.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ricardo.pontointeligente.api.dto.CadastroPFDto;
import com.ricardo.pontointeligente.api.enums.PerfilEnum;
import com.ricardo.pontointeligente.api.modelos.Empresa;
import com.ricardo.pontointeligente.api.modelos.Funcionario;
import com.ricardo.pontointeligente.api.response.Response;
import com.ricardo.pontointeligente.api.servicos.EmpresaService;
import com.ricardo.pontointeligente.api.servicos.FuncionarioService;
import com.ricardo.pontointeligente.api.utils.PasswordUtils;

@RestController
@CrossOrigin(origins = "*")
public class CadastroPFController {

	private final Logger log = LoggerFactory.getLogger(CadastroPFController.class);

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/api/cadastrar-pf")
	public ResponseEntity<Response<CadastroPFDto>> cadastrar(@Valid @RequestBody CadastroPFDto dto,
			BindingResult result) {

		log.info("Cadastrando PF: {}", dto.toString());

		Response<CadastroPFDto> response = new Response<CadastroPFDto>();
		validaDadosExistentes(dto, result);
		if (result.hasErrors()) {
			log.error("Erro no cadastro de PF: {}", result.getAllErrors());
			result.getAllErrors().forEach(erro -> response.getErrors().add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Funcionario funcionario = convertToEntity(dto);
		funcionario.setEmpresa(empresaService.buscaPorCNPJ(dto.getCnpj()).get());

		funcionarioService.persistir(funcionario);
		response.setData(convertToDTO(funcionario));
		return ResponseEntity.ok(response);
	}

	private CadastroPFDto convertToDTO(Funcionario entity) {
		CadastroPFDto dto = modelMapper.map(entity, CadastroPFDto.class);
		dto.setSenha(null);
		if (entity.getEmpresa() != null) {
			dto.setCnpj(entity.getEmpresa().getCnpj());
		}

		return dto;
	}

	private Funcionario convertToEntity(CadastroPFDto dto) {
		Funcionario entity = modelMapper.map(dto, Funcionario.class);
		entity.setPerfil(PerfilEnum.ROLE_USUARIO);
		entity.setSenha(PasswordUtils.gerarBCrypt(dto.getSenha()));
		return entity;
	}

	private void validaDadosExistentes(CadastroPFDto dto, BindingResult result) {
		Optional<Empresa> empresa = empresaService.buscaPorCNPJ(dto.getCnpj());
		if (!empresa.isPresent()) {
			result.addError(new ObjectError("empresa", "Empresa não cadastrada."));
		}
		funcionarioService.buscarPorCPF(dto.getCpf())
				.ifPresent(funcionario -> result.addError(new ObjectError("funcionario", "CPF já cadastrado.")));
		funcionarioService.buscarPorEmail(dto.getEmail())
				.ifPresent(funcionario -> result.addError(new ObjectError("funcionario", "Email já cadastrado")));
	}
}
