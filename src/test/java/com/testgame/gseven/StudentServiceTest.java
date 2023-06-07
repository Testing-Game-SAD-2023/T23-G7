package com.testgame.gseven;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.testgame.gseven.model.service.StudentService;

@SpringBootApplication
public class StudentServiceTest {

	@MockBean
	private StudentService studentService;
	
	@Test
	void StudentServiceTest1() {
		
	}
}
