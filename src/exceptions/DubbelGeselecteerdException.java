package exceptions;

public class DubbelGeselecteerdException extends RuntimeException {

	public DubbelGeselecteerdException() {
		super("Deze speler werd al geselecteerd");
	}

	public DubbelGeselecteerdException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DubbelGeselecteerdException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
