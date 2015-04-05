package com.pkstudio.hive.users;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.pkstudio.commons.IntegrationTest;

public class UserIntegrationTests extends IntegrationTest {
	
	@Test
	public void getUserById_shouldReturnUser() throws Exception {
		mvc().perform(get("/rest/users/1")
				  .secure(true)
				  .headers(authenticationHeaders(1, "pawel")))
		 .andExpect(status().isOk())
		 .andExpect(jsonPath("id").value(1))
		 .andExpect(jsonPath("username").value("pawel"))
		 .andExpect(jsonPath("roles").isArray())
		 .andExpect(jsonPath("roles").value(contains(UserRole.USER.toString())))
		 .andExpect(jsonPath("password").doesNotExist());
	}
	
	@Test
	public void getUserById_shouldReturnUnauthorizedWhenNoAuthenticationToken() throws Exception {
		mvc().perform(get("/rest/users/1")
					  .secure(true))
			 .andExpect(status().isUnauthorized());
	}
	
	@Test
	public void getUserById_shouldReturnForbiddenForDifferentUser() throws Exception {
		mvc().perform(get("/rest/users/2")
					  .secure(true)
					  .headers(authenticationHeaders(1, "pawel")))
			 .andExpect(status().isForbidden());
	}
	
	@Test
	public void getMe_shouldReturnUnauthorizedWhenNoAuthenticationToken() throws Exception {
		mvc().perform(get("/rest/users/me")
				  .secure(true))
		 .andExpect(status().isUnauthorized());
	}
	
	@Test
	public void getMe_shouldReturnCurrentUser() throws Exception {
		mvc().perform(get("/rest/users/me")
				  .secure(true)
				  .headers(authenticationHeaders(1, "pawel")))
		 .andExpect(status().isOk())
		 .andExpect(jsonPath("id").value(1))
		 .andExpect(jsonPath("username").value("pawel"))
		 .andExpect(jsonPath("roles").isArray())
		 .andExpect(jsonPath("roles").value(contains(UserRole.USER.toString())))
		 .andExpect(jsonPath("password").doesNotExist());
	}

	@Test
	public void createUser_shouldCreateUser() throws Exception {
		User userToCreate = new User();
		String json = "{\"username\":\"username\",\"password\":\"password\", \"email\":\"newEmail@Email.com\"}";
		
		mvc().perform(post("/rest/users")
				.secure(true)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		  .andExpect(status().isCreated())
		  .andExpect(jsonPath("id").exists())
		  .andExpect(header().string(LOCATION, containsString("/rest/users/")));
	}
}
