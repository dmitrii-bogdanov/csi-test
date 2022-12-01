package bogdanov.csi.test.services.validators;

import bogdanov.csi.test.dto.PriceDto;
import bogdanov.csi.test.exceptions.price.*;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class PriceValidationServiceInterfaceTest {

    private final PriceValidationServiceInterface priceValidationService;

    private PriceDto getDefaultPriceDto() {
        final PriceDto price = new PriceDto();
        price.setId(1);
        price.setProductCode("P1");
        price.setNumber(1);
        price.setDepart(1);
        price.setBegin(LocalDateTime.of(2000, 1,1,0,0,0,0));
        price.setBegin(LocalDateTime.of(2000, 12,31,59,59,59,0));
        price.setValue(1);

        return price;
    }

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
        price.setId(-1);

        InvalidPriceException e = assertThrows(InvalidPriceException.class,
                                               () -> priceValidationService.validate(price));
        assertEquals(InvalidPriceIdException.class, e.getClass());
    }

    @Test
    void checkZeroId() {

        final PriceDto price = getDefaultPriceDto();
        price.setId(0);

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

}