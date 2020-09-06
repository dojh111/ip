package walter.exceptions;

/**
 * Exception class for exceptions specific to walter.walter
 */
public class WalterException extends Exception {

    protected String errorMessage;

    public WalterException (String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
