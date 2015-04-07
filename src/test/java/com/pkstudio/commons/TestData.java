package com.pkstudio.commons;

import java.util.HashSet;
import java.util.Set;

import com.pkstudio.hive.games.Game;
import com.pkstudio.hive.users.User;
import com.pkstudio.hive.users.UserAuthority;
import com.pkstudio.hive.users.UserRole;

public class TestData {
	private static final String TEST_USER_ONE_USERNAME = "usernameOne";
	private static final String TEST_USER_ONE_PASSWORD = "passwordOne";
	private static final String TEST_USER_ONE_EMAIL = "userOne@email.com";
	
	private static final String TEST_USER_TWO_USERNAME = "usernameTwo";
	private static final String TEST_USER_TWO_PASSWORD = "passwordTwo";
	private static final String TEST_USET_TWO_EMAIL = "userTwo@email.com";	
	
	private static final String TEST_GAME_ONE_NAME = "gameOne";
	private static final String TEST_GAME_ONE_PASSWORD = null;
	
	public final static User TEST_USER_ONE = createTestUser(TEST_USER_ONE_USERNAME, TEST_USER_ONE_PASSWORD, TEST_USER_ONE_EMAIL);
	public final static User TEST_USER_TWO = createTestUser(TEST_USER_TWO_USERNAME, TEST_USER_TWO_PASSWORD, TEST_USET_TWO_EMAIL);
	public final static Game TEST_GAME_ONE = createTestGame(TEST_GAME_ONE_NAME, TEST_GAME_ONE_PASSWORD, TEST_USER_ONE, null);
	
	public static User createTestUser(String username, String password, String email) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setEnabled(true);
		
		Set<UserAuthority> userAuthorities = new HashSet<UserAuthority>();
		UserAuthority userRole = UserRole.USER.asAuthorityFor(user);
		userAuthorities.add(userRole);
		
		user.setAuthorities(userAuthorities);
		
		return user;
	}

	public static Game createTestGame(String gameName, String password, User playerOne, User playerTwo) {
		Game game = new Game();
		game.setName(gameName);
		game.setPassword(password);
		game.setPlayerOne(playerOne);
		game.setPlayerTwo(playerTwo);

		return game;
	}
}
