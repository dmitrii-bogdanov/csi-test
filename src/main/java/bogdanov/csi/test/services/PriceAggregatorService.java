package bogdanov.csi.test.services;

import bogdanov.csi.test.dto.PriceDto;
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

        final Map<PricePlacement, List<PriceDto>> currentPricesByPricePlacement = getPricesByPricePlacement(
                currentPrices);
        final Map<PricePlacement, List<PriceDto>> newPricesByPricePlacement = getPricesByPricePlacement(newPrices);

        sortPrices(currentPricesByPricePlacement);
        sortPrices(newPricesByPricePlacement);

        final List<PriceDto> aggregatedPrices = aggregate(currentPricesByPricePlacement, newPricesByPricePlacement);

        return aggregatedPrices;
    }

    private void validate(final List<PriceDto> prices) throws InvalidPriceException {
        for (final PriceDto price : prices) {
            priceValidationService.validate(price);
        }
    }


    private void sortPrices(final Map<PricePlacement, List<PriceDto>> pricesByPricePlacement) {
        PricePlacement pricePlacement;
        List<PriceDto> prices;
        for (final Map.Entry<PricePlacement, List<PriceDto>> entry : pricesByPricePlacement.entrySet()) {
            pricePlacement = entry.getKey();
            prices = entry.getValue();
            pricesByPricePlacement.put(pricePlacement, sortPrices(prices));
        }
    }

    private List<PriceDto> sortPrices(final List<PriceDto> prices) {
        return prices.stream().sorted().collect(Collectors.toList());
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

    private List<PriceDto> aggregate(final Map<PricePlacement, List<PriceDto>> currentPricesByPricePlacement,
                                     final Map<PricePlacement, List<PriceDto>> newPricesByPricePlacement) {

        final List<PriceDto> result = new ArrayList<>();

        List<PriceDto> currentPrices;
        List<PriceDto> newPrices;
        List<PriceDto> aggregatedPrices;
        for (final PricePlacement pricePlacement : newPricesByPricePlacement.keySet()) {
            currentPrices = currentPricesByPricePlacement.get(pricePlacement);
            newPrices = newPricesByPricePlacement.get(pricePlacement);
            aggregatedPrices = new LinkedList<>();

            if (currentPrices == null || currentPrices.isEmpty()) {
                result.addAll(newPrices);
                continue;
            }

            aggregatedPrices.addAll(currentPrices);

            if (!newPrices.isEmpty()) {

                for (final PriceDto newPrice : newPrices) {
                    applyNewPrice(aggregatedPrices, newPrice);
                }

                aggregatedPrices = combineSamePricesPeriods(aggregatedPrices);
            }

            result.addAll(aggregatedPrices);
        }

        return result;
    }

    private void applyNewPrice(final List<PriceDto> prices, final PriceDto newPrice) {

        removeCoveredPrices(prices, newPrice);
        final boolean hasExtendedCurrentPrice = extendSamePrices(prices, newPrice);
        cutCrossedPrices(prices, newPrice);
        if (!hasExtendedCurrentPrice) {
            prices.add(newPrice);
        }
    }

    /**
     * Удаляет текущие цены, которые полностью перекрываются новой ценой
     *
     * @param prices   - текущие цены
     * @param newPrice - новая цена
     * @return
     */
    private void removeCoveredPrices(final List<PriceDto> prices, final PriceDto newPrice) {
        final Iterator<PriceDto> iterator = prices.iterator();
        PriceDto                 price;
        while (iterator.hasNext()) {
            price = iterator.next();
            if (isCurrentPriceCovered(price, newPrice)) {
                iterator.remove();
            }
        }
    }

    private boolean isCurrentPriceCovered(final PriceDto currentPrice, final PriceDto newPrice) {
        return !currentPrice.getBegin().isBefore(newPrice.getBegin())
               && !currentPrice.getEnd().isAfter(newPrice.getEnd());
    }

    /**
     * Расширяет срок действия текущей цены,
     * если период действия новой цены с тем же значением пересекается или дополняет период текущей
     *
     * @param prices   - текущие цены
     * @param newPrice - новая цена
     * @return true - если новая цена была использована для расширения текующей
     */
    private boolean extendSamePrices(final List<PriceDto> prices, final PriceDto newPrice) {
        final Iterator<PriceDto> iterator                = prices.iterator();
        boolean                  hasExtendedCurrentPrice = false;
        PriceDto                 price;
        while (iterator.hasNext()) {
            price = iterator.next();
            if (!price.equals(newPrice) && arePricesPeriodsExtended(price, newPrice)) {
                hasExtendedCurrentPrice = true;
                extendPeriod(price, newPrice);
            }
        }
        return hasExtendedCurrentPrice;
    }

    private void extendPeriod(final PriceDto price1, final PriceDto price2) {
        price1.setBegin(DateUtil.min(price1.getBegin(), price2.getBegin()));
        price1.setEnd(DateUtil.max(price1.getEnd(), price2.getEnd()));
    }

    private boolean arePricesPeriodsExtended(final PriceDto price1, final PriceDto price2) {
        return price1.getValue() == price2.getValue()
               && (
                       !price1.getBegin().isAfter(price2.getEnd().plusSeconds(1))
                       || !price1.getEnd().isBefore(price2.getBegin().minusSeconds(1))
               );
    }

    /**
     * Обрезает период действия текущей цены при пересечении с новой,
     * значение которой отлично от текущей
     *
     * @param prices   - текущие цены
     * @param newPrice - новая цена
     * @return
     */
    private void cutCrossedPrices(final List<PriceDto> prices, final PriceDto newPrice) {
        final Iterator<PriceDto> iterator = prices.iterator();
        PriceDto                 price;
        while (iterator.hasNext()) {
            price = iterator.next();
            if ((price.getValue() != newPrice.getValue())
                && arePricesPeriodsCrossed(price, newPrice)) {
                cutPeriod(price, newPrice);
            }
        }
    }

    private void cutPeriod(final PriceDto price, final PriceDto newPrice) {
        if (price.getBegin().isAfter(newPrice.getBegin())) {
            price.setBegin(newPrice.getEnd());
        } else {
            price.setEnd(newPrice.getBegin());
        }
    }

    private boolean arePricesPeriodsCrossed(final PriceDto price1, final PriceDto price2) {
        return !price1.getBegin().isBefore(price2.getBegin())
               || !price1.getEnd().isBefore(price2.getBegin());
    }

    /**
     * Объединяет цены внутри списка, если их значения равны
     * @param prices
     * @return
     */
    private List<PriceDto> combineSamePricesPeriods(final List<PriceDto> prices) {

        final List<PriceDto> result      = new LinkedList<>(prices);
        Iterator<PriceDto>   tmpIterator = result.iterator();
        PriceDto             tmp;

        while (tmpIterator.hasNext()) {
            tmp = tmpIterator.next();
            if (extendSamePrices(result, tmp)) {
                tmpIterator.remove();
            }
        }

        return result;
    }

}
