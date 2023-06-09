package com.testgame.gseven.model.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.testgame.gseven.model.dto.Student;

public interface IStudentDAO extends MongoRepository<Student,String>{

	void deleteByEmail(String email);
	Student findByEmail(String email);
	Student findByConfirmationToken(String confirmationToken);
	Student findByPasswordToken(String passwordToken);
	boolean existsByEmail(String email);
	boolean existsByPasswordToken(String passwordToken);
	boolean existsByConfirmationToken(String confirmationToken);
}
