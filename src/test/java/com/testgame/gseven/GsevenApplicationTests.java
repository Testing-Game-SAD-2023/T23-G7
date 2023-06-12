package com.testgame.gseven;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.testgame.gseven.controller.RegistrationController;

@SpringBootTest
class GsevenApplicationTests {

	@Autowired
	private RegistrationController registrationController;
	
	@Test
	void contextLoads() {
		assertThat(registrationController).isNotNull();
		
	}

}
