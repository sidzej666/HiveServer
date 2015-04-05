package com.pkstudio.hive.users;

import static com.pkstudio.hive.security.TokenAuthenticationService.AUTH_HEADER_NAME;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pkstudio.hive.security.TokenAuthenticationService;

@RestController
@RequestMapping(value = "/rest/users")
public class UserController {

	private final UserService userService;
	private final TokenAuthenticationService tokenAuthenticationService;
	
	@Inject
	public UserController(UserService userService, TokenAuthenticationService tokenAuthenticationService) {
		this.userService = userService;
		this.tokenAuthenticationService = tokenAuthenticationService;
	}
	
	@RequestMapping(value = "/{id}", method = GET)
	public User getUser(@PathVariable int id) {
		return userService.getById(id);
	}
	
	@RequestMapping(value = "/me", method = GET)
	public User getUser(@RequestHeader(AUTH_HEADER_NAME) String authToken) {
		User userFromToken = tokenAuthenticationService.getUserFromToken(authToken);
		User user = userService.getById(userFromToken.getId());
		return user;
	}
	
	@RequestMapping(method = POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public User createUser(HttpServletResponse response, @RequestBody User user) {
		User createdUser = userService.createUser(user);
		response.addHeader(HttpHeaders.LOCATION, linkTo(UserController.class).slash(createdUser.getId()).toUriComponentsBuilder().scheme("https").build().toString());
		return createdUser;
	}
}
