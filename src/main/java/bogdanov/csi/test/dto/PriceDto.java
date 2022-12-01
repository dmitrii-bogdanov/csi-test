package bogdanov.csi.test.dto;

import bogdanov.csi.test.exceptions.price.InvalidPriceException;
import lombok.Data;
import org.springframework.util.comparator.Comparators;

import java.time.LocalDateTime;

/** DTO цены продукта */
@Data
public class PriceDto implements Comparable{

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

    @Override
    public int compareTo(Object o) {

        if (o == null) {
            throw new InvalidPriceException("Object compared against this one is null");
        }

        final PriceDto other = (PriceDto) o;

        int result;

        result = this.productCode.compareTo(other.productCode);
        if (result != 0) {
            return  result;
        }

        result = Comparators.comparable().compare(this.depart, other.depart);
        if (result != 0) {
            return result;
        }

        result = Comparators.comparable().compare(this.number, other.number);
        if (result != 0) {
            return result;
        }

        result = this.begin.compareTo(other.begin);
        if (result != 0) {
            return result;
        }

        result = this.end.compareTo(other.end);
        if (result != 0) {
            return result;
        }

        result = Comparators.comparable().compare(this.value, other.value);
        if (result != 0) {
            return result;
        }

        return 0;

    }
}
