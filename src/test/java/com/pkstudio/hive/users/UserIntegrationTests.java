package com.pkstudio.hive.users;

import static com.pkstudio.hive.security.TokenAuthenticationService.AUTH_HEADER_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;

import com.pkstudio.commons.IntegrationTest;

public class UserIntegrationTests extends IntegrationTest {
	
	@Inject
	private UsersDao usersDao;
	
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
		  .andExpect(jsonPath("id").exists())
		  .andExpect(jsonPath("password").doesNotExist())
		  .andExpect(jsonPath("email").value(is(email)))
		  .andExpect(jsonPath("roles").isArray())
		  .andExpect(jsonPath("roles").value(contains(UserRole.USER.toString())))
		  .andExpect(header().string(LOCATION, containsString("/rest/users/")))
		  .andExpect(header().string(AUTH_HEADER_NAME, containsString(".")));
		
		User user = usersDao.findByUsername("username");
		assertThat(user.getAuthorities().size()).isEqualTo(1);
		assertThat(user.hasRole(UserRole.USER)).isTrue();
		assertThat(user.getEmail()).isEqualTo(email);
		assertThat(user.getPassword()).isEqualTo(password);
		assertThat(user.getUsername()).isEqualTo(username);
		assertThat(user.getId()).isEqualTo(3);
		assertThat(user.isAccountNonExpired()).isTrue();
		assertThat(user.isAccountNonLocked()).isTrue();
		assertThat(user.isCredentialsNonExpired()).isTrue();
		assertThat(user.isEnabled()).isTrue();
	}
}
