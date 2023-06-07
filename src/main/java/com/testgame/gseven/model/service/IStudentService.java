package com.testgame.gseven.model.service;


import com.testgame.gseven.model.dto.Student;
import com.testgame.gseven.utility.exceptions.ConfirmationTokenNotFoundException;
import com.testgame.gseven.utility.exceptions.PasswordTokenNotFoundException;
import com.testgame.gseven.utility.exceptions.StudentAlreadyRegisteredException;
import com.testgame.gseven.utility.exceptions.StudentNotFoundException;

public interface IStudentService {

	void registerStudent(Student student) throws StudentAlreadyRegisteredException;
	void changePassword(String email, String newPassword) throws PasswordTokenNotFoundException;
	void enableStudent(String confirmationToken) throws ConfirmationTokenNotFoundException;
	void sendEmailChangePassword(String email) throws StudentNotFoundException;
}
	
