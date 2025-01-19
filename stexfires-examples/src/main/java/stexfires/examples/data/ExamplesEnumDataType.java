package stexfires.examples.data;

import org.jspecify.annotations.Nullable;
import stexfires.data.DataTypeConverterException;
import stexfires.data.EnumDataTypeFormatter;
import stexfires.data.EnumDataTypeParser;
import stexfires.util.Alignment;
import stexfires.util.CommonCharsetNames;
import stexfires.util.function.StringUnaryOperators;

import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Month;
import java.util.*;

@SuppressWarnings({"UseOfSystemOutOrSystemErr"})
public final class ExamplesEnumDataType {

    private ExamplesEnumDataType() {
    }

    private static <T extends @Nullable Enum<T>> void testFormat(@Nullable T source, EnumDataTypeFormatter<T> formatter) {
        try {
            System.out.println("Format: \"" + source + "\". Result: " + formatter.format(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Format: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static <T extends @Nullable Enum<T>> void testParse(@Nullable String source, EnumDataTypeParser<T> parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + parser.parse(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    public static void main(String... args) {
        System.out.println("---EnumDataTypeFormatter");
        testFormat(null, new EnumDataTypeFormatter<Alignment>(null));
        testFormat(null, new EnumDataTypeFormatter<Alignment>(Alignment.CENTER::name));
        testFormat(Alignment.CENTER, new EnumDataTypeFormatter<>(null));

        testFormat(null, new EnumDataTypeFormatter<CommonCharsetNames>(null));
        testFormat(null, new EnumDataTypeFormatter<CommonCharsetNames>(CommonCharsetNames.ISO_8859_1::name));
        testFormat(CommonCharsetNames.ISO_8859_1, new EnumDataTypeFormatter<>(null));

        testFormat(DayOfWeek.MONDAY, new EnumDataTypeFormatter<>(null));
        testFormat(Month.APRIL, new EnumDataTypeFormatter<>(null));
        testFormat(System.Logger.Level.WARNING, new EnumDataTypeFormatter<>(null));
        testFormat(RoundingMode.HALF_DOWN, new EnumDataTypeFormatter<>(null));

        System.out.println("---EnumDataTypeParser");
        testParse(null, new EnumDataTypeParser<>(Alignment.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, null));
        testParse(null, new EnumDataTypeParser<>(Alignment.class, StringUnaryOperators.upperCase(Locale.ENGLISH), () -> Alignment.CENTER, null));
        testParse("", new EnumDataTypeParser<>(Alignment.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, null));
        testParse("", new EnumDataTypeParser<>(Alignment.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, () -> Alignment.CENTER));
        testParse("CENTER", new EnumDataTypeParser<>(Alignment.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, null));
        testParse("center", new EnumDataTypeParser<>(Alignment.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, null));
        testParse("test", new EnumDataTypeParser<>(Alignment.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, null));

        testParse(null, new EnumDataTypeParser<>(CommonCharsetNames.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, null));
        testParse(null, new EnumDataTypeParser<>(CommonCharsetNames.class, StringUnaryOperators.upperCase(Locale.ENGLISH), () -> CommonCharsetNames.ISO_8859_1, null));
        testParse("", new EnumDataTypeParser<>(CommonCharsetNames.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, null));
        testParse("", new EnumDataTypeParser<>(CommonCharsetNames.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, () -> CommonCharsetNames.ISO_8859_1));
        testParse("ISO_8859_1", new EnumDataTypeParser<>(CommonCharsetNames.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, null));
        testParse("iso_8859_1", new EnumDataTypeParser<>(CommonCharsetNames.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, null));
        testParse("test", new EnumDataTypeParser<>(CommonCharsetNames.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, null));

        testParse("MONDAY", new EnumDataTypeParser<>(DayOfWeek.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, null));
        testParse("APRIL", new EnumDataTypeParser<>(Month.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, null));
        testParse("WARNING", new EnumDataTypeParser<>(System.Logger.Level.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, null));
        testParse("HALF_DOWN", new EnumDataTypeParser<>(RoundingMode.class, StringUnaryOperators.upperCase(Locale.ENGLISH), null, null));
    }

}
