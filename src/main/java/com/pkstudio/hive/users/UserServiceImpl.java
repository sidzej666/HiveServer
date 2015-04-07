package com.pkstudio.hive.users;

import static com.pkstudio.hive.users.User.MAX_EMAIL_LENGTH;
import static com.pkstudio.hive.users.User.MAX_PASSWORD_LENGTH;
import static com.pkstudio.hive.users.User.MAX_USERNAME_LENGTH;
import static java.lang.String.format;
import static org.springframework.util.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import com.pkstudio.hive.exceptions.rest.FieldError;
import com.pkstudio.hive.exceptions.rest.ValidationException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private UsersDao usersDao;
	private EmailValidator emailValidator = EmailValidator.getInstance();
	
	@Inject
	public UserServiceImpl(UsersDao usersDao) {
		this.usersDao = usersDao;
	}
	
	@Override
	public User getById(int id) {
		return usersDao.getById(id);
	}

	@Override
	public User createUser(User user) {
		//TODO wrzucic validacje semantyczna i wstepne przygotowanie do klasy user??
		List<FieldError> validationErrors = new ArrayList<FieldError>();
		
		trimAndValidateUsername(user, validationErrors);
		validatePassword(user, validationErrors);
		trimAndValidateEmail(user, validationErrors);
		
		if (!validationErrors.isEmpty()) {
			throw new ValidationException(validationErrors);
		}
		
		checkUsernameAvailability(user, validationErrors);
		checkEmailAvailability(user, validationErrors);
		
		if (!validationErrors.isEmpty()) {
			throw new ValidationException(validationErrors);
		}
		
		setNewUserDefaultValues(user);
		
		usersDao.save(user);
		
		return usersDao.getById(user.getId());
	}

	private void checkEmailAvailability(User user, List<FieldError> validationErrors) {
		if (usersDao.findByEmail(user.getEmail()) != null) {
			validationErrors.add(new FieldError("email", String.format("email '%s' is already taken, choose another one", user.getEmail())));
		}
	}

	private void setNewUserDefaultValues(User user) {
		user.setEnabled(true);
		Set<UserAuthority> roles = new HashSet<UserAuthority>();
		UserAuthority userRole = UserRole.USER.asAuthorityFor(user);
		roles.add(userRole);
		user.setAuthorities(roles);
	}

	private void checkUsernameAvailability(User user, List<FieldError> validationErrors) {
		if (usersDao.findByUsername(user.getUsername()) != null) {
			validationErrors.add(new FieldError("username", String.format("username '%s' is already taken, choose another one", user.getUsername())));
		}
	}

	private void trimAndValidateEmail(User user, List<FieldError> validationErrors) {
		if (user.getEmail() != null) {
			user.setEmail(user.getEmail().trim());
		}
		if (isEmpty(user.getEmail())) {
			validationErrors.add(new FieldError("email", "email can't be empty"));
			return;
		}
		if (user.getEmail().length() > User.MAX_EMAIL_LENGTH) {
			validationErrors.add(new FieldError("email", String.format("email can't be longer than %s characters", MAX_EMAIL_LENGTH)));
			return;
		}
		if (!emailValidator.isValid(user.getEmail())) {
			validationErrors.add(new FieldError("email", "must be a valid email"));
			return;
		}
	}

	private void validatePassword(User user, List<FieldError> validationErrors) {
		if (isEmpty(user.getPassword())) {
			validationErrors.add(new FieldError("password", "password can't be empty"));
			return;
		}
		if (user.getPassword().length() < 5) {
			validationErrors.add(new FieldError("password", "password need to be at least 5 characters long"));
			return;
		}
		if (user.getPassword().length() > User.MAX_PASSWORD_LENGTH) {
			validationErrors.add(new FieldError("password", String.format("password can't be longer than %s characters", MAX_PASSWORD_LENGTH)));
			return;
		}
	}

	private void trimAndValidateUsername(User user, List<FieldError> validationErrors) {
		if (user.getUsername() != null) {
			user.setUsername(user.getUsername().trim());
		}
		if (isEmpty(user.getUsername())) {
			validationErrors.add(new FieldError("username", "username can't be empty"));
			return;
		}
		if (user.getUsername().length() > User.MAX_USERNAME_LENGTH) {
			validationErrors.add(new FieldError("username", format("username can't be longer than %s characters", MAX_USERNAME_LENGTH)));
			return;
		}
	}
}
