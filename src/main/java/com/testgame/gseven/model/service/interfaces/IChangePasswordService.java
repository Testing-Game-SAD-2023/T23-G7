package com.testgame.gseven.model.service.interfaces;

import com.testgame.gseven.utility.exceptions.PasswordTokenNotFoundException;
import com.testgame.gseven.utility.exceptions.StudentNotFoundException;

public interface IChangePasswordService {
	public void sendEmailChangePassword(String email, String URLpath) throws StudentNotFoundException;
	public void changePassword(String passwordToken, String newPassword) throws PasswordTokenNotFoundException ;
}
