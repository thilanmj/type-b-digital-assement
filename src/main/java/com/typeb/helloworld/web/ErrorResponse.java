package com.typeb.helloworld.web;

public record ErrorResponse(String error) {

	public static final String INVALID_INPUT = "Invalid Input";

	public static ErrorResponse invalidInput() {
		return new ErrorResponse(INVALID_INPUT);
	}

}
