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
import com.testgame.gseven.model.service.interfaces.IRegistrationService;
import com.testgame.gseven.model.service.utils.EmailService;
import com.testgame.gseven.model.service.utils.FindInfoService;
import com.testgame.gseven.utility.exceptions.StudentAlreadyRegisteredException;
@Service
public class RegistrationService implements IRegistrationService{
	@Autowired
	private IRoleDAO roleRepository;
	@Autowired
	private IStudentDAO studentRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private FindInfoService findInfoService;
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
	 * @param URLweb 		parametro che verrà inserito nel corpo dell'email, e funge da link di conferma per confermare
	 * 						l'identità dello studente.
	 * @return 				Non ritorna valori. In caso di errori, restituisce le eccezioni che devono che devono
	 * 						essere opportunamente gestite con try-catch.
	 * @throws StudentAlreadyRegisteredException eccezione restituita quando si tenta di salvare nella base dati uno studente
	 * 											con una email già registrata.
	 * @throws MailParseException
	 * @throws MailAuthenticationException
	 * @throws MailSendException
	 * @throws MailException
	 * */
	@Override
	public void registerStudent(Student studentForm, String URLweb) throws MailParseException, MailAuthenticationException,
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
			emailService.sendConfirmationEmail(studentForm.getEmail(), URLweb, confirmationToken);
		}catch(Exception ex){
			throw ex;
		}
	}
}
