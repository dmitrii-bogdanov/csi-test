package bogdanov.csi.test.dto;

import bogdanov.csi.test.exceptions.price.InvalidPriceException;
import lombok.Data;
import org.springframework.util.comparator.Comparators;

import java.time.LocalDateTime;

/** DTO цены продукта */
@Data
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

}
