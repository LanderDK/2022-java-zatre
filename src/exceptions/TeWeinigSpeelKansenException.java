package exceptions;

public class TeWeinigSpeelKansenException extends RuntimeException {

	public TeWeinigSpeelKansenException() {
		super("Deze speler heeft te weinig spelkansen");
	}

	public TeWeinigSpeelKansenException(String message) {
		super(message);
	}

	public TeWeinigSpeelKansenException(Throwable cause) {
		super(cause);
	}
}
