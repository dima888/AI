package utilities;

public class TechnicalException extends Exception {
	
	public static String TECHEX = "TechEx";
	private static final long serialVersionUID = -5390201238009020791L;

	private TechnicalException() {
		super();
	}

	private TechnicalException(String errMsg) {
		super("Technical Exception");
	}

	public static void throwNewTechnicalException() throws TechnicalException {
		throw new TechnicalException();
	}

	public static void throwNewTechnicalException(String errMsg)
			throws TechnicalException {
		throw new TechnicalException(errMsg);
	}
}
