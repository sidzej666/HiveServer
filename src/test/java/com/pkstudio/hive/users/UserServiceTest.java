package com.pkstudio.hive.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.pkstudio.commons.TestBase;
import com.pkstudio.hive.exceptions.EmailRequiredException;
import com.pkstudio.hive.exceptions.InvalidEmailException;
import com.pkstudio.hive.exceptions.PasswordRequiredException;
import com.pkstudio.hive.exceptions.PasswordToShortException;
import com.pkstudio.hive.exceptions.UsernameRequiredException;
import com.pkstudio.hive.exceptions.UsernameTakenException;

public class UserServiceTest extends TestBase {
	
	private final String EXISTING_USERNAME = "existing_username";
	private final String NEW_USERNAME = "new_username";
	private final String NEW_USER_PASSWORD = "password";
	private final String TO_SHORT_PASSWORD = "pass";
	private final String NEW_USER_EMAIL = "email@email.com";
	private final String INVALID_EMAIL = "invalid_email";
	
	@Mock
	private UsersDao usersDao;
	
	@InjectMocks
	private UserServiceImpl userService;
	
	@Test(expected = UsernameRequiredException.class)
	public void createUserShouldThrowUsernameRequiredExceptionWhenNoUsernameIsNull() {
		User user = new User();
		user.setUsername(null);
		
		userService.createUser(user);
	}
	
	@Test(expected = UsernameRequiredException.class)
	public void createUserShouldThrowUsernameRequiredExceptionWhenNoUsernameIsEmpty() {
		User user = new User();
		user.setUsername("");
		
		userService.createUser(user);
	}
	
	@Test(expected = UsernameTakenException.class)
	public void createUserShouldThrowUsernameTakenExceptionWhenUsernameAlreadyTaken() {
		User user = new User();
		user.setUsername(EXISTING_USERNAME);
		user.setPassword(NEW_USER_PASSWORD);
		user.setEmail(NEW_USER_EMAIL);
		when(usersDao.findByUsername(EXISTING_USERNAME)).thenReturn(user);
		
		userService.createUser(user);
	}
	
	@Test(expected = PasswordRequiredException.class)
	public void createUserShouldThrowPasswordRequiredExceptionWhenPasswordIsNull() {
		User user = new User();
		user.setUsername(NEW_USERNAME);
		user.setPassword(null);
		
		userService.createUser(user);
	}
	
	@Test(expected = PasswordRequiredException.class)
	public void createUserShouldThrowPasswordRequiredExceptionWhenPasswordIsEmpty() {
		User user = new User();
		user.setUsername(NEW_USERNAME);
		user.setPassword("");
		
		userService.createUser(user);
	}
	
	@Test(expected = PasswordToShortException.class)
	public void createUserShouldThrowPasswordToShortExceptionWhenPasswordIsToShort() {
		User user = new User();
		user.setUsername(NEW_USERNAME);
		user.setPassword(TO_SHORT_PASSWORD);
		
		userService.createUser(user);
	}
	
	@Test(expected = EmailRequiredException.class)
	public void createUserShouldThrowEmailRequiredExceptionWhenEmailIsNull() {
		User user = new User();
		user.setUsername(NEW_USERNAME);
		user.setPassword(NEW_USER_PASSWORD);
		user.setEmail(null);
		
		userService.createUser(user);
	}
	
	@Test(expected = EmailRequiredException.class)
	public void createUserShouldThrowEmailRequiredExceptionWhenEmailIsEmpty() {
		User user = new User();
		user.setUsername(NEW_USERNAME);
		user.setPassword(NEW_USER_PASSWORD);
		user.setEmail("");
		
		userService.createUser(user);
	}
	
	@Test(expected = InvalidEmailException.class)
	public void createUserShouldThrowInvalidEmailExceptionWhenEmailIsInvalid() {
		User user = new User();
		user.setUsername(NEW_USERNAME);
		user.setPassword(NEW_USER_PASSWORD);
		user.setEmail(INVALID_EMAIL);
		
		try {
			userService.createUser(user);
		} catch (InvalidEmailException e) {
			assertThat(e.getMessage()).contains(INVALID_EMAIL);
			throw e;
		}
	}
	
	@Test
	public void createUserShouldCreateUserAndSetDefaultParameters() {
		User user = new User();
		user.setId(1);
		user.setUsername(NEW_USERNAME);
		user.setPassword(NEW_USER_PASSWORD);
		user.setEmail(NEW_USER_EMAIL);
		when(usersDao.getById(1)).thenReturn(user);
		
		User returnedUser = userService.createUser(user);
		
		verify(usersDao).save(user);
		assertThat(user.getUsername()).isEqualTo(NEW_USERNAME);
		assertThat(user.getEmail()).isEqualTo(NEW_USER_EMAIL);
		assertThat(user.getPassword()).isEqualTo(NEW_USER_PASSWORD);
		assertThat(user.getRoles().size()).isEqualTo(1);
		assertThat(user.getRoles()).contains(UserRole.USER);
		assertThat(user.isEnabled()).isTrue();
		assertThat(user.isCredentialsNonExpired()).isTrue();
		assertThat(user.isAccountNonExpired()).isTrue();
		assertThat(user.isAccountNonLocked()).isTrue();
		assertThat(returnedUser).isEqualTo(user);
	}
	
	@Test
	public void createUserShouldTrimEmailAddress() {
		User user = new User();
		user.setId(1);
		user.setUsername(NEW_USERNAME);
		user.setPassword(NEW_USER_PASSWORD);
		user.setEmail(" " + NEW_USER_EMAIL + " ");
		
		userService.createUser(user);
		
		assertThat(user.getEmail()).isEqualTo(NEW_USER_EMAIL);
	}
	
	@Test
	public void createUserShouldTrimUsername() {
		User user = new User();
		user.setId(1);
		user.setUsername(NEW_USERNAME);
		user.setPassword(" " + NEW_USER_PASSWORD + " ");
		user.setEmail(NEW_USER_EMAIL);
		
		userService.createUser(user);
		
		assertThat(user.getUsername()).isEqualTo(NEW_USERNAME);
	}
}
