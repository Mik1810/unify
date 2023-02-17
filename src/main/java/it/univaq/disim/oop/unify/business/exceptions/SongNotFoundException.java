package it.univaq.disim.oop.unify.business.exceptions;

@SuppressWarnings("serial")
public class SongNotFoundException extends BusinessException {

	public SongNotFoundException() {
	}

	public SongNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SongNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public SongNotFoundException(String message) {
		super(message);
	}

	public SongNotFoundException(Throwable cause) {
		super(cause);
	}
}
