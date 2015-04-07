package com.pkstudio.hive.users;

import static com.pkstudio.commons.TestData.TEST_USER_ONE;
import static com.pkstudio.commons.TestData.TEST_USER_TWO;
import static com.pkstudio.commons.TestData.createTestUser;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;

import com.pkstudio.commons.DatabaseTest;

public class UsersDaoTest extends DatabaseTest {
	@Inject
	private UsersDao usersDao;
	
	@Test
	public void getById_shouldGet() {
		User user = usersDao.getById(TEST_USER_ONE.getId());
		
		assertThat(user.getUsername()).isEqualTo(TEST_USER_ONE.getUsername());
		assertThat(user.getRoles()).isEqualTo(TEST_USER_ONE.getRoles());
	}
	
	@Test
	public void getById_shouldReturnNull() {
		User user = usersDao.getById(TEST_USER_TWO.getId() + 1);
		assertThat(user).isNull();
	}
	
	@Test
	public void getAll_shouldReturn() {
		List<User> users = usersDao.getAll();
		assertThat(users).containsOnly(TEST_USER_ONE, TEST_USER_TWO);
	}
	
	@Test
	public void deleteById_shouldDelete() {
		usersDao.deleteById(TEST_USER_TWO.getId());
		assertThat(usersDao.getById(TEST_USER_TWO.getId())).isNull();
	}
	
	@Test
	public void findByUsername_shouldFind() {
		User user = usersDao.findByUsername(TEST_USER_ONE.getUsername());
		assertThat(user).isEqualTo(TEST_USER_ONE);
	}
	
	@Test
	public void findByUsername_shouldReturnNullForNonExistentUser() {
		User user = usersDao.findByUsername("NON_EXISTENT_USERNAME");
		assertThat(user).isNull();
	}
	
	@Test
	public void findByEmail_shouldReturn() {
		User user = usersDao.findByEmail(TEST_USER_ONE.getEmail());
		assertThat(user).isEqualTo(TEST_USER_ONE);
	}
	
	@Test
	public void findByEmail_shouldReturnNullForNonExistentEmail() {
		User user = usersDao.findByEmail("NON_EXISTENT_EMAIL");
		assertThat(user).isNull();
	}
	
	@Test
	public void createUser_shouldCreate() {
		User user = createTestUser("sample username", "password", "valid@email.com.com");
		
		usersDao.save(user);
		
		assertThat(user.getId()).isEqualTo(TEST_USER_TWO.getId() + 1);
		assertThat(usersDao.getById(user.getId())).isEqualTo(user);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void createUser_shouldThrowForUsernameConstraintViolation() {
		User user = createTestUser(TEST_USER_ONE.getUsername(), "password", "valid@email.com.com");
		usersDao.save(user);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void createUser_shouldThrowForEmailConstraintViolation() {
		User user = createTestUser("sample username", "password", TEST_USER_ONE.getEmail());
		usersDao.save(user);
	}
}
