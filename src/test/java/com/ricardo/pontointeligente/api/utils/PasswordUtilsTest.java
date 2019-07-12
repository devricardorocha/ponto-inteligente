package com.ricardo.pontointeligente.api.utils;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtilsTest {
	
	private final String SENHA = "12345678";
	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Test
	public void testSenhaNula() {
		assertNull(PasswordUtils.gerarBCrypt(null));
	}

	@Test
	public void testEncode() {
		assertTrue(encoder.matches(SENHA, PasswordUtils.gerarBCrypt(SENHA)));
	}
}
