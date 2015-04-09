package com.pkstudio.hive.users;

import static com.pkstudio.commons.TestData.TEST_USER_ONE;
import static com.pkstudio.commons.TestData.TEST_USER_TWO;
import static com.pkstudio.hive.security.TokenAuthenticationService.AUTH_HEADER_NAME;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.pkstudio.commons.IntegrationTest;

public class UserIntegrationTests extends IntegrationTest {
	
	@Inject
	private UsersDao usersDao;
	
	@Test
	public void getUserById_shouldReturnUser() throws Exception {
		mvc().perform(get("/rest/users/" + TEST_USER_ONE.getId())
				  .secure(true)
				  .headers(authenticationHeaders(TEST_USER_ONE)))
		 .andExpect(status().isOk())
		 .andExpect(jsonPath("$.id").value(TEST_USER_ONE.getId()))
		 .andExpect(jsonPath("$.username").value(TEST_USER_ONE.getUsername()))
		 .andExpect(jsonPath("$.roles[*]").value(TEST_USER_ONE.getRolesInStringFormat().get(0)))
		 .andExpect(jsonPath("$.password").doesNotExist());
	}
	
	@Test
	public void getUserById_shouldReturnUnauthorizedWhenNoAuthenticationToken() throws Exception {
		mvc().perform(get("/rest/users/" + TEST_USER_ONE.getId())
					  .secure(true))
			 .andExpect(status().isUnauthorized())
			 .andExpect(jsonPath("$.code").value(401))
			 .andExpect(jsonPath("$.status").value(401))
			 .andExpect(jsonPath("$.message").exists())
			 .andExpect(jsonPath("$.developerMessage").exists());
	}
	
	@Test
	public void getUserById_shouldReturnForbiddenForDifferentUser() throws Exception {
		mvc().perform(get("/rest/users/" + TEST_USER_TWO.getId())
					  .secure(true)
					  .headers(authenticationHeaders(TEST_USER_ONE)))
			 .andExpect(status().isForbidden())
			 .andExpect(jsonPath("$.code").value(403))
			 .andExpect(jsonPath("$.status").value(403))
			 .andExpect(jsonPath("$.message").exists())
			 .andExpect(jsonPath("$.developerMessage").exists());
	}
	
	@Test
	public void getUserById_shouldReturnResourceNotFoundForNonExistentUser() throws Exception {
		mvc().perform(get("/rest/users/" + (TEST_USER_TWO.getId() + 1))
				  .secure(true)
				  .headers(authenticationHeaders(TEST_USER_ONE)))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.code").value(404))
		.andExpect(jsonPath("$.status").value(404))
		.andExpect(jsonPath("$.message").exists())
		.andExpect(jsonPath("$.developerMessage").exists());
	}
	
	@Test
	public void getMe_shouldReturnUnauthorizedWhenNoAuthenticationToken() throws Exception {
		mvc().perform(get("/rest/users/me")
				  .secure(true))
			 .andExpect(status().isUnauthorized())
			 .andExpect(jsonPath("$.code").value(401))
			 .andExpect(jsonPath("$.status").value(401))
			 .andExpect(jsonPath("$.message").exists())
			 .andExpect(jsonPath("$.developerMessage").exists());
	}
	
	@Test
	public void getMe_shouldReturnCurrentUser() throws Exception {
		mvc().perform(get("/rest/users/me")
				  .secure(true)
				  .headers(authenticationHeaders(TEST_USER_ONE)))
		 .andExpect(status().isOk())
		 .andExpect(jsonPath("$.id").value(TEST_USER_ONE.getId()))
		 .andExpect(jsonPath("$.username").value(TEST_USER_ONE.getUsername()))
		 .andExpect(jsonPath("$.roles[*]").value(TEST_USER_ONE.getRolesInStringFormat().get(0)))
		 .andExpect(jsonPath("$.password").doesNotExist());
	}

	@Test
	public void createUser_shouldCreateUser() throws Exception {
		String username = "username";
		String password = "password";
		String email = "newEmail@Email.com";
		
		String json = "{ \"username\":\"" + username + "\"," +
					    "\"password\":\"" + password + "\"," +
					    "\"email\":\"" + email + "\"}";
		
		mvc().perform(post("/rest/users")
				.secure(true)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		  .andExpect(status().isCreated())
		  .andExpect(jsonPath("$.id").exists())
		  .andExpect(jsonPath("$.password").doesNotExist())
		  .andExpect(jsonPath("$.email").value(is(email)))
		  .andExpect(jsonPath("$.roles[*]").value(contains(UserRole.USER.toString())))
		  .andExpect(header().string(LOCATION, containsString("/rest/users/")))
		  .andExpect(header().string(AUTH_HEADER_NAME, containsString(".")));
		
		User user = usersDao.findByUsername(username);
		assertThat(user.getAuthorities().size()).isEqualTo(1);
		assertThat(user.hasRole(UserRole.USER)).isTrue();
		assertThat(user.getEmail()).isEqualTo(email);
		assertThat(user.getPassword()).isEqualTo(password);
		assertThat(user.getUsername()).isEqualTo(username);
		assertThat(user.getId()).isEqualTo(TEST_USER_TWO.getId() + 1);
		assertThat(user.isAccountNonExpired()).isTrue();
		assertThat(user.isAccountNonLocked()).isTrue();
		assertThat(user.isCredentialsNonExpired()).isTrue();
		assertThat(user.isEnabled()).isTrue();
	}
	
	@Test
	public void createUser_shouldContainFieldErrorsForRequiredFields() throws Exception {
		String username = "";
		String password = "";
		String email = "";
		
		String json = "{ \"username\":\"" + username + "\"," +
					    "\"password\":\"" + password + "\"," +
					    "\"email\":\"" + email + "\"}";
		
		mvc().perform(post("/rest/users")
				.secure(true)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		  .andExpect(status().isBadRequest())
		  .andExpect(jsonPath("$.code").value(400))
		  .andExpect(jsonPath("$.status").value(400))
		  .andExpect(jsonPath("$.message").exists())
		  .andExpect(jsonPath("$.developerMessage").exists())
		  .andExpect(jsonPath("$.fieldErrors", hasSize(3)))
		  .andExpect(jsonPath("$.fieldErrors[*].fieldName", containsInAnyOrder("username", "email", "password")))
		  .andExpect(jsonPath("$.fieldErrors[*].message",
				  			  containsInAnyOrder("Username can't be empty", "Email can't be empty", "Password can't be empty")));
	}
	
	@Test
	public void createUser_shouldContainUsernameAndEmailAlreadyTakenErrorFields() throws Exception {
		String username = TEST_USER_ONE.getUsername();
		String password = "password";
		String email = TEST_USER_ONE.getEmail();
		
		String json = "{ \"username\":\"" + username + "\"," +
					    "\"password\":\"" + password + "\"," +
					    "\"email\":\"" + email + "\"}";
		
		mvc().perform(post("/rest/users")
				.secure(true)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		  .andExpect(status().isBadRequest())
		  .andExpect(jsonPath("$.code").value(400))
		  .andExpect(jsonPath("$.status").value(400))
		  .andExpect(jsonPath("$.message").exists())
		  .andExpect(jsonPath("$.developerMessage").exists())
		  .andExpect(jsonPath("$.fieldErrors", hasSize(2)))
		  .andExpect(jsonPath("$.fieldErrors[*].fieldName", containsInAnyOrder("username", "email")))
		  .andExpect(jsonPath("$.fieldErrors[*].message",
				  			  containsInAnyOrder(format("Username '%s' is already taken", username),
				  					  			 format("Email '%s' is already taken", email))));
	}
}
