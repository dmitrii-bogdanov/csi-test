package bogdanov.csi.test.exceptions.price;

public class InvalidPriceProductCodeException extends InvalidPriceException {

    public InvalidPriceProductCodeException() {
        super();
    }

    public InvalidPriceProductCodeException(String message) {
        super(message);
    }

    public InvalidPriceProductCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPriceProductCodeException(Throwable cause) {
        super(cause);
    }
}
