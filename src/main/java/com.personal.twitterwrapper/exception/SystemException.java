package com.personal.twitterwrapper.exception;

public class SystemException extends Exception {

	public SystemException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public SystemException(String msg) {
		super(msg);
	}

}
