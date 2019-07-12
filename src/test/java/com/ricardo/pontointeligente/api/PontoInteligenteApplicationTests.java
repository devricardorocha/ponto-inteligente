package com.ricardo.pontointeligente.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.ricardo.pontointeligente.api.utils.consts.Constantes;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(Constantes.ACTIVE_PROFILE_TEST)
public class PontoInteligenteApplicationTests {
	
	private final Logger log = LoggerFactory.getLogger(PontoInteligenteApplicationTests.class);

	@Test
	public void contextLoads() {
		log.info("Executando testes unitários");
	}

}
