package com.testgame.gseven.utility.exceptions;

@SuppressWarnings("serial")
public class PasswordTokenNotFoundException extends Exception{
	
	public PasswordTokenNotFoundException() {
		super("The password token doesn't exists.");
	}
}
