package com.testgame.gseven.model.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.testgame.gseven.model.dao.IStudentDAO;
import com.testgame.gseven.model.dto.Student;
import com.testgame.gseven.model.service.interfaces.IChangePasswordService;
import com.testgame.gseven.model.service.utils.EmailService;
import com.testgame.gseven.model.service.utils.FindInfoService;
import com.testgame.gseven.utility.exceptions.PasswordTokenNotFoundException;
import com.testgame.gseven.utility.exceptions.StudentNotEnabledException;
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
	
	
	/** Metodo che permette di cominciare la procedura di cambio password. In particolare, controlla prima
	 * che lo studente sia registrato e che si abilitato, e se vengono passati con successo genera un
	 * token di una pagina univoca, chiamato token password. Il token viene salvato sulla base dati
	 * e inviata tramite email allo studente. Se i controlli falliscono, restituisce le eccezioni descritte.
	 * @param email parametro di tipo {@code String} che permette di cercare sulla base dati lo studente e inviare il token
	 * 				da cliccare.
	 * @param		URLweb url del sito su cui utilizzare il servizio (es. http://www.miosito.it)
	 * @param		URLpath	url del percorso che verrà concatenato con URLsite per creare la pagina di procedura di cambio password. (es.  "/changePasswordProcess/").
	 * @return		Non ritorna valori. In caso di errori, restituisce le eccezioni che devono
	 * 				che devono essere opportunamente gestite con try-catch.
	 * @throws StudentNotFoundException eccezione restituita quando l'email fornita in ingresso	
	 *									non corrisponde a nessuno studente nella base dati. 
	 * @throws StudentNotEnabledException eccezione resistuita quando lo studente trovato con l'email fornita
	 * 										in ingresso non è abilitato.
	 * */
	@Override
	public void beginChangePassword(String email, String URLweb, String URLpath) throws StudentNotFoundException, StudentNotEnabledException {
		
		
		boolean isRegistered = findInfoService.isStudentRegistered(email);
		
		if(!isRegistered) {
			throw new StudentNotFoundException();
		}
		
		Student student = studentRepository.findByEmail(email);
		
		if(!student.isEnabled()) {
			throw new StudentNotEnabledException();
		}
		
		String passwordToken = UUID.randomUUID().toString();
		student.setPasswordToken(passwordToken);
		studentRepository.save(student);
		emailService.sendEmailResetPassword(email, URLweb, URLpath, passwordToken);
	}
	
	/** Metodo che permette di effettuare il salvataggio della nuova password sulla base dati.
	 * In particolare, viene fornito il  token della password, preso dall'URL della pagina univoca e la
	 * nuova password in chiaro (NON cifrata). Alla fine del cambio, il token viene cancellato. Se il
	 * token della password non viene trovato, verrà restituita una eccezione.
	 * 
	 * @param passwordToken 	parametro di tipo {@code String} che funge da chiave di ricerca
	 * 							per selezionare lo studente che ha richiesto il cambio.
	 * @param newPassword 		parametro di tipo {@code String}, ed è la nuova password scritta
	 * 							in chiaro (NON cifrata) da salvare nella base dati allo studente
	 * 							che ha richiesto il cambio.
	 * @return  				Non ritorna valori. In caso di errori, restituisce le eccezioni
	 * 							che devono che devono essere opportunamente gestite con try-catch.
	 * @throws PasswordTokenNotFoundException eccezione restituita quando il token della password fornito in
	 * 											input non viene trovato nella base dati.
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
