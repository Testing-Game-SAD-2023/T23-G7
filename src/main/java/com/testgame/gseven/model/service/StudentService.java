package com.testgame.gseven.model.service;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.testgame.gseven.model.dao.IRoleDAO;
import com.testgame.gseven.model.dao.IStudentDAO;
import com.testgame.gseven.model.dto.Role;
import com.testgame.gseven.model.dto.Student;
import com.testgame.gseven.utility.exceptions.ConfirmationTokenNotFoundException;
import com.testgame.gseven.utility.exceptions.PasswordTokenNotFoundException;
import com.testgame.gseven.utility.exceptions.StudentAlreadyRegisteredException;
import com.testgame.gseven.utility.exceptions.StudentNotFoundException;

@Service
public class StudentService implements IStudentService {


	@Autowired
	private FindInfoService findInfoService;
	
	@Autowired
	private IStudentDAO studentRepository;
	
	@Autowired
	private IRoleDAO roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	/** Metodo che permette di salvare lo studente sulla base dati. Come parametro di ingresso deve
	 * essere passato un oggetto di tipo {@code Student} con tutti i campi popolati escluso l'{@code id}, che
	 * verrà creato e associato allo studente automaticamente, operazione che viene svolta dalla base dati.
	 * Inoltre viene generato un token di conferma dell'identità, che viene inviato tramite email allo studente
	 * appena registrato al fine di verificare la sua identità.
	 * L'oggetto {@studentForm} può essere popolato da un apposito form.
	 * @param studentForm 	oggetto di tipo {@code Student} che deve contenere i campi pieni, al fine di salvare lo
	 * 						studente sulla base dati
	 * @return 				Non ritorna valori. Eventuali errori che possono presentarsi,
	 * 						sono eccezioni che devono che devono essere opportunamente gestite con try-catch.
	 * @throws StudentAlreadyRegisteredException si sta tentato di salvare nella base dati uno studente
	 * 											con una email già registrata.
	 * @throws MailParseException
	 * @throws MailAuthenticationException
	 * @throws MailSendException
	 * @throws MailException
	 * */
	@Override
	public void registerStudent(Student studentForm) throws MailParseException, MailAuthenticationException,
														MailSendException, MailException, StudentAlreadyRegisteredException{
		
		boolean isRegistered = findInfoService.isStudentRegistered(studentForm.getEmail());
		if(isRegistered) {
			throw new StudentAlreadyRegisteredException();
		}
		
		String confirmationToken = UUID.randomUUID().toString();
		Role studentRole = roleRepository.findByName("STUDENTE");
        
		if(studentRole == null)
			studentRole = roleRepository.save(new Role("STUDENTE"));
		
		Student student = new Student(studentForm.getName(),
									studentForm.getSurname(),
									studentForm.getGender(),
									studentForm.getNationality(),
									studentForm.getStudyTitle(),
									studentForm.getDateOfBirth(),
									studentForm.getEmail(),
									confirmationToken,
									false,
									passwordEncoder.encode(studentForm.getPassword()),
									Arrays.asList(studentRole));
		
		try {
			studentRepository.save(student);
		} catch(Exception ex) {
			throw ex;
		}
		
		try {
			emailService.sendVerificationEmail(studentForm.getEmail(), "localhost:8080", confirmationToken);
		}catch(Exception ex){
			throw ex;
		}
	}
	
	/** Metodo che permette di cominciare la procedura di cambio password. In particolare, viene
	 * generato un token di una pagina univoca, chiamato token password. Il token viene salvato
	 * sulla base dati e inviata tramite email allo studente.
	 * @param email parametro di tipo {@code String} che permette di cercare sulla base dati lo studente e inviare il token
	 * 				da cliccare.
	 * @eturn		Non ritorna valori. Eventuali errori che possono presentarsi,
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
