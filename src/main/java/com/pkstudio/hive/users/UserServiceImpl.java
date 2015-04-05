package com.pkstudio.hive.users;

import static org.springframework.util.StringUtils.isEmpty;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import com.pkstudio.hive.exceptions.EmailRequiredException;
import com.pkstudio.hive.exceptions.EmailTakenException;
import com.pkstudio.hive.exceptions.InvalidEmailException;
import com.pkstudio.hive.exceptions.PasswordRequiredException;
import com.pkstudio.hive.exceptions.PasswordToShortException;
import com.pkstudio.hive.exceptions.UsernameRequiredException;
import com.pkstudio.hive.exceptions.UsernameTakenException;

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
		
		validateAndTrimUsername(user);
		validatePassword(user);
		validateAndTrimEmail(user);
		
		checkUsernameAvailability(user);
		checkEmailAvailability(user);
		setNewUserDefaultValues(user);
		
		usersDao.save(user);
		
		return usersDao.getById(user.getId());
	}

	private void checkEmailAvailability(User user) {
		if (usersDao.findByEmail(user.getEmail()) != null) {
			throw new EmailTakenException(user.getEmail());
		}
	}

	private void setNewUserDefaultValues(User user) {
		user.setEnabled(true);
		Set<UserAuthority> roles = new HashSet<UserAuthority>();
		UserAuthority userRole = UserRole.USER.asAuthorityFor(user);
		roles.add(userRole);
		user.setAuthorities(roles);
	}

	private void checkUsernameAvailability(User user) {
		if (usersDao.findByUsername(user.getUsername()) != null) {
			throw new UsernameTakenException(user.getUsername());
		}
	}

	private void validateAndTrimEmail(User user) {
		if (isEmpty(user.getEmail())) {
			throw new EmailRequiredException();
		}
		
		user.setEmail(user.getEmail().trim());
		
		if (!emailValidator.isValid(user.getEmail())) {
			throw new InvalidEmailException(user.getEmail());
		}
	}

	private void validatePassword(User user) {
		if (isEmpty(user.getPassword())) {
			throw new PasswordRequiredException();
		}
		
		if (user.getPassword().length() < 5) {
			throw new PasswordToShortException();
		}
	}

	private void validateAndTrimUsername(User user) {
		if (isEmpty(user.getUsername())) {
			throw new UsernameRequiredException();
		}
		user.setUsername(user.getUsername().trim());
	}
}
