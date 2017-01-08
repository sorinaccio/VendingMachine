package exception;

/**
 * Created by sorinaccio on 1/9/2017.
 */
public class InsufficientCoinsException extends RuntimeException {

    public InsufficientCoinsException() {
        super();
    }

    public InsufficientCoinsException(String message) {
        super(message);
    }
}
