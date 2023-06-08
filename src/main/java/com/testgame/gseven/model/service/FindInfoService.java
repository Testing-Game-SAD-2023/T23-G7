package com.testgame.gseven.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testgame.gseven.model.dao.IStudentDAO;
import com.testgame.gseven.model.dto.Student;

@Service
public class FindInfoService {

	@Autowired
	private IStudentDAO studentRepository;
	
	/** Cerca nella base dati lo studente con l'email fornita in ingresso
	 *  e restituisce un oggetto Student popolato dalle informazioni
	 *  trovate nella base dati. Restituisce null se l'email non corrisponde a nessuno studente.
	 * @param email chiave di ricerca dello studente nella base dati.
	 * @return Restituisce un oggetto {@code Student} con i campi popolati se viene trovato oppure
	 * {@code null} se non viene trovato.
	 * */
	public Student getStudentByEmail(String email) {
		return studentRepository.findByEmail(email);
	}
	
	/** Cerca nella base dati lo studente con il token di conferma
	 * fornito in ingresso e restituisce un oggetto Student popolato
	 * dalle informazioni trovate nella base dati.
	 * @param token chiave di ricerca dello studente nella base dati
	 * @return Restituisce un oggetto {@code Student} con i campi popolati se viene trovato oppure 
	 * {@code null} se non viene trovato.
	 *  */
	public Student getStudentByConfirmationToken(String token) {
		return studentRepository.findByConfirmationToken(token);
	}
	
	/** Cerca nella base dati lo studente con il token della password
	 * fornito in ingresso e restituisce un oggetto Student popolato dalle
	 * informazioni trovato nella base dati.
	 * @param pswtoken chiave di ricerca dello studente nella base dati
	 * @return Restituisce un oggetto {@code Student} con i campi popolati se viene trovato oppure
	 * {@code null} se non viene trovato.
	 */
	public Student getStudentByPasswordToken(String pswtoken) {
		return studentRepository.findByPasswordToken(pswtoken);
	}
	
	
	/** Cerca nella base dati lo studente usando l'id fornito in ingresso e
	 * restituisce un oggetto Student popolato dalle informazioni trovato nella base dati.
	 * @param id chiave di ricerca dello studente nella base dati.
	 * @return Restituisce un oggetto {@code Student} con i campi popolati se viene trovato oppure
	 * {@code null} se non viene trovato.
	 */
	public Student getStudentById(String id) {
		return studentRepository.findById(id).get();
	}
	
	/** Cerca nella base dati lo studente con l'email fornita in ingresso
	 * e restituisce true se viene trovato, altrimenti false.
	 * @param email Chiave di ricerca dello studente nella base dati
	 * @return Restituisce {@code true} se viene trovato uno studente con questa email,
	 * {@code false} se non viene trovato.
	 * */
	public boolean isStudentRegistered(String email) {
		return studentRepository.existsByEmail(email);
	}
	
	/** Cerca nella base dati il token di conferma dell'identità associato a un qualsiasi studente,
	 * e se viene trovato restitituisce true, altrimenti false.
	 * @param confirmationToken token di conferma che verrà utilizzato come chiave di ricerca nella base dati
	 * @return Restituisce {@code true} se il token esiste (ed è associato a un qualsiasi studente) nella base dati, 
	 * {@code false} se non viene trovato.
	 * */
	public boolean doesConfirmationTokenExists(String confirmationToken) {
		return studentRepository.existsByConfirmationToken(confirmationToken);
	}
	
	/** Cerca nella base dati il token della password associato a un qualsiasi studente,
	 * e se viene trovato restitituisce true, altrimenti false. Il token della password associato
	 * a uno studente registratoserve a
	 * generare una pagina web univoca per poter cambiare la password dello studente
	 * @param confirmationToken token di conferma che verrà utilizzato come chiave di ricerca nella base dati
	 * @return Restituisce {@code true} se il token esiste (ed è associato a un qualsiasi studente) nella base dati, 
	 * {@code false} se non viene trovato.
	 * */
	public boolean doesPasswordTokenExists(String passwordToken) {
		return studentRepository.existsByPasswordToken(passwordToken);
	}
	
}
