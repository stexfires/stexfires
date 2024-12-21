package stexfires.examples.data;

import org.jspecify.annotations.Nullable;
import stexfires.data.*;
import stexfires.util.function.StringUnaryOperators;

import java.text.NumberFormat;
import java.time.*;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Locale;

import static stexfires.data.DataTypeConverters.*;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "MagicNumber", "UseOfObsoleteDateTimeApi", "SameParameterValue"})
public final class ExamplesTimeDataType {

    private ExamplesTimeDataType() {
    }

    private static <T extends @Nullable TemporalAccessor> void testParse(@Nullable String source, DataTypeParser<T> parser, DateTimeFormatter formatter) {
        try {
            T parseResult = parser.parse(source);
            String formattedResult = null;
            if (parseResult != null) {
                try {
                    formattedResult = "\"" + formatter.format(parseResult) + "\" (" + parseResult.getClass().getSimpleName() + ")";
                } catch (DateTimeException e) {
                    formattedResult = e.getMessage();
                }
            }
            System.out.println("Parse: \"" + source + "\". Result: " + formattedResult + " toString: " + parseResult);
        } catch (DataTypeConverterException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testParseDate(@Nullable String source, DataTypeParser<Date> parser, DateTimeFormatter formatter) {
        try {
            Date parseResult = parser.parse(source);
            String formattedResult = null;
            if (parseResult != null) {
                try {
                    formattedResult = "\"" + formatter.format(parseResult.toInstant()) + "\" (" + parseResult.getClass().getSimpleName() + ")";
                } catch (DateTimeException e) {
                    formattedResult = e.getMessage();
                }
            }
            System.out.println("Parse: \"" + source + "\". Result: " + formattedResult + " toString: " + parseResult);
        } catch (DataTypeConverterException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static <T extends @Nullable Object> void testFormat(@Nullable T source, DataTypeFormatter<T> formatter) {
        try {
            System.out.println("Format: \"" + source + "\". Result: " + formatter.format(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Format: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    public static void main(String... args) {
        DateTimeFormatter instantFormatter = DateTimeFormatter.ISO_INSTANT;
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy G", Locale.US);
        DateTimeFormatter yearMonthFormatter = DateTimeFormatter.ofPattern("yyyy-MM", Locale.US);
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMM", Locale.US);
        DateTimeFormatter monthDayFormatter = DateTimeFormatter.ofLocalizedPattern("MMMMd").withLocale(Locale.US);
        DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEE", Locale.US);
        DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.US);
        DateTimeFormatter japaneseDateFormatter = DateTimeFormatter.ofLocalizedPattern("GyMMd").withChronology(JapaneseChronology.INSTANCE).withLocale(Locale.JAPAN);
        DateTimeFormatter localTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US);
        DateTimeFormatter zonedDateTimeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;

        System.out.println("---TimeDataTypeFormatter");
        testFormat(null, new TimeDataTypeFormatter<Instant>(instantFormatter, null));
        testFormat(null, new TimeDataTypeFormatter<Instant>(instantFormatter, () -> "NULL"));
        testFormat(Instant.now(), new TimeDataTypeFormatter<>(instantFormatter, null));
        testFormat(Instant.now(), new TimeDataTypeFormatter<>(DateTimeFormatter.ISO_DATE, null));
        testFormat(Year.of(2022), new TimeDataTypeFormatter<>(yearFormatter, null));
        testFormat(YearMonth.of(2022, 11), new TimeDataTypeFormatter<>(yearMonthFormatter, null));
        testFormat(Month.NOVEMBER, new TimeDataTypeFormatter<>(monthFormatter, null));
        testFormat(MonthDay.of(11, 25), new TimeDataTypeFormatter<>(monthDayFormatter, null));
        testFormat(DayOfWeek.FRIDAY, new TimeDataTypeFormatter<>(dayOfWeekFormatter, null));
        testFormat(LocalDate.of(2022, 11, 25), new TimeDataTypeFormatter<>(localDateFormatter, null));
        testFormat(JapaneseDate.from(LocalDate.of(2022, 11, 25)), new TimeDataTypeFormatter<>(japaneseDateFormatter, null));
        testFormat(LocalTime.of(22, 51, 35), new TimeDataTypeFormatter<>(localTimeFormatter, null));
        testFormat(ZonedDateTime.now(), new TimeDataTypeFormatter<>(zonedDateTimeFormatter, null));

        System.out.println("---TimeDataTypeParser");
        testParse(null, new TimeDataTypeParser<>(instantFormatter, Instant::from, null, null), instantFormatter);
        testParse(null, new TimeDataTypeParser<>(instantFormatter, Instant::from, Instant::now, null), instantFormatter);
        testParse("", new TimeDataTypeParser<>(instantFormatter, Instant::from, null, null), instantFormatter);
        testParse("", new TimeDataTypeParser<>(instantFormatter, Instant::from, null, Instant::now), instantFormatter);
        testParse("2022-11-25T22:51:35.766633800Z", new TimeDataTypeParser<>(instantFormatter, Instant::from, null, null), instantFormatter);
        testParse("2022-11-25", new TimeDataTypeParser<>(instantFormatter, Instant::from, null, null), instantFormatter);
        testParse("2022-11-25+01:00", new TimeDataTypeParser<>(DateTimeFormatter.ISO_DATE, Instant::from, null, null), instantFormatter);
        testParse("2022 AD", new TimeDataTypeParser<>(yearFormatter, Year::from, null, null), yearFormatter);
        testParse("2022-11", new TimeDataTypeParser<>(yearMonthFormatter, YearMonth::from, null, null), yearMonthFormatter);
        testParse("Nov", new TimeDataTypeParser<>(monthFormatter, Month::from, null, null), monthFormatter);
        testParse("November 25", new TimeDataTypeParser<>(monthDayFormatter, MonthDay::from, null, null), monthDayFormatter);
        testParse("Fri", new TimeDataTypeParser<>(dayOfWeekFormatter, DayOfWeek::from, null, null), dayOfWeekFormatter);
        testParse("25.11.2022", new TimeDataTypeParser<>(localDateFormatter, LocalDate::from, null, null), localDateFormatter);
        testParse("令和4/11/25", new TimeDataTypeParser<>(japaneseDateFormatter, JapaneseDate::from, null, null), japaneseDateFormatter);
        testParse("22:51:35", new TimeDataTypeParser<>(localTimeFormatter, LocalTime::from, null, null), localTimeFormatter);
        testParse("2022-11-25T22:51:35.1634235+01:00[Europe/Berlin]", new TimeDataTypeParser<>(zonedDateTimeFormatter, ZonedDateTime::from, null, null), zonedDateTimeFormatter);

        System.out.println("---GenericDataTypeFormatter newInstantEpochSecondsDataTypeFormatter newInstantEpochMilliDataTypeFormatter");
        testFormat(Instant.now(), GenericDataTypeFormatter.forInstantEpochSecond(null));
        testFormat(Instant.now(), GenericDataTypeFormatter.forInstantEpochMilli(null));

        System.out.println("---GenericDataTypeFormatter newInstantEpochSecondDataTypeParser newInstantEpochMilliDataTypeParser");
        testParse(null, GenericDataTypeParser.forInstantEpochSecondWithSuppliers(Instant::now, null), instantFormatter);
        testParse("", GenericDataTypeParser.forInstantEpochSecondWithSuppliers(null, Instant::now), instantFormatter);
        testParse("1673760570", GenericDataTypeParser.forInstantEpochSecond(null), instantFormatter);
        testParse(String.valueOf(Long.MAX_VALUE), GenericDataTypeParser.forInstantEpochSecond(null), instantFormatter);

        testParse("1673761073289", GenericDataTypeParser.forInstantEpochMilli(null), instantFormatter);
        testParse("1.673.761.073.289", GenericDataTypeParser.forInstantEpochMilli(null), instantFormatter);

        System.out.println("---compose andThen NumberDataTypeFormatter toEpochMilli surround");
        testFormat(Instant.now(),
                new NumberDataTypeFormatter<Long>(NumberFormat.getIntegerInstance(Locale.GERMANY), null)
                        .compose(Instant::toEpochMilli)
                        .andThen(StringUnaryOperators.surround("'", "'")));

        System.out.println("---compose andThen NumberDataTypeParser removeStringFromStart removeStringFromEnd ofEpochMilli");
        testParse("'1.673.761.073.289'",
                new NumberDataTypeParser<>(NumberFormat.getIntegerInstance(Locale.GERMANY), NumberDataTypeParser::toLong, null, null)
                        .compose(StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")))
                        .andThen(Instant::ofEpochMilli), instantFormatter);

        System.out.println("---ConvertingDataTypeFormatter Instant, Long");
        ConvertingDataTypeFormatter<Instant, Long> convertingDataTypeFormatterInstantLong = new ConvertingDataTypeFormatter<>(
                Instant::toEpochMilli,
                new NumberDataTypeFormatter<>(NumberFormat.getIntegerInstance(Locale.GERMANY), null),
                StringUnaryOperators.surround("'", "'"),
                () -> "Instant is null!");
        testFormat(null, convertingDataTypeFormatterInstantLong);
        testFormat(Instant.now(), convertingDataTypeFormatterInstantLong);

        System.out.println("---ConvertingDataTypeParser Instant, Long");
        ConvertingDataTypeParser<Instant, Long> convertingDataTypeParserInstantLong = new ConvertingDataTypeParser<>(
                StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")),
                new NumberDataTypeParser<>(NumberFormat.getIntegerInstance(Locale.GERMANY), NumberDataTypeParser::toLong, null, () -> 1000L * 60L * 60L * 24L),
                Instant::ofEpochMilli,
                () -> Instant.ofEpochMilli(1000L),
                () -> Instant.ofEpochMilli(-1000L));
        testParse(null, convertingDataTypeParserInstantLong, instantFormatter);
        testParse("", convertingDataTypeParserInstantLong, instantFormatter);
        testParse("''", convertingDataTypeParserInstantLong, instantFormatter);
        testParse("1.673.761.073.289", convertingDataTypeParserInstantLong, instantFormatter);
        testParse("'1673761073289'", convertingDataTypeParserInstantLong, instantFormatter);
        testParse("'1.673.761.073.289'", convertingDataTypeParserInstantLong, instantFormatter);

        System.out.println("---ConvertingDataTypeFormatter Date, Instant");
        ConvertingDataTypeFormatter<Date, Instant> convertingDataTypeFormatterDateInstant = new ConvertingDataTypeFormatter<>(
                DATE_TO_INSTANT,
                new TimeDataTypeFormatter<>(instantFormatter, null),
                StringUnaryOperators.surround("'", "'"),
                () -> "Instant is null!");
        testFormat(null, convertingDataTypeFormatterDateInstant);
        testFormat(new Date(System.currentTimeMillis()), convertingDataTypeFormatterDateInstant);
        testFormat(new Date(Long.MAX_VALUE), convertingDataTypeFormatterDateInstant);

        System.out.println("---ConvertingDataTypeParser Date, Instant");
        ConvertingDataTypeParser<Date, Instant> convertingDataTypeParserDateInstant = new ConvertingDataTypeParser<>(
                StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")),
                new TimeDataTypeParser<>(instantFormatter, Instant::from, null, Instant::now),
                INSTANT_TO_DATE,
                () -> new Date(0),
                () -> new Date(1000L * 60L * 60L));
        testParseDate(null, convertingDataTypeParserDateInstant, instantFormatter);
        testParseDate("", convertingDataTypeParserDateInstant, instantFormatter);
        testParseDate("''", convertingDataTypeParserDateInstant, instantFormatter);
        testParseDate("2023-01-24T18:56:53.038Z", convertingDataTypeParserDateInstant, instantFormatter);
        testParseDate("'2023-01-24T18:56:53.038Z'", convertingDataTypeParserDateInstant, instantFormatter);
        testParseDate("'2023-01-24'", convertingDataTypeParserDateInstant, instantFormatter);
        testParseDate("'+292278994-08-17T07:12:55.807Z'", convertingDataTypeParserDateInstant, instantFormatter);
        testParseDate("'+392278994-08-17T07:12:55.807Z'", convertingDataTypeParserDateInstant, instantFormatter);
    }

}
