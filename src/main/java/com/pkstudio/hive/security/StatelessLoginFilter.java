package com.pkstudio.hive.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.web.context.request.ServletWebRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pkstudio.hive.exceptions.rest.DefaultRestErrorResolver;
import com.pkstudio.hive.exceptions.rest.RestError;

public class StatelessLoginFilter extends
		AbstractAuthenticationProcessingFilter {
	
	private TokenAuthenticationService tokenAuthenticationService;
	private UserDetailsService userDetailsService;
	private DefaultRestErrorResolver defaultRestErrorResolver;
	
	protected StatelessLoginFilter(String defaultFilterProcessesUrl, TokenAuthenticationService tokenAuthenticationService,
			AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
			DefaultRestErrorResolver defaultRestErrorResolver) {
		super(defaultFilterProcessesUrl);
		this.tokenAuthenticationService = tokenAuthenticationService;
		this.userDetailsService = userDetailsService;
		this.defaultRestErrorResolver = defaultRestErrorResolver;
		setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException,
			IOException, ServletException {
		try {
			if (HttpMethod.OPTIONS.toString().equalsIgnoreCase(request.getMethod())) {
				return null;
			}
			final User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
			final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
					user.getUsername(), user.getPassword());
			return getAuthenticationManager().authenticate(loginToken);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json");
			RestError restError = defaultRestErrorResolver.resolveError(new ServletWebRequest(request), 
					null, new BadCredentialsException("Bad credentials"));
			response.getWriter().write(restError.toJsonString());
			return null;
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain, Authentication authentication) throws IOException, ServletException {

		// Lookup the complete User object from the database and create an Authentication for it
		final User authenticatedUser = userDetailsService.loadUserByUsername(authentication.getName());
		final UserAuthentication userAuthentication = new UserAuthentication(authenticatedUser);

		// Add the custom token as HTTP header to the response
		tokenAuthenticationService.addAuthentication(response, userAuthentication);

		// Add the authentication to the Security context
		SecurityContextHolder.getContext().setAuthentication(userAuthentication);
	}
}
