package com.reeverse.gseven.service;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.reeverse.gseven.dto.UserDto;
import com.reeverse.gseven.model.Role;
import com.reeverse.gseven.model.User;
import com.reeverse.gseven.repository.RoleRepository;
import com.reeverse.gseven.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public void saveUser(UserDto userDto) throws MailParseException, MailAuthenticationException,
												MailSendException, MailException{
		String confirmationToken = UUID.randomUUID().toString();
		
		Role studentRole = roleRepository.findByName("STUDENTE");
        
		if(studentRole == null)
			studentRole = roleRepository.save(new Role("STUDENTE"));
		
		User user = new User(userDto.getName(),
							 userDto.getSurname(),
							 userDto.getGender(),
							 userDto.getNationality(),
							 userDto.getStudyTitle(),
							 userDto.getDateOfBirth(),
							 userDto.getEmail(),
							 confirmationToken,
							 false,
							 passwordEncoder.encode(userDto.getPassword()),
							 Arrays.asList(studentRole));
		
		userRepository.save(user);
		sendVerificationEmail(user, "localhost:8080", confirmationToken);
		
	}
	
	public void updatePassword(User user, String newPassword) {
		
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setPasswordToken("");
		userRepository.save(user);
	}
	
	public void beginChangePassword(User user) {
		String passwordToken = UUID.randomUUID().toString();
		
		user.setPasswordToken(passwordToken);
		userRepository.save(user);
		sendEmailResetPassword(user, "localhost:8080", passwordToken);
	}
	
	public void sendEmailResetPassword(User user, String siteURL, String passwordToken) {
		String subject = "Cambio password";
		String body = "Clicca sul seguente link per cambiare la tua password: " +
				siteURL+ "/resetPasswordProcess/" + passwordToken;
		emailService.sendEmail(user.getEmail(), subject, body);
	}
	
	private void sendVerificationEmail(User user, String siteURL, String confirmationToken){
		String subject = "Conferma registrazione";
		String body = "Clicca sul seguente link per confermare la tua registrazione: " +
				siteURL+ "/confirm/token=" + confirmationToken;
		emailService.sendEmail(user.getEmail(), subject, body);
	}
	
	
	@Override
	public User findByConfirmationToken(String token) {
		return userRepository.findByConfirmationToken(token);
	}
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public User findByPasswordToken(String pswtoken) {
		return userRepository.findByPasswordToken(pswtoken);
	}
	
	@Override
	public void enableUser(User user) {
		user.setEnabled(true);
		userRepository.save(user);		
	}



	
}
