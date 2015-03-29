package com.pkstudio.hive.users;

import static org.springframework.util.StringUtils.isEmpty;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import com.pkstudio.hive.exceptions.EmailRequiredException;
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
		if (isEmpty(user.getUsername())) {
			throw new UsernameRequiredException();
		}
		
		if (isEmpty(user.getPassword())) {
			throw new PasswordRequiredException();
		}
		
		if (user.getPassword().length() < 5) {
			throw new PasswordToShortException();
		}
		
		if (isEmpty(user.getEmail())) {
			throw new EmailRequiredException();
		}
		
		user.setUsername(user.getUsername().trim());
		user.setEmail(user.getEmail().trim());
		
		if (!emailValidator.isValid(user.getEmail())) {
			throw new InvalidEmailException(user.getEmail());
		}
		
		if (usersDao.findByUsername(user.getUsername()) != null) {
			throw new UsernameTakenException();
		}

		user.setEnabled(true);
		Set<UserRole> roles = new HashSet<UserRole>();
		roles.add(UserRole.USER);
		user.setRoles(roles);
		
		usersDao.save(user);
		
		return usersDao.getById(user.getId());
	}
}
