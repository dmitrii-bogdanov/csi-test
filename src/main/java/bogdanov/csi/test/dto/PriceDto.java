package bogdanov.csi.test.dto;

import bogdanov.csi.test.exceptions.price.InvalidPriceException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.comparator.Comparators;

import java.time.LocalDateTime;

/** DTO цены продукта */
@Data
@NoArgsConstructor
public class PriceDto{

    /** идентификатор в БД */
    private Long id;
    /** код товара */
    private String productCode;
    /** номер цены */
    private int number;
    /** номер отдела */
    private int depart;
    /** начало действия */
    private LocalDateTime begin;
    /** конец действия */
    private LocalDateTime end;
    /** значение цены в копейках */
    private long value;
    /** является ли цена новой */
    private boolean isNew;

    public PriceDto(final PriceDto price, final Long id, final LocalDateTime begin, final LocalDateTime end) {
        this.id = id;
        this.productCode = price.productCode;
        this.number = price.number;
        this.depart = price.depart;
        this.begin = begin;
        this.end = end;
        this.value = price.value;
        this.isNew = price.isNew;
    }

}
