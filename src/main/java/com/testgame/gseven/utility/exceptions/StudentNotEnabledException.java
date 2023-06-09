package com.testgame.gseven.utility.exceptions;

@SuppressWarnings("serial")
public class StudentNotEnabledException extends Exception {
	public StudentNotEnabledException() {
		super("The student is not enabled.");
	}
}
