package bogdanov.csi.test.services;

import bogdanov.csi.test.dto.PriceDto;
import bogdanov.csi.test.exceptions.price.InvalidPriceException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PriceAggregatorServiceInterface {

    List<PriceDto> aggregate(List<PriceDto> currentPrices, List<PriceDto> newPrices) throws InvalidPriceException;

}
