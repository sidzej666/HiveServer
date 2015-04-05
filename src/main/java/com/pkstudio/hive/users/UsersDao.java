package com.pkstudio.hive.users;

import com.pkstudio.generic.dao.GenericDao;

public interface UsersDao extends GenericDao<User> {
	User findByUsername(String username);
	User findByEmail(String email);
}
