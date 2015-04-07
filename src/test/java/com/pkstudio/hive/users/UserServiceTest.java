package com.pkstudio.hive.users;

import static com.pkstudio.hive.users.User.MAX_EMAIL_LENGTH;
import static com.pkstudio.hive.users.User.MAX_PASSWORD_LENGTH;
import static com.pkstudio.hive.users.User.MAX_USERNAME_LENGTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.pkstudio.commons.TestBase;
import com.pkstudio.hive.exceptions.rest.FieldError;
import com.pkstudio.hive.exceptions.rest.ValidationException;

public class UserServiceTest extends TestBase {
	
	private static final String ONLY_SPACES = "    ";
	private static final String EMPTY_STRING = "";
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
	
	@Test(expected = ValidationException.class)
	public void createUser_shouldThrowWhenUsernameIsNull() {
		User user = validNewUser();
		user.setUsername(null);
		
		try {
			userService.createUser(user);
		} catch (ValidationException e) {
			assertThat(e.getFieldErrors()).hasSize(1);
			containsUsernameCantBeEmptyFieldError(e.getFieldErrors());
			throw e;
		}
	}

	@Test(expected = ValidationException.class)
	public void createUser_shouldThrowWhenUsernameIsEmpty() {
		User user = validNewUser();
		user.setUsername(EMPTY_STRING);
		
		try {
			userService.createUser(user);
		} catch (ValidationException e) {
			assertThat(e.getFieldErrors()).hasSize(1);
			containsUsernameCantBeEmptyFieldError(e.getFieldErrors());
			throw e;
		}
	}
	
	@Test(expected = ValidationException.class)
	public void createUser_shouldThrowWhenUsernameContainsOnlySpaces() {
		User user = validNewUser();
		user.setUsername(ONLY_SPACES);
		
		try {
			userService.createUser(user);
		} catch (ValidationException e) {
			assertThat(e.getFieldErrors()).hasSize(1);
			containsUsernameCantBeEmptyFieldError(e.getFieldErrors());
			throw e;
		}
	}
	
	@Test(expected = ValidationException.class)
	public void createUser_shouldThrowWhenUsernameIsToLong() {
		User user = validNewUser();
		user.setUsername(toLongUsername());
		
		try {
			userService.createUser(user);
		} catch (ValidationException e) {
			assertThat(e.getFieldErrors()).hasSize(1);
			containsUsernameToLongFieldError(e.getFieldErrors());
			throw e;
		}
	}
	
	@Test
	public void createUser_shouldLetMaxLengthUsername() {
		User user = validNewUser();
		user.setUsername(maxLengthUsername());
		
		userService.createUser(user);
	}

	@Test(expected = ValidationException.class)
	public void createUser_shouldThrowAndContainAllFieldErrorsWhenAllDataIsEmpty() {
		User user = new User();
		user.setUsername(EMPTY_STRING);
		user.setEmail(EMPTY_STRING);
		user.setPassword(EMPTY_STRING);
		
		try {
			userService.createUser(user);
		} catch (ValidationException e) {
			assertThat(e.getFieldErrors()).hasSize(3);
			containsUsernameCantBeEmptyFieldError(e.getFieldErrors());
			containsEmailCantBeEmptyFieldError(e.getFieldErrors());
			containsPasswordCantBeEmptyFieldError(e.getFieldErrors());
			throw e;
		}
	}

	@Test(expected = ValidationException.class)
	public void createUser_shouldThrowWhenUsernameAlreadyTaken() {
		User user = validNewUser();
		user.setUsername(EXISTING_USERNAME);
		when(usersDao.findByUsername(EXISTING_USERNAME)).thenReturn(user);
		
		try {
			userService.createUser(user);
		} catch (ValidationException e) {
			assertThat(e.getFieldErrors()).hasSize(1);
			containsUsernameAlreadyTakenErrorField(e.getFieldErrors(), EXISTING_USERNAME);
			throw e;
		}
	}

	@Test(expected = ValidationException.class)
	public void createUser_shouldThrowVWhenPasswordIsNull() {
		User user = validNewUser();
		user.setPassword(null);
		
		try {
			userService.createUser(user);
		} catch (ValidationException e) {
			assertThat(e.getFieldErrors()).hasSize(1);
			containsPasswordCantBeEmptyFieldError(e.getFieldErrors());
			throw e;
		}
	}
	
	@Test(expected = ValidationException.class)
	public void createUser_shouldThrowWhenPasswordIsEmpty() {
		User user = validNewUser();
		user.setPassword(EMPTY_STRING);
		
		try {
			userService.createUser(user);
		} catch (ValidationException e) {
			assertThat(e.getFieldErrors()).hasSize(1);
			containsPasswordCantBeEmptyFieldError(e.getFieldErrors());
			throw e;
		}
	}
	
	@Test(expected = ValidationException.class)
	public void createUser_shouldThrowWhenPasswordIsToShort() {
		User user = validNewUser();
		user.setPassword(TO_SHORT_PASSWORD);
		
		try {
			userService.createUser(user);
		} catch (ValidationException e) {
			assertThat(e.getFieldErrors()).hasSize(1);
			containsPasswordToShortFieldError(e.getFieldErrors());
			throw e;
		}
	}
	
	@Test(expected = ValidationException.class)
	public void createUser_shouldThrowWhenPasswordIsToLong() {
		User user = validNewUser();
		user.setPassword(toLongPassword());
		
		try {
			userService.createUser(user);
		} catch (ValidationException e) {
			assertThat(e.getFieldErrors()).hasSize(1);
			containsPasswordToLongFieldError(e.getFieldErrors());
			throw e;
		}
	}
	
	@Test
	public void createUser_shoulLetMaxLengthPassword() {
		User user = validNewUser();
		user.setPassword(maxLengthPassword());
		
		userService.createUser(user);
	}
	
	@Test(expected = ValidationException.class)
	public void createUser_shouldThrowWhenEmailIsNull() {
		User user = validNewUser();
		user.setEmail(null);
		
		try {
			userService.createUser(user);
		} catch (ValidationException e) {
			assertThat(e.getFieldErrors()).hasSize(1);
			containsEmailCantBeEmptyFieldError(e.getFieldErrors());
			throw e;
		}
	}
	
	@Test(expected = ValidationException.class)
	public void createUser_shouldThrowWhenEmailIsEmpty() {
		User user = validNewUser();
		user.setEmail(EMPTY_STRING);
		
		try {
			userService.createUser(user);
		} catch (ValidationException e) {
			assertThat(e.getFieldErrors()).hasSize(1);
			containsEmailCantBeEmptyFieldError(e.getFieldErrors());
			throw e;
		}
	}
	
	@Test(expected = ValidationException.class)
	public void createUser_shouldThrowWhenEmailContainsOnlySpaces() {
		User user = validNewUser();
		user.setEmail(ONLY_SPACES);
		
		try {
			userService.createUser(user);
		} catch (ValidationException e) {
			assertThat(e.getFieldErrors()).hasSize(1);
			containsEmailCantBeEmptyFieldError(e.getFieldErrors());
			throw e;
		}
	}
	
	@Test(expected = ValidationException.class)
	public void createUser_shouldThrowWhenEmailIsInvalid() {
		User user = validNewUser();
		user.setEmail(INVALID_EMAIL);
		
		try {
			userService.createUser(user);
		} catch (ValidationException e) {
			assertThat(e.getFieldErrors()).hasSize(1);
			containsInvalidEmailErrorField(e.getFieldErrors());
			throw e;
		}
	}
	
	@Test(expected = ValidationException.class)
	public void createUser_shouldThrowWhenEmailIsToLong() {
		User user = validNewUser();
		user.setEmail(toLongEmail());
		
		try {
			userService.createUser(user);
		} catch (ValidationException e) {
			assertThat(e.getFieldErrors()).hasSize(1);
			containsToLongEmailErrorField(e.getFieldErrors());
			throw e;
		}
	}
	
	@Test(expected = ValidationException.class)
	public void createUser_shouldLetWhenEmailMaxLength() {
		User user = validNewUser();
		user.setEmail(maxLengthEmail());
		
		userService.createUser(user);
	}

	@Test
	public void createUser_shouldCreateUserAndSetDefaultParameters() {
		User user = validNewUser();
		user.setId(1);
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
	public void createUser_shouldTrimEmailAddress() {
		User user = validNewUser();
		user.setEmail(" " + NEW_USER_EMAIL + " ");
		
		userService.createUser(user);
		
		assertThat(user.getEmail()).isEqualTo(NEW_USER_EMAIL);
	}
	
	@Test
	public void createUser_shouldTrimUsername() {
		User user = validNewUser();
		user.setUsername(" " + NEW_USERNAME + " ");
		
		userService.createUser(user);
		
		assertThat(user.getUsername()).isEqualTo(NEW_USERNAME);
	}
	
	@Test(expected = ValidationException.class)
	public void createUser_shouldThrowWhenEmailAlreadyTaken() {
		User user = validNewUser();
		user.setEmail(EXISTING_EMAIL);
		when(usersDao.findByEmail(EXISTING_EMAIL)).thenReturn(user);		
		
		try {
			userService.createUser(user);
		} catch (ValidationException e) {
			assertThat(e.getFieldErrors()).hasSize(1);
			containsEmailAlreadyTakenErrorField(e.getFieldErrors(), EXISTING_EMAIL);
			throw e;
		}
	}

	@Test
	public void findById_shouldReturnExistingUser() {
		User user = validNewUser();
		when(usersDao.getById(EXISTING_USER_ID)).thenReturn(user);
		
		assertThat(userService.getById(EXISTING_USER_ID)).isEqualTo(user);
	}
	
	@Test
	public void findById_shouldReturnNullForNonexistentUser() {
		when(usersDao.getById(NONEXISTENT_USER_ID)).thenReturn(null);
		
		assertThat(userService.getById(NONEXISTENT_USER_ID)).isNull();
	}
	
	private void containsUsernameCantBeEmptyFieldError(List<FieldError> fieldErrors) {
		assertThat(fieldErrors.contains(new FieldError("username", "username can't be empty"))).isTrue();
	}
	
	private void containsUsernameToLongFieldError(List<FieldError> fieldErrors) {
		assertThat(fieldErrors.contains(
				new FieldError("username", String.format("username can't be longer than %s characters", MAX_USERNAME_LENGTH)))).isTrue();
	}
	
	private void containsUsernameAlreadyTakenErrorField(
			List<FieldError> fieldErrors, String username) {
		assertThat(fieldErrors.contains(
				new FieldError("username", String.format("username '%s' is already taken, choose another one", username)))).isTrue();
	}
	
	private void containsPasswordCantBeEmptyFieldError( List<FieldError> fieldErrors) {
		assertThat(fieldErrors.contains(new FieldError("password", "password can't be empty"))).isTrue();
	}
	
	private void containsPasswordToShortFieldError(List<FieldError> fieldErrors) {
		assertThat(fieldErrors.contains(new FieldError("password", "password need to be at least 5 characters long"))).isTrue();
	}
	
	private void containsPasswordToLongFieldError(List<FieldError> fieldErrors) {
		assertThat(fieldErrors.contains(
				new FieldError("password", String.format("password can't be longer than %s characters", MAX_PASSWORD_LENGTH)))).isTrue();
	}

	private void containsEmailCantBeEmptyFieldError(List<FieldError> fieldErrors) {
		assertThat(fieldErrors.contains(new FieldError("email", "email can't be empty"))).isTrue();
	}
	
	private void containsInvalidEmailErrorField(List<FieldError> fieldErrors) {
		assertThat(fieldErrors.contains(new FieldError("email", "must be a valid email"))).isTrue();
	}
	
	private void containsToLongEmailErrorField(List<FieldError> fieldErrors) {
		assertThat(fieldErrors.contains(
				new FieldError("email", String.format("email can't be longer than %s characters", MAX_EMAIL_LENGTH)))).isTrue();
	}
	
	private void containsEmailAlreadyTakenErrorField(
			List<FieldError> fieldErrors, String email) {
		assertThat(fieldErrors.contains(
				new FieldError("email", String.format("email '%s' is already taken, choose another one", email)))).isTrue();
	}
	
	private String toLongUsername() {
		return generateXLengthString(MAX_USERNAME_LENGTH + 1);
	}
	
	private String maxLengthUsername() {
		return generateXLengthString(MAX_USERNAME_LENGTH);
	}
	
	private String toLongPassword() {
		return generateXLengthString(MAX_PASSWORD_LENGTH + 1);
	}
	
	private String maxLengthPassword() {
		return generateXLengthString(MAX_PASSWORD_LENGTH);
	}
	
	private String toLongEmail() {
		return "a@" + generateXLengthString(MAX_EMAIL_LENGTH - 5) + ".com"; 
	}
	
	private String maxLengthEmail() {
		return "a@" + generateXLengthString(MAX_EMAIL_LENGTH - 6) + ".com";
	}

	private String generateXLengthString(int maxUsernameLength) {
		StringBuilder toLongUsername = new StringBuilder("");
		for (int i = 0; i < maxUsernameLength; i++) {
			toLongUsername.append("a");
		}
		return toLongUsername.toString();
	}
	
	private User validNewUser() {
		User user = new User();
		user.setUsername(NEW_USERNAME);
		user.setPassword(NEW_USER_PASSWORD);
		user.setEmail(NEW_USER_EMAIL);
		user.setId(0);
		return user;
	}
}
