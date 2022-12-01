package bogdanov.csi.test.validators;

import bogdanov.csi.test.dto.PriceDto;
import bogdanov.csi.test.exceptions.price.InvalidPriceException;
import org.springframework.stereotype.Service;

@Service
public interface PriceValidationServiceInterface {

    void validate(PriceDto price) throws InvalidPriceException;

}
