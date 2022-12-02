package bogdanov.csi.test.exceptions.price;

public class InvalidPriceNumberException extends InvalidPriceException {

    public InvalidPriceNumberException() {
        super();
    }

    public InvalidPriceNumberException(String message) {
        super(message);
    }

    public InvalidPriceNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPriceNumberException(Throwable cause) {
        super(cause);
    }

}
