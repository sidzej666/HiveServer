package com.pkstudio.commons;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.pkstudio.hive.games.GamesDao;
import com.pkstudio.hive.users.UsersDao;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath:spring-context.xml"})
@ActiveProfiles("test")
@Transactional
public abstract class DatabaseTest {
	
	@Inject
	private UsersDao usersDao;
	@Inject
	private GamesDao gameDao;
	
	private static boolean isInitialized = false;
	
	@Before
	public void createDatabase() {
		if (!isInitialized) {
			usersDao.save(TestData.TEST_USER_ONE);
			usersDao.save(TestData.TEST_USER_TWO);
			gameDao.save(TestData.TEST_GAME_ONE);
		}
		//isInitialized = true;
	}
}
