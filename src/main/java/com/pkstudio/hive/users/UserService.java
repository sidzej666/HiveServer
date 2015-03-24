package com.pkstudio.hive.users;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostFilter;

public interface UserService {
	
	@Secured("ROLE_PLAYER")
	@PostFilter("returnObject.username == principal")
	public User getById(int id);
}
