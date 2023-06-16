package com.testgame.gseven.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testgame.gseven.model.dto.Student;
import com.testgame.gseven.model.service.ChangePasswordService;
import com.testgame.gseven.model.service.RegistrationService;
import com.testgame.gseven.model.service.utils.ConfirmationService;
import com.testgame.gseven.model.service.utils.FindInfoService;
import com.testgame.gseven.utility.exceptions.ConfirmationTokenNotFoundException;
import com.testgame.gseven.utility.exceptions.PasswordTokenNotFoundException;
import com.testgame.gseven.utility.exceptions.StudentAlreadyRegisteredException;
import com.testgame.gseven.utility.exceptions.StudentNotEnabledException;
import com.testgame.gseven.utility.exceptions.StudentNotFoundException;

@RestController
@RequestMapping("/api")
public class ControllerAPI {

	
	@Autowired
	private RegistrationService registrationService;
	
	@Autowired
	private ChangePasswordService changePasswordService;
	
	@Autowired
	private FindInfoService findInfoService;
	
	@Autowired
	private ConfirmationService confirmationService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	/* RestController GET /api/studentid:
	 * Questa API di tipo Get permette di restituire l'ID dello studente registrato
	 * a partire dal suo ID passato tramite oggetto JSON. 
	 * Per prima cosa si estrapola il valore mail dall'oggetto JSON ricevuto.
	 * Successivamente si ricerca lo studente all'interno della base dati avente
	 * quella specifica mail.
	 * Se non viene trovato si restituisce NULL, l'ID altrimenti.
	 */
	
	@GetMapping("/studentid")
    public String getStudentIdHTTP(@RequestBody String jsonEmail) {
		JSONObject obj = new JSONObject(jsonEmail);
		String email = obj.getString("email");
		
		Student student = findInfoService.getStudentByEmail(email);
		if(student != null) {
			return student.getId();
		}
		return "NULL";
    }
	
	
	
	/* RestController GET /api/studentinfo:
	 * Questa API permette di inoltrare una richiesta HTTP di tipo GET 
	 * che a partire dall’ID ritorna le info dello studente salvato come oggetto JSON.
	 * Per prima cosa si estrapola il valore dell'ID dal JSON passato come input
	 * Successivamente si ricerca lo studente con quell'ID all'interno della base dati.
	 * Se esso viene trovato viene restituito lo studente, NULL altrimenti. 
	 *  
	 */

	@GetMapping("/studentinfo")
    public Student getStudentInfoHTTP(@RequestBody String jsonId) {
		JSONObject obj = new JSONObject(jsonId);
		String id = obj.getString("id");
	    
	    	Student student = new Student();
	    
	    	try{
		   student = findInfoService.getStudentById(id);
		}
		catch(Exception e) {
			return null;
		}
		
		return student;
    }
	
	
	/* RestController GET /api/login:
	 * Questa API permette di inoltrare una richiesta HTTP di tipo POST 
	 * che avvia il processo di autenticazione dello studente. .
	 * 
	 * Per prima cosa si estrapolano email e password dal JSON passato come payload.
	 * Successivamente, si ricerca lo studente sulla base dati con quella mail.
	 * Se lo studente non esiste viene lanciata una eccezione. Al contrario se lo studente
	 * viene trovato si effettua un confronto fra le due password.
	 * Se tutto va a buon fine viene restituito un valore true, false altrimenti. 
	 */

	@PostMapping("/login")
    public boolean tryLogin(@RequestBody String credentials) {
		JSONObject obj = new JSONObject(credentials);
		String email = obj.getString("email");
	    String password = obj.getString("password");
	    Student student = new Student();
	    
	    try{
		   student = findInfoService.getStudentByEmail(email);
		}
		catch(Exception e) {
			return false;
		}
		if(!student.isEnabled()) {
			return false;
		}
		
	    String encryptedPassword = student.getPassword();
	    boolean isPasswordRight = passwordEncoder.matches(password, encryptedPassword);
		return isPasswordRight;
    }
	
	
	
	/*Rest Controller POST /api/registration:
	 * Questa API permette di inoltrare una richiesta HTTP di tipo POST 
	 * che avvia il processo di registrazione dello studente.
	 * 
	 * Nel caso in cui la registrazione va a buon fine, viene restituito
	 * un messaggio "SUCCESS" altrimenti l'eccezione generata. 
	 * 
	 */
	
	@PostMapping("/registration")
	public String registration(@RequestBody Student studentForm) {
		try {
			registrationService.registerStudent(studentForm, "localhost:8080", "/confirm/token=");
		} catch (StudentAlreadyRegisteredException e) {
			return e.getMessage();
		}
		return "SUCCESS";
	}
	
	
	/*Rest Controller POST /confirm/token
	 * Questa API permette di inoltrare una richiesta HTTP di tipo POST che 
	 * avvia il processo di validazione della mail attraverso il link ricevuto tramite mail.
	 * 
	 * Una volta estrapolato il valore del confirmationToken dall'oggetto JSON passato come
	 * payload viene lanciato il servizio di conferma email.
	 * Se la convalida dell'account va a buon fine si ritorna un messaggio di successo altrimenti
	 * la relativa eccezione lanciata. 
	 */
	
	
	@PostMapping("/confirm/token")
    public String confirmEmail(@RequestBody String jsonConfirmationToken) {
		JSONObject obj = new JSONObject(jsonConfirmationToken);
		String confirmationToken = obj.getString("confirmationToken");
		
		try {
			confirmationService.confirmStudentByToken(confirmationToken);
		} catch (ConfirmationTokenNotFoundException e) {
			 return e.getMessage();
		}
       
		return "SUCCESS";
    }
	
	
	
	@PostMapping("/changepassword")
	public String changePassword(@RequestBody String jsonEmail) {
		JSONObject obj = new JSONObject(jsonEmail);
		String email = obj.getString("email");
		
		try {
			changePasswordService.beginChangePassword(email, "localhost:8080", "/changePasswordProcess/token=");
		} catch (StudentNotFoundException e) {
			return e.getMessage();
		} catch (StudentNotEnabledException e) {
			return e.getMessage();
		}
		
		return "SUCCESS";
	}
	
	@PostMapping("/changepassword/token")
	public String changePasswordProcess(@RequestBody String jsonChangePassword) {
		JSONObject obj = new JSONObject(jsonChangePassword);
		String passwordToken = obj.getString("passwordToken");
		String newPassword = obj.getString("newPassword");
		
		if(passwordToken.isEmpty())
			return "Password token is empty.";
		
		boolean isPasswordTokenFound = findInfoService.doesPasswordTokenExists(passwordToken);
		if(!isPasswordTokenFound) {
			return new PasswordTokenNotFoundException().getMessage();
		}
		
		try {
			changePasswordService.changePassword(passwordToken, newPassword);
		} catch (PasswordTokenNotFoundException e) {
			return e.getMessage();
		}		
		return "SUCCESS";
	}
	
	
}
