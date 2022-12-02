package bogdanov.csi.test.exceptions.price;

public class InvalidPriceDateException extends InvalidPriceException {

    public InvalidPriceDateException() {
        super();
    }

    public InvalidPriceDateException(String message) {
        super(message);
    }

    public InvalidPriceDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPriceDateException(Throwable cause) {
        super(cause);
    }
}
