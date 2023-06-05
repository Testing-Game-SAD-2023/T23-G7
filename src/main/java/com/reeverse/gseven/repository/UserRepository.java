package com.reeverse.gseven.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.reeverse.gseven.model.User;

public interface UserRepository extends MongoRepository<User,String>{

	User findByEmail(String email);
	User findByConfirmationToken(String confirmationToken);
	User findByPasswordToken(String passwordToken);
}
