package com.testgame.gseven.model.service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testgame.gseven.model.dto.Student;
import com.testgame.gseven.model.service.interfaces.IConfirmationService;
import com.testgame.gseven.utility.exceptions.ConfirmationTokenNotFoundException;

@Service
public class ConfirmationService implements IConfirmationService{
	
	@Autowired
	private UtilityService utilityService;
	
	@Autowired
	private FindInfoService findInfoService;
	
	@Override
	public void confirmStudentByToken(String token) throws ConfirmationTokenNotFoundException{
		Student student = findInfoService.getStudentByConfirmationToken(token);
		if(student == null) {
			throw new ConfirmationTokenNotFoundException();
		}
		
	    if (student != null) {
	    	utilityService.enableStudent(student);
	    }
	}
}
