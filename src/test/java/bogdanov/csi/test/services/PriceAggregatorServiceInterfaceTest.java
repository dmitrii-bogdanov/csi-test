package bogdanov.csi.test.services;

import bogdanov.csi.test.dto.PriceDto;
import bogdanov.csi.test.exceptions.price.InvalidPriceException;
import bogdanov.csi.test.services.validators.PriceValidationServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class PriceAggregatorServiceInterfaceTest extends PriceServiceTest{

    @Autowired
    private PriceAggregatorServiceInterface aggregatorService;

    @Autowired
    private PriceValidationServiceInterface validationService;

    @Test
    void checkNullCurrentPricesList() {
        final List<PriceDto> currentPrices = null;
        final List<PriceDto> newPrices = Collections.singletonList(getDefaultPriceDto());

        InvalidPriceException e = assertThrows(InvalidPriceException.class,
                () -> aggregatorService.aggregate(currentPrices, newPrices));
        assertEquals(InvalidPriceException.class, e.getClass());
    }

    @Test
    void checkNullNewPricesList() {
        final List<PriceDto> currentPrices = Collections.singletonList(getDefaultPriceDto());
        final List<PriceDto> newPrices = null;

        InvalidPriceException e = assertThrows(InvalidPriceException.class,
                () -> aggregatorService.aggregate(currentPrices, newPrices));
        assertEquals(InvalidPriceException.class, e.getClass());
    }

    @Test
    void checkValid1() {
        final List<PriceDto> currentPrices = Collections.EMPTY_LIST;
        final List<PriceDto> newPrices = Collections.EMPTY_LIST;

        final List<PriceDto> result = assertDoesNotThrow(() -> aggregatorService.aggregate(currentPrices, newPrices));
        assertTrue(result.isEmpty());
    }

    @Test
    void checkValid2() {
        final List<PriceDto> currentPrices = new ArrayList<>();
        final PriceDto currentPrice = getDefaultPriceDto();
        currentPrices.add(currentPrice);
        final List<PriceDto> newPrices = new ArrayList<>();
        final PriceDto newPrice = getCopy(currentPrice);
        newPrice.setId(currentPrice.getId() + 1);
        newPrice.setProductCode(currentPrice.getProductCode() + "_NEW");
        newPrice.setNew(true);
        newPrices.add(newPrice);

        final List<PriceDto> result = assertDoesNotThrow(() -> aggregatorService.aggregate(currentPrices, newPrices));
        assertTrue(result.contains(currentPrice));
        assertTrue(result.contains(newPrice));
        assertEquals(2, result.size());
        result.forEach(price -> assertDoesNotThrow(() -> validationService.validate(price)));
    }

    @Test
    void checkValid3() {
        final List<PriceDto> currentPrices = new ArrayList<>();
        final PriceDto currentPrice = getDefaultPriceDto();
        currentPrices.add(currentPrice);
        final List<PriceDto> newPrices = Collections.EMPTY_LIST;

        final List<PriceDto> result = assertDoesNotThrow(() -> aggregatorService.aggregate(currentPrices, newPrices));
        assertTrue(result.contains(currentPrice));
        assertEquals(1, result.size());
        result.forEach(price -> assertDoesNotThrow(() -> validationService.validate(price)));
    }

    @Test
    void checkValid4() {
        final List<PriceDto> currentPrices = Collections.EMPTY_LIST;
        final List<PriceDto> newPrices = new ArrayList<>();
        final PriceDto newPrice = getDefaultPriceDto();
        newPrice.setNew(true);
        newPrices.add(newPrice);

        final List<PriceDto> result = assertDoesNotThrow(() -> aggregatorService.aggregate(currentPrices, newPrices));
        assertTrue(result.contains(newPrice));
        assertEquals(1, result.size());
        result.forEach(price -> assertDoesNotThrow(() -> validationService.validate(price)));
    }

    @Test
    void checkValid5() {
        final List<PriceDto> currentPrices = new ArrayList<>();
        final PriceDto currentPrice = getDefaultPriceDto();
        currentPrices.add(currentPrice);
        final List<PriceDto> newPrices = new ArrayList<>();
        final PriceDto newPrice = getCopy(currentPrice);
        newPrice.setId(currentPrice.getId() + 1);
        newPrice.setValue(currentPrice.getValue() * 2);
        newPrice.setNew(true);
        newPrices.add(newPrice);

        final List<PriceDto> result = assertDoesNotThrow(() -> aggregatorService.aggregate(currentPrices, newPrices));
        assertTrue(result.contains(newPrice));
        assertEquals(1, result.size());
        result.forEach(price -> assertDoesNotThrow(() -> validationService.validate(price)));
    }

    @Test
    void checkValid6() {
        final List<PriceDto> currentPrices = new ArrayList<>();
        final PriceDto currentPrice = getDefaultPriceDto();
        currentPrices.add(currentPrice);
        final List<PriceDto> newPrices = new ArrayList<>();
        final PriceDto newPrice = getCopy(currentPrice);
        newPrice.setId(currentPrice.getId() + 1);
        newPrice.setBegin(currentPrice.getBegin().minusSeconds(1));
        newPrice.setEnd(currentPrice.getEnd().plusSeconds(1));
        newPrice.setValue(currentPrice.getValue() * 2);
        newPrice.setNew(true);
        newPrices.add(newPrice);

        final List<PriceDto> result = assertDoesNotThrow(() -> aggregatorService.aggregate(currentPrices, newPrices));
        assertTrue(result.contains(newPrice));
        assertEquals(1, result.size());
        result.forEach(price -> assertDoesNotThrow(() -> validationService.validate(price)));
    }

    @Test
    void checkValid7() {
        final List<PriceDto> currentPrices = new ArrayList<>();
        final PriceDto currentPrice = getDefaultPriceDto();
        currentPrices.add(currentPrice);
        final List<PriceDto> newPrices = new ArrayList<>();
        final PriceDto newPrice = getCopy(currentPrice);
        newPrice.setId(currentPrice.getId() + 1);
        newPrice.setBegin(currentPrice.getBegin().minusSeconds(1));
        newPrice.setValue(currentPrice.getValue() * 2);
        newPrice.setNew(true);
        newPrices.add(newPrice);

        final List<PriceDto> result = assertDoesNotThrow(() -> aggregatorService.aggregate(currentPrices, newPrices));
        assertTrue(result.contains(newPrice));
        assertEquals(1, result.size());
        result.forEach(price -> assertDoesNotThrow(() -> validationService.validate(price)));
    }

    @Test
    void checkValid8() {
        final List<PriceDto> currentPrices = new ArrayList<>();
        final PriceDto currentPrice = getDefaultPriceDto();
        currentPrices.add(currentPrice);
        final List<PriceDto> newPrices = new ArrayList<>();
        final PriceDto newPrice = getCopy(currentPrice);
        newPrice.setId(currentPrice.getId() + 1);
        newPrice.setEnd(currentPrice.getEnd().plusSeconds(1));
        newPrice.setValue(currentPrice.getValue() * 2);
        newPrice.setNew(true);
        newPrices.add(newPrice);

        final List<PriceDto> result = assertDoesNotThrow(() -> aggregatorService.aggregate(currentPrices, newPrices));
        assertTrue(result.contains(newPrice));
        assertEquals(1, result.size());
        result.forEach(price -> assertDoesNotThrow(() -> validationService.validate(price)));
    }

    @Test
    void checkValid9() {

        final int dateShiftDays = 1;

        final List<PriceDto> currentPrices = new ArrayList<>();
        final PriceDto currentPrice = getDefaultPriceDto();
        currentPrices.add(currentPrice);
        final List<PriceDto> newPrices = new ArrayList<>();
        final PriceDto newPrice = getCopy(currentPrice);
        newPrice.setId(currentPrice.getId() + 1);
        newPrice.setBegin(currentPrice.getBegin().minusDays(dateShiftDays));
        newPrice.setEnd(currentPrice.getEnd().minusDays(dateShiftDays));
        newPrice.setValue(currentPrice.getValue() * 2);
        newPrice.setNew(true);
        newPrices.add(newPrice);

        final PriceDto expected = getCopy(currentPrice);
        expected.setBegin(newPrice.getEnd().plusSeconds(1));

        final List<PriceDto> result = assertDoesNotThrow(() -> aggregatorService.aggregate(currentPrices, newPrices));
        assertTrue(result.contains(expected));
        assertTrue(result.contains(newPrice));
        assertEquals(2, result.size());
        result.forEach(price -> assertDoesNotThrow(() -> validationService.validate(price)));
    }

    @Test
    void checkValid10() {

        final int dateShiftDays = 1;

        final List<PriceDto> currentPrices = new ArrayList<>();
        final PriceDto currentPrice = getDefaultPriceDto();
        currentPrices.add(currentPrice);
        final List<PriceDto> newPrices = new ArrayList<>();
        final PriceDto newPrice = getCopy(currentPrice);
        newPrice.setId(currentPrice.getId() + 1);
        newPrice.setBegin(currentPrice.getBegin().plusDays(dateShiftDays));
        newPrice.setEnd(currentPrice.getEnd().plusDays(dateShiftDays));
        newPrice.setValue(currentPrice.getValue() * 2);
        newPrice.setNew(true);
        newPrices.add(newPrice);

        final PriceDto expected = getCopy(currentPrice);
        expected.setEnd(newPrice.getBegin().minusSeconds(1));

        final List<PriceDto> result = assertDoesNotThrow(() -> aggregatorService.aggregate(currentPrices, newPrices));
        assertTrue(result.contains(newPrice));
        assertTrue(result.contains(expected));
        assertEquals(2, result.size());
        result.forEach(price -> assertDoesNotThrow(() -> validationService.validate(price)));
    }

    @Test
    void checkValid11() {

        final int dateShiftSeconds = 1;

        final List<PriceDto> currentPrices = new ArrayList<>();
        final PriceDto currentPrice = getDefaultPriceDto();
        currentPrices.add(currentPrice);
        final List<PriceDto> newPrices = new ArrayList<>();
        final PriceDto newPrice = getCopy(currentPrice);
        newPrice.setId(currentPrice.getId() + 1);
        newPrice.setBegin(currentPrice.getBegin().plusSeconds(dateShiftSeconds));
        newPrice.setEnd(currentPrice.getEnd().plusSeconds(dateShiftSeconds));
        newPrice.setValue(currentPrice.getValue() * 2);
        newPrice.setNew(true);
        newPrices.add(newPrice);

        final PriceDto expected = getCopy(currentPrice);
        expected.setEnd(newPrice.getBegin().minusSeconds(1));

        final List<PriceDto> result = assertDoesNotThrow(() -> aggregatorService.aggregate(currentPrices, newPrices));
        assertTrue(result.contains(newPrice));
        assertTrue(result.contains(expected));
        assertEquals(2, result.size());
        result.forEach(price -> assertDoesNotThrow(() -> validationService.validate(price)));
    }

    @Test
    void checkValid12() {

        final int dateShiftSeconds = 1;

        final List<PriceDto> currentPrices = new ArrayList<>();
        final PriceDto currentPrice = getDefaultPriceDto();
        currentPrices.add(currentPrice);
        final List<PriceDto> newPrices = new ArrayList<>();
        final PriceDto newPrice = getCopy(currentPrice);
        newPrice.setId(currentPrice.getId() + 1);
        newPrice.setBegin(currentPrice.getBegin().minusSeconds(dateShiftSeconds));
        newPrice.setEnd(currentPrice.getEnd().minusSeconds(dateShiftSeconds));
        newPrice.setValue(currentPrice.getValue() * 2);
        newPrice.setNew(true);
        newPrices.add(newPrice);

        final PriceDto expected = getCopy(currentPrice);
        expected.setBegin(newPrice.getEnd().plusSeconds(1));
        log.info(expected.toString());

        final List<PriceDto> result = assertDoesNotThrow(() -> aggregatorService.aggregate(currentPrices, newPrices));
        assertTrue(result.contains(newPrice));
        assertTrue(result.contains(expected));
        assertEquals(2, result.size());
        result.forEach(price -> assertDoesNotThrow(() -> validationService.validate(price)));
    }

    @Test
    void checkValid13() {

        final List<PriceDto> currentPrices = new ArrayList<>();
        final PriceDto currentPrice = getDefaultPriceDto();
        currentPrices.add(currentPrice);
        currentPrices.forEach(price -> log.info("current: " + price));

        final List<PriceDto> newPrices = new ArrayList<>();
        final PriceDto newPrice = getCopy(currentPrice);
        newPrice.setId(newPrice.getId() + 1);
        newPrice.setValue(newPrice.getValue() * 2);
        newPrice.setNew(true);
        newPrice.setBegin(LocalDateTime.of(2000, 6, 1, 0,0,0,0));
        newPrice.setBegin(LocalDateTime.of(2000, 6, 30, 23,59,59,0));
        newPrices.add(newPrice);
        newPrices.forEach(price -> log.info("new: " + price));

        final List<PriceDto> expectedPrices = new ArrayList<>();
        PriceDto expectedPrice;

        expectedPrice = getCopy(currentPrice);
        expectedPrice.setEnd(newPrice.getBegin().minusSeconds(1));
        expectedPrices.add(expectedPrice);

        expectedPrices.add(newPrice);

        expectedPrice = getCopy(currentPrice);
        expectedPrice.setId(null);
        expectedPrice.setBegin(newPrice.getEnd().plusSeconds(1));
        expectedPrices.add(expectedPrice);

        final List<PriceDto> result = assertDoesNotThrow(() -> aggregatorService.aggregate(currentPrices, newPrices));

        result.forEach(price -> log.info("result: " + price));
        expectedPrices.forEach(price -> log.info("expected: " + price));

        assertTrue(result.containsAll(expectedPrices));
//        expectedPrices.forEach(price -> assertTrue(result.contains(price)));
        assertEquals(expectedPrices.size(), result.size());
        result.forEach(price -> assertDoesNotThrow(() -> validationService.validate(price)));
    }

}