package utilities;

public class KeineInventurAtomarerBauteileException extends Exception {
    public static String KIABEX = "KeineInventurAtomarerBauteileException";
    private static final long serialVersionUID = 2560972177738813068L;

    private KeineInventurAtomarerBauteileException() {
        super();
    }

    public static void throwKeineInventurAtomarerBauteileException()
            throws KeineInventurAtomarerBauteileException {
        throw new KeineInventurAtomarerBauteileException();
    }
}
