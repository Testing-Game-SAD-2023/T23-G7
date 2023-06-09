package com.testgame.gseven.model.service.interfaces;

import com.testgame.gseven.utility.exceptions.PasswordTokenNotFoundException;
import com.testgame.gseven.utility.exceptions.StudentNotEnabledException;
import com.testgame.gseven.utility.exceptions.StudentNotFoundException;

public interface IChangePasswordService {
	public void beginChangePassword(String email,  String URLsite, String URLpath) throws StudentNotFoundException, StudentNotEnabledException;
	public void changePassword(String passwordToken, String newPassword) throws PasswordTokenNotFoundException ;
}
