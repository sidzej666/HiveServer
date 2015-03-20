package com.pkstudio.hive.security;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.context.request.ServletWebRequest;

import com.pkstudio.hive.exceptions.rest.DefaultRestErrorResolver;
import com.pkstudio.hive.exceptions.rest.RestError;

public final class RestAuthenticationEntryPoint implements
		AuthenticationEntryPoint {
	@Inject
	DefaultRestErrorResolver defaultRestErrorResolver;
	
	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		RestError restError = defaultRestErrorResolver.resolveError(new ServletWebRequest(request), 
				null, new BadCredentialsException("Bad credentials"));
		response.getWriter().write(restError.toJsonString());
	}
	
	public void setDefaultRestErrorResolver(DefaultRestErrorResolver defaultRestErrorResolver) {
		this.defaultRestErrorResolver = defaultRestErrorResolver;
	}
}