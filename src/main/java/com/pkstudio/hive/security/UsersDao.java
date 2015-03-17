package com.pkstudio.hive.security;

import com.pkstudio.generic.dao.GenericDao;

public interface UsersDao extends GenericDao<User> {
	User findByUsername(String username);
}
