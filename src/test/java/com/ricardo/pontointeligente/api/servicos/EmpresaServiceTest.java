package com.ricardo.pontointeligente.api.servicos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.ricardo.pontointeligente.api.modelos.Empresa;
import com.ricardo.pontointeligente.api.repositorios.EmpresaRepository;
import com.ricardo.pontointeligente.api.utils.consts.Constantes;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(Constantes.ACTIVE_PROFILE_TEST)
public class EmpresaServiceTest {

	@Autowired
	private EmpresaService empresaService;
	
	@MockBean
	private EmpresaRepository empresaRepository;
	
	private final String CNPJ = "012345678912";
	private final String UNREGISTERED_CNPJ = "332165431212";
	
	@Before
	public void setUp() {
		BDDMockito.given(empresaRepository.save(Mockito.any(Empresa.class))).willReturn(new Empresa());
		BDDMockito.given(empresaRepository.findByCnpj(CNPJ)).willReturn(obterEmpresaComCNPJ(CNPJ));
	}
	
	@Test
	public void testBuscarPorCNPJ() {
		Optional<Empresa> empresa = empresaService.buscaPorCNPJ(CNPJ);
		assertTrue(empresa.isPresent());
		assertEquals(CNPJ, empresa.get().getCnpj());
		
		assertFalse(empresaService.buscaPorCNPJ(UNREGISTERED_CNPJ).isPresent());
	}
	
	@Test
	public void testPersistir() {
		assertNotNull(empresaService.persistir(new Empresa()));
	}

	private Empresa obterEmpresaComCNPJ(String cnpj) {
		Empresa empresa = new Empresa();
		empresa.setCnpj(cnpj);
		return empresa;
	}

}
