package com.pkstudio.commons;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/message-source.xml"})
public class TestBase {
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
}
