package it.univaq.disim.oop.unify.business.exceptions;

@SuppressWarnings("serial")
public class AlbumNotFoundException extends BusinessException{

	public AlbumNotFoundException() {
	}

	public AlbumNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AlbumNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlbumNotFoundException(String message) {
		super(message);
	}

	public AlbumNotFoundException(Throwable cause) {
		super(cause);
	}
}
