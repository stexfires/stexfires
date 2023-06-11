package stexfires.examples.javatest;

import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "MagicNumber", "StaticCollection", "SpellCheckingInspection"})
public final class DateTimeFormatterTest {

    private DateTimeFormatterTest() {
    }

    private static final List<String> PATTERN = List.of("yyyyMMddHHmmss", "dd.MM.yyyy HH:mm:ss.SSS", "QQQQ yyyy", "QQ/yy MMM",
            "VV",
            "v - vvvv",
            "x - xx - xxx - xxxx",
            "X - XX - XXX - XXXX",
            "z - zzzz",
            "Z - ZZZZ");
    private static final List<String> LOCALIZED_PATTERN = List.of("Gy", "y", "yyyy", "yMMMM", "yMd", "yMEd", "yMdHms", "yMdHmsv");

    private static void showDateTimeFormatterOfPattern() {
        System.out.println("-showDateTimeFormatterOfPattern---");

        ZonedDateTime zonedDateTime1 = ZonedDateTime.of(2022, 12, 31, 20, 45, 35, 123456789, ZoneId.of("GMT+0"));
        ZonedDateTime zonedDateTime2 = ZonedDateTime.of(2022, 12, 31, 20, 45, 35, 123456789, ZoneId.of("GMT+1"));
        ZonedDateTime zonedDateTime3 = ZonedDateTime.of(2022, 12, 31, 20, 45, 35, 123456789, ZoneId.of("Europe/Berlin"));
        printlnPattern(zonedDateTime1);
        printlnPattern(zonedDateTime2);
        printlnPattern(zonedDateTime3);
    }

    private static void printlnPattern(ZonedDateTime zonedDateTime) {
        System.out.println(zonedDateTime);
        for (String pattern : PATTERN) {
            System.out.println("Pattern: " + pattern);
            System.out.println(zonedDateTime.format(DateTimeFormatter.ofPattern(pattern, Locale.US)));
            System.out.println(zonedDateTime.format(DateTimeFormatter.ofPattern(pattern, Locale.GERMANY)));
            System.out.println(zonedDateTime.format(DateTimeFormatter.ofPattern(pattern, Locale.FRANCE)));
            System.out.println(zonedDateTime.format(DateTimeFormatter.ofPattern(pattern, Locale.JAPAN)));
        }
    }

    private static void showDateTimeFormatterOfLocalizedPattern() {
        System.out.println("-showDateTimeFormatterOfLocalizedPattern---");

        ZonedDateTime zonedDateTime = ZonedDateTime.of(2022, 12, 31, 20, 45, 35, 123456789, ZoneId.of("GMT+2"));

        printlnLocalizedPattern(zonedDateTime);
    }

    private static void printlnLocalizedPattern(ZonedDateTime zonedDateTime) {
        System.out.println(zonedDateTime);
        for (String pattern : LOCALIZED_PATTERN) {
            printlnLocalizedPattern(zonedDateTime, pattern, Locale.US);
            printlnLocalizedPattern(zonedDateTime, pattern, Locale.GERMANY);
            printlnLocalizedPattern(zonedDateTime, pattern, Locale.FRANCE);
            printlnLocalizedPattern(zonedDateTime, pattern, Locale.JAPAN);
        }
    }

    private static void printlnLocalizedPattern(ZonedDateTime zonedDateTime, String pattern, Locale locale) {
        try {
            System.out.println(pattern + " " + locale +
                    ":\t  " + zonedDateTime.format(DateTimeFormatter.ofLocalizedPattern(pattern).withLocale(locale)));
        } catch (DateTimeException e) {
            System.out.println(pattern + " " + locale + ":\t  " + e.getMessage());
        }
    }

    public static void main(String... args) {
        showDateTimeFormatterOfPattern();
        showDateTimeFormatterOfLocalizedPattern();
    }

}
