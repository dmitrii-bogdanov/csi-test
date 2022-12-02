package bogdanov.csi.test.util;

import java.time.LocalDateTime;

public class DateUtil {

    public static LocalDateTime min(final LocalDateTime date1, final LocalDateTime date2) {
        final LocalDateTime min = date1.isBefore(date2)
                                  ? date1
                                  : date2;
        return LocalDateTime.of(min.toLocalDate(), min.toLocalTime());
    }

    public static LocalDateTime max(final LocalDateTime date1, final LocalDateTime date2) {
        final LocalDateTime max = date1.isAfter(date2)
                                  ? date1
                                  : date2;
        return LocalDateTime.of(max.toLocalDate(), max.toLocalTime());
    }

    public static boolean isInside(final LocalDateTime date,
                                               final LocalDateTime border1,
                                               final LocalDateTime border2) {
        return (!date.isBefore(border1) && !date.isAfter(border2))
                || (!date.isBefore(border2) && !date.isAfter(border1));
    }

}
