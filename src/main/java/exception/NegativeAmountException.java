package exception;

/**
 * Created by sorinaccio on 1/9/2017.
 */
public class NegativeAmountException extends RuntimeException{

    public NegativeAmountException() {
        super();
    }

    public NegativeAmountException(String message) {
        super(message);
    }

}
