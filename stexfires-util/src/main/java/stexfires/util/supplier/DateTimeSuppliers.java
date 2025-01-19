package stexfires.util.supplier;

import java.time.*;
import java.util.*;
import java.util.function.*;
import java.util.random.*;

/**
 * This class contains {@code static} utility methods
 * for creating {@link Supplier}s for DateTime/Temporal objects,
 * like {@link Instant}, {@link LocalDate}, {@link LocalTime}, {@link LocalDateTime}, {@link ZonedDateTime},
 * {@link Year}, {@link Month}, {@link YearMonth}, {@link DayOfWeek}, {@link Duration}, {@link Period}.
 * <p>
 * There are also methods for creating random {@link LongSupplier}s and {@link IntSupplier}s, which can be used
 * for creating random DateTime/Temporal objects.
 *
 * @see java.util.function.Supplier
 * @see java.time.temporal.TemporalAccessor
 * @see java.time.Instant
 * @see java.time.LocalDate
 * @see java.time.LocalTime
 * @see java.time.LocalDateTime
 * @see java.time.ZonedDateTime
 * @see java.time.Year
 * @see java.time.Month
 * @see java.time.YearMonth
 * @see java.time.DayOfWeek
 * @see java.time.Duration
 * @see java.time.Period
 * @since 0.1
 */
public final class DateTimeSuppliers {

    private DateTimeSuppliers() {
    }

    public static Supplier<Instant> instantOfEpochSecond(LongSupplier epochSecondSupplier) {
        Objects.requireNonNull(epochSecondSupplier);
        return () -> Instant.ofEpochSecond(epochSecondSupplier.getAsLong());
    }

    public static Supplier<Instant> instantOfEpochMilli(LongSupplier epochMilliSupplier) {
        Objects.requireNonNull(epochMilliSupplier);
        return () -> Instant.ofEpochMilli(epochMilliSupplier.getAsLong());
    }

    public static Supplier<LocalDate> localDateOfInstant(Supplier<Instant> instantSupplier,
                                                         ZoneId zone) {
        Objects.requireNonNull(instantSupplier);
        Objects.requireNonNull(zone);
        return () -> LocalDate.ofInstant(instantSupplier.get(), zone);
    }

    public static Supplier<LocalDate> localDateOfEpochDay(LongSupplier epochDaySupplier) {
        Objects.requireNonNull(epochDaySupplier);
        return () -> LocalDate.ofEpochDay(epochDaySupplier.getAsLong());
    }

    public static Supplier<LocalTime> localTimeOfInstant(Supplier<Instant> instantSupplier,
                                                         ZoneId zone) {
        Objects.requireNonNull(instantSupplier);
        Objects.requireNonNull(zone);
        return () -> LocalTime.ofInstant(instantSupplier.get(), zone);
    }

    public static Supplier<LocalTime> localTimeOfSecondOfDay(LongSupplier secondOfDaySupplier) {
        Objects.requireNonNull(secondOfDaySupplier);
        return () -> LocalTime.ofSecondOfDay(secondOfDaySupplier.getAsLong());
    }

    public static Supplier<LocalTime> localTimeOfNanoOfDay(LongSupplier nanoOfDaySupplier) {
        Objects.requireNonNull(nanoOfDaySupplier);
        return () -> LocalTime.ofNanoOfDay(nanoOfDaySupplier.getAsLong());
    }

    public static Supplier<LocalDateTime> localDateTimeOfInstant(Supplier<Instant> instantSupplier,
                                                                 ZoneId zone) {
        Objects.requireNonNull(instantSupplier);
        Objects.requireNonNull(zone);
        return () -> LocalDateTime.ofInstant(instantSupplier.get(), zone);
    }

    public static Supplier<LocalDateTime> localDateTimeOfLocaleDateAndLocalTime(Supplier<LocalDate> localDateSupplier,
                                                                                Supplier<LocalTime> localTimeSupplier) {
        Objects.requireNonNull(localDateSupplier);
        Objects.requireNonNull(localTimeSupplier);
        return () -> LocalDateTime.of(localDateSupplier.get(), localTimeSupplier.get());
    }

    public static Supplier<ZonedDateTime> zonedDateTimeOfInstant(Supplier<Instant> instantSupplier,
                                                                 ZoneId zone) {
        Objects.requireNonNull(instantSupplier);
        Objects.requireNonNull(zone);
        return () -> ZonedDateTime.ofInstant(instantSupplier.get(), zone);
    }

    public static Supplier<ZonedDateTime> zonedDateTimeOfLocalDateTime(Supplier<LocalDateTime> localDateTimeSupplier,
                                                                       ZoneId zone) {
        Objects.requireNonNull(localDateTimeSupplier);
        Objects.requireNonNull(zone);
        return () -> ZonedDateTime.of(localDateTimeSupplier.get(), zone);
    }

    public static Supplier<ZonedDateTime> zonedDateTimeOfLocalDateAndLocalTime(Supplier<LocalDate> localDateSupplier,
                                                                               Supplier<LocalTime> localTimeSupplier,
                                                                               ZoneId zone) {
        Objects.requireNonNull(localDateSupplier);
        Objects.requireNonNull(localTimeSupplier);
        Objects.requireNonNull(zone);
        return () -> ZonedDateTime.of(localDateSupplier.get(), localTimeSupplier.get(), zone);
    }

    public static Supplier<Year> year(IntSupplier yearSupplier) {
        Objects.requireNonNull(yearSupplier);
        return () -> Year.of(yearSupplier.getAsInt());
    }

    public static Supplier<Month> month(IntSupplier monthSupplier) {
        Objects.requireNonNull(monthSupplier);
        return () -> Month.of(monthSupplier.getAsInt());
    }

    public static Supplier<YearMonth> yearMonth(IntSupplier yearSupplier,
                                                IntSupplier monthSupplier) {
        Objects.requireNonNull(yearSupplier);
        Objects.requireNonNull(monthSupplier);
        return () -> YearMonth.of(yearSupplier.getAsInt(), monthSupplier.getAsInt());
    }

    public static Supplier<YearMonth> yearMonthOfYearAndMonth(Supplier<Year> yearSupplier,
                                                              Supplier<Month> monthSupplier) {
        Objects.requireNonNull(yearSupplier);
        Objects.requireNonNull(monthSupplier);
        return () -> yearSupplier.get().atMonth(monthSupplier.get());
    }

    public static Supplier<DayOfWeek> dayOfWeek(IntSupplier dayOfWeekSupplier) {
        Objects.requireNonNull(dayOfWeekSupplier);
        return () -> DayOfWeek.of(dayOfWeekSupplier.getAsInt());
    }

    public static Supplier<Duration> durationOfSeconds(LongSupplier secondsSupplier) {
        Objects.requireNonNull(secondsSupplier);
        return () -> Duration.ofSeconds(secondsSupplier.getAsLong());
    }

    public static Supplier<Period> periodOfDays(IntSupplier daysSupplier) {
        Objects.requireNonNull(daysSupplier);
        return () -> Period.ofDays(daysSupplier.getAsInt());
    }

    public static LongSupplier randomEpochSecond(RandomGenerator random,
                                                 Instant startInclusive,
                                                 Instant endExclusive) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(startInclusive);
        Objects.requireNonNull(endExclusive);
        return RandomNumberSuppliers.randomPrimitiveLong(random, startInclusive.getEpochSecond(), endExclusive.getEpochSecond());
    }

    public static LongSupplier randomEpochMilli(RandomGenerator random,
                                                Instant startInclusive,
                                                Instant endExclusive) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(startInclusive);
        Objects.requireNonNull(endExclusive);
        return RandomNumberSuppliers.randomPrimitiveLong(random, startInclusive.toEpochMilli(), endExclusive.toEpochMilli());
    }

    public static LongSupplier randomEpochDay(RandomGenerator random,
                                              LocalDate startInclusive,
                                              LocalDate endExclusive) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(startInclusive);
        Objects.requireNonNull(endExclusive);
        return RandomNumberSuppliers.randomPrimitiveLong(random, startInclusive.toEpochDay(), endExclusive.toEpochDay());
    }

    public static LongSupplier randomSecondOfDay(RandomGenerator random,
                                                 LocalTime startInclusive,
                                                 LocalTime endExclusive) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(startInclusive);
        Objects.requireNonNull(endExclusive);
        return RandomNumberSuppliers.randomPrimitiveLong(random, startInclusive.toSecondOfDay(), endExclusive.toSecondOfDay());
    }

    public static LongSupplier randomSecondOfDayInclusive(RandomGenerator random,
                                                          LocalTime startInclusive,
                                                          LocalTime endInclusive) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(startInclusive);
        Objects.requireNonNull(endInclusive);
        return RandomNumberSuppliers.randomPrimitiveLong(random, startInclusive.toSecondOfDay(), endInclusive.toSecondOfDay() + 1);
    }

    public static LongSupplier randomNanoOfDay(RandomGenerator random,
                                               LocalTime startInclusive,
                                               LocalTime endExclusive) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(startInclusive);
        Objects.requireNonNull(endExclusive);
        return RandomNumberSuppliers.randomPrimitiveLong(random, startInclusive.toNanoOfDay(), endExclusive.toNanoOfDay());
    }

    public static LongSupplier randomNanoOfDayInclusive(RandomGenerator random,
                                                        LocalTime startInclusive,
                                                        LocalTime endInclusive) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(startInclusive);
        Objects.requireNonNull(endInclusive);
        return RandomNumberSuppliers.randomPrimitiveLong(random, startInclusive.toNanoOfDay(), endInclusive.toNanoOfDay() + 1);
    }

    public static IntSupplier randomYearInclusive(RandomGenerator random,
                                                  Year startInclusive,
                                                  Year endInclusive) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(startInclusive);
        Objects.requireNonNull(endInclusive);
        return RandomNumberSuppliers.randomPrimitiveInt(random, startInclusive.getValue(), endInclusive.getValue() + 1);
    }

    public static IntSupplier randomMonthInclusive(RandomGenerator random,
                                                   Month startInclusive,
                                                   Month endInclusive) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(startInclusive);
        Objects.requireNonNull(endInclusive);
        return RandomNumberSuppliers.randomPrimitiveInt(random, startInclusive.getValue(), endInclusive.getValue() + 1);
    }

    public static IntSupplier randomDayOfWeekInclusive(RandomGenerator random,
                                                       DayOfWeek startInclusive,
                                                       DayOfWeek endInclusive) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(startInclusive);
        Objects.requireNonNull(endInclusive);
        return RandomNumberSuppliers.randomPrimitiveInt(random, startInclusive.getValue(), endInclusive.getValue() + 1);
    }

}
