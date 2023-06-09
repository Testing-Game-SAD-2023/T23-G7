package com.testgame.gseven.model.service.interfaces;

import org.springframework.security.core.Authentication;

public interface IAuthService {
	public String getStudentId();
	public boolean isStudentAuthenticated(Authentication authentication);
}
