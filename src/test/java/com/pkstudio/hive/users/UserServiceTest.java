package com.pkstudio.hive.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.pkstudio.commons.TestBase;
import com.pkstudio.hive.exceptions.EmailRequiredException;
import com.pkstudio.hive.exceptions.EmailTakenException;
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
	private final String EXISTING_EMAIL = "existing@email.com";
	private final String INVALID_EMAIL = "invalid_email";
	private final int EXISTING_USER_ID = 1;
	private final int NONEXISTENT_USER_ID = 0;
	
	@Mock
	private UsersDao usersDao;
	
	@InjectMocks
	private UserServiceImpl userService;
	
	@Test(expected = UsernameRequiredException.class)
	public void createUser_ShouldThrowUsernameRequiredExceptionWhenUsernameIsNull() {
		User user = new User();
		user.setUsername(null);
		
		userService.createUser(user);
	}
	
	@Test(expected = UsernameRequiredException.class)
	public void createUser_ShouldThrowUsernameRequiredExceptionWhenUsernameIsEmpty() {
		User user = new User();
		user.setUsername("");
		
		userService.createUser(user);
	}
	
	@Test(expected = UsernameTakenException.class)
	public void createUser_ShouldThrowUsernameTakenExceptionWhenUsernameAlreadyTaken() {
		User user = new User();
		user.setUsername(EXISTING_USERNAME);
		user.setPassword(NEW_USER_PASSWORD);
		user.setEmail(NEW_USER_EMAIL);
		when(usersDao.findByUsername(EXISTING_USERNAME)).thenReturn(user);
		
		try {
			userService.createUser(user);
		} catch (UsernameTakenException e) {
			assertThat(e.getMessage().contains(EXISTING_USERNAME));
			throw e;
		}
	}
	
	@Test(expected = PasswordRequiredException.class)
	public void createUser_ShouldThrowPasswordRequiredExceptionWhenPasswordIsNull() {
		User user = new User();
		user.setUsername(NEW_USERNAME);
		user.setPassword(null);
		
		userService.createUser(user);
	}
	
	@Test(expected = PasswordRequiredException.class)
	public void createUser_ShouldThrowPasswordRequiredExceptionWhenPasswordIsEmpty() {
		User user = new User();
		user.setUsername(NEW_USERNAME);
		user.setPassword("");
		
		userService.createUser(user);
	}
	
	@Test(expected = PasswordToShortException.class)
	public void createUser_ShouldThrowPasswordToShortExceptionWhenPasswordIsToShort() {
		User user = new User();
		user.setUsername(NEW_USERNAME);
		user.setPassword(TO_SHORT_PASSWORD);
		
		userService.createUser(user);
	}
	
	@Test(expected = EmailRequiredException.class)
	public void createUser_ShouldThrowEmailRequiredExceptionWhenEmailIsNull() {
		User user = new User();
		user.setUsername(NEW_USERNAME);
		user.setPassword(NEW_USER_PASSWORD);
		user.setEmail(null);
		
		userService.createUser(user);
	}
	
	@Test(expected = EmailRequiredException.class)
	public void createUser_ShouldThrowEmailRequiredExceptionWhenEmailIsEmpty() {
		User user = new User();
		user.setUsername(NEW_USERNAME);
		user.setPassword(NEW_USER_PASSWORD);
		user.setEmail("");
		
		userService.createUser(user);
	}
	
	@Test(expected = InvalidEmailException.class)
	public void createUser_ShouldThrowInvalidEmailExceptionWhenEmailIsInvalid() {
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
	public void createUser_ShouldCreateUserAndSetDefaultParameters() {
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
	public void createUser_ShouldTrimEmailAddress() {
		User user = new User();
		user.setId(1);
		user.setUsername(NEW_USERNAME);
		user.setPassword(NEW_USER_PASSWORD);
		user.setEmail(" " + NEW_USER_EMAIL + " ");
		
		userService.createUser(user);
		
		assertThat(user.getEmail()).isEqualTo(NEW_USER_EMAIL);
	}
	
	@Test
	public void createUser_ShouldTrimUsername() {
		User user = new User();
		user.setId(1);
		user.setUsername(" " + NEW_USERNAME + " ");
		user.setPassword(NEW_USER_PASSWORD);
		user.setEmail(NEW_USER_EMAIL);
		
		userService.createUser(user);
		
		assertThat(user.getUsername()).isEqualTo(NEW_USERNAME);
	}
	
	@Test(expected = EmailTakenException.class)
	public void createUser_shouldThrowWhenEmailAlreadyTaken() {
		User user = new User();
		user.setUsername(NEW_USERNAME);
		user.setEmail(EXISTING_EMAIL);
		user.setPassword(NEW_USER_PASSWORD);
		when(usersDao.findByEmail(EXISTING_EMAIL)).thenReturn(user);		
		
		try {
			userService.createUser(user);
		} catch (EmailTakenException e) {
			assertThat(e.getMessage().contains(EXISTING_EMAIL));
			throw e;
		}
	}
	
	@Test
	public void findById_shouldReturnExistingUser() {
		User user = new User();
		when(usersDao.getById(EXISTING_USER_ID)).thenReturn(user);
		
		assertThat(userService.getById(EXISTING_USER_ID)).isEqualTo(user);
	}
	
	@Test
	public void findById_shouldReturnNullForNonexistentUser() {
		when(usersDao.getById(NONEXISTENT_USER_ID)).thenReturn(null);
		
		assertThat(userService.getById(NONEXISTENT_USER_ID)).isNull();
	}
}
