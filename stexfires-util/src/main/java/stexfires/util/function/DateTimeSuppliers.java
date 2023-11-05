package stexfires.util.function;

import org.jetbrains.annotations.NotNull;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;

/**
 * @since 0.1
 */
public final class DateTimeSuppliers {

    private DateTimeSuppliers() {
    }

    public static Supplier<Instant> instantOfEpochSecond(@NotNull LongSupplier epochSecondSupplier) {
        Objects.requireNonNull(epochSecondSupplier);
        return () -> Instant.ofEpochSecond(epochSecondSupplier.getAsLong());
    }

    public static Supplier<Instant> instantOfEpochMilli(@NotNull LongSupplier epochMilliSupplier) {
        Objects.requireNonNull(epochMilliSupplier);
        return () -> Instant.ofEpochMilli(epochMilliSupplier.getAsLong());
    }

    public static Supplier<LocalDate> localDateOfInstant(@NotNull Supplier<Instant> instantSupplier,
                                                         @NotNull ZoneId zone) {
        Objects.requireNonNull(instantSupplier);
        Objects.requireNonNull(zone);
        return () -> LocalDate.ofInstant(instantSupplier.get(), zone);
    }

    public static Supplier<LocalDate> localDateOfEpochDay(@NotNull LongSupplier epochDaySupplier) {
        Objects.requireNonNull(epochDaySupplier);
        return () -> LocalDate.ofEpochDay(epochDaySupplier.getAsLong());
    }

    public static Supplier<LocalTime> localTimeOfInstant(@NotNull Supplier<Instant> instantSupplier,
                                                         @NotNull ZoneId zone) {
        Objects.requireNonNull(instantSupplier);
        Objects.requireNonNull(zone);
        return () -> LocalTime.ofInstant(instantSupplier.get(), zone);
    }

    public static Supplier<LocalTime> localTimeOfSecondOfDay(@NotNull LongSupplier secondOfDaySupplier) {
        Objects.requireNonNull(secondOfDaySupplier);
        return () -> LocalTime.ofSecondOfDay(secondOfDaySupplier.getAsLong());
    }

    public static Supplier<LocalTime> localTimeOfNanoOfDay(@NotNull LongSupplier nanoOfDaySupplier) {
        Objects.requireNonNull(nanoOfDaySupplier);
        return () -> LocalTime.ofNanoOfDay(nanoOfDaySupplier.getAsLong());
    }

    public static Supplier<LocalDateTime> localDateTimeOfInstant(@NotNull Supplier<Instant> instantSupplier,
                                                                 @NotNull ZoneId zone) {
        Objects.requireNonNull(instantSupplier);
        Objects.requireNonNull(zone);
        return () -> LocalDateTime.ofInstant(instantSupplier.get(), zone);
    }

    public static Supplier<LocalDateTime> localDateTimeOfLocaleDateAndLocalTime(@NotNull Supplier<LocalDate> localDateSupplier,
                                                                                @NotNull Supplier<LocalTime> localTimeSupplier) {
        Objects.requireNonNull(localDateSupplier);
        Objects.requireNonNull(localTimeSupplier);
        return () -> LocalDateTime.of(localDateSupplier.get(), localTimeSupplier.get());
    }

    public static Supplier<ZonedDateTime> zonedDateTimeOfInstant(@NotNull Supplier<Instant> instantSupplier,
                                                                 @NotNull ZoneId zone) {
        Objects.requireNonNull(instantSupplier);
        Objects.requireNonNull(zone);
        return () -> ZonedDateTime.ofInstant(instantSupplier.get(), zone);
    }

    public static Supplier<ZonedDateTime> zonedDateTimeOfLocalDateTime(@NotNull Supplier<LocalDateTime> localDateTimeSupplier,
                                                                       @NotNull ZoneId zone) {
        Objects.requireNonNull(localDateTimeSupplier);
        Objects.requireNonNull(zone);
        return () -> ZonedDateTime.of(localDateTimeSupplier.get(), zone);
    }

    public static Supplier<ZonedDateTime> zonedDateTimeOfLocalDateAndLocalTime(@NotNull Supplier<LocalDate> localDateSupplier,
                                                                               @NotNull Supplier<LocalTime> localTimeSupplier,
                                                                               @NotNull ZoneId zone) {
        Objects.requireNonNull(localDateSupplier);
        Objects.requireNonNull(localTimeSupplier);
        Objects.requireNonNull(zone);
        return () -> ZonedDateTime.of(localDateSupplier.get(), localTimeSupplier.get(), zone);
    }

    public static Supplier<Year> year(@NotNull IntSupplier yearSupplier) {
        Objects.requireNonNull(yearSupplier);
        return () -> Year.of(yearSupplier.getAsInt());
    }

    public static Supplier<Month> month(@NotNull IntSupplier monthSupplier) {
        Objects.requireNonNull(monthSupplier);
        return () -> Month.of(monthSupplier.getAsInt());
    }

    public static Supplier<YearMonth> yearMonth(@NotNull IntSupplier yearSupplier,
                                                @NotNull IntSupplier monthSupplier) {
        Objects.requireNonNull(yearSupplier);
        Objects.requireNonNull(monthSupplier);
        return () -> YearMonth.of(yearSupplier.getAsInt(), monthSupplier.getAsInt());
    }

    public static Supplier<DayOfWeek> dayOfWeek(@NotNull IntSupplier dayOfWeekSupplier) {
        Objects.requireNonNull(dayOfWeekSupplier);
        return () -> DayOfWeek.of(dayOfWeekSupplier.getAsInt());
    }

    public static Supplier<Duration> durationOfSeconds(@NotNull LongSupplier secondsSupplier) {
        Objects.requireNonNull(secondsSupplier);
        return () -> Duration.ofSeconds(secondsSupplier.getAsLong());
    }

    public static Supplier<Period> periodOfDays(@NotNull IntSupplier daysSupplier) {
        Objects.requireNonNull(daysSupplier);
        return () -> Period.ofDays(daysSupplier.getAsInt());
    }

    public static LongSupplier randomEpochSecond(@NotNull RandomGenerator random,
                                                 @NotNull Instant startInclusive,
                                                 @NotNull Instant endExclusive) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(startInclusive);
        Objects.requireNonNull(endExclusive);
        return RandomNumberSuppliers.randomPrimitiveLong(random, startInclusive.getEpochSecond(), endExclusive.getEpochSecond());
    }

    public static LongSupplier randomEpochMilli(@NotNull RandomGenerator random,
                                                @NotNull Instant startInclusive,
                                                @NotNull Instant endExclusive) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(startInclusive);
        Objects.requireNonNull(endExclusive);
        return RandomNumberSuppliers.randomPrimitiveLong(random, startInclusive.toEpochMilli(), endExclusive.toEpochMilli());
    }

    public static LongSupplier randomEpochDay(@NotNull RandomGenerator random,
                                              @NotNull LocalDate startInclusive,
                                              @NotNull LocalDate endExclusive) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(startInclusive);
        Objects.requireNonNull(endExclusive);
        return RandomNumberSuppliers.randomPrimitiveLong(random, startInclusive.toEpochDay(), endExclusive.toEpochDay());
    }

    public static LongSupplier randomSecondOfDay(@NotNull RandomGenerator random,
                                                 @NotNull LocalTime startInclusive,
                                                 @NotNull LocalTime endExclusive) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(startInclusive);
        Objects.requireNonNull(endExclusive);
        return RandomNumberSuppliers.randomPrimitiveLong(random, startInclusive.toSecondOfDay(), endExclusive.toSecondOfDay());
    }

    public static LongSupplier randomSecondOfDayInclusive(@NotNull RandomGenerator random,
                                                          @NotNull LocalTime startInclusive,
                                                          @NotNull LocalTime endInclusive) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(startInclusive);
        Objects.requireNonNull(endInclusive);
        return RandomNumberSuppliers.randomPrimitiveLong(random, startInclusive.toSecondOfDay(), endInclusive.toSecondOfDay() + 1);
    }

    public static LongSupplier randomNanoOfDay(@NotNull RandomGenerator random,
                                               @NotNull LocalTime startInclusive,
                                               @NotNull LocalTime endExclusive) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(startInclusive);
        Objects.requireNonNull(endExclusive);
        return RandomNumberSuppliers.randomPrimitiveLong(random, startInclusive.toNanoOfDay(), endExclusive.toNanoOfDay());
    }

    public static LongSupplier randomNanoOfDayInclusive(@NotNull RandomGenerator random,
                                                        @NotNull LocalTime startInclusive,
                                                        @NotNull LocalTime endInclusive) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(startInclusive);
        Objects.requireNonNull(endInclusive);
        return RandomNumberSuppliers.randomPrimitiveLong(random, startInclusive.toNanoOfDay(), endInclusive.toNanoOfDay() + 1);
    }

    public static IntSupplier randomMonthInclusive(@NotNull RandomGenerator random,
                                                   @NotNull Month startInclusive,
                                                   @NotNull Month endInclusive) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(startInclusive);
        Objects.requireNonNull(endInclusive);
        return RandomNumberSuppliers.randomPrimitiveInt(random, startInclusive.getValue(), endInclusive.getValue() + 1);
    }

    public static IntSupplier randomDayOfWeekInclusive(@NotNull RandomGenerator random,
                                                       @NotNull DayOfWeek startInclusive,
                                                       @NotNull DayOfWeek endInclusive) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(startInclusive);
        Objects.requireNonNull(endInclusive);
        return RandomNumberSuppliers.randomPrimitiveInt(random, startInclusive.getValue(), endInclusive.getValue() + 1);
    }

}
