package com.testgame.gseven.model.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.testgame.gseven.model.dto.StudentDetails;
import com.testgame.gseven.model.service.interfaces.IAuthService;

@Service
public class AuthService implements IAuthService{
	
	/** Metodo che permette di ricavare l'ID dello studente registrato 
	 * @return Il metodo ritorna un valore di tipo {@code String} che rappresetna l'ID univoco associato 
	 * all'utente registrato, null altrimenti.
	 * 
	 */
	@Override
	public String getStudentId() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(principal instanceof StudentDetails) {
			return ((StudentDetails)principal).getStudentId();
		}
		return null;
	}
	
	@Override
	public boolean isStudentAuthenticated(Authentication authentication) {
		authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
	        return false;
	    }
	    return true;
	}
}
