package com.testgame.gseven.utility.exceptions;

@SuppressWarnings("serial")
public class ConfirmationTokenNotFoundException extends Exception {
	public ConfirmationTokenNotFoundException() {
		super("The confirmation token doesn't exists.");
	}
}
