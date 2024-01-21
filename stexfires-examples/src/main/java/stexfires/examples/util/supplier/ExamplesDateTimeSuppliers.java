package stexfires.examples.util.supplier;

import stexfires.util.supplier.DateTimeSuppliers;
import stexfires.util.supplier.RandomNumberSuppliers;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesDateTimeSuppliers {

    private static final RandomGenerator RANDOM = new Random();
    private static final long STREAM_LIMIT = 10L;
    private static final Instant INSTANT_NOW = Instant.now();
    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    private ExamplesDateTimeSuppliers() {
    }

    private static <T> void generateAndPrintStream(String title, Supplier<? extends T> supplier) {
        System.out.println(title);
        Stream.generate(supplier)
              .limit(STREAM_LIMIT)
              .forEachOrdered(System.out::println);
    }

    private static void showInstantSuppliers() {
        System.out.println("-showInstantSuppliers---");

        generateAndPrintStream("instantOfEpochSecond randomEpochSecond - instantNow + 1 day",
                DateTimeSuppliers.instantOfEpochSecond(
                        DateTimeSuppliers.randomEpochSecond(RANDOM,
                                INSTANT_NOW,
                                INSTANT_NOW.plus(1, ChronoUnit.DAYS))));
        generateAndPrintStream("instantOfEpochMilli randomEpochMilli - instantNow + 1 day",
                DateTimeSuppliers.instantOfEpochMilli(
                        DateTimeSuppliers.randomEpochMilli(RANDOM,
                                INSTANT_NOW,
                                INSTANT_NOW.plus(1, ChronoUnit.DAYS))));
    }

    private static void showLocalDateSuppliers() {
        System.out.println("-showLocalDateSuppliers---");

        LocalDate localDateNow = LocalDate.ofInstant(INSTANT_NOW, ZONE_ID);

        generateAndPrintStream("localDateOfInstant instantOfEpochSecond randomEpochSecond - instantNow + 10 days",
                DateTimeSuppliers.localDateOfInstant(
                        DateTimeSuppliers.instantOfEpochSecond(
                                DateTimeSuppliers.randomEpochSecond(RANDOM,
                                        INSTANT_NOW,
                                        INSTANT_NOW.plus(10, ChronoUnit.DAYS))),
                        ZONE_ID));

        generateAndPrintStream("localDateOfEpochDay randomEpochDay - localDateNow + 10 days",
                DateTimeSuppliers.localDateOfEpochDay(
                        DateTimeSuppliers.randomEpochDay(RANDOM,
                                localDateNow,
                                localDateNow.plusDays(10))));
    }

    private static void showLocalTimeSuppliers() {
        System.out.println("-showLocalTimeSuppliers---");

        LocalTime localTimeNow = LocalTime.ofInstant(INSTANT_NOW, ZONE_ID);

        generateAndPrintStream("localTimeOfInstant instantOfEpochSecond randomEpochSecond - instantNow + 1 hour",
                DateTimeSuppliers.localTimeOfInstant(
                        DateTimeSuppliers.instantOfEpochSecond(
                                DateTimeSuppliers.randomEpochSecond(RANDOM,
                                        INSTANT_NOW,
                                        INSTANT_NOW.plus(1, ChronoUnit.HOURS))),
                        ZONE_ID));

        generateAndPrintStream("localTimeOfInstant instantOfEpochMilli randomEpochMilli - instantNow + 1 hour",
                DateTimeSuppliers.localTimeOfInstant(
                        DateTimeSuppliers.instantOfEpochMilli(
                                DateTimeSuppliers.randomEpochMilli(RANDOM,
                                        INSTANT_NOW,
                                        INSTANT_NOW.plus(1, ChronoUnit.HOURS))),
                        ZONE_ID));

        generateAndPrintStream("localTimeOfSecondOfDay randomSecondOfDay - localTimeNow + 1 hour",
                DateTimeSuppliers.localTimeOfSecondOfDay(
                        DateTimeSuppliers.randomSecondOfDay(RANDOM,
                                localTimeNow,
                                localTimeNow.plusHours(1))));

        generateAndPrintStream("localTimeOfSecondOfDay randomSecondOfDayInclusive - LocalTime.MIN - LocalTime.MAX",
                DateTimeSuppliers.localTimeOfSecondOfDay(
                        DateTimeSuppliers.randomSecondOfDayInclusive(RANDOM,
                                LocalTime.MIN,
                                LocalTime.MAX)));

        generateAndPrintStream("localTimeOfNanoOfDay randomNanoOfDay - localTimeNow + 1 hour",
                DateTimeSuppliers.localTimeOfNanoOfDay(
                        DateTimeSuppliers.randomNanoOfDay(RANDOM,
                                localTimeNow,
                                localTimeNow.plusHours(1))));

        generateAndPrintStream("localTimeOfNanoOfDay randomNanoOfDayInclusive - LocalTime.MIN - LocalTime.MAX",
                DateTimeSuppliers.localTimeOfNanoOfDay(
                        DateTimeSuppliers.randomNanoOfDayInclusive(RANDOM,
                                LocalTime.MIN,
                                LocalTime.MAX)));
    }

    private static void showLocalDateTimeSuppliers() {
        System.out.println("-showLocalDateTimeSuppliers---");

        LocalDate localDateNow = LocalDate.ofInstant(INSTANT_NOW, ZONE_ID);
        LocalTime localTimeNow = LocalTime.ofInstant(INSTANT_NOW, ZONE_ID);
        LocalDateTime localDateTimeNow = LocalDateTime.ofInstant(INSTANT_NOW, ZONE_ID);

        generateAndPrintStream("localDateTimeOfInstant instantOfEpochSecond randomEpochSecond - instantNow + 1 day",
                DateTimeSuppliers.localDateTimeOfInstant(
                        DateTimeSuppliers.instantOfEpochSecond(
                                DateTimeSuppliers.randomEpochSecond(RANDOM,
                                        INSTANT_NOW,
                                        INSTANT_NOW.plus(1, ChronoUnit.DAYS))),
                        ZONE_ID));

        generateAndPrintStream("localDateTimeOfLocalDateAndLocalTime localDateOfEpochDay randomEpochDay - localDateNow + 1 day AND localTimeOfSecondOfDay randomSecondOfDay - localTimeNow + 1 hour",
                DateTimeSuppliers.localDateTimeOfLocaleDateAndLocalTime(
                        DateTimeSuppliers.localDateOfEpochDay(
                                DateTimeSuppliers.randomEpochDay(RANDOM,
                                        localDateNow,
                                        localDateNow.plusDays(10))),
                        DateTimeSuppliers.localTimeOfSecondOfDay(
                                DateTimeSuppliers.randomSecondOfDay(RANDOM,
                                        localTimeNow,
                                        localTimeNow.plusHours(1)))));
    }

    private static void showZonedDateTimeSuppliers() {
        System.out.println("-showZonedDateTimeSuppliers---");

        LocalDate localDateNow = LocalDate.ofInstant(INSTANT_NOW, ZONE_ID);
        LocalTime localTimeNow = LocalTime.ofInstant(INSTANT_NOW, ZONE_ID);
        ZonedDateTime zonedDateTimeNow = ZonedDateTime.ofInstant(INSTANT_NOW, ZONE_ID);

        generateAndPrintStream("zonedDateTimeOfInstant instantOfEpochSecond randomEpochSecond - instantNow + 1 day",
                DateTimeSuppliers.zonedDateTimeOfInstant(
                        DateTimeSuppliers.instantOfEpochSecond(
                                DateTimeSuppliers.randomEpochSecond(RANDOM,
                                        INSTANT_NOW,
                                        INSTANT_NOW.plus(1, ChronoUnit.DAYS))),
                        ZONE_ID));

        generateAndPrintStream("zonedDateTimeOfLocalDateTime localDateTimeOfInstant instantOfEpochSecond randomEpochSecond - instantNow + 1 day",
                DateTimeSuppliers.zonedDateTimeOfLocalDateTime(
                        DateTimeSuppliers.localDateTimeOfInstant(
                                DateTimeSuppliers.instantOfEpochSecond(
                                        DateTimeSuppliers.randomEpochSecond(RANDOM,
                                                INSTANT_NOW,
                                                INSTANT_NOW.plus(1, ChronoUnit.DAYS))),
                                ZONE_ID),
                        ZONE_ID));

        generateAndPrintStream("zonedDateTimeOfLocalDateAndLocalTime localDateOfEpochDay randomEpochDay - localDateNow + 10 days AND localTimeOfSecondOfDay randomSecondOfDay - localTimeNow + 1 hour",
                DateTimeSuppliers.zonedDateTimeOfLocalDateAndLocalTime(
                        DateTimeSuppliers.localDateOfEpochDay(
                                DateTimeSuppliers.randomEpochDay(RANDOM,
                                        localDateNow,
                                        localDateNow.plusDays(10))),
                        DateTimeSuppliers.localTimeOfSecondOfDay(
                                DateTimeSuppliers.randomSecondOfDay(RANDOM,
                                        localTimeNow,
                                        localTimeNow.plusHours(1))),
                        ZONE_ID));
    }

    private static void showYear() {
        System.out.println("-showYear---");

        generateAndPrintStream("year randomPrimitiveInt - 2020-2022",
                DateTimeSuppliers.year(
                        RandomNumberSuppliers.randomPrimitiveInt(RANDOM, 2020, 2022)));

        generateAndPrintStream("year randomYearInclusive - 2020-2022",
                DateTimeSuppliers.year(
                        DateTimeSuppliers.randomYearInclusive(RANDOM, Year.of(2020), Year.of(2022))));
    }

    private static void showMonth() {
        System.out.println("-showMonth---");

        generateAndPrintStream("month randomPrimitiveInt - 9-13",
                DateTimeSuppliers.month(
                        RandomNumberSuppliers.randomPrimitiveInt(RANDOM, 9, 13)));

        generateAndPrintStream("month randomMonthInclusive - SEPTEMBER-DECEMBER",
                DateTimeSuppliers.month(
                        DateTimeSuppliers.randomMonthInclusive(RANDOM, Month.SEPTEMBER, Month.DECEMBER)));
    }

    private static void showYearMonth() {
        System.out.println("-showYearMonth---");

        generateAndPrintStream("yearMonth randomPrimitiveInt - 2020-2024 AND randomPrimitiveInt - 9-13",
                DateTimeSuppliers.yearMonth(
                        RandomNumberSuppliers.randomPrimitiveInt(RANDOM, 2020, 2024),
                        RandomNumberSuppliers.randomPrimitiveInt(RANDOM, 9, 13)));

        generateAndPrintStream("yearMonthOfYearAndMonth randomYearInclusive - 2020-2022 AND randomMonthInclusive - APRIL-JUNE",
                DateTimeSuppliers.yearMonthOfYearAndMonth(
                        DateTimeSuppliers.year(
                                DateTimeSuppliers.randomYearInclusive(RANDOM, Year.of(2020), Year.of(2022))),
                        DateTimeSuppliers.month(
                                DateTimeSuppliers.randomMonthInclusive(RANDOM, Month.APRIL, Month.JUNE))));
    }

    private static void showDayOfWeek() {
        System.out.println("-showDayOfWeek---");

        generateAndPrintStream("dayOfWeek randomPrimitiveInt - 1-3",
                DateTimeSuppliers.dayOfWeek(
                        RandomNumberSuppliers.randomPrimitiveInt(RANDOM, 1, 3)));

        generateAndPrintStream("dayOfWeek randomDayOfWeekInclusive - MONDAY-FRIDAY",
                DateTimeSuppliers.dayOfWeek(
                        DateTimeSuppliers.randomDayOfWeekInclusive(RANDOM, DayOfWeek.MONDAY, DayOfWeek.FRIDAY)));
    }

    private static void showDuration() {
        System.out.println("-showDuration---");

        generateAndPrintStream("durationOfSeconds randomPrimitiveLong - 1-10",
                DateTimeSuppliers.durationOfSeconds(
                        RandomNumberSuppliers.randomPrimitiveLong(RANDOM, 1, 10)));
    }

    private static void showPeriod() {
        System.out.println("-showPeriod---");

        generateAndPrintStream("periodOfDays randomPrimitiveInt - 1-10",
                DateTimeSuppliers.periodOfDays(
                        RandomNumberSuppliers.randomPrimitiveInt(RANDOM, 1, 10)));
    }

    public static void main(String... args) {
        showInstantSuppliers();
        showLocalDateSuppliers();
        showLocalTimeSuppliers();
        showLocalDateTimeSuppliers();
        showZonedDateTimeSuppliers();
        showYear();
        showMonth();
        showYearMonth();
        showDayOfWeek();
        showDuration();
        showPeriod();
    }

}
