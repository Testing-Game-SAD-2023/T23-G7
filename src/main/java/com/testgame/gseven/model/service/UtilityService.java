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
	 * passando lo studente da abilitare come parametro {@code String}. Esso verrà abilitato
	 * settando a {code true} il campo enabled, e dopodiché salvato sulla base dati.
	 * Uso tipico: dal controller, per ottenere lo studente si può chiamare getStudentByConfirmationToken()
	 * e dopodiché passarlo come parametro.
	 * {@code textbox}
	 * 
	 * @param student		parametro di tipo {@code Student} e rappresenta lo studente che deve essere
	 * 						abilitato.
	 * @return  			Non ritorna valori. Eventuali errori che possono presentarsi,
	 * 						sono eccezioni che devono che devono essere opportunamente gestite con try-catch.
	 * */
	@Override
	public void enableStudent(Student student) {
		student.setEnabled(true);
		student.setConfirmationToken(null);
		studentRepository.save(student);
	}
}
