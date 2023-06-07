package com.testgame.gseven.utility.exceptions;

import java.lang.Exception;

@SuppressWarnings("serial")
public class StudentNotFoundException extends Exception{
	public StudentNotFoundException() {
        super("Student not found. ");
    }
}
