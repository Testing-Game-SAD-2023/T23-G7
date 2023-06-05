package com.reeverse.gseven.service;

import com.reeverse.gseven.dto.UserDto;
import com.reeverse.gseven.model.User;

public interface UserService {

	void saveUser(UserDto userDto);
	void enableUser(User user);
	User findUserByEmail(String email);
	User findByConfirmationToken(String token);
	User findByPasswordToken(String pswtoken);
}
	
