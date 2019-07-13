package com.ricardo.pontointeligente.api.controllers;

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

import com.ricardo.pontointeligente.api.dto.CadastroPJDto;
import com.ricardo.pontointeligente.api.enums.PerfilEnum;
import com.ricardo.pontointeligente.api.modelos.Empresa;
import com.ricardo.pontointeligente.api.modelos.Funcionario;
import com.ricardo.pontointeligente.api.response.Response;
import com.ricardo.pontointeligente.api.servicos.EmpresaService;
import com.ricardo.pontointeligente.api.servicos.FuncionarioService;
import com.ricardo.pontointeligente.api.utils.PasswordUtils;

@RestController
@CrossOrigin(origins = "*")
public class CadastroPJController {

	private final Logger log = LoggerFactory.getLogger(CadastroPJController.class);

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/api/cadastrar-pj")
	public ResponseEntity<Response<CadastroPJDto>> cadastrar(@Valid @RequestBody CadastroPJDto dto,
			BindingResult result) {

		log.info("Cadastrando PJ: {}", dto.toString());

		Response<CadastroPJDto> response = new Response<CadastroPJDto>();
		validaDadosExistentes(dto, result);
		if (result.hasErrors()) {
			log.error("Erro no cadastro de PJ: {}", result.getAllErrors());
			result.getAllErrors().forEach(erro -> response.getErrors().add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Empresa empresa = convertDTOtoEmpresaEntity(dto);
		empresaService.persistir(empresa);

		Funcionario funcionario = convertDTOtoFuncionarioEntity(dto);
		funcionario.setEmpresa(empresa);
		funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
		this.funcionarioService.persistir(funcionario);

		response.setData(convertEntityToDTO(funcionario));
		return ResponseEntity.ok(response);
	}

	private void validaDadosExistentes(CadastroPJDto dto, BindingResult result) {
		empresaService.buscaPorCNPJ(dto.getCnpj())
				.ifPresent(empresa -> result.addError(new ObjectError("empresa", "Empresa já cadastrada.")));
		funcionarioService.buscarPorEmail(dto.getEmail())
				.ifPresent(funcionario -> result.addError(new ObjectError("funcionario", "Email já cadastrado.")));
		funcionarioService.buscarPorCPF(dto.getCpf())
				.ifPresent(funcionario -> result.addError(new ObjectError("funcionario", "CPF já cadastrado.")));
	}

	private CadastroPJDto convertEntityToDTO(Funcionario funcionario) {
		CadastroPJDto dto = modelMapper.map(funcionario, CadastroPJDto.class);
		dto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
		dto.setCnpj(funcionario.getEmpresa().getCnpj());
		dto.setSenha(null);
		return dto;
	}

	private Funcionario convertDTOtoFuncionarioEntity(CadastroPJDto dto) {
		
		Funcionario entity = modelMapper.map(dto, Funcionario.class);
		if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
			entity.setSenha(PasswordUtils.gerarBCrypt(dto.getSenha()));
		}
		
		return entity;
	}

	private Empresa convertDTOtoEmpresaEntity(CadastroPJDto dto) {
		return modelMapper.map(dto, Empresa.class);
	}

}
