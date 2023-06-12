package com.testgame.gseven.model.servicetest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailException;
import org.springframework.test.web.servlet.MockMvc;

import com.testgame.gseven.model.dao.IStudentDAO;
import com.testgame.gseven.model.dto.Role;
import com.testgame.gseven.model.dto.Student;
import com.testgame.gseven.model.service.RegistrationService;
import com.testgame.gseven.model.service.utils.FindInfoService;
import com.testgame.gseven.utility.exceptions.StudentAlreadyRegisteredException;


@SpringBootTest
public class RegistrationServiceTest {

	
	
	@MockBean
	private FindInfoService findInfoService;

	@MockBean
	private RegistrationService registrationService;
	@MockBean
	private IStudentDAO studentRepository;
	
	private Student student;
	 
	@BeforeEach                                          
    void setUp() {
		student = new Student("Emanuele", "Di Maio", "M", "Italian", "Nothing",
				"16-07-1999", "emanueledimaioscuola@gmail.com", null, false, "123", null);
    }
	
	@Test
	public void contextLoads() {
		assertThat(registrationService).isNotNull();
	}
	
	@Test
	public void registerNewStudentTest() {
		student.setName("Luca");
		student.setEmail("ludotcom1996@gmail.com");
		student.setPassword("123");
		try {
			doNothing().when(registrationService).registerStudent(student, "localhost:8080", "");
		} catch (MailException | StudentAlreadyRegisteredException e) {
			e.printStackTrace();
		}
	}
	
/*	@Test
	void registerAlreadyRegisteredStudentTest() {

		student.setName("Emanuele");
		student.setEmail("emanueledimaioscuola@gmail.com");
		student.setPassword("123");
		
		StudentAlreadyRegisteredException thrown = assertThrows(
				StudentAlreadyRegisteredException.class,
		           () -> registrationService.registerStudent(student, "localost:8080"),
		           "Expected registerStudent() to throw, but it didn't");

		    assertEquals(new StudentAlreadyRegisteredException(), thrown.getClass());
	}*/
	
	@Test
	void registerAlreadyRegisteredStudentTest2() {
		student = new Student("Emanuele", "Di Maio", "M", "Italian", "Nothing",
				"16-07-1999", "emanueledimaioscuola@gmail.com", null, false, "123", null);
		
		Mockito.when(findInfoService.isStudentRegistered(student.getEmail())).thenReturn(true);
		Mockito.when(studentRepository.existsByEmail(student.getEmail())).thenReturn(true);
        /*
		StudentAlreadyRegisteredException thrown = assertThrows(
				StudentAlreadyRegisteredException.class,

		assertThat(thrown.getClass()).isSameAs(StudentAlreadyRegisteredException.class);
        verify(studentRepository).findByEmail(student.getEmail());
	*/}
	
}
