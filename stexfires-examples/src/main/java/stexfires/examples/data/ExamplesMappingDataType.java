package stexfires.examples.data;

import org.jspecify.annotations.Nullable;
import stexfires.data.DataTypeConverterException;
import stexfires.data.MappingDataTypeFormatter;
import stexfires.data.MappingDataTypePair;
import stexfires.data.MappingDataTypeParser;

import java.nio.charset.CodingErrorAction;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings({"UseOfSystemOutOrSystemErr"})
public final class ExamplesMappingDataType {

    private ExamplesMappingDataType() {
    }

    private static <T extends @Nullable Object> void testParseMappingDataType(@Nullable String source, MappingDataTypeParser<T> parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + parser.parse(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static <T extends @Nullable Object> void testFormatMappingDataType(@Nullable T source, MappingDataTypeFormatter<T> formatter) {
        try {
            System.out.println("Format: \"" + source + "\". Result: " + formatter.format(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Format: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    public static void main(String... args) {
        // The value 'CodingErrorAction.REPLACE' was intentionally omitted to test error cases.
        List<MappingDataTypePair<CodingErrorAction>> pairList = MappingDataTypePair.createPairList(
                Stream.of(CodingErrorAction.IGNORE, CodingErrorAction.REPORT), CodingErrorAction::toString);

        System.out.println("---MappingDataTypeFormatter CodingErrorAction");
        testFormatMappingDataType(null, new MappingDataTypeFormatter<>(pairList, null));
        testFormatMappingDataType(null, new MappingDataTypeFormatter<>(pairList, CodingErrorAction.IGNORE::toString));
        testFormatMappingDataType(CodingErrorAction.IGNORE, new MappingDataTypeFormatter<>(pairList, null));
        testFormatMappingDataType(CodingErrorAction.REPORT, new MappingDataTypeFormatter<>(pairList, null));
        testFormatMappingDataType(CodingErrorAction.REPLACE, new MappingDataTypeFormatter<>(pairList, null));

        System.out.println("---MappingDataTypeParser CodingErrorAction");
        testParseMappingDataType(null, new MappingDataTypeParser<>(pairList, null, null));
        testParseMappingDataType(null, new MappingDataTypeParser<>(pairList, () -> CodingErrorAction.IGNORE, null));
        testParseMappingDataType("", new MappingDataTypeParser<>(pairList, null, null));
        testParseMappingDataType("", new MappingDataTypeParser<>(pairList, null, () -> CodingErrorAction.IGNORE));
        testParseMappingDataType("IGNORE", new MappingDataTypeParser<>(pairList, null, null));
        testParseMappingDataType("REPORT", new MappingDataTypeParser<>(pairList, null, null));
        testParseMappingDataType("REPLACE", new MappingDataTypeParser<>(pairList, null, null));
        testParseMappingDataType("REPLACE", new MappingDataTypeParser<>(MappingDataTypePair.createPairListCodingErrorAction(), null, null));
    }

}
