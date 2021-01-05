package com.zfsoft.batchimport.exception;

public class BaseMsgException extends RuntimeException {
	private static final long serialVersionUID = -105500584464532362L;

	public BaseMsgException(String message) {
		super(message);
	}
	public BaseMsgException() {
		super();
	}
}
