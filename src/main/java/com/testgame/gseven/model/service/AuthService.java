package com.testgame.gseven.model.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.testgame.gseven.model.dto.StudentDetails;
import com.testgame.gseven.model.service.interfaces.IAuthService;

@Service
public class AuthService implements IAuthService{

	@Override
	public String getStudentId() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(principal instanceof StudentDetails) {
			return ((StudentDetails)principal).getStudentId();
		}
		return null;
	}
}
