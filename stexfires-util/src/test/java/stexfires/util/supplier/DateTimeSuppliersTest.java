package stexfires.util.supplier;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.*;
import java.util.function.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link stexfires.util.supplier.DateTimeSuppliers}.
 */
@SuppressWarnings({"StaticCollection", "TestMethodWithoutAssertion", "MagicNumber"})
class DateTimeSuppliersTest {

    private static final List<ZoneId> ZONE_IDS = List.of(
            ZoneOffset.UTC,
            ZoneId.systemDefault(),
            ZoneId.of("Europe/Berlin"),
            ZoneId.of("America/New_York"));

    private static <T> void assertFiveTimesEquals(T expected, Supplier<T> supplier) {
        for (int i = 0; i < 5; i++) {
            assertEquals(expected, supplier.get(), supplier::toString);
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#instantOfEpochSecond(java.util.function.LongSupplier)}.
     */
    @Test
    void instantOfEpochSecond() {
        assertFiveTimesEquals(Instant.EPOCH, DateTimeSuppliers.instantOfEpochSecond(() -> 0L));
        for (Long longValue : List.of(-1_000_000_000_000L, -1_000_000_000L, -1L, 0L, 1L, 1_000_000_000L, 1_000_000_000_000L)) {
            assertFiveTimesEquals(Instant.ofEpochSecond(longValue), DateTimeSuppliers.instantOfEpochSecond(() -> longValue));
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#instantOfEpochMilli(java.util.function.LongSupplier)}.
     */
    @Test
    void instantOfEpochMilli() {
        assertFiveTimesEquals(Instant.EPOCH, DateTimeSuppliers.instantOfEpochMilli(() -> 0L));
        for (Long longValue : List.of(-1_000_000_000_000_000L, -1_000_000_000_000L, -1_000_000_000L, -1L, 0L, 1L, 1_000_000_000L, 1_000_000_000_000L, 1_000_000_000_000_000L)) {
            assertFiveTimesEquals(Instant.ofEpochMilli(longValue), DateTimeSuppliers.instantOfEpochMilli(() -> longValue));
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#localDateOfInstant(java.util.function.Supplier, java.time.ZoneId)}.
     */
    @Test
    void localDateOfInstant() {
        assertFiveTimesEquals(LocalDate.EPOCH, DateTimeSuppliers.localDateOfInstant(() -> Instant.EPOCH, ZoneOffset.UTC));
        for (ZoneId zoneId : ZONE_IDS) {
            assertFiveTimesEquals(LocalDate.ofInstant(Instant.EPOCH, zoneId), DateTimeSuppliers.localDateOfInstant(() -> Instant.EPOCH, zoneId));
            for (Long longValue : List.of(-1_000_000_000_000_000L, -1_000_000_000_000L, -1_000_000_000L, -1L, 0L, 1L, 1_000_000_000L, 1_000_000_000_000L, 1_000_000_000_000_000L)) {
                Instant instant = Instant.ofEpochMilli(longValue);
                assertFiveTimesEquals(LocalDate.ofInstant(instant, zoneId), DateTimeSuppliers.localDateOfInstant(() -> instant, zoneId));
            }
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#localDateOfEpochDay(java.util.function.LongSupplier)}.
     */
    @Test
    void localDateOfEpochDay() {
        assertFiveTimesEquals(LocalDate.EPOCH, DateTimeSuppliers.localDateOfEpochDay(() -> 0L));
        for (Long longValue : List.of(-1_000_000L, -366L, -365L, -1L, 0L, 1L, 365L, 366L, 1_000_000L)) {
            assertFiveTimesEquals(LocalDate.ofEpochDay(longValue), DateTimeSuppliers.localDateOfEpochDay(() -> longValue));
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#localTimeOfInstant(java.util.function.Supplier, java.time.ZoneId)}.
     */
    @Test
    void localTimeOfInstant() {
        assertFiveTimesEquals(LocalTime.MIDNIGHT, DateTimeSuppliers.localTimeOfInstant(() -> Instant.EPOCH, ZoneOffset.UTC));
        for (ZoneId zoneId : ZONE_IDS) {
            assertFiveTimesEquals(LocalTime.ofInstant(Instant.EPOCH, zoneId), DateTimeSuppliers.localTimeOfInstant(() -> Instant.EPOCH, zoneId));
            for (Long longValue : List.of(-1_000_000_000_000_000L, -1_000_000_000_000L, -1_000_000_000L, -1L, 0L, 1L, 1_000_000_000L, 1_000_000_000_000L, 1_000_000_000_000_000L)) {
                Instant instant = Instant.ofEpochMilli(longValue);
                assertFiveTimesEquals(LocalTime.ofInstant(instant, zoneId), DateTimeSuppliers.localTimeOfInstant(() -> instant, zoneId));
            }
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#localTimeOfSecondOfDay(java.util.function.LongSupplier)}.
     */
    @Test
    void localTimeOfSecondOfDay() {
        assertFiveTimesEquals(LocalTime.MIDNIGHT, DateTimeSuppliers.localTimeOfSecondOfDay(() -> 0L));
        assertFiveTimesEquals(LocalTime.NOON, DateTimeSuppliers.localTimeOfSecondOfDay(() -> 12L * 60L * 60L));
        for (Long longValue : List.of(0L, 1L, (24L * 60L * 60L) - 1L)) {
            assertFiveTimesEquals(LocalTime.ofSecondOfDay(longValue), DateTimeSuppliers.localTimeOfSecondOfDay(() -> longValue));
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#localTimeOfNanoOfDay(java.util.function.LongSupplier)}.
     */
    @Test
    void localTimeOfNanoOfDay() {
        assertFiveTimesEquals(LocalTime.MIDNIGHT, DateTimeSuppliers.localTimeOfNanoOfDay(() -> 0L));
        assertFiveTimesEquals(LocalTime.NOON, DateTimeSuppliers.localTimeOfNanoOfDay(() -> 12L * 60L * 60L * 1_000_000_000L));
        for (Long longValue : List.of(0L, 1L, (24L * 60L * 60L * 1_000_000_000L) - 1L)) {
            assertFiveTimesEquals(LocalTime.ofNanoOfDay(longValue), DateTimeSuppliers.localTimeOfNanoOfDay(() -> longValue));
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#localDateTimeOfInstant(java.util.function.Supplier, java.time.ZoneId)}.
     */
    @Test
    void localDateTimeOfInstant() {
        for (ZoneId zoneId : ZONE_IDS) {
            assertFiveTimesEquals(LocalDateTime.ofInstant(Instant.EPOCH, zoneId), DateTimeSuppliers.localDateTimeOfInstant(() -> Instant.EPOCH, zoneId));
            for (Long longValue : List.of(-1_000_000_000_000_000L, -1_000_000_000_000L, -1_000_000_000L, -1L, 0L, 1L, 1_000_000_000L, 1_000_000_000_000L, 1_000_000_000_000_000L)) {
                Instant instant = Instant.ofEpochMilli(longValue);
                assertFiveTimesEquals(LocalDateTime.ofInstant(instant, zoneId), DateTimeSuppliers.localDateTimeOfInstant(() -> instant, zoneId));
            }
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#localDateTimeOfLocaleDateAndLocalTime(java.util.function.Supplier, java.util.function.Supplier)}.
     */
    @Test
    void localDateTimeOfLocaleDateAndLocalTime() {
        for (LocalDate localDate : List.of(LocalDate.EPOCH, LocalDate.of(1_000, 1, 1), LocalDate.of(1_970, 1, 1), LocalDate.of(2_000, 2, 29), LocalDate.of(9_999, 12, 31))) {
            for (LocalTime localTime : List.of(LocalTime.MIDNIGHT, LocalTime.NOON, LocalTime.of(0, 0, 0, 0), LocalTime.of(23, 59, 59, 999_999_999))) {
                assertFiveTimesEquals(LocalDateTime.of(localDate, localTime), DateTimeSuppliers.localDateTimeOfLocaleDateAndLocalTime(() -> localDate, () -> localTime));
            }
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#zonedDateTimeOfInstant(java.util.function.Supplier, java.time.ZoneId)}.
     */
    @Test
    void zonedDateTimeOfInstant() {
        for (ZoneId zoneId : ZONE_IDS) {
            assertFiveTimesEquals(ZonedDateTime.ofInstant(Instant.EPOCH, zoneId), DateTimeSuppliers.zonedDateTimeOfInstant(() -> Instant.EPOCH, zoneId));
            for (Long longValue : List.of(-1_000_000_000_000_000L, -1_000_000_000_000L, -1_000_000_000L, -1L, 0L, 1L, 1_000_000_000L, 1_000_000_000_000L, 1_000_000_000_000_000L)) {
                Instant instant = Instant.ofEpochMilli(longValue);
                assertFiveTimesEquals(ZonedDateTime.ofInstant(instant, zoneId), DateTimeSuppliers.zonedDateTimeOfInstant(() -> instant, zoneId));
            }
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#zonedDateTimeOfLocalDateTime(java.util.function.Supplier, java.time.ZoneId)}.
     */
    @Test
    void zonedDateTimeOfLocalDateTime() {
        assertFiveTimesEquals(ZonedDateTime.of(LocalDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC), ZoneOffset.UTC), DateTimeSuppliers.zonedDateTimeOfLocalDateTime(() -> LocalDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC), ZoneOffset.UTC));
        assertFiveTimesEquals(ZonedDateTime.of(1_970, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC), DateTimeSuppliers.zonedDateTimeOfLocalDateTime(() -> LocalDateTime.of(1_970, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC));
        for (ZoneId zoneId : ZONE_IDS) {
            assertFiveTimesEquals(ZonedDateTime.of(LocalDateTime.ofInstant(Instant.EPOCH, zoneId), zoneId), DateTimeSuppliers.zonedDateTimeOfLocalDateTime(() -> LocalDateTime.ofInstant(Instant.EPOCH, zoneId), zoneId));
            for (Long longValue : List.of(-1_000_000_000_000_000L, -1_000_000_000_000L, -1_000_000_000L, -1L, 0L, 1L, 1_000_000_000L, 1_000_000_000_000L, 1_000_000_000_000_000L)) {
                Instant instant = Instant.ofEpochMilli(longValue);
                assertFiveTimesEquals(ZonedDateTime.of(LocalDateTime.ofInstant(instant, zoneId), zoneId), DateTimeSuppliers.zonedDateTimeOfLocalDateTime(() -> LocalDateTime.ofInstant(instant, zoneId), zoneId));
            }
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#zonedDateTimeOfLocalDateAndLocalTime(java.util.function.Supplier, java.util.function.Supplier, java.time.ZoneId)}.
     */
    @Test
    void zonedDateTimeOfLocalDateAndLocalTime() {
        assertFiveTimesEquals(ZonedDateTime.of(LocalDate.EPOCH, LocalTime.MIDNIGHT, ZoneOffset.UTC), DateTimeSuppliers.zonedDateTimeOfLocalDateAndLocalTime(() -> LocalDate.EPOCH, () -> LocalTime.MIDNIGHT, ZoneOffset.UTC));
        assertFiveTimesEquals(ZonedDateTime.of(1_970, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC), DateTimeSuppliers.zonedDateTimeOfLocalDateAndLocalTime(() -> LocalDate.of(1_970, 1, 1), () -> LocalTime.MIDNIGHT, ZoneOffset.UTC));
        for (ZoneId zoneId : ZONE_IDS) {
            assertFiveTimesEquals(ZonedDateTime.of(LocalDate.EPOCH, LocalTime.MIDNIGHT, zoneId), DateTimeSuppliers.zonedDateTimeOfLocalDateAndLocalTime(() -> LocalDate.EPOCH, () -> LocalTime.MIDNIGHT, zoneId));
            for (LocalDate localDate : List.of(LocalDate.EPOCH, LocalDate.of(1_000, 1, 1), LocalDate.of(1_970, 1, 1), LocalDate.of(2_000, 2, 29), LocalDate.of(9_999, 12, 31))) {
                for (LocalTime localTime : List.of(LocalTime.MIDNIGHT, LocalTime.NOON, LocalTime.of(0, 0, 0, 0), LocalTime.of(23, 59, 59, 999_999_999))) {
                    assertFiveTimesEquals(ZonedDateTime.of(localDate, localTime, zoneId), DateTimeSuppliers.zonedDateTimeOfLocalDateAndLocalTime(() -> localDate, () -> localTime, zoneId));
                }
            }
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#year(java.util.function.IntSupplier)}.
     */
    @Test
    void year() {
        assertFiveTimesEquals(Year.of(0), DateTimeSuppliers.year(() -> 0));
        for (int year = -3_000; year < 3_000; year++) {
            int finalYear = year;
            assertFiveTimesEquals(Year.of(year), DateTimeSuppliers.year(() -> finalYear));
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#month(java.util.function.IntSupplier)}.
     */
    @Test
    void month() {
        assertFiveTimesEquals(Month.JANUARY, DateTimeSuppliers.month(Month.JANUARY::getValue));
        for (int month = 1; month <= 12; month++) {
            int finalMonth = month;
            assertFiveTimesEquals(Month.of(finalMonth), DateTimeSuppliers.month(() -> finalMonth));
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#yearMonth(java.util.function.IntSupplier, java.util.function.IntSupplier)}.
     */
    @Test
    void yearMonth() {
        assertFiveTimesEquals(YearMonth.of(0, 1), DateTimeSuppliers.yearMonth(() -> 0, () -> 1));
        for (int year = -3_000; year < 3_000; year++) {
            for (int month = 1; month <= 12; month++) {
                int finalYear = year;
                int finalMonth = month;
                assertFiveTimesEquals(YearMonth.of(finalYear, finalMonth), DateTimeSuppliers.yearMonth(() -> finalYear, () -> finalMonth));
            }
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#yearMonthOfYearAndMonth(java.util.function.Supplier, java.util.function.Supplier)}.
     */
    @Test
    void yearMonthOfYearAndMonth() {
        assertFiveTimesEquals(YearMonth.of(0, Month.JANUARY), DateTimeSuppliers.yearMonthOfYearAndMonth(() -> Year.of(0), () -> Month.JANUARY));
        for (int year = -3_000; year < 3_000; year++) {
            for (int month = 1; month <= 12; month++) {
                Year finalYear = Year.of(year);
                Month finalMonth = Month.of(month);
                assertFiveTimesEquals(finalYear.atMonth(finalMonth), DateTimeSuppliers.yearMonthOfYearAndMonth(() -> finalYear, () -> finalMonth));
            }
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#dayOfWeek(java.util.function.IntSupplier)}.
     */
    @Test
    void dayOfWeek() {
        assertFiveTimesEquals(DayOfWeek.MONDAY, DateTimeSuppliers.dayOfWeek(DayOfWeek.MONDAY::getValue));
        for (int dayOfWeek = 1; dayOfWeek <= 7; dayOfWeek++) {
            int finalDayOfWeek = dayOfWeek;
            assertFiveTimesEquals(DayOfWeek.of(finalDayOfWeek), DateTimeSuppliers.dayOfWeek(() -> finalDayOfWeek));
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#durationOfSeconds(java.util.function.LongSupplier)}.
     */
    @Test
    void durationOfSeconds() {
        assertFiveTimesEquals(Duration.ZERO, DateTimeSuppliers.durationOfSeconds(() -> 0L));
        for (Long longValue : List.of(-1_000_000_000L, -1_000_000L, -1L, 0L, 1L, 1_000_000L, 1_000_000_000L)) {
            assertFiveTimesEquals(Duration.ofSeconds(longValue), DateTimeSuppliers.durationOfSeconds(() -> longValue));
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#periodOfDays(java.util.function.IntSupplier)}.
     */
    @Test
    void periodOfDays() {
        assertFiveTimesEquals(Period.ZERO, DateTimeSuppliers.periodOfDays(() -> 0));
        for (int days = -1_000_000; days <= 1_000_000; days++) {
            int finalDays = days;
            assertFiveTimesEquals(Period.ofDays(finalDays), DateTimeSuppliers.periodOfDays(() -> finalDays));
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#randomEpochSecond(java.util.random.RandomGenerator, java.time.Instant, java.time.Instant)}.
     */
    @Test
    void randomEpochSecond() {
        LongSupplier longSupplier01 = DateTimeSuppliers.randomEpochSecond(new Random(0L), Instant.EPOCH, Instant.EPOCH.plusSeconds(1L));
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(0L, longSupplier01.getAsLong());

        LongSupplier longSupplier0100 = DateTimeSuppliers.randomEpochSecond(new Random(0L), Instant.EPOCH, Instant.EPOCH.plusSeconds(100L));
        assertEquals(60L, longSupplier0100.getAsLong());
        assertEquals(83L, longSupplier0100.getAsLong());
        assertEquals(93L, longSupplier0100.getAsLong());
        assertEquals(45L, longSupplier0100.getAsLong());
        assertEquals(30L, longSupplier0100.getAsLong());
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#randomEpochMilli(java.util.random.RandomGenerator, java.time.Instant, java.time.Instant)}.
     */
    @Test
    void randomEpochMilli() {
        LongSupplier longSupplier01 = DateTimeSuppliers.randomEpochMilli(new Random(0L), Instant.EPOCH, Instant.EPOCH.plusMillis(1L));
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(0L, longSupplier01.getAsLong());

        LongSupplier longSupplier0100 = DateTimeSuppliers.randomEpochMilli(new Random(0L), Instant.EPOCH, Instant.EPOCH.plusMillis(100L));
        assertEquals(60L, longSupplier0100.getAsLong());
        assertEquals(83L, longSupplier0100.getAsLong());
        assertEquals(93L, longSupplier0100.getAsLong());
        assertEquals(45L, longSupplier0100.getAsLong());
        assertEquals(30L, longSupplier0100.getAsLong());
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#randomEpochDay(java.util.random.RandomGenerator, java.time.LocalDate, java.time.LocalDate)}.
     */
    @Test
    void randomEpochDay() {
        LongSupplier longSupplier01 = DateTimeSuppliers.randomEpochDay(new Random(0L), LocalDate.EPOCH, LocalDate.EPOCH.plusDays(1L));
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(0L, longSupplier01.getAsLong());

        LongSupplier longSupplier0100 = DateTimeSuppliers.randomEpochDay(new Random(0L), LocalDate.EPOCH, LocalDate.EPOCH.plusDays(100L));
        assertEquals(60L, longSupplier0100.getAsLong());
        assertEquals(83L, longSupplier0100.getAsLong());
        assertEquals(93L, longSupplier0100.getAsLong());
        assertEquals(45L, longSupplier0100.getAsLong());
        assertEquals(30L, longSupplier0100.getAsLong());
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#randomSecondOfDay(java.util.random.RandomGenerator, java.time.LocalTime, java.time.LocalTime)}.
     */
    @Test
    void randomSecondOfDay() {
        LongSupplier longSupplier01 = DateTimeSuppliers.randomSecondOfDay(new Random(0L), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT.plusSeconds(1L));
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(0L, longSupplier01.getAsLong());

        LongSupplier longSupplier0100 = DateTimeSuppliers.randomSecondOfDay(new Random(0L), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT.plusSeconds(100L));
        assertEquals(60L, longSupplier0100.getAsLong());
        assertEquals(83L, longSupplier0100.getAsLong());
        assertEquals(93L, longSupplier0100.getAsLong());
        assertEquals(45L, longSupplier0100.getAsLong());
        assertEquals(30L, longSupplier0100.getAsLong());
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#randomSecondOfDayInclusive(java.util.random.RandomGenerator, java.time.LocalTime, java.time.LocalTime)}.
     */
    @Test
    void randomSecondOfDayInclusive() {
        LongSupplier longSupplier00 = DateTimeSuppliers.randomSecondOfDayInclusive(new Random(0L), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT.plusSeconds(0L));
        assertEquals(0L, longSupplier00.getAsLong());
        assertEquals(0L, longSupplier00.getAsLong());
        assertEquals(0L, longSupplier00.getAsLong());
        assertEquals(0L, longSupplier00.getAsLong());
        assertEquals(0L, longSupplier00.getAsLong());

        LongSupplier longSupplier01 = DateTimeSuppliers.randomSecondOfDayInclusive(new Random(0L), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT.plusSeconds(1L));
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(1L, longSupplier01.getAsLong());
        assertEquals(1L, longSupplier01.getAsLong());

        LongSupplier longSupplier0100 = DateTimeSuppliers.randomSecondOfDayInclusive(new Random(0L), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT.plusSeconds(100L));
        assertEquals(49L, longSupplier0100.getAsLong());
        assertEquals(33L, longSupplier0100.getAsLong());
        assertEquals(60L, longSupplier0100.getAsLong());
        assertEquals(97L, longSupplier0100.getAsLong());
        assertEquals(19L, longSupplier0100.getAsLong());
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#randomNanoOfDay(java.util.random.RandomGenerator, java.time.LocalTime, java.time.LocalTime)}.
     */
    @Test
    void randomNanoOfDay() {
        LongSupplier longSupplier01 = DateTimeSuppliers.randomNanoOfDay(new Random(0L), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT.plusNanos(1L));
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(0L, longSupplier01.getAsLong());

        LongSupplier longSupplier0100 = DateTimeSuppliers.randomNanoOfDay(new Random(0L), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT.plusNanos(100L));
        assertEquals(60L, longSupplier0100.getAsLong());
        assertEquals(83L, longSupplier0100.getAsLong());
        assertEquals(93L, longSupplier0100.getAsLong());
        assertEquals(45L, longSupplier0100.getAsLong());
        assertEquals(30L, longSupplier0100.getAsLong());

    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#randomNanoOfDayInclusive(java.util.random.RandomGenerator, java.time.LocalTime, java.time.LocalTime)}.
     */
    @Test
    void randomNanoOfDayInclusive() {
        LongSupplier longSupplier00 = DateTimeSuppliers.randomNanoOfDayInclusive(new Random(0L), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT.plusNanos(0L));
        assertEquals(0L, longSupplier00.getAsLong());
        assertEquals(0L, longSupplier00.getAsLong());
        assertEquals(0L, longSupplier00.getAsLong());
        assertEquals(0L, longSupplier00.getAsLong());
        assertEquals(0L, longSupplier00.getAsLong());

        LongSupplier longSupplier01 = DateTimeSuppliers.randomNanoOfDayInclusive(new Random(0L), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT.plusNanos(1L));
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(0L, longSupplier01.getAsLong());
        assertEquals(1L, longSupplier01.getAsLong());
        assertEquals(1L, longSupplier01.getAsLong());

        LongSupplier longSupplier0100 = DateTimeSuppliers.randomNanoOfDayInclusive(new Random(0L), LocalTime.MIDNIGHT, LocalTime.MIDNIGHT.plusNanos(100L));
        assertEquals(49L, longSupplier0100.getAsLong());
        assertEquals(33L, longSupplier0100.getAsLong());
        assertEquals(60L, longSupplier0100.getAsLong());
        assertEquals(97L, longSupplier0100.getAsLong());
        assertEquals(19L, longSupplier0100.getAsLong());
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#randomYearInclusive(java.util.random.RandomGenerator, java.time.Year, java.time.Year)}.
     */
    @Test
    void randomYearInclusive() {
        IntSupplier intSupplier000 = DateTimeSuppliers.randomYearInclusive(new Random(0L), Year.of(0), Year.of(0));
        assertEquals(0, intSupplier000.getAsInt());
        assertEquals(0, intSupplier000.getAsInt());
        assertEquals(0, intSupplier000.getAsInt());
        assertEquals(0, intSupplier000.getAsInt());
        assertEquals(0, intSupplier000.getAsInt());
        assertEquals(0, intSupplier000.getAsInt());
        assertEquals(0, intSupplier000.getAsInt());
        assertEquals(0, intSupplier000.getAsInt());
        assertEquals(0, intSupplier000.getAsInt());
        assertEquals(0, intSupplier000.getAsInt());

        IntSupplier intSupplier001 = DateTimeSuppliers.randomYearInclusive(new Random(0L), Year.of(0), Year.of(1));
        assertEquals(0, intSupplier001.getAsInt());
        assertEquals(0, intSupplier001.getAsInt());
        assertEquals(0, intSupplier001.getAsInt());
        assertEquals(0, intSupplier001.getAsInt());
        assertEquals(0, intSupplier001.getAsInt());
        assertEquals(0, intSupplier001.getAsInt());
        assertEquals(1, intSupplier001.getAsInt());
        assertEquals(1, intSupplier001.getAsInt());
        assertEquals(0, intSupplier001.getAsInt());
        assertEquals(1, intSupplier001.getAsInt());

        IntSupplier intSupplier00100 = DateTimeSuppliers.randomYearInclusive(new Random(0L), Year.of(0), Year.of(100));
        assertEquals(67L, intSupplier00100.getAsInt());
        assertEquals(72L, intSupplier00100.getAsInt());
        assertEquals(93L, intSupplier00100.getAsInt());
        assertEquals(5L, intSupplier00100.getAsInt());
        assertEquals(9L, intSupplier00100.getAsInt());
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#randomMonthInclusive(java.util.random.RandomGenerator, java.time.Month, java.time.Month)}.
     */
    @Test
    void randomMonthInclusive() {
        IntSupplier intSupplier011 = DateTimeSuppliers.randomMonthInclusive(new Random(0L), Month.JANUARY, Month.JANUARY);
        assertEquals(1, intSupplier011.getAsInt());
        assertEquals(1, intSupplier011.getAsInt());
        assertEquals(1, intSupplier011.getAsInt());
        assertEquals(1, intSupplier011.getAsInt());
        assertEquals(1, intSupplier011.getAsInt());

        IntSupplier intSupplier0112 = DateTimeSuppliers.randomMonthInclusive(new Random(0L), Month.JANUARY, Month.DECEMBER);
        assertEquals(1, intSupplier0112.getAsInt());
        assertEquals(5, intSupplier0112.getAsInt());
        assertEquals(2, intSupplier0112.getAsInt());
        assertEquals(12, intSupplier0112.getAsInt());
        assertEquals(12, intSupplier0112.getAsInt());
    }

    /**
     * Test method for {@link stexfires.util.supplier.DateTimeSuppliers#randomDayOfWeekInclusive(java.util.random.RandomGenerator, java.time.DayOfWeek, java.time.DayOfWeek)}.
     */
    @Test
    void randomDayOfWeekInclusive() {
        IntSupplier intSupplier011 = DateTimeSuppliers.randomDayOfWeekInclusive(new Random(0L), DayOfWeek.MONDAY, DayOfWeek.MONDAY);
        assertEquals(1, intSupplier011.getAsInt());
        assertEquals(1, intSupplier011.getAsInt());
        assertEquals(1, intSupplier011.getAsInt());
        assertEquals(1, intSupplier011.getAsInt());
        assertEquals(1, intSupplier011.getAsInt());

        IntSupplier intSupplier0112 = DateTimeSuppliers.randomDayOfWeekInclusive(new Random(0L), DayOfWeek.MONDAY, DayOfWeek.SUNDAY);
        assertEquals(6, intSupplier0112.getAsInt());
        assertEquals(3, intSupplier0112.getAsInt());
        assertEquals(5, intSupplier0112.getAsInt());
        assertEquals(3, intSupplier0112.getAsInt());
        assertEquals(5, intSupplier0112.getAsInt());
    }

}