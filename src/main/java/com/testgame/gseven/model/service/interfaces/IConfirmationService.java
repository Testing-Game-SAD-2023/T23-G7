package com.testgame.gseven.model.service.interfaces;

import com.testgame.gseven.utility.exceptions.ConfirmationTokenNotFoundException;

public interface IConfirmationService {

	void confirmStudentByToken(String token) throws ConfirmationTokenNotFoundException;

}
