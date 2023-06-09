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
	 * @return Ritorna un valore di tipo {@code String} che rappresetna l'ID univoco associato 
	 * 			all'utente autenticato nella sessione corrente, null se non è autenticato.
	 */
	@Override
	public String getStudentId() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(principal instanceof StudentDetails) {
			return ((StudentDetails)principal).getStudentId();
		}
		return null;
	}
	
	/** Metodo che restituisce {@code true} se nella sessione corrente
	 * uno studente è autenticato. Se non è autenticato restituisce {@code false}.
	 * @param authentication	istanza di tipo {@code Authentication} di Spring Security
	 * 							che permette di ricavare le informazioni della sessione corrente.
	 * @return	Se nella sessione corrente, uno studente è loggato, allora ritorna {@code true}, altrimenti
	 * 			{@code false}.
	 */
	@Override
	public boolean isStudentAuthenticated(Authentication authentication) {
		authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
	        return false;
	    }
	    return true;
	}
}
