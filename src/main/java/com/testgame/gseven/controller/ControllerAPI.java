package com.testgame.gseven.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@PostMapping("/registration")
	public String registration(@RequestBody Student studentForm) {
		try {
			registrationService.registerStudent(studentForm, "localhost:8080", "/confirm/token=");
		} catch (StudentAlreadyRegisteredException e) {
			return e.getMessage();
		}
		return "SUCCESS";
	}
	
	
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
