package com.testgame.gseven.utility.exceptions;

@SuppressWarnings("serial")
public class StudentAlreadyRegisteredException extends Exception {
	public StudentAlreadyRegisteredException() {
		super("The student with this email is already registered.");
	}
}
