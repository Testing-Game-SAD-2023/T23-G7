package com.testgame.gseven.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.testgame.gseven.model.dao.IStudentDAO;
import com.testgame.gseven.model.dto.Student;
import com.testgame.gseven.model.service.interfaces.IUtilityService;
import com.testgame.gseven.utility.exceptions.ConfirmationTokenNotFoundException;

@Service
public class UtilityService implements IUtilityService {


	@Autowired
	private FindInfoService findInfoService;
	
	@Autowired
	private IStudentDAO studentRepository;

	/** Metodo che permette di abilitare il nuovo studente registrato. L'abilitazione viene effettuata
	 * cercando nella base dati il token confirmationToken, di tipo {@code String}, e abilitare
	 * (cambiare il valore del campi enable da {@code false} a {@code true}) lo studente associato
	 * a quel token.
	 * Dal controller, il token lo si può acquisire tramite l'URL o inserito tramite un apposito
	 * {@code textbox}
	 * 
	 * @param confirmationToken	parametro di tipo {@code String} che funge da chiave di ricerca
	 * 							per selezionare lo studente da abilitare.
	 * @return  				Non ritorna valori. Eventuali errori che possono presentarsi,
	 * 							sono eccezioni che devono che devono essere opportunamente gestite con try-catch.
	 * @throws ConfirmationTokenNotFoundException eccezione restituita quando il token di conferma
	 * 											 dell'identità non esiste nella base dati. 
	 * */
	@Override
	public void enableStudent(String confirmationToken) throws ConfirmationTokenNotFoundException {
		boolean isConfirmationTokenFound = findInfoService.doesConfirmationTokenExists(confirmationToken);
		if(!isConfirmationTokenFound) {
			throw new ConfirmationTokenNotFoundException();
		}
		
		Student student = studentRepository.findByConfirmationToken(confirmationToken);
		student.setEnabled(true);
		student.setConfirmationToken(null);
		studentRepository.save(student);		
	}
}
