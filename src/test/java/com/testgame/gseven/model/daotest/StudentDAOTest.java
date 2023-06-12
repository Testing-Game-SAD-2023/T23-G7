package com.testgame.gseven.model.daotest;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.testgame.gseven.model.dao.IStudentDAO;

@SpringBootTest
public class StudentDAOTest {

	@MockBean
	private IStudentDAO studentRepository;
	
	@Test
	public void test() {
		Mockito.when(studentRepository.existsByEmail("emanueledimaioscuola@gmail.com")).thenReturn(true);
		assertEquals(studentRepository.existsByEmail("emanueledimaioscuola@gmail.com"), true);
	}
}
