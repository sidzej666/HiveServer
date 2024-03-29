package com.pkstudio.hive.security;

import static com.pkstudio.hive.security.TokenHandler.SEPARATOR_SPLITTER;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.pkstudio.hive.users.User;
import com.pkstudio.hive.users.UsersDao;

@Component
public class TokenAuthenticationService {
	public static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
	public static final long TEN_DAYS = 1000 * 60 * 60 * 24 * 10;

	private final TokenHandler tokenHandler;
	private final UsersDao usersDao;

	@Inject
	public TokenAuthenticationService(UsersDao usersDao, TokenHandler tokenHandler) {
		this.tokenHandler = tokenHandler;
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
				User userFromDatabase = usersDao.findByUsername(user.getUsername());
				if (userFromDatabase == null) {
					return null;
				}
				return new UserAuthentication(userFromDatabase);
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
