package xpa.shadow.weather.util;

import java.time.format.DateTimeFormatter;

public final class DateTimeUtils {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    private DateTimeUtils() {}
}
