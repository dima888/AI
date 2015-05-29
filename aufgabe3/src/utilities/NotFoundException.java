package utilities;

public class NotFoundException extends Exception {
    public static String NFEX = "NotFoundException";
    private static final long serialVersionUID = 2560972177738813068L;

    private NotFoundException() {
        super();
    }

    public static void throwNotFoundException()
            throws NotFoundException {
        throw new NotFoundException();
    }
}
