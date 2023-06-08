package com.testgame.gseven.model.service.interfaces;



import com.testgame.gseven.utility.exceptions.ConfirmationTokenNotFoundException;


public interface IUtilityService {

	void enableStudent(String confirmationToken) throws ConfirmationTokenNotFoundException;

}
	
