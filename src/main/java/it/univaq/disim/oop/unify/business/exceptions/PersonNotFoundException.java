package it.univaq.disim.oop.unify.business.exceptions;

@SuppressWarnings("serial")
public class PersonNotFoundException extends BusinessException {

	public PersonNotFoundException() {
	}

	public PersonNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PersonNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersonNotFoundException(String message) {
		super(message);
	}

	public PersonNotFoundException(Throwable cause) {
		super(cause);
	}
}