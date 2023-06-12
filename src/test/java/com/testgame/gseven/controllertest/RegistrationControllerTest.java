package com.testgame.gseven.controllertest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailException;
import org.springframework.validation.BindingResult;

import com.testgame.gseven.controller.RegistrationController;
import com.testgame.gseven.model.dto.Student;
import com.testgame.gseven.model.service.RegistrationService;
import com.testgame.gseven.utility.exceptions.StudentAlreadyRegisteredException;

public class RegistrationControllerTest {

	
	
	private RegistrationController registrationController;
	
	
	private Student student;
	private BindingResult result;
	
	@BeforeEach                                          
    void setUp() {
		student = new Student();
		registrationController = new RegistrationController();
    }
	
	@Test
	public void contextLoads() {
		assertThat(registrationController).isNotNull();
	}

	
	@Test
	void registerNewStudentControllerTest() {

		student.setName("Emanuele");
		student.setEmail("emanueledimaioscuola@gmail.com");
		student.setPassword("123");
		
		
/*		StudentAlreadyRegisteredException thrown = assertThrows(
				StudentAlreadyRegisteredException.class,
		           () -> registrationService.registerStudent(student, "localost:8080"),
		           "Expected registerStudent() to throw, but it didn't");

		    assert(thrown.getMessage().contains("The student with this email is already registered."));
*/	}
}
