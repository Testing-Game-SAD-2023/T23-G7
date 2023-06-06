package com.reeverse.gseven.model.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.reeverse.gseven.model.dto.Student;

public interface IStudentDAO extends MongoRepository<Student,String>{

	Student findByEmail(String email);
	Student findByConfirmationToken(String confirmationToken);
	Student findByPasswordToken(String passwordToken);
}
