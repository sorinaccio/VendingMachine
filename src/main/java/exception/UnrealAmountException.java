package exception;

/**
 * Created by sorinaccio on 1/9/2017.
 */
public class UnrealAmountException extends RuntimeException{

    public UnrealAmountException() {
    }

    public UnrealAmountException(String message) {
        super(message);
    }
}
