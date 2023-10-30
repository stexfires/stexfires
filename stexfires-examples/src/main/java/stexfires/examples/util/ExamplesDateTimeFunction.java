package stexfires.examples.util;

import stexfires.util.function.DateTimeSuppliers;
import stexfires.util.function.RandomNumberSuppliers;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesDateTimeFunction {

    private static final RandomGenerator random = new Random();
    private static final Instant instantNow = Instant.now();
    private static final ZoneId zoneId = ZoneId.systemDefault();

    private ExamplesDateTimeFunction() {
    }

    private static <T> void generateAndPrintStream(String title, Supplier<? extends T> supplier) {
        System.out.println(title);
        Stream.generate(supplier).limit(10L).forEachOrdered(System.out::println);
    }

    private static void showRandomInstantSuppliers() {
        System.out.println("-showRandomInstantSuppliers---");

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

    private static void showRandomLocalDateSuppliers() {
        System.out.println("-showRandomLocalDateSuppliers---");

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

    private static void showRandomLocalTimeSuppliers() {
        System.out.println("-showRandomLocalTimeSuppliers---");

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

        generateAndPrintStream("localTimeOfNanoOfDay randomNanoOfDay - localTimeNow + 1 hour",
                DateTimeSuppliers.localTimeOfNanoOfDay(
                        DateTimeSuppliers.randomNanoOfDay(random,
                                localTimeNow,
                                localTimeNow.plusHours(1))));
    }

    private static void showRandomLocalDateTimeSuppliers() {
        System.out.println("-showRandomLocalDateTimeSuppliers---");

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

    private static void showRandomZonedDateTimeSuppliers() {
        System.out.println("-showRandomZonedDateTimeSuppliers---");

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

    private static void showRandomYear() {
        System.out.println("-showRandomYear---");

        generateAndPrintStream("year randomPrimitiveInt - 2020-2024",
                DateTimeSuppliers.year(
                        RandomNumberSuppliers.randomPrimitiveInt(random, 2020, 2024)));
    }

    private static void showRandomMonth() {
        System.out.println("-showRandomMonth---");

        generateAndPrintStream("month randomPrimitiveInt - 9-13",
                DateTimeSuppliers.month(
                        RandomNumberSuppliers.randomPrimitiveInt(random, 9, 13)));

        generateAndPrintStream("month randomMonthInclusive - SEPTEMBER-DECEMBER",
                DateTimeSuppliers.month(
                        DateTimeSuppliers.randomMonthInclusive(random, Month.SEPTEMBER, Month.DECEMBER)));
    }

    private static void showRandomYearMonth() {
        System.out.println("-showRandomYearMonth---");

        generateAndPrintStream("yearMonth randomPrimitiveInt - 2020-2024 AND randomPrimitiveInt - 9-13",
                DateTimeSuppliers.yearMonth(
                        RandomNumberSuppliers.randomPrimitiveInt(random, 2020, 2024),
                        RandomNumberSuppliers.randomPrimitiveInt(random, 9, 13)));
    }

    private static void showRandomDayOfWeek() {
        System.out.println("-showRandomDayOfWeek---");

        generateAndPrintStream("dayOfWeek randomPrimitiveInt - 1-3",
                DateTimeSuppliers.dayOfWeek(
                        RandomNumberSuppliers.randomPrimitiveInt(random, 1, 3)));

        generateAndPrintStream("dayOfWeek randomDayOfWeekInclusive - MONDAY-FRIDAY",
                DateTimeSuppliers.dayOfWeek(
                        DateTimeSuppliers.randomDayOfWeekInclusive(random, DayOfWeek.MONDAY, DayOfWeek.FRIDAY)));
    }

    private static void showRandomDuration() {
        System.out.println("-showRandomDuration---");

        generateAndPrintStream("durationOfSeconds randomPrimitiveLong - 1-10",
                DateTimeSuppliers.durationOfSeconds(
                        RandomNumberSuppliers.randomPrimitiveLong(random, 1, 10)));
    }

    private static void showRandomPeriod() {
        System.out.println("-showRandomPeriod---");

        generateAndPrintStream("periodOfDays randomPrimitiveInt - 1-10",
                DateTimeSuppliers.periodOfDays(
                        RandomNumberSuppliers.randomPrimitiveInt(random, 1, 10)));
    }

    public static void main(String... args) {
        showRandomInstantSuppliers();
        showRandomLocalDateSuppliers();
        showRandomLocalTimeSuppliers();
        showRandomLocalDateTimeSuppliers();
        showRandomZonedDateTimeSuppliers();
        showRandomYear();
        showRandomMonth();
        showRandomYearMonth();
        showRandomDayOfWeek();
        showRandomDuration();
        showRandomPeriod();
    }

}
