package bogdanov.csi.test.dto;

import lombok.Data;

import java.time.LocalDateTime;

/** DTO цены продукта */
@Data
public class PriceDto {

    /** идентификатор в БД */
    private long id;
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

}
