package com.pkstudio.hive.users;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;

public interface UserService {
	
	@Secured("ROLE_PLAYER")
	@PostAuthorize("returnObject.username == principal")
	public User getById(int id);
}
