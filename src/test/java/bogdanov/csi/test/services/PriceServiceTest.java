package bogdanov.csi.test.services;

import bogdanov.csi.test.dto.PriceDto;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class PriceServiceTest {

    protected PriceDto getDefaultPriceDto() {
        final PriceDto price = new PriceDto();
        price.setId(1L);
        price.setProductCode("P1");
        price.setNumber(1);
        price.setDepart(1);
        price.setBegin(LocalDateTime.of(2000, 1, 1, 0, 0, 0, 0));
        price.setEnd(LocalDateTime.of(2000, 12,31,23,59,59,0));
        price.setValue(1);

        return price;
    }

    protected PriceDto getCopy(final PriceDto price) {
        final PriceDto copy = new PriceDto();
        copy.setId(price.getId());
        copy.setProductCode(price.getProductCode());
        copy.setNumber(price.getNumber());
        copy.setDepart(price.getDepart());
        copy.setBegin(price.getBegin());
        copy.setEnd(price.getEnd());
        copy.setValue(price.getValue());
        copy.setNew(price.isNew());

        return copy;
    }

}
