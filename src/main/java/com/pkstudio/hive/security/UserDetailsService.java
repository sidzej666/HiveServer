package com.pkstudio.hive.security;

import javax.inject.Inject;

import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.pkstudio.hive.users.User;
import com.pkstudio.hive.users.UsersDao;

@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	
	@Inject
	private UsersDao userRepo;

	private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

	@Override
	public final User loadUserByUsername(String username) throws UsernameNotFoundException {
		final User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("user not found");
		}
		detailsChecker.check(user);
		return user;
	}
}
