package bogdanov.csi.test.exceptions.price;

public class IntersectedPricesException extends InvalidPriceException {

    public IntersectedPricesException() {
        super();
    }

    public IntersectedPricesException(String message) {
        super(message);
    }

    public IntersectedPricesException(String message, Throwable cause) {
        super(message, cause);
    }

    public IntersectedPricesException(Throwable cause) {
        super(cause);
    }
}
