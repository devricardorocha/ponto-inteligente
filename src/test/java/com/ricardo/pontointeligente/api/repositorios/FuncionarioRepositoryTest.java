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

import com.ricardo.pontointeligente.api.enums.PerfilEnum;
import com.ricardo.pontointeligente.api.modelos.Empresa;
import com.ricardo.pontointeligente.api.modelos.Funcionario;
import com.ricardo.pontointeligente.api.utils.PasswordUtils;
import com.ricardo.pontointeligente.api.utils.consts.Constantes;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(Constantes.ACTIVE_PROFILE_TEST)
public class FuncionarioRepositoryTest {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private EmpresaRepository empresaRepository;

	private static final String REGISTERED_EMAIL = "test@mail.com";
	private static final String REGISTERED_CPF = "01234567891";

	private static final String UNREGISTERED_EMAIL = "untest@mail.com";
	private static final String UNREGISTERED_CPF = "19876453210";

	@Before
	public void setUp() {
		Empresa empresa = empresaRepository.save(obterDadosEmpresa());
		funcionarioRepository.save(obterDadosFuncionario(empresa));
	}

	@After
	public void tearDown() {
		funcionarioRepository.deleteAll();
		empresaRepository.deleteAll();
	}

	@Test
	public void testBuscarFuncionarioPorEmail() {
		Funcionario funcionario = this.funcionarioRepository.findByEmail(REGISTERED_EMAIL);
		assertNotNull(funcionario);
		assertEquals(REGISTERED_EMAIL, funcionario.getEmail());

		assertNull(this.funcionarioRepository.findByEmail(UNREGISTERED_EMAIL));
	}

	@Test
	public void testBuscarFuncionarioPorCPF() {
		Funcionario funcionario = this.funcionarioRepository.findByCpf(REGISTERED_CPF);
		assertNotNull(funcionario);
		assertEquals(REGISTERED_CPF, funcionario.getCpf());

		assertNull(this.funcionarioRepository.findByCpf(UNREGISTERED_CPF));
	}

	@Test
	public void testBuscarFuncionarioPorCPFOuEmail() {
		Funcionario funcionarioCpfEEmail = this.funcionarioRepository.findByCpfOrEmail(REGISTERED_CPF,
				REGISTERED_EMAIL);
		assertNotNull(funcionarioCpfEEmail);
		assertEquals(REGISTERED_CPF, funcionarioCpfEEmail.getCpf());
		assertEquals(REGISTERED_EMAIL, funcionarioCpfEEmail.getEmail());

		Funcionario funcionarioCpf = this.funcionarioRepository.findByCpfOrEmail(REGISTERED_CPF, UNREGISTERED_EMAIL);
		assertNotNull(funcionarioCpf);
		assertEquals(REGISTERED_CPF, funcionarioCpf.getCpf());

		Funcionario funcionarioEmail = this.funcionarioRepository.findByCpfOrEmail(UNREGISTERED_CPF, REGISTERED_EMAIL);
		assertNotNull(funcionarioEmail);
		assertEquals(REGISTERED_EMAIL, funcionarioEmail.getEmail());

		assertNull(this.funcionarioRepository.findByCpfOrEmail(UNREGISTERED_CPF, UNREGISTERED_EMAIL));
	}

	private Funcionario obterDadosFuncionario(Empresa empresa) {
		Funcionario funcionario = new Funcionario();
		funcionario.setCpf(REGISTERED_CPF);
		funcionario.setDataAtualizacao(new Date());
		funcionario.setDataCriacao(new Date());
		funcionario.setEmail(REGISTERED_EMAIL);
		funcionario.setEmpresa(empresa);
		funcionario.setNome("Funcionario Teste");
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setQtdHorasAlmoco(1f);
		funcionario.setQtdHorasTrabalhoDia(8f);
		funcionario.setSenha(PasswordUtils.gerarBCrypt("12345678"));
		return funcionario;
	}

	private Empresa obterDadosEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setCnpj("123456789123456");
		empresa.setDataAtualizacao(new Date());
		empresa.setDataCriacao(new Date());
		empresa.setRazaoSocial("Empresa de teste");
		return empresa;
	}
}
