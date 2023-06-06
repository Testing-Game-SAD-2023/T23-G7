package com.reeverse.gseven.model.service;

import com.reeverse.gseven.model.dto.Student;

public interface IStudentService {

	void saveStudent(Student student);
	void enableStudent(Student student);
	Student findUserByEmail(String email);
	Student findByConfirmationToken(String token);
	Student findByPasswordToken(String pswtoken);
}
	
