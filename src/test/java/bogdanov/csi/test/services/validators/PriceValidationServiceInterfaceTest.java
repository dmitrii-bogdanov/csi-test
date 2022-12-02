package bogdanov.csi.test.services.validators;

import bogdanov.csi.test.dto.PriceDto;
import bogdanov.csi.test.exceptions.price.*;
import bogdanov.csi.test.services.PriceServiceTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class PriceValidationServiceInterfaceTest extends PriceServiceTest {

    @Autowired
    private PriceValidationServiceInterface priceValidationService;

    @Test
    void checkValidPrice() {
        final PriceDto price = getDefaultPriceDto();
        assertDoesNotThrow(() -> priceValidationService.validate(price));
    }

    @Test
    void checkNullPrice() {

        final PriceDto price = null;

        InvalidPriceException e = assertThrows(InvalidPriceException.class,
                                               () -> priceValidationService.validate(price));
        assertEquals(NullPriceException.class, e.getClass());
    }

    @Test
    void checkNegativeId() {

        final PriceDto price = getDefaultPriceDto();
        price.setId(-1L);

        InvalidPriceException e = assertThrows(InvalidPriceException.class,
                                               () -> priceValidationService.validate(price));
        assertEquals(InvalidPriceIdException.class, e.getClass());
    }

    @Test
    void checkZeroId() {

        final PriceDto price = getDefaultPriceDto();
        price.setId(0L);

        InvalidPriceException e = assertThrows(InvalidPriceException.class,
                                               () -> priceValidationService.validate(price));
        assertEquals(InvalidPriceIdException.class, e.getClass());
    }

    @Test
    void checkNullProductCode() {

        final PriceDto price = getDefaultPriceDto();
        price.setProductCode(null);

        InvalidPriceException e = assertThrows(InvalidPriceException.class,
                                               () -> priceValidationService.validate(price));
        assertEquals(InvalidPriceProductCodeException.class, e.getClass());
    }

    @Test
    void checkEmptyProductCode() {

        final PriceDto price = getDefaultPriceDto();
        price.setProductCode(Strings.EMPTY);

        InvalidPriceException e = assertThrows(InvalidPriceException.class,
                                               () -> priceValidationService.validate(price));
        assertEquals(InvalidPriceProductCodeException.class, e.getClass());
    }

    @Test
    void checkBlankProductCode() {

        final PriceDto price = getDefaultPriceDto();
        price.setProductCode("\t  \t ");

        InvalidPriceException e = assertThrows(InvalidPriceException.class,
                                               () -> priceValidationService.validate(price));
        assertEquals(InvalidPriceProductCodeException.class, e.getClass());
    }

    @Test
    void checkNegativeNumber() {

        final PriceDto price = getDefaultPriceDto();
        price.setNumber(-1);

        InvalidPriceException e = assertThrows(InvalidPriceException.class,
                                               () -> priceValidationService.validate(price));
        assertEquals(InvalidPriceNumberException.class, e.getClass());
    }

    @Test
    void checkZeroNumber() {

        final PriceDto price = getDefaultPriceDto();
        price.setNumber(0);

        InvalidPriceException e = assertThrows(InvalidPriceException.class,
                                               () -> priceValidationService.validate(price));
        assertEquals(InvalidPriceNumberException.class, e.getClass());
    }

    @Test
    void checkNegativeDepart() {

        final PriceDto price = getDefaultPriceDto();
        price.setDepart(-1);

        InvalidPriceException e = assertThrows(InvalidPriceException.class,
                                               () -> priceValidationService.validate(price));
        assertEquals(InvalidPriceDepartException.class, e.getClass());
    }

    @Test
    void checkZeroDepart() {

        final PriceDto price = getDefaultPriceDto();
        price.setDepart(0);

        InvalidPriceException e = assertThrows(InvalidPriceException.class,
                                               () -> priceValidationService.validate(price));
        assertEquals(InvalidPriceDepartException.class, e.getClass());
    }

    @Test
    void checkNullBeginDate() {

        final PriceDto price = getDefaultPriceDto();
        price.setBegin(null);

        InvalidPriceException e = assertThrows(InvalidPriceException.class,
                                               () -> priceValidationService.validate(price));
        assertEquals(InvalidPriceDateException.class, e.getClass());
    }

    @Test
    void checkNullEndDate() {

        final PriceDto price = getDefaultPriceDto();
        price.setEnd(null);

        InvalidPriceException e = assertThrows(InvalidPriceException.class,
                                               () -> priceValidationService.validate(price));
        assertEquals(InvalidPriceDateException.class, e.getClass());
    }

    @Test
    void checkInvalidDatesOrder() {

        final PriceDto price = getDefaultPriceDto();
        final LocalDateTime tmp = price.getBegin();
        price.setBegin(price.getEnd());
        price.setEnd(tmp);

        InvalidPriceException e = assertThrows(InvalidPriceException.class,
                                               () -> priceValidationService.validate(price));
        assertEquals(InvalidPriceDateException.class, e.getClass());
    }

    @Test
    void checkNegativeValue() {

        final PriceDto price = getDefaultPriceDto();
        price.setValue(-1);

        InvalidPriceException e = assertThrows(InvalidPriceException.class,
                                               () -> priceValidationService.validate(price));
        assertEquals(InvalidPriceValueException.class, e.getClass());
    }

}