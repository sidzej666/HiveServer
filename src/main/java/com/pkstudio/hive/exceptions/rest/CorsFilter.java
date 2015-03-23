package com.pkstudio.hive.exceptions.rest;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class CorsFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "http://localhost:9000");
	    response.addHeader("Access-Control-Allow-Methods", "Cache-Control, Pragma, Origin, Authorization, Content-Type, X-Requested-With");
	    response.addHeader("Access-Control-Allow-Headers", "GET, PUT, OPTIONS, X-CSRF-TOKEN, Content-Type");
	    response.addHeader("Access-Control-Allow-Credentials", "true");
	    filterChain.doFilter(request, response);
	}

}