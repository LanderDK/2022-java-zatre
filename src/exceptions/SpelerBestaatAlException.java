package exceptions;

public class SpelerBestaatAlException extends RuntimeException {

	public SpelerBestaatAlException() {
		super("Deze speler bestaat al");
	}

	public SpelerBestaatAlException(String message) {
		super(message);
	}

	public SpelerBestaatAlException(Throwable cause) {
		super(cause);
	}

	public SpelerBestaatAlException(String message, Throwable cause) {
		super(message, cause);
	}
}
