package utilities;

public class Precondition {

	private Precondition() {
	}

	public static void assertArgument(boolean condition) {
		try {
			if (!condition)
				throw new Exception("Assertion failed!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
