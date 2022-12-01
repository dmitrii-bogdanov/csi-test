package bogdanov.csi.test.exceptions.price;

public class InvalidPriceDepartException extends InvalidPriceException {

    public InvalidPriceDepartException() {
        super();
    }

    public InvalidPriceDepartException(String message) {
        super(message);
    }

    public InvalidPriceDepartException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPriceDepartException(Throwable cause) {
        super(cause);
    }
}
