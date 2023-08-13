package exceptions;

public class NietInHetMiddenException extends RuntimeException 
{
	public NietInHetMiddenException() {
		super("Het steentje moet in het midden worden gelegd");
	}

	public NietInHetMiddenException(String message) {
		super(message);
	}
}
