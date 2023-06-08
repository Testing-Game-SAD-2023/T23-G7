package com.testgame.gseven.model.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.testgame.gseven.model.dao.IStudentDAO;
import com.testgame.gseven.model.dto.Student;
import com.testgame.gseven.model.service.interfaces.IChangePasswordService;
import com.testgame.gseven.utility.exceptions.PasswordTokenNotFoundException;
import com.testgame.gseven.utility.exceptions.StudentNotFoundException;
@Service
public class ChangePasswordService implements IChangePasswordService{
	@Autowired
	private FindInfoService findInfoService;
	
	@Autowired
	private IStudentDAO studentRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	
	/** Metodo che permette di cominciare la procedura di cambio password. In particolare, viene
	 * generato un token di una pagina univoca, chiamato token password. Il token viene salvato
	 * sulla base dati e inviata tramite email allo studente.
	 * @param email parametro di tipo {@code String} che permette di cercare sulla base dati lo studente e inviare il token
	 * 				da cliccare.
	 * @return		Non ritorna valori. Eventuali errori che possono presentarsi,
	 * 				sono eccezioni che devono che devono essere opportunamente gestite con try-catch.
	 * @throws StudentNotFoundException eccezione restituita quando l'email fornita in ingresso	
	 *									non corrisponde a nessuno studente nella base dati. 
	 * */
	@Override
	public void sendEmailChangePassword(String email, String URLpath) throws StudentNotFoundException {
		String passwordToken = UUID.randomUUID().toString();
		
		boolean isRegistered = findInfoService.isStudentRegistered(email);
		if(!isRegistered) {
			throw new StudentNotFoundException();
		}
		
		Student student = studentRepository.findByEmail(email);
		student.setPasswordToken(passwordToken);
		studentRepository.save(student);
		emailService.sendEmailResetPassword(email, "localhost:8080", URLpath ,passwordToken);
	}
	
	/** Metodo che permette di effettuare il salvataggio della nuova password sulla base dati.
	 * In particolare, viene fornito il  token della password, preso dall'URL della pagina univoca e la
	 * nuova password in chiaro (NON cifrata). Alla fine del cambio, il token viene cancellato.
	 * 
	 * @param passwordToken 	parametro di tipo {@code String} che funge da chiave di ricerca
	 * 							per selezionare lo studente che ha richiesto il cambio.
	 * @param newPassword 		parametro di tipo {@code String}, ed è la nuova password scritta
	 * 							in chiaro (NON cifrata) da salvare nella base dati allo studente
	 * 							che ha richiesto il cambio.
	 * @return  				Non ritorna valori. Eventuali errori che possono presentarsi,
	 * 							sono eccezioni che devono che devono essere opportunamente gestite con try-catch.
	 * @throws PasswordTokenNotFoundException eccezione restituita quando il token della password fornito in
	 * 											input non esiste nella base dati		
	 * 
	 * */
	@Override
	public void changePassword(String passwordToken, String newPassword) throws PasswordTokenNotFoundException {
		
		boolean isPasswordTokenFound = findInfoService.doesPasswordTokenExists(passwordToken);
		if(!isPasswordTokenFound) {
			throw new PasswordTokenNotFoundException();
		}
		
		Student student = studentRepository.findByPasswordToken(passwordToken);
		student.setPassword(passwordEncoder.encode(newPassword));
		student.setPasswordToken(null);
		studentRepository.save(student);
	}
}
