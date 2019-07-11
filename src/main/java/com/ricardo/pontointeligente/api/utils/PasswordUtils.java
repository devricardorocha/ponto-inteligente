package com.ricardo.pontointeligente.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public abstract class PasswordUtils {

	private static final Logger log = LoggerFactory.getLogger(PasswordUtils.class);

	/**
	 * Criptogra senha do usuário.
	 * 
	 * @param senha Senha a ser criptografada BASE64
	 * @return String Senha criptografada
	 */
	public static String gerarBCrypt(String senha) {
		if (senha != null) {
			log.info("Gerando hash com o BCrypt.");
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			return encoder.encode(senha);
		}

		return null;
	}

}
