package com.pkstudio.hive.users;

import javax.transaction.Transactional;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;

public interface UserService {
	
	@Secured("USER")
	@PostAuthorize("returnObject == null or returnObject.username == principal")
	public User getById(int id);
	
	@Transactional
	public User createUser(User user);
}
