package exceptions;

public class foutAantalSpelersException extends RuntimeException {

	public foutAantalSpelersException() {
		super("Het spel moet gestart worden met min. 2 en max. 4 spelers");
	}

	public foutAantalSpelersException(String message) {
		super(message);
	}
}
