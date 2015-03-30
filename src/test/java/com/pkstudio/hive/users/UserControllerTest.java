package com.pkstudio.hive.users;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;

import com.pkstudio.commons.TestBase;

public class UserControllerTest extends TestBase {
	
	private MockMvc mockMvc;
	
	@Mock
	private UserService userService;
	
	@InjectMocks
	private UserController userController;
	
	@Test
	public void createUserShouldRethrowUserServiceError() {
		
	}
}
