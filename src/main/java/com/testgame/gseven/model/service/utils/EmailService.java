package com.testgame.gseven.model.service.utils;

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
	
    /*
     * Metodo che sfrutta la {@code SendMail} per inviare una mail con uno specifico link atto al reset della password.
     * 
     * @param email Parametro di tipo {@code String} che indica l'indirizzo email dell'utente
     * a cui mandare la mail.
     * 
     * @param siteURL Parametro di tipo {@code String} che indica l'URL del sito web sul quale collegarsi
     * per ricevere il servizio.
     * 
     * @param URLpath Parametro di tipo {@code String} che specifica il percorso "siteURL/.." al quale fare
     * riferimento.
     * 
     * @param passwordToken Parametro di tipo {@code String} che indica il token randomico per poter verificare
     * la mail in maniera univoca.
     * 
     * @return Il metodo non ritorna nessun parametro.
     * 
     */
	public void sendEmailResetPassword(String email, String URLweb,String URLpath ,String passwordToken) {
		String subject = "Cambio password";
		String body = "Clicca sul seguente link per cambiare la tua password: " +
				URLweb+ URLpath + passwordToken;
		sendEmail(email, subject, body);
	}
	
	
	
	 /** Metodo che sfrutta la {@code SendMail} per inviare una mail all'indirizzo di posta elettronica inserito dall'utente per 
	 * verificare la sua validità.
	 * 
	 * @param email Parametro di tipo {@code String} che indica l'indirizzo email dell'utente
    	 * a cui mandare la mail.
     	 * 
     	 * @param URLweb Parametro di tipo {@code String} che indica l'URL del sito web sul quale collegarsi
    	 * per ricevere il servizio.
     	 * @param URLpath parametro di tipo {@code String} che indica la pagina del sito web dove avviare la conferma del profilo.
         * 
         * @param confirmationToken Parametro di tipo {@code String} che indica il token randomico per poter verificare
         * la mail in maniera univoca per la sua conferma
         * 
         * @return Il metodo non ritorna nessun parametro. 
	 */
	public void sendConfirmationEmail(String email, String URLweb, String URLpath, String confirmationToken){
		String subject = "Conferma registrazione";
		String body = "Clicca sul seguente link per confermare la tua registrazione: " +
				URLweb+ URLpath + confirmationToken;
		sendEmail(email, subject, body);
	}
	
	
	/* Metodo che viene utilizzato per inviare effettivamente una mail  all'indirizzo di posta elettronica
	 * passato in ingresso. 
	 * 
	 * @param email Parametro di tipo {@code String} che indica l'indirizzo email dell'utente
     * a cui mandare la mail.
	 * 
	 * @param subject Parametro di {@code String} che indica il nome dell'oggetto della mail inviata dal servizio. 
	 * 
	 * 
	 * @param body Parametro di tipo {@code String} che rappresenta il body, ossia il corpo, del messaggio
	 * inviato tramite posta elettronica. 
	 * 
	 * @return Il metodo non ritorna nessun parametro.
	 * 
	 * 
	 * @throws MailParseException
	 * @throws MailAuthenticationException
	 * @throws MailSendException
	 * @throws MailException
	 * 
	 * 
	 */
	
    private void sendEmail(String to, String subject, String body) throws MailParseException, MailAuthenticationException,
    																	MailSendException, MailException{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
    }
}

