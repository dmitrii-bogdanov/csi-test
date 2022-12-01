package bogdanov.csi.test.exceptions.price;

public class InvalidPriceValueException extends InvalidPriceException {

    public InvalidPriceValueException() {
        super();
    }

    public InvalidPriceValueException(String message) {
        super(message);
    }

    public InvalidPriceValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPriceValueException(Throwable cause) {
        super(cause);
    }
}
