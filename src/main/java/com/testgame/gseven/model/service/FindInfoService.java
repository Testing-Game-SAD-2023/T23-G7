package com.testgame.gseven.model.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testgame.gseven.model.dao.IStudentDAO;
import com.testgame.gseven.model.dto.Student;

@Service
public class FindInfoService {

	@Autowired
	private IStudentDAO studentRepository;
	
	/** Cerca nella base dati lo studente con l'email fornita in ingresso
	 *  e restituisce un oggetto Studente popolato dalle informazioni
	 *  trovate nella base dati.
	 * @param email
	 *  Chiave di ricerca dello studente nella base dati.
	 *  
	 * */
	public Student getStudentByEmail(String email) {
		return studentRepository.findByEmail(email);
	}
	
	public Student getStudentByConfirmationToken(String token) {
		return studentRepository.findByConfirmationToken(token);
	}
	
	public Student getStudentByPasswordToken(String pswtoken) {
		return studentRepository.findByPasswordToken(pswtoken);
	}
	
	public Student getStudentById(String id) {
		Optional<Student> studentBuff = studentRepository.findById(id);
		Student student = studentBuff.get();
		return student;
		
	}
	/** Cerca nella base dati lo studente con l'email fornita in ingresso
	 * e restituisce true se viene trovato, false altrimenti.
	 * @param email Chiave di ricerca dello studente nella base dati
	 * @return boolean
	 * */
	public boolean isStudentRegistered(String email) {
		return studentRepository.existsByEmail(email);
	}
	
	public boolean doesConfirmationTokenExists(String confirmationToken) {
		return studentRepository.existsByConfirmationToken(confirmationToken);
	}
	
	public boolean doesPasswordTokenExists(String passwordToken) {
		return studentRepository.existsByPasswordToken(passwordToken);
	}
	
}
