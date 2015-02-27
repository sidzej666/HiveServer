package com.pkstudio.hive.jbehave;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

@Steps
public class WelcomePageSteps {
	
	private String name;
	
	@When("I enter the welcome page providing <name> as a name")
	public void welcomePage(@Named("name") String name) {
		this.name = name;
	}
	
	@Then("the message should be <message>")
	public void theMessageShouldBe(@Named("message") String message) {
		assertThat("Hello aaa" + name, is(message));
	}
}
