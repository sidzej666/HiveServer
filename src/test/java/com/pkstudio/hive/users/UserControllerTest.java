package com.pkstudio.hive.users;

import static com.pkstudio.commons.TestData.createTestUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.pkstudio.commons.TestBase;
import com.pkstudio.hive.exceptions.rest.ResourceNotFoundException;
import com.pkstudio.hive.security.TokenAuthenticationService;
import com.pkstudio.hive.security.UserAuthentication;

public class UserControllerTest extends TestBase {

	@Mock
	private TokenAuthenticationService tokenAuthenticationService;
	@Mock
	private UserService userService;
	@Mock 
	private HttpServletResponse response;
	
	private User sampleUser = sampleUser();
	
	@InjectMocks
	UserController userController;
	
	@Before
	public void setRequestContext() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}
	
	@Test
	public void getUser_shouldReturnUser() {
		when(userService.getById(sampleUser.getId())).thenReturn(sampleUser);
		
		User user = userController.getUser(sampleUser.getId());
		
		assertThat(user).isEqualTo(sampleUser);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void getUser_shouldThrowWhenUserNotExists() {
		int nonExistentId = 0;
		when(userService.getById(nonExistentId)).thenReturn(null);
		
		try {
			userController.getUser(nonExistentId);
		} catch (ResourceNotFoundException e) {
			assertThat(e.getMessage()).isEqualTo(String.format("user with id '%d' does not exist", nonExistentId));
			throw e;
		}
	}
	
	@Test
	public void getMe_shouldGeUser() {
		String authToken = "authToken";
		when(tokenAuthenticationService.getUserFromToken(authToken)).thenReturn(sampleUser);
		when(userService.getById(sampleUser.getId())).thenReturn(sampleUser);
		
		User user = userController.getUser(authToken);
		
		assertThat(user).isEqualTo(sampleUser);
	}
	
	@Test
	public void createUser_shouldCreate() {
		when(userService.createUser(sampleUser)).thenReturn(sampleUser);
		
		User user = userController.createUser(response, sampleUser);
		assertThat(user).isEqualTo(sampleUser);
		verify(userService).createUser(user);
	}
	
	@Test
	public void createUser_shouldAddLocationHeader() {
		when(userService.createUser(sampleUser)).thenReturn(sampleUser);
		userController.createUser(response, sampleUser);
		
		verify(response).addHeader(Mockito.eq(HttpHeaders.LOCATION), Mockito.startsWith("https"));
		verify(response).addHeader(Mockito.eq(HttpHeaders.LOCATION), Mockito.endsWith(sampleUser.getId().toString()));
	}
	
	@Test
	public void createUser_shouldAddAuthenticationHeader() {
		when(userService.createUser(sampleUser)).thenReturn(sampleUser);
		userController.createUser(response, sampleUser);
		
		verify(tokenAuthenticationService).addAuthentication(response, new UserAuthentication(sampleUser));
	}
	
	private User sampleUser() {
		User user = createTestUser("username", "password", "email");
		user.setId(666);
		return user;
	}
	
}
