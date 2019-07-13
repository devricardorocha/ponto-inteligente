package com.ricardo.pontointeligente.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ricardo.pontointeligente.api.modelos.Empresa;
import com.ricardo.pontointeligente.api.servicos.EmpresaService;
import com.ricardo.pontointeligente.api.utils.consts.Constantes;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(Constantes.ACTIVE_PROFILE_TEST)
@AutoConfigureMockMvc
@WithMockUser
public class EmpresaControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private EmpresaService empresaService;

	private final String BUSCA_EMPRESA_CNPJ_PATH = "/api/empresas/";
	private final String CNPJ_CADASTRADO = "14630054740";
	private final String CNPJ_NAO_CADASTRADO = "74861941555";
	private final Long ID = 1l;
	private final String RAZAO_SOCIAL = "Empresa de teste";

	@Test
	public void testBuscaEmpresaPorCNPJ() throws Exception {

		BDDMockito.given(empresaService.buscaPorCNPJ(CNPJ_NAO_CADASTRADO)).willReturn(Optional.empty());
		mvc.perform(MockMvcRequestBuilders.get(BUSCA_EMPRESA_CNPJ_PATH + CNPJ_NAO_CADASTRADO)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andExpect(
						jsonPath("$.errors").value("Empresa com CNPJ " + CNPJ_NAO_CADASTRADO + " não foi encontrado."));

		BDDMockito.given(empresaService.buscaPorCNPJ(CNPJ_CADASTRADO)).willReturn(Optional.ofNullable(obterEmpresa()));
		mvc.perform(MockMvcRequestBuilders.get(BUSCA_EMPRESA_CNPJ_PATH + CNPJ_CADASTRADO)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID)).andExpect(jsonPath("$.data.cnpj").value(CNPJ_CADASTRADO))
				.andExpect(jsonPath("$.data.razaoSocial").value(RAZAO_SOCIAL));
	}

	private Empresa obterEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setId(ID);
		empresa.setCnpj(CNPJ_CADASTRADO);
		empresa.setRazaoSocial(RAZAO_SOCIAL);
		return empresa;
	}
}
