package bogdanov.csi.test.validators;

import bogdanov.csi.test.dto.PriceDto;
import bogdanov.csi.test.exceptions.price.*;

public class PriceValidationService implements PriceValidationServiceInterface {

    @Override
    public void validate(final PriceDto price) throws InvalidPriceException {
        checkNull(price);
        checkId(price);
        checkProductCode(price);
        checkNumber(price);
        checkDepart(price);
        checkBeginDate(price);
        checkEndDate(price);
        checkDatesOrder(price);
        checkValue(price);
    }

    private void checkNull(final PriceDto price) throws NullPriceException {
        if (price == null) {
            throw new NullPriceException();
        }
    }

    private void checkId(final PriceDto price) throws InvalidPriceIdException {
        if (price.getId() <= 0) {
            throw new InvalidPriceIdException("Id is not positive. Received: " + price.getId());
        }
    }

    private void checkProductCode(final PriceDto price) throws InvalidPriceProductCodeException {
        if (price.getProductCode() == null) {
            throw new InvalidPriceProductCodeException("Product code is null");
        }
    }

    private void checkNumber(final PriceDto price) throws InvalidPriceNumberException {
        if (price.getNumber() <= 0) {
            throw new InvalidPriceNumberException("Number is not positive. Received: " + price.getNumber());
        }
    }

    private void checkDepart(final PriceDto price) {
        if (price.getDepart() <= 0) {
            throw new InvalidPriceDepartException("Depart is not positive. Received: " + price.getDepart());
        }
    }

    private void checkBeginDate(final PriceDto price) throws InvalidPriceDateException {
        if (price.getBegin() == null) {
            throw new InvalidPriceDateException("Begin date is null");
        }
    }

    private void checkEndDate(final PriceDto price) throws InvalidPriceDateException {
        if (price.getEnd() == null) {
            throw new InvalidPriceDateException("End date is null");
        }
    }

    private void checkDatesOrder(final PriceDto price) throws InvalidPriceDateException {
        if (price.getBegin().compareTo(price.getEnd()) > 0) {
            throw new InvalidPriceDateException("Begin date is after end date. Received: "
                                                + "{ begin: " + price.getBegin() + " }, "
                                                + "{ end : " + price.getEnd() + " }");
        }
    }

    private void checkValue(final PriceDto price) throws InvalidPriceValueException {
        if (price.getValue() < 0) {
            throw new InvalidPriceValueException("Negative price value. Received: " + price.getValue());
        }
    }

}
