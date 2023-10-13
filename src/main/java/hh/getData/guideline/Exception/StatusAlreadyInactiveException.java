package hh.getData.guideline.Exception;

public class StatusAlreadyInactiveException extends RuntimeException {

    public StatusAlreadyInactiveException() {
        super();
    }

    public StatusAlreadyInactiveException(String message) {
        super(message);
    }

    public StatusAlreadyInactiveException(String message, Throwable cause) {
        super(message, cause);
    }

    public StatusAlreadyInactiveException(Throwable cause) {
        super(cause);
    }
}
