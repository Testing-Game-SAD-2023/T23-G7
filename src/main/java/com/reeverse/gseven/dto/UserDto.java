package com.reeverse.gseven.dto;

import org.springframework.data.annotation.Id;


public class UserDto {
	
	@Id
	private String id;
	
	private String name;
	private String surname;
	private String gender;
	private String nationality;
	private String studyTitle;
	private String dateOfBirth;
	private String email;
	private String password;

	
	
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
}
