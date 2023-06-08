package com.testgame.gseven.model.dto;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@SuppressWarnings("serial")
public class StudentDetails extends User{
	private String studentId;
	
	public StudentDetails(Student student) {
		super(student.getEmail(), student.getPassword(), student.isEnabled(), true, true, true, mapRolesToAuthorities(student));
		studentId = student.getId();
	}

	
			
	private static Collection<GrantedAuthority> mapRolesToAuthorities(Student student){
		return student.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}



	public String getStudentId() {
		return studentId;
	}



	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
}
