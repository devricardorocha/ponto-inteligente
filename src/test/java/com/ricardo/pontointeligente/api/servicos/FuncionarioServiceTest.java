package com.ricardo.pontointeligente.api.servicos;

import static org.junit.Assert.assertEquals;
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

import com.ricardo.pontointeligente.api.modelos.Funcionario;
import com.ricardo.pontointeligente.api.repositorios.FuncionarioRepository;
import com.ricardo.pontointeligente.api.utils.consts.Constantes;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(Constantes.ACTIVE_PROFILE_TEST)
public class FuncionarioServiceTest {
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@MockBean
	private FuncionarioRepository funcionarioRepository;
	
	private final String CPF = "12345678912";
	private final String EMAIL = "test@mail.com";
	private final Long ID = 1l;
	
	@Before
	public void setUp() {
		BDDMockito.given(funcionarioRepository.save(Mockito.any(Funcionario.class))).willReturn(new Funcionario());
		BDDMockito.given(funcionarioRepository.findByCpf(CPF)).willReturn(obterDadosFuncionario());
		BDDMockito.given(funcionarioRepository.findByEmail(EMAIL)).willReturn(obterDadosFuncionario());
		BDDMockito.given(funcionarioRepository.findById(ID)).willReturn(Optional.ofNullable(obterDadosFuncionario()));
	}
	
	@Test
	public void testBuscarPorID() {
		Optional<Funcionario> funcionario = funcionarioService.buscarPorID(ID);
		assertTrue(funcionario.isPresent());
		assertEquals(ID, funcionario.get().getId());
	}
	
	@Test
	public void testBuscarPorCPF() {
		Optional<Funcionario> funcionario = funcionarioService.buscarPorCPF(CPF);
		assertTrue(funcionario.isPresent());
		assertEquals(CPF, funcionario.get().getCpf());
	}
	
	@Test
	public void testBuscarPorEmail() {
		Optional<Funcionario> funcionario = funcionarioService.buscarPorEmail(EMAIL);
		assertTrue(funcionario.isPresent());
		assertEquals(EMAIL, funcionario.get().getEmail());
	}
	
	@Test
	public void persistir() {
		assertNotNull(funcionarioService.persistir(new Funcionario()));
	}

	private Funcionario obterDadosFuncionario() {
		Funcionario funcionario = new Funcionario();
		funcionario.setId(ID);
		funcionario.setCpf(CPF);
		funcionario.setEmail(EMAIL);
		return funcionario;
	}
	
}
