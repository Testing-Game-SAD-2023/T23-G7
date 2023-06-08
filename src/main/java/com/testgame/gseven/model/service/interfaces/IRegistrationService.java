package com.testgame.gseven.model.service.interfaces;

import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;

import com.testgame.gseven.model.dto.Student;
import com.testgame.gseven.utility.exceptions.StudentAlreadyRegisteredException;

public interface IRegistrationService {
	public void registerStudent(Student studentForm) throws MailParseException, MailAuthenticationException,
	MailSendException, MailException, StudentAlreadyRegisteredException;
}
