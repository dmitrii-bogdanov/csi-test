package bogdanov.csi.test.util.price;

import lombok.Data;

/**
 * Содержит информацию о коде продукта, номере цены и отдела
 */
@Data
public class PricePlacement {

    /** код товара */
    private final String productCode;
    /** номер цены */
    private final int number;
    /** номер отдела */
    private final int depart;

    public PricePlacement(final String productCode, final int number, final int depart) {
        this.productCode = productCode;
        this.number = number;
        this.depart = depart;
    }

}
