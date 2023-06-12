package com.testgame.gseven.model.service.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.testgame.gseven.model.dao.IStudentDAO;
import com.testgame.gseven.model.dto.Student;
import com.testgame.gseven.model.dto.StudentDetails;

@Service
public class StudentDetailsService implements UserDetailsService {

	private IStudentDAO studentRepository;

	@Autowired
	public StudentDetailsService(IStudentDAO studentRepository) {
		this.studentRepository = studentRepository;
	}
	
	/**Metodo che carica lo StudentDetails a partire dalla sua email, che viene
	 * passata come parametro di ingresso.
	 * 
	 * @param email parametro di tipo {@code String} che permette di cercare sulla base dati
	 * lo studente attraverso la sua email.
	 * 
	 * @return Ritorna l'oggetto StudentDetails, ereditata da UserDetails
	 * 
	 * @throws UsernameNotFoundException eccezione restituita quando l'email fornita in ingresso	
	 *	non corrisponde a nessuno studente nella base dati.
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
	
		Student studentUser = studentRepository.findByEmail(email);
		
		if(studentUser != null && studentUser.isEnabled()) {
			return new StudentDetails(studentUser);
		}else {
			throw new UsernameNotFoundException("Invalid email or password");
		}
	}
}
