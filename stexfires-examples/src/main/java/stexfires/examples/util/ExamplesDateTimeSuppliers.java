package stexfires.examples.util;

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

    private static final RandomGenerator random = new Random();
    private static final Instant instantNow = Instant.now();
    private static final ZoneId zoneId = ZoneId.systemDefault();

    private ExamplesDateTimeSuppliers() {
    }

    private static <T> void generateAndPrintStream(String title, Supplier<? extends T> supplier) {
        System.out.println(title);
        Stream.generate(supplier).limit(10L).forEachOrdered(System.out::println);
    }

    private static void showInstantSuppliers() {
        System.out.println("-showInstantSuppliers---");

        generateAndPrintStream("instantOfEpochSecond randomEpochSecond - instantNow + 1 day",
                DateTimeSuppliers.instantOfEpochSecond(
                        DateTimeSuppliers.randomEpochSecond(random,
                                instantNow,
                                instantNow.plus(1, ChronoUnit.DAYS))));
        generateAndPrintStream("instantOfEpochMilli randomEpochMilli - instantNow + 1 day",
                DateTimeSuppliers.instantOfEpochMilli(
                        DateTimeSuppliers.randomEpochMilli(random,
                                instantNow,
                                instantNow.plus(1, ChronoUnit.DAYS))));
    }

    private static void showLocalDateSuppliers() {
        System.out.println("-showLocalDateSuppliers---");

        LocalDate localDateNow = LocalDate.ofInstant(instantNow, zoneId);

        generateAndPrintStream("localDateOfInstant instantOfEpochSecond randomEpochSecond - instantNow + 10 days",
                DateTimeSuppliers.localDateOfInstant(
                        DateTimeSuppliers.instantOfEpochSecond(
                                DateTimeSuppliers.randomEpochSecond(random,
                                        instantNow,
                                        instantNow.plus(10, ChronoUnit.DAYS))),
                        zoneId));

        generateAndPrintStream("localDateOfEpochDay randomEpochDay - localDateNow + 10 days",
                DateTimeSuppliers.localDateOfEpochDay(
                        DateTimeSuppliers.randomEpochDay(random,
                                localDateNow,
                                localDateNow.plusDays(10))));
    }

    private static void showLocalTimeSuppliers() {
        System.out.println("-showLocalTimeSuppliers---");

        LocalTime localTimeNow = LocalTime.ofInstant(instantNow, zoneId);

        generateAndPrintStream("localTimeOfInstant instantOfEpochSecond randomEpochSecond - instantNow + 1 hour",
                DateTimeSuppliers.localTimeOfInstant(
                        DateTimeSuppliers.instantOfEpochSecond(
                                DateTimeSuppliers.randomEpochSecond(random,
                                        instantNow,
                                        instantNow.plus(1, ChronoUnit.HOURS))),
                        zoneId));

        generateAndPrintStream("localTimeOfInstant instantOfEpochMilli randomEpochMilli - instantNow + 1 hour",
                DateTimeSuppliers.localTimeOfInstant(
                        DateTimeSuppliers.instantOfEpochMilli(
                                DateTimeSuppliers.randomEpochMilli(random,
                                        instantNow,
                                        instantNow.plus(1, ChronoUnit.HOURS))),
                        zoneId));

        generateAndPrintStream("localTimeOfSecondOfDay randomSecondOfDay - localTimeNow + 1 hour",
                DateTimeSuppliers.localTimeOfSecondOfDay(
                        DateTimeSuppliers.randomSecondOfDay(random,
                                localTimeNow,
                                localTimeNow.plusHours(1))));

        generateAndPrintStream("localTimeOfSecondOfDay randomSecondOfDayInclusive - LocalTime.MIN - LocalTime.MAX",
                DateTimeSuppliers.localTimeOfSecondOfDay(
                        DateTimeSuppliers.randomSecondOfDayInclusive(random,
                                LocalTime.MIN,
                                LocalTime.MAX)));

        generateAndPrintStream("localTimeOfNanoOfDay randomNanoOfDay - localTimeNow + 1 hour",
                DateTimeSuppliers.localTimeOfNanoOfDay(
                        DateTimeSuppliers.randomNanoOfDay(random,
                                localTimeNow,
                                localTimeNow.plusHours(1))));

        generateAndPrintStream("localTimeOfNanoOfDay randomNanoOfDayInclusive - LocalTime.MIN - LocalTime.MAX",
                DateTimeSuppliers.localTimeOfNanoOfDay(
                        DateTimeSuppliers.randomNanoOfDayInclusive(random,
                                LocalTime.MIN,
                                LocalTime.MAX)));
    }

    private static void showLocalDateTimeSuppliers() {
        System.out.println("-showLocalDateTimeSuppliers---");

        LocalDate localDateNow = LocalDate.ofInstant(instantNow, zoneId);
        LocalTime localTimeNow = LocalTime.ofInstant(instantNow, zoneId);
        LocalDateTime localDateTimeNow = LocalDateTime.ofInstant(instantNow, zoneId);

        generateAndPrintStream("localDateTimeOfInstant instantOfEpochSecond randomEpochSecond - instantNow + 1 day",
                DateTimeSuppliers.localDateTimeOfInstant(
                        DateTimeSuppliers.instantOfEpochSecond(
                                DateTimeSuppliers.randomEpochSecond(random,
                                        instantNow,
                                        instantNow.plus(1, ChronoUnit.DAYS))),
                        zoneId));

        generateAndPrintStream("localDateTimeOfLocalDateAndLocalTime localDateOfEpochDay randomEpochDay - localDateNow + 1 day AND localTimeOfSecondOfDay randomSecondOfDay - localTimeNow + 1 hour",
                DateTimeSuppliers.localDateTimeOfLocaleDateAndLocalTime(
                        DateTimeSuppliers.localDateOfEpochDay(
                                DateTimeSuppliers.randomEpochDay(random,
                                        localDateNow,
                                        localDateNow.plusDays(10))),
                        DateTimeSuppliers.localTimeOfSecondOfDay(
                                DateTimeSuppliers.randomSecondOfDay(random,
                                        localTimeNow,
                                        localTimeNow.plusHours(1)))));
    }

    private static void showZonedDateTimeSuppliers() {
        System.out.println("-showZonedDateTimeSuppliers---");

        LocalDate localDateNow = LocalDate.ofInstant(instantNow, zoneId);
        LocalTime localTimeNow = LocalTime.ofInstant(instantNow, zoneId);
        ZonedDateTime zonedDateTimeNow = ZonedDateTime.ofInstant(instantNow, zoneId);

        generateAndPrintStream("zonedDateTimeOfInstant instantOfEpochSecond randomEpochSecond - instantNow + 1 day",
                DateTimeSuppliers.zonedDateTimeOfInstant(
                        DateTimeSuppliers.instantOfEpochSecond(
                                DateTimeSuppliers.randomEpochSecond(random,
                                        instantNow,
                                        instantNow.plus(1, ChronoUnit.DAYS))),
                        zoneId));

        generateAndPrintStream("zonedDateTimeOfLocalDateTime localDateTimeOfInstant instantOfEpochSecond randomEpochSecond - instantNow + 1 day",
                DateTimeSuppliers.zonedDateTimeOfLocalDateTime(
                        DateTimeSuppliers.localDateTimeOfInstant(
                                DateTimeSuppliers.instantOfEpochSecond(
                                        DateTimeSuppliers.randomEpochSecond(random,
                                                instantNow,
                                                instantNow.plus(1, ChronoUnit.DAYS))),
                                zoneId),
                        zoneId));

        generateAndPrintStream("zonedDateTimeOfLocalDateAndLocalTime localDateOfEpochDay randomEpochDay - localDateNow + 10 days AND localTimeOfSecondOfDay randomSecondOfDay - localTimeNow + 1 hour",
                DateTimeSuppliers.zonedDateTimeOfLocalDateAndLocalTime(
                        DateTimeSuppliers.localDateOfEpochDay(
                                DateTimeSuppliers.randomEpochDay(random,
                                        localDateNow,
                                        localDateNow.plusDays(10))),
                        DateTimeSuppliers.localTimeOfSecondOfDay(
                                DateTimeSuppliers.randomSecondOfDay(random,
                                        localTimeNow,
                                        localTimeNow.plusHours(1))),
                        zoneId));
    }

    private static void showYear() {
        System.out.println("-showYear---");

        generateAndPrintStream("year randomPrimitiveInt - 2020-2022",
                DateTimeSuppliers.year(
                        RandomNumberSuppliers.randomPrimitiveInt(random, 2020, 2022)));

        generateAndPrintStream("year randomYearInclusive - 2020-2022",
                DateTimeSuppliers.year(
                        DateTimeSuppliers.randomYearInclusive(random, Year.of(2020), Year.of(2022))));
    }

    private static void showMonth() {
        System.out.println("-showMonth---");

        generateAndPrintStream("month randomPrimitiveInt - 9-13",
                DateTimeSuppliers.month(
                        RandomNumberSuppliers.randomPrimitiveInt(random, 9, 13)));

        generateAndPrintStream("month randomMonthInclusive - SEPTEMBER-DECEMBER",
                DateTimeSuppliers.month(
                        DateTimeSuppliers.randomMonthInclusive(random, Month.SEPTEMBER, Month.DECEMBER)));
    }

    private static void showYearMonth() {
        System.out.println("-showYearMonth---");

        generateAndPrintStream("yearMonth randomPrimitiveInt - 2020-2024 AND randomPrimitiveInt - 9-13",
                DateTimeSuppliers.yearMonth(
                        RandomNumberSuppliers.randomPrimitiveInt(random, 2020, 2024),
                        RandomNumberSuppliers.randomPrimitiveInt(random, 9, 13)));

        generateAndPrintStream("yearMonthOfYearAndMonth randomYearInclusive - 2020-2022 AND randomMonthInclusive - APRIL-JUNE",
                DateTimeSuppliers.yearMonthOfYearAndMonth(
                        DateTimeSuppliers.year(
                                DateTimeSuppliers.randomYearInclusive(random, Year.of(2020), Year.of(2022))),
                        DateTimeSuppliers.month(
                                DateTimeSuppliers.randomMonthInclusive(random, Month.APRIL, Month.JUNE))));
    }

    private static void showDayOfWeek() {
        System.out.println("-showDayOfWeek---");

        generateAndPrintStream("dayOfWeek randomPrimitiveInt - 1-3",
                DateTimeSuppliers.dayOfWeek(
                        RandomNumberSuppliers.randomPrimitiveInt(random, 1, 3)));

        generateAndPrintStream("dayOfWeek randomDayOfWeekInclusive - MONDAY-FRIDAY",
                DateTimeSuppliers.dayOfWeek(
                        DateTimeSuppliers.randomDayOfWeekInclusive(random, DayOfWeek.MONDAY, DayOfWeek.FRIDAY)));
    }

    private static void showDuration() {
        System.out.println("-showDuration---");

        generateAndPrintStream("durationOfSeconds randomPrimitiveLong - 1-10",
                DateTimeSuppliers.durationOfSeconds(
                        RandomNumberSuppliers.randomPrimitiveLong(random, 1, 10)));
    }

    private static void showPeriod() {
        System.out.println("-showPeriod---");

        generateAndPrintStream("periodOfDays randomPrimitiveInt - 1-10",
                DateTimeSuppliers.periodOfDays(
                        RandomNumberSuppliers.randomPrimitiveInt(random, 1, 10)));
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
