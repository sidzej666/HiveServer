package com.pkstudio.hive.security;

import static com.pkstudio.hive.security.TokenHandler.SEPARATOR_SPLITTER;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.pkstudio.hive.users.User;
import com.pkstudio.hive.users.UsersDao;

@Component
public class TokenAuthenticationService {
	public static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
	private static final long TEN_DAYS = 1000 * 60 * 60 * 24 * 10;

	private final TokenHandler tokenHandler;
	private final UsersDao usersDao;

	@Autowired
	public TokenAuthenticationService(@Value("${token.secret}") String secret, UsersDao usersDao) {
		tokenHandler = new TokenHandler(DatatypeConverter.parseBase64Binary(secret));
		this.usersDao = usersDao;
	}

	public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
		final User user = authentication.getDetails();
		user.setExpires(System.currentTimeMillis() + TEN_DAYS);
		response.addHeader(AUTH_HEADER_NAME, tokenHandler.createTokenForUser(user));
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		final String token = request.getHeader(AUTH_HEADER_NAME);
		if (token != null) {
			final User user = tokenHandler.parseUserFromToken(token);
			if (user != null) {
				return new UserAuthentication(usersDao.findByUsername(user.getUsername()));
			}
		}
		return null;
	}
	
	public User getUserFromToken(String token) {
		final String userPartFromToken = token.split(SEPARATOR_SPLITTER)[0];
		try {
			final byte[] userBytes = tokenHandler.fromBase64(userPartFromToken);
			return tokenHandler.fromJSON(userBytes);
		} catch (IllegalArgumentException e) {
			//log tempering attempt here
		}
		return null;
	}
}
