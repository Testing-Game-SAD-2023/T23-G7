package com.testgame.gseven.model.dto;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

@Document (collection = "students")
public class Student {
	
	@Id
	@NotNull
    @NotBlank
    @NotEmpty
	private String id;
	private String confirmationToken;
	private String passwordToken;
    private boolean enabled;
    

    @NotNull
    @NotBlank
    @NotEmpty
	private String name;
    
    @NotNull
    @NotBlank
    @NotEmpty
	private String surname;
    
    @NotNull
    @NotBlank
    @NotEmpty
    private String gender;
	
    @NotNull
    @NotBlank
    @NotEmpty
    private String nationality;
	
    @NotNull
    @NotBlank
    @NotEmpty
    private String studyTitle;
	
    @NotNull
    @NotBlank
    @NotEmpty
    private String dateOfBirth;
	
    @NotNull
    @NotBlank
    @NotEmpty
    private String email;
	
    @NotNull
    @NotBlank
    @NotEmpty
    private String password;
	
    @NotNull
    @NotBlank
    @NotEmpty
    private List<Role> roles = new ArrayList<>();
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String sex) {
		this.gender = sex;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getStudyTitle() {
		return studyTitle;
	}
	public void setStudyTitle(String studyTitle) {
		this.studyTitle = studyTitle;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public String getConfirmationToken() {
		return confirmationToken;
	}
	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPasswordToken() {
		return passwordToken;
	}
	public void setPasswordToken(String passwordToken) {
		this.passwordToken = passwordToken;
	}

	public Student(String name, String surname, String sex, String nationality, String studyTitle,
			String dateOfBirth, String email, String confirmationToken, boolean enabled ,String password, List<Role> roles) {
		this.name = name;
		this.surname = surname;
		this.gender = sex;
		this.nationality = nationality;
		this.studyTitle = studyTitle;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.password = password;
		this.confirmationToken = confirmationToken;
		this.enabled = enabled;
		this.roles = roles;
	}
	public Student() {
	}

}
