package bogdanov.csi.test.exceptions.price;

public class InvalidPriceIdException extends InvalidPriceException {

    public InvalidPriceIdException() {
        super();
    }

    public InvalidPriceIdException(String message) {
        super(message);
    }

    public InvalidPriceIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPriceIdException(Throwable cause) {
        super(cause);
    }
}
