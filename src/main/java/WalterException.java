/**
 * Exception class for exceptions specific to Walter
 */
public class WalterException extends Exception {

    protected String errorMessage;

    public WalterException (String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
