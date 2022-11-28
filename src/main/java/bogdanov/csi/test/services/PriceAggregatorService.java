package bogdanov.csi.test.services;

import bogdanov.csi.test.dto.PriceDto;

import java.util.List;

public class PriceAggregatorService implements PriceAggregatorServiceInterface {

    @Override
    public List<PriceDto> aggregate(List<PriceDto> currentPrices, List<PriceDto> newPrices) {
        return null;
    }

}
