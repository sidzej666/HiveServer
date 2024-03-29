package com.pkstudio.hive.security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pkstudio.hive.users.User;

@Component
public final class TokenHandler {

	private static final String HMAC_ALGO = "HmacSHA256";
	public static final String SEPARATOR = ".";
	public static final String SEPARATOR_SPLITTER = "\\.";

	private final Mac hmac;

	@Inject
	public TokenHandler(@Value("${token.secret}") String secret) {
		try {
			hmac = Mac.getInstance(HMAC_ALGO);
			hmac.init(new SecretKeySpec(DatatypeConverter.parseBase64Binary(secret), HMAC_ALGO));
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new IllegalStateException("failed to initialize HMAC: " + e.getMessage(), e);
		}
	}

	public User parseUserFromToken(String token) {
		final String[] parts = token.split(SEPARATOR_SPLITTER);
		if (parts.length == 2 && parts[0].length() > 0 && parts[1].length() > 0) {
			try {
				final byte[] userBytes = fromBase64(parts[0]);
				final byte[] hash = fromBase64(parts[1]);

				boolean validHash = Arrays.equals(createHmac(userBytes), hash);
				if (validHash) {
					final User user = fromJSON(userBytes);
					if (new Date().getTime() < user.getExpires()) {
						return user;
					}
				}
			} catch (IllegalArgumentException e) {
				//log tempering attempt here
			}
		}
		return null;
	}

	public String createTokenForUser(User user) {
		byte[] userBytes = toJSON(user);
		byte[] hash = createHmac(userBytes);
		final StringBuilder sb = new StringBuilder(170);
		sb.append(toBase64(userBytes));
		sb.append(SEPARATOR);
		sb.append(toBase64(hash));
		return sb.toString();
	}

	public User fromJSON(final byte[] userBytes) {
		try {
			return new ObjectMapper().readValue(new ByteArrayInputStream(userBytes), User.class);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private byte[] toJSON(User user) {
		try {
			return new ObjectMapper().writeValueAsBytes(user);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException(e);
		}
	}

	private String toBase64(byte[] content) {
		return DatatypeConverter.printBase64Binary(content);
	}

	public byte[] fromBase64(String content) {
		return DatatypeConverter.parseBase64Binary(content);
	}

	// synchronized to guard internal hmac object
	private synchronized byte[] createHmac(byte[] content) {
		return hmac.doFinal(content);
	}
}
