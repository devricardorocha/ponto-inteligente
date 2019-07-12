package com.ricardo.pontointeligente.api.repositorios;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.ricardo.pontointeligente.api.modelos.Empresa;
import com.ricardo.pontointeligente.api.utils.consts.Constantes;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(Constantes.ACTIVE_PROFILE_TEST)
public class EmpresaRepositoryTest {

	@Autowired
	private EmpresaRepository empresaRepository;

	private final String REGISTERED_CNPJ = "012345678901234";
	
	private final String NOT_REGISTERED_CNPJ = "456123789451234";

	@Before
	public void setUp() {
		Empresa empresa = new Empresa();
		empresa.setCnpj(REGISTERED_CNPJ);
		empresa.setDataAtualizacao(new Date());
		empresa.setDataCriacao(new Date());
		empresa.setRazaoSocial("Empresa de teste");

		empresaRepository.save(empresa);
	}

	@After
	public void tearDown() {
		empresaRepository.deleteAll();
	}

	@Test
	public void testBuscarPorCNPJ() {
		Empresa empresa = empresaRepository.findByCnpj(REGISTERED_CNPJ);
		assertNotNull(empresa);
		assertEquals(REGISTERED_CNPJ, empresa.getCnpj());
		
		assertNull(empresaRepository.findByCnpj(NOT_REGISTERED_CNPJ));
	}
	
}
