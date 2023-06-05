package com.reeverse.gseven.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.reeverse.gseven.model.Student;

public interface UserRepository extends MongoRepository<Student,String>{

	Student findByEmail(String email);
	Student findByConfirmationToken(String confirmationToken);
	Student findByPasswordToken(String passwordToken);
}
