package it.univaq.disim.oop.unify.business.exceptions;

@SuppressWarnings("serial")
public class ArtistNotFoundException extends BusinessException{

	public ArtistNotFoundException() {
	}

	public ArtistNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ArtistNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ArtistNotFoundException(String message) {
		super(message);
	}

	public ArtistNotFoundException(Throwable cause) {
		super(cause);
	}
}
