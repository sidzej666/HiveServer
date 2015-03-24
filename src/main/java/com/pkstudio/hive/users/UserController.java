package com.pkstudio.hive.users;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/users")
public class UserController {

	private final UserService userService;
	
	@Inject
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(value = "/{id}", method = GET)
	public User getUser(@PathVariable int id) {
		return userService.getById(id);
	}
}
