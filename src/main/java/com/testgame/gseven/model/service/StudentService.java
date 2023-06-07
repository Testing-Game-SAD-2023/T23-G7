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

@Service
public class StudentService implements IStudentService {

	@Autowired
	private IStudentDAO studentRepository;
	
	@Autowired
	private IRoleDAO roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public void saveStudent(Student studentForm) throws MailParseException, MailAuthenticationException,
												MailSendException, MailException{
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
		
		studentRepository.save(student);
		sendVerificationEmail(student, "localhost:8080", confirmationToken);
	}
	
	public void updatePassword(Student student, String newPassword) {
		student.setPassword(passwordEncoder.encode(newPassword));
		student.setPasswordToken("");
		studentRepository.save(student);
	}
	
	public void beginChangePassword(Student student) {
		String passwordToken = UUID.randomUUID().toString();
		
		student.setPasswordToken(passwordToken);
		studentRepository.save(student);
		sendEmailResetPassword(student, "localhost:8080", passwordToken);
	}
	
	public void sendEmailResetPassword(Student student, String siteURL, String passwordToken) {
		String subject = "Cambio password";
		String body = "Clicca sul seguente link per cambiare la tua password: " +
				siteURL+ "/resetPasswordProcess/" + passwordToken;
		emailService.sendEmail(student.getEmail(), subject, body);
	}
	
	private void sendVerificationEmail(Student student, String siteURL, String confirmationToken){
		String subject = "Conferma registrazione";
		String body = "Clicca sul seguente link per confermare la tua registrazione: " +
				siteURL+ "/confirm/token=" + confirmationToken;
		emailService.sendEmail(student.getEmail(), subject, body);
	}

	@Override
	public Student findByConfirmationToken(String token) {
		return studentRepository.findByConfirmationToken(token);
	}
	
	@Override
	public Student findUserByEmail(String email) {
		return studentRepository.findByEmail(email);
	}
	
	@Override
	public Student findByPasswordToken(String pswtoken) {
		return studentRepository.findByPasswordToken(pswtoken);
	}
	
	@Override
	public void enableStudent(Student student) {
		student.setEnabled(true);
		studentRepository.save(student);		
	}

	
}
