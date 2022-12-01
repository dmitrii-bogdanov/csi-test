package bogdanov.csi.test.util;

import java.time.LocalDateTime;

public class DateUtil {

    public static LocalDateTime min(final LocalDateTime date1, final LocalDateTime date2) {
        final LocalDateTime min = date1.isBefore(date2) ? date1 : date2;
        return LocalDateTime.of(min.toLocalDate(), min.toLocalTime());
    }

    public static LocalDateTime max(final LocalDateTime date1, final LocalDateTime date2) {
        final LocalDateTime max = date1.isAfter(date2) ? date1 : date2;
        return LocalDateTime.of(max.toLocalDate(), max.toLocalTime());
    }

}
