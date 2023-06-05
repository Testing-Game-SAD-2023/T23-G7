package com.reeverse.gseven.service;

import com.reeverse.gseven.dto.UserDto;
import com.reeverse.gseven.model.Student;

public interface UserService {

	void saveUser(UserDto userDto);
	void enableUser(Student user);
	Student findUserByEmail(String email);
	Student findByConfirmationToken(String token);
	Student findByPasswordToken(String pswtoken);
}
	
