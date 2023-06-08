package com.testgame.gseven.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {
	@Autowired
	private final JavaMailSender javaMailSender;
	
	
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

	public void sendEmailResetPassword(String email, String siteURL,String URLpath ,String passwordToken) {
		String subject = "Cambio password";
		String body = "Clicca sul seguente link per cambiare la tua password: " +
				siteURL+ URLpath + passwordToken;
		sendEmail(email, subject, body);
	}
	
	public void sendVerificationEmail(String email, String siteURL, String confirmationToken){
		String subject = "Conferma registrazione";
		String body = "Clicca sul seguente link per confermare la tua registrazione: " +
				siteURL+ "/confirm/token=" + confirmationToken;
		sendEmail(email, subject, body);
	}
	
    private void sendEmail(String to, String subject, String body) throws MailParseException, MailAuthenticationException,
    																	MailSendException, MailException{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
    }
}

