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
        newPrice.setEnd(LocalDateTime.of(2000, 6, 30, 23,59,59,0));
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
        assertEquals(expectedPrices.size(), result.size());
        result.forEach(price -> assertDoesNotThrow(() -> validationService.validate(price)));
    }

    @Test
    void checkValid14() {
        final List<PriceDto> currentPrices = new ArrayList<>();

        //region текущие цены
        PriceDto currentPrice;

        currentPrice = new PriceDto();
        currentPrice.setId(1L);
        currentPrice.setProductCode("122856");
        currentPrice.setNumber(1);
        currentPrice.setDepart(1);
        currentPrice.setBegin(LocalDateTime.of(2013, 1, 1, 0, 0, 0));
        currentPrice.setEnd(LocalDateTime.of(2013, 1, 31, 23, 59, 59));
        currentPrice.setValue(11000);
        currentPrices.add(currentPrice);

        currentPrice = new PriceDto();
        currentPrice.setId(2L);
        currentPrice.setProductCode("122856");
        currentPrice.setNumber(2);
        currentPrice.setDepart(1);
        currentPrice.setBegin(LocalDateTime.of(2013, 1, 10, 0, 0, 0));
        currentPrice.setEnd(LocalDateTime.of(2013, 1, 20, 23, 59, 59));
        currentPrice.setValue(99000);
        currentPrices.add(currentPrice);

        currentPrice = new PriceDto();
        currentPrice.setId(3L);
        currentPrice.setProductCode("6654");
        currentPrice.setNumber(1);
        currentPrice.setDepart(2);
        currentPrice.setBegin(LocalDateTime.of(2013, 1, 1, 0, 0, 0));
        currentPrice.setEnd(LocalDateTime.of(2013, 1, 31, 23, 59, 59));
        currentPrice.setValue(5000);
        currentPrices.add(currentPrice);

        //endregion

        final List<PriceDto> newPrices = new ArrayList<>();

        //region новые цены
        PriceDto newPrice;

        newPrice = new PriceDto();
        newPrice.setId(4L);
        newPrice.setProductCode("122856");
        newPrice.setNumber(1);
        newPrice.setDepart(1);
        newPrice.setBegin(LocalDateTime.of(2013, 1, 20, 0, 0, 0));
        newPrice.setEnd(LocalDateTime.of(2013, 2, 20, 23, 59, 59));
        newPrice.setValue(11000);
        newPrices.add(newPrice);

        newPrice = new PriceDto();
        newPrice.setId(5L);
        newPrice.setProductCode("122856");
        newPrice.setNumber(2);
        newPrice.setDepart(1);
        newPrice.setBegin(LocalDateTime.of(2013, 1, 15, 0, 0, 0));
        newPrice.setEnd(LocalDateTime.of(2013, 1, 25, 23, 59, 59));
        newPrice.setValue(92000);
        newPrices.add(newPrice);

        newPrice = new PriceDto();
        newPrice.setId(6L);
        newPrice.setProductCode("6654");
        newPrice.setNumber(1);
        newPrice.setDepart(2);
        newPrice.setBegin(LocalDateTime.of(2013, 1, 12, 0, 0, 0));
        newPrice.setEnd(LocalDateTime.of(2013, 1, 13, 23, 59, 59));
        newPrice.setValue(4000);
        newPrices.add(newPrice);

        //endregion

        final List<PriceDto> expectedPrices = new ArrayList<>();

        //region ожидаемые цены
        PriceDto expectedPrice;

        expectedPrice = new PriceDto();
        expectedPrice.setId(1L);
        expectedPrice.setProductCode("122856");
        expectedPrice.setNumber(1);
        expectedPrice.setDepart(1);
        expectedPrice.setBegin(LocalDateTime.of(2013, 1, 1, 0, 0, 0));
        expectedPrice.setEnd(LocalDateTime.of(2013, 2, 20, 23, 59, 59));
        expectedPrice.setValue(11000);
        expectedPrices.add(expectedPrice);

        expectedPrice = new PriceDto();
        expectedPrice.setId(2L);
        expectedPrice.setProductCode("122856");
        expectedPrice.setNumber(2);
        expectedPrice.setDepart(1);
        expectedPrice.setBegin(LocalDateTime.of(2013, 1, 10, 0, 0, 0));
        expectedPrice.setEnd(LocalDateTime.of(2013, 1, 14, 23, 59, 59));
        expectedPrice.setValue(99000);
        expectedPrices.add(expectedPrice);

        expectedPrice = new PriceDto();
        expectedPrice.setId(null);
        expectedPrice.setProductCode("122856");
        expectedPrice.setNumber(2);
        expectedPrice.setDepart(1);
        expectedPrice.setBegin(LocalDateTime.of(2013, 1, 15, 0, 0, 0));
        expectedPrice.setEnd(LocalDateTime.of(2013, 1, 25, 23, 59, 59));
        expectedPrice.setValue(92000);
        expectedPrices.add(expectedPrice);

        expectedPrice = new PriceDto();
        expectedPrice.setId(3L);
        expectedPrice.setProductCode("6654");
        expectedPrice.setNumber(1);
        expectedPrice.setDepart(2);
        expectedPrice.setBegin(LocalDateTime.of(2013, 1, 1, 0, 0, 0));
        expectedPrice.setEnd(LocalDateTime.of(2013, 1, 11, 23, 59, 59));
        expectedPrice.setValue(5000);
        expectedPrices.add(expectedPrice);

        expectedPrice = new PriceDto();
        expectedPrice.setId(null);
        expectedPrice.setProductCode("6654");
        expectedPrice.setNumber(1);
        expectedPrice.setDepart(2);
        expectedPrice.setBegin(LocalDateTime.of(2013, 1, 12, 0, 0, 0));
        expectedPrice.setEnd(LocalDateTime.of(2013, 1, 13, 23, 59, 59));
        expectedPrice.setValue(4000);
        expectedPrices.add(expectedPrice);

        expectedPrice = new PriceDto();
        expectedPrice.setId(null);
        expectedPrice.setProductCode("6654");
        expectedPrice.setNumber(1);
        expectedPrice.setDepart(2);
        expectedPrice.setBegin(LocalDateTime.of(2013, 1, 14, 0, 0, 0));
        expectedPrice.setEnd(LocalDateTime.of(2013, 1, 31, 23, 59, 59));
        expectedPrice.setValue(5000);
        expectedPrices.add(expectedPrice);

        expectedPrices.forEach(price -> {
            price.setId(null);
            price.setNew(false);
        });

        //endregion

        final List<PriceDto> result = assertDoesNotThrow(() -> aggregatorService.aggregate(currentPrices, newPrices));
        result.forEach(price -> {
            price.setId(null);
            price.setNew(false);
        });

        assertTrue(result.containsAll(expectedPrices));
        assertEquals(expectedPrices.size(), result.size());
        result.forEach(price -> assertDoesNotThrow(() -> validationService.validate(price)));
    }

    @Test
    void checkValid15() {
        final List<PriceDto> currentPrices = new ArrayList<>();

        //region текущие цены
        PriceDto currentPrice;

        currentPrice = new PriceDto();
        currentPrice.setId(1L);
        currentPrice.setProductCode("1");
        currentPrice.setNumber(1);
        currentPrice.setDepart(1);
        currentPrice.setBegin(LocalDateTime.of(2013, 1, 1, 0, 0, 0));
        currentPrice.setEnd(LocalDateTime.of(2013, 9, 30, 23, 59, 59));
        currentPrice.setValue(100);
        currentPrices.add(currentPrice);

        currentPrice = getCopy(currentPrice);
        currentPrice.setId(2L);
        currentPrice.setBegin(LocalDateTime.of(2013, 10, 1, 0, 0, 0));
        currentPrice.setEnd(LocalDateTime.of(2013, 12, 31, 23, 59, 59));
        currentPrice.setValue(120);
        currentPrices.add(currentPrice);

        //endregion

        final List<PriceDto> newPrices = new ArrayList<>();

        //region новые цены
        PriceDto newPrice;

        newPrice = getCopy(currentPrice);
        newPrice.setId(3L);
        newPrice.setBegin(LocalDateTime.of(2013, 6, 1, 0, 0, 0));
        newPrice.setEnd(LocalDateTime.of(2013, 10, 31, 23, 59, 59));
        newPrice.setValue(110);
        newPrices.add(newPrice);

        //endregion

        final List<PriceDto> expectedPrices = new ArrayList<>();

        //region ожидаемые цены
        PriceDto expectedPrice;

        expectedPrice = getCopy(currentPrice);
        expectedPrice.setBegin(LocalDateTime.of(2013, 1, 1, 0, 0, 0));
        expectedPrice.setEnd(LocalDateTime.of(2013, 5, 31, 23, 59, 59));
        expectedPrice.setValue(100);
        expectedPrices.add(expectedPrice);

        expectedPrice = getCopy(currentPrice);
        expectedPrice.setBegin(LocalDateTime.of(2013, 6, 1, 0, 0, 0));
        expectedPrice.setEnd(LocalDateTime.of(2013, 10, 31, 23, 59, 59));
        expectedPrice.setValue(110);
        expectedPrices.add(expectedPrice);

        expectedPrice = getCopy(currentPrice);
        expectedPrice.setBegin(LocalDateTime.of(2013, 11, 1, 0, 0, 0));
        expectedPrice.setEnd(LocalDateTime.of(2013, 12, 31, 23, 59, 59));
        expectedPrice.setValue(120);
        expectedPrices.add(expectedPrice);

        expectedPrices.forEach(price -> {
            price.setId(null);
            price.setNew(false);
        });

        //endregion

        expectedPrices.forEach(price -> log.info("expected: " + price));

        final List<PriceDto> result = assertDoesNotThrow(() -> aggregatorService.aggregate(currentPrices, newPrices));
        result.forEach(price -> {
            price.setId(null);
            price.setNew(false);
        });

        result.forEach(price -> log.info("  result: " + price));

        assertTrue(result.containsAll(expectedPrices));
        assertEquals(expectedPrices.size(), result.size());
        result.forEach(price -> assertDoesNotThrow(() -> validationService.validate(price)));
    }

    @Test
    void checkValid16() {
        final List<PriceDto> currentPrices = new ArrayList<>();

        //region текущие цены
        PriceDto currentPrice;

        currentPrice = new PriceDto();
        currentPrice.setId(1L);
        currentPrice.setProductCode("1");
        currentPrice.setNumber(1);
        currentPrice.setDepart(1);
        currentPrice.setBegin(LocalDateTime.of(2013, 1, 1, 0, 0, 0));
        currentPrice.setEnd(LocalDateTime.of(2013, 3, 31, 23, 59, 59));
        currentPrice.setValue(80);
        currentPrices.add(currentPrice);

        currentPrice = getCopy(currentPrice);
        currentPrice.setId(2L);
        currentPrice.setBegin(LocalDateTime.of(2013, 4, 1, 0, 0, 0));
        currentPrice.setEnd(LocalDateTime.of(2013, 7, 31, 23, 59, 59));
        currentPrice.setValue(87);
        currentPrices.add(currentPrice);

        currentPrice = getCopy(currentPrice);
        currentPrice.setId(2L);
        currentPrice.setBegin(LocalDateTime.of(2013, 8, 1, 0, 0, 0));
        currentPrice.setEnd(LocalDateTime.of(2013, 12, 31, 23, 59, 59));
        currentPrice.setValue(90);
        currentPrices.add(currentPrice);

        //endregion

        final List<PriceDto> newPrices = new ArrayList<>();

        //region новые цены
        PriceDto newPrice;

        newPrice = getCopy(currentPrice);
        newPrice.setId(4L);
        newPrice.setBegin(LocalDateTime.of(2013, 2, 20, 0, 0, 0));
        newPrice.setEnd(LocalDateTime.of(2013, 5, 19, 23, 59, 59));
        newPrice.setValue(80);
        newPrices.add(newPrice);

        newPrice = getCopy(currentPrice);
        newPrice.setId(5L);
        newPrice.setBegin(LocalDateTime.of(2013, 5, 20, 0, 0, 0));
        newPrice.setEnd(LocalDateTime.of(2013, 11, 19, 23, 59, 59));
        newPrice.setValue(85);
        newPrices.add(newPrice);

        //endregion

        final List<PriceDto> expectedPrices = new ArrayList<>();

        //region ожидаемые цены
        PriceDto expectedPrice;

        expectedPrice = getCopy(currentPrice);
        expectedPrice.setBegin(LocalDateTime.of(2013, 1, 1, 0, 0, 0));
        expectedPrice.setEnd(LocalDateTime.of(2013, 5, 19, 23, 59, 59));
        expectedPrice.setValue(80);
        expectedPrices.add(expectedPrice);

        expectedPrice = getCopy(currentPrice);
        expectedPrice.setBegin(LocalDateTime.of(2013, 5, 20, 0, 0, 0));
        expectedPrice.setEnd(LocalDateTime.of(2013, 11, 19, 23, 59, 59));
        expectedPrice.setValue(85);
        expectedPrices.add(expectedPrice);

        expectedPrice = getCopy(currentPrice);
        expectedPrice.setBegin(LocalDateTime.of(2013, 11, 20, 0, 0, 0));
        expectedPrice.setEnd(LocalDateTime.of(2013, 12, 31, 23, 59, 59));
        expectedPrice.setValue(90);
        expectedPrices.add(expectedPrice);

        expectedPrices.forEach(price -> {
            price.setId(null);
            price.setNew(false);
        });

        //endregion

        expectedPrices.forEach(price -> log.info("expected: " + price));

        final List<PriceDto> result = assertDoesNotThrow(() -> aggregatorService.aggregate(currentPrices, newPrices));
        result.forEach(price -> {
            price.setId(null);
            price.setNew(false);
        });

        result.forEach(price -> log.info("  result: " + price));

        assertTrue(result.containsAll(expectedPrices));
        assertEquals(expectedPrices.size(), result.size());
        result.forEach(price -> assertDoesNotThrow(() -> validationService.validate(price)));
    }

}