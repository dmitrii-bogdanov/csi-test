package bogdanov.csi.test.services;

import bogdanov.csi.test.dto.PriceDto;
import bogdanov.csi.test.exceptions.price.IntersectedPricesException;
import bogdanov.csi.test.exceptions.price.InvalidPriceException;
import bogdanov.csi.test.util.DateUtil;
import bogdanov.csi.test.util.price.PricePlacement;
import bogdanov.csi.test.services.validators.PriceValidationServiceInterface;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PriceAggregatorService implements PriceAggregatorServiceInterface {

    private final PriceValidationServiceInterface priceValidationService;

    @Override
    public List<PriceDto> aggregate(final List<PriceDto> currentPrices, final List<PriceDto> newPrices)
            throws InvalidPriceException {

        validate(currentPrices);
        validate(newPrices);

        final List<PriceDto> prices = combine(currentPrices, newPrices);

        final Map<PricePlacement, List<PriceDto>> pricesByPricePlacement = getPricesByPricePlacement(prices);

        return aggregate(pricesByPricePlacement);
    }

    private void validate(final List<PriceDto> prices) throws InvalidPriceException {
        if (prices == null) {
            throw new InvalidPriceException("List is null");
        }
        checkPricesAreNotIntersected(prices);
        for (final PriceDto price : prices) {
            priceValidationService.validate(price);
        }
    }

    private void checkPricesAreNotIntersected(final List<PriceDto> prices) throws IntersectedPricesException {
        for (int i = 0; i < prices.size() - 1; i++) {
            if (areIntersected(prices.get(i), prices.get(i + 1))) {
                throw new IntersectedPricesException("Prices are intersected");
            }
        }
    }

    private List<PriceDto> combine(final List<PriceDto> currentPrices, final List<PriceDto> newPrices) {

        final List<PriceDto> combinedPrices = new ArrayList<>();

        combinedPrices.addAll(currentPrices);
        combinedPrices.addAll(newPrices.stream()
                                       .peek(price -> price.setNew(true))
                                       .collect(Collectors.toList()));

        return combinedPrices;
    }

    private void sortPrices(final List<PriceDto> prices) {
        prices.sort(Comparator.comparing(PriceDto::getBegin));
    }

    private Map<PricePlacement, List<PriceDto>> getPricesByPricePlacement(final List<PriceDto> prices) {

        final Map<PricePlacement, List<PriceDto>> pricesByPricePlacement = new HashMap<>();

        PricePlacement pricePlacement;
        for (final PriceDto price : prices) {
            pricePlacement = new PricePlacement(price.getProductCode(), price.getNumber(), price.getDepart());
            pricesByPricePlacement.putIfAbsent(pricePlacement, new ArrayList<>());
            pricesByPricePlacement.get(pricePlacement).add(price);
        }

        return pricesByPricePlacement;
    }

    private List<PriceDto> aggregate(final Map<PricePlacement, List<PriceDto>> pricesByPricePlacement) {

        final List<PriceDto> result = new ArrayList<>();

        for (final List<PriceDto> prices : pricesByPricePlacement.values()) {
            cutIntersectedPrices(prices);
            sortPrices(prices);
            combineSamePricesPeriods(prices);
            result.addAll(prices);
        }

        return result;
    }

    private void cutIntersectedPrices(final List<PriceDto> prices) {
        final Iterator<PriceDto> priceIterator = prices.iterator();
        PriceDto                 price;
        Iterator<PriceDto>       tmpIterator;
        PriceDto                 tmp;
        List<PriceDto>           cutPrices;

        while (priceIterator.hasNext()) {
            price = priceIterator.next();

            tmpIterator = prices.iterator();
            while (tmpIterator.hasNext()) {
                tmp = tmpIterator.next();

                if (price.equals(tmp)) {
                    continue;
                }

                if (!isOnlyOneNew(price, tmp)) {
                    continue;
                }

                if (areIntersected(price, tmp)) {
                    cutPeriods(price, tmp, prices);
                }
            }
        }

    }

    private boolean areIntersected(final PriceDto price1, final PriceDto price2) {
        return DateUtil.isInside(price1.getBegin(), price2.getBegin(), price2.getEnd())
               || DateUtil.isInside(price1.getEnd(), price2.getBegin(), price2.getEnd())
               || DateUtil.isInside(price2.getBegin(), price1.getBegin(), price1.getEnd())
               || DateUtil.isInside(price2.getEnd(), price1.getBegin(), price1.getEnd());
    }

    private boolean isOnlyOneNew(final PriceDto price1, final PriceDto price2) {
        return (price1.isNew() && !price2.isNew())
               || (price2.isNew() && !price1.isNew());
    }

    private void cutPeriods(final PriceDto price1, final PriceDto price2, final List<PriceDto> prices) {

        final PriceDto currentPrice = price1.isNew()
                                      ? price2
                                      : price1;
        final PriceDto newPrice = price1.isNew()
                                  ? price1
                                  : price2;

        if (isCovered(currentPrice, newPrice)) {
            prices.remove(currentPrice);
        } else if (isCovered(newPrice, currentPrice)) {
            currentPrice.setEnd(newPrice.getBegin());

            final PriceDto tmp = new PriceDto(currentPrice,
                                              null,
                                              newPrice.getEnd().plusSeconds(1),
                                              currentPrice.getEnd());

            prices.add(tmp);
        } else if (currentPrice.getBegin().isBefore(newPrice.getBegin())) {
            currentPrice.setEnd(newPrice.getBegin().minusSeconds(1));
        } else {
            currentPrice.setBegin(newPrice.getEnd().plusSeconds(1));
        }
    }

    private boolean isCovered(final PriceDto price1, final PriceDto price2) {
        return DateUtil.isInside(price1.getBegin(), price2.getBegin(), price2.getEnd())
               && DateUtil.isInside(price1.getEnd(), price2.getBegin(), price2.getEnd());
    }

    /**
     * Объединяет цены внутри списка, если их значения равны
     *
     * @param prices
     * @return
     */
    private void combineSamePricesPeriods(final List<PriceDto> prices) {

        final Iterator<PriceDto> priceIterator = prices.iterator();
        PriceDto                 prevPrice     = null;
        PriceDto                 price;

        while (priceIterator.hasNext()) {

            if (prevPrice == null) {
                prevPrice = priceIterator.next();
                continue;
            }

            price = priceIterator.next();

            if ((prevPrice.getValue() == price.getValue())
                && arePeriodsExtendingEachOther(prevPrice, price)) {

                extendPeriod(prevPrice, price);
                priceIterator.remove();
                prevPrice = null;
            } else {
                prevPrice = price;
            }
        }
    }

    private boolean arePeriodsExtendingEachOther(final PriceDto price1, final PriceDto price2) {
        return !price1.getBegin().isAfter(price2.getEnd().plusSeconds(1))
               || !price1.getEnd().isBefore(price2.getBegin().minusSeconds(1));
    }

    private void extendPeriod(final PriceDto price1, final PriceDto price2) {
        price1.setBegin(DateUtil.min(price1.getBegin(), price2.getBegin()));
        price1.setEnd(DateUtil.max(price1.getEnd(), price2.getEnd()));
    }

}
