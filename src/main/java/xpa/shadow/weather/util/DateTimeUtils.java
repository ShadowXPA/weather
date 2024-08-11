package xpa.shadow.weather.util;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public final class DateTimeUtils {

    public static final DateTimeFormatter DATE_TIME_FORMATTER_SHORT = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.SHORT);

    private DateTimeUtils() {
    }
}
