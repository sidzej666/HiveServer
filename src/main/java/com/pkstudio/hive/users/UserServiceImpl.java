package com.pkstudio.hive.users;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private UsersDao usersDao;
	
	@Inject
	public UserServiceImpl(UsersDao usersDao) {
		this.usersDao = usersDao;
	}
	
	@Override
	public User getById(int id) {
		return usersDao.getById(id);
	}
}
