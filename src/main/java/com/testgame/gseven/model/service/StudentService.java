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
	
	/** Prova javadoc
	 * @throws StudentAlreadyRegisteredException 
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
