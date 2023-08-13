package exceptions;

public class SpelerNietGevondenException extends RuntimeException {

	public SpelerNietGevondenException() {
		super("Deze speler is niet gevonden");
	}

	public SpelerNietGevondenException(String message) {
		super(message);
	}

	public SpelerNietGevondenException(Throwable cause) {
		super(cause);
	}

	public SpelerNietGevondenException(String message, Throwable cause) {
		super(message, cause);
	}

}
