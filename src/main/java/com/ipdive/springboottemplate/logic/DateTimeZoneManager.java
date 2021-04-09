package com.ipdive.springboottemplate.logic;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateTimeZoneManager {

    public static DateTimeFormatter getDateTimeFormatter() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
                        .withLocale(Locale.UK)
                        .withZone(ZoneId.systemDefault());
        return formatter;
    }
}
