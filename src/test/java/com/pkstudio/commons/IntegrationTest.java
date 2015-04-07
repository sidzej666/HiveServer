package com.pkstudio.commons;

import static com.pkstudio.hive.security.TokenAuthenticationService.AUTH_HEADER_NAME;
import static com.pkstudio.hive.security.TokenAuthenticationService.TEN_DAYS;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.Date;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.pkstudio.hive.security.TokenAuthenticationService;
import com.pkstudio.hive.security.TokenHandler;
import com.pkstudio.hive.users.User;

@WebAppConfiguration("classpath:**/mvc-dispatcher-servlet.xml")
@Transactional
public class IntegrationTest extends DatabaseTest {

	@Inject
	private WebApplicationContext webAppContext;
	
	@Inject
	private FilterChainProxy springSecurityFilterChain;
	
	@Inject
	private TokenHandler tokenHandler;
	
	private static MockMvc mvc;
    
    public MockMvc mvc() {
    	
    	if (mvc == null) {
    		mvc = MockMvcBuilders
                    .webAppContextSetup(webAppContext)
                    .addFilter(springSecurityFilterChain)
                    .alwaysExpect(content().contentType("application/json;charset=UTF-8"))
                    .build();
    	}
    	return mvc;
    }
    
    protected HttpHeaders authenticationHeaders(int userId, String username) {
    	HttpHeaders httpHeaders = new HttpHeaders();
    	httpHeaders.add(AUTH_HEADER_NAME,
    			tokenHandler.createTokenForUser(new User(userId, username, tenDaysAfterNow())));
    	return httpHeaders;
    }
    
    protected HttpHeaders authenticationHeaders(User user) {
    	return authenticationHeaders(user.getId(), user.getUsername());
    }
    
    private Date tenDaysAfterNow() {
    	Date date = new Date();
    	long tenDaysAfter = date.getTime() + TEN_DAYS;
    	return new Date(tenDaysAfter);
    }
}
