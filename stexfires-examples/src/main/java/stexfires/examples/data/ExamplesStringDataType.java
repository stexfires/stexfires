package stexfires.examples.data;

import stexfires.data.DataTypeConverterException;
import stexfires.data.DataTypeFormatter;
import stexfires.data.DataTypeParser;
import stexfires.data.StringDataTypeFormatter;
import stexfires.data.StringDataTypeParser;
import stexfires.util.Strings;
import stexfires.util.function.StringPredicates;
import stexfires.util.function.StringUnaryOperators;
import stexfires.util.function.Suppliers;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Locale;

@SuppressWarnings({"UseOfSystemOutOrSystemErr"})
public final class ExamplesStringDataType {

    private ExamplesStringDataType() {
    }

    private static void testParse(String source, DataTypeParser<String> parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + parser.parse(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testFormat(String source, DataTypeFormatter<String> formatter) {
        try {
            System.out.println("Format: \"" + source + "\". Result: " + formatter.format(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Format: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    public static void main(String... args) {

        System.out.println("---StringDataTypeFormatter NULL and EMPTY handling");
        testFormat(null, new StringDataTypeFormatter(null, null));
        testFormat(null, new StringDataTypeFormatter(null, () -> "<NULL>"));
        testFormat("", new StringDataTypeFormatter(null, null));

        System.out.println("---StringDataTypeParser NULL and EMPTY handling");
        testParse(null, new StringDataTypeParser(null, null, null, null));
        testParse(null, new StringDataTypeParser(null, null, () -> "<NULL>", null));
        testParse("", new StringDataTypeParser(null, null, null, null));
        testParse("", new StringDataTypeParser(null, null, null, () -> "<EMPTY>"));

        System.out.println("---StringDataTypeFormatter identity");
        testFormat(null, StringDataTypeFormatter.identity());
        testFormat("", StringDataTypeFormatter.identity());
        testFormat("Test", StringDataTypeFormatter.identity());
        testFormat("ä Ä ß s SS", StringDataTypeFormatter.identity());

        System.out.println("---StringDataTypeParser identity");
        testParse(null, StringDataTypeParser.identity());
        testParse("", StringDataTypeParser.identity());
        testParse("Test", StringDataTypeParser.identity());
        testParse("ä Ä ß s SS", StringDataTypeParser.identity());

        System.out.println("---StringDataTypeFormatter upperCase");
        testFormat(null, StringDataTypeFormatter.passingNull(StringUnaryOperators.upperCase(Locale.GERMAN)));
        testFormat("", StringDataTypeFormatter.passingNull(StringUnaryOperators.upperCase(Locale.GERMAN)));
        testFormat("    ", StringDataTypeFormatter.passingNull(StringUnaryOperators.upperCase(Locale.GERMAN)));
        testFormat("Test", StringDataTypeFormatter.passingNull(StringUnaryOperators.upperCase(Locale.GERMAN)));
        testFormat("ä Ä ß s SS", StringDataTypeFormatter.passingNull(StringUnaryOperators.upperCase(Locale.GERMAN)));

        System.out.println("---StringDataTypeParser upperCase");
        testParse(null, StringDataTypeParser.withEqualityCheck(StringUnaryOperators.upperCase(Locale.GERMAN)));
        testParse("", StringDataTypeParser.withEqualityCheck(StringUnaryOperators.upperCase(Locale.GERMAN)));
        testParse("    ", StringDataTypeParser.withEqualityCheck(StringUnaryOperators.upperCase(Locale.GERMAN)));
        testParse("test", StringDataTypeParser.withEqualityCheck(StringUnaryOperators.upperCase(Locale.GERMAN)));
        testParse("TEST", StringDataTypeParser.withEqualityCheck(StringUnaryOperators.upperCase(Locale.GERMAN)));
        testParse("Ä Ä SS S SS", StringDataTypeParser.withEqualityCheck(StringUnaryOperators.upperCase(Locale.GERMAN)));

        System.out.println("---StringDataTypeFormatter lowerCase");
        testFormat("Test", StringDataTypeFormatter.passingNull(StringUnaryOperators.lowerCase(Locale.GERMAN)));
        testFormat("ä Ä ß s SS", StringDataTypeFormatter.passingNull(StringUnaryOperators.lowerCase(Locale.GERMAN)));

        System.out.println("---StringDataTypeParser lowerCase");
        testParse("test", StringDataTypeParser.withEqualityCheck(StringUnaryOperators.lowerCase(Locale.GERMAN)));
        testParse("TEST", StringDataTypeParser.withEqualityCheck(StringUnaryOperators.lowerCase(Locale.GERMAN)));
        testParse("ä ä ß s ss", StringDataTypeParser.withEqualityCheck(StringUnaryOperators.lowerCase(Locale.GERMAN)));

        System.out.println("---StringDataTypeFormatter surround");
        testFormat(null, new StringDataTypeFormatter(StringUnaryOperators.surround("'", "'"), Suppliers.constantOfNotNull("''")));
        testFormat("", new StringDataTypeFormatter(StringUnaryOperators.surround("'", "'"), Suppliers.constantOfNotNull("''")));
        testFormat("    ", new StringDataTypeFormatter(StringUnaryOperators.surround("'", "'"), Suppliers.constantOfNotNull("''")));
        testFormat("''", new StringDataTypeFormatter(StringUnaryOperators.surround("'", "'"), Suppliers.constantOfNotNull("''")));
        testFormat("Test", new StringDataTypeFormatter(StringUnaryOperators.surround("'", "'"), Suppliers.constantOfNotNull("''")));

        System.out.println("---StringDataTypeParser surroundedBy strict");
        testParse(null, new StringDataTypeParser(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringDataTypeParser.THROW_ERROR_FOR_NULL, StringDataTypeParser.THROW_ERROR_FOR_EMPTY));
        testParse("", new StringDataTypeParser(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringDataTypeParser.THROW_ERROR_FOR_NULL, StringDataTypeParser.THROW_ERROR_FOR_EMPTY));
        testParse("Test", new StringDataTypeParser(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringDataTypeParser.THROW_ERROR_FOR_NULL, StringDataTypeParser.THROW_ERROR_FOR_EMPTY));
        testParse("''", new StringDataTypeParser(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringDataTypeParser.THROW_ERROR_FOR_NULL, StringDataTypeParser.THROW_ERROR_FOR_EMPTY));
        testParse("'    '", new StringDataTypeParser(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringDataTypeParser.THROW_ERROR_FOR_NULL, StringDataTypeParser.THROW_ERROR_FOR_EMPTY));
        testParse("''''", new StringDataTypeParser(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringDataTypeParser.THROW_ERROR_FOR_NULL, StringDataTypeParser.THROW_ERROR_FOR_EMPTY));
        testParse("'Test'", new StringDataTypeParser(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringDataTypeParser.THROW_ERROR_FOR_NULL, StringDataTypeParser.THROW_ERROR_FOR_EMPTY));

        System.out.println("---StringDataTypeParser surroundedBy optional");
        testParse(null, new StringDataTypeParser(null, StringUnaryOperators.conditionalOperator(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringUnaryOperators.identity()), Suppliers.constantNull(), Suppliers.constantOfNotNull(Strings.EMPTY)));
        testParse("", new StringDataTypeParser(null, StringUnaryOperators.conditionalOperator(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringUnaryOperators.identity()), Suppliers.constantNull(), Suppliers.constantOfNotNull(Strings.EMPTY)));
        testParse("Test", new StringDataTypeParser(null, StringUnaryOperators.conditionalOperator(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringUnaryOperators.identity()), Suppliers.constantNull(), Suppliers.constantOfNotNull(Strings.EMPTY)));
        testParse("''", new StringDataTypeParser(null, StringUnaryOperators.conditionalOperator(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringUnaryOperators.identity()), Suppliers.constantNull(), Suppliers.constantOfNotNull(Strings.EMPTY)));
        testParse("'    '", new StringDataTypeParser(null, StringUnaryOperators.conditionalOperator(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringUnaryOperators.identity()), Suppliers.constantNull(), Suppliers.constantOfNotNull(Strings.EMPTY)));
        testParse("''''", new StringDataTypeParser(null, StringUnaryOperators.conditionalOperator(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringUnaryOperators.identity()), Suppliers.constantNull(), Suppliers.constantOfNotNull(Strings.EMPTY)));
        testParse("'Test'", new StringDataTypeParser(null, StringUnaryOperators.conditionalOperator(StringPredicates.surroundedBy("'", "'"), StringUnaryOperators.concat(StringUnaryOperators.removeStringFromStart("'"), StringUnaryOperators.removeStringFromEnd("'")), StringUnaryOperators.identity()), Suppliers.constantNull(), Suppliers.constantOfNotNull(Strings.EMPTY)));

        System.out.println("---StringDataTypeFormatter Base64");
        testFormat("Test", StringDataTypeFormatter.passingNull(StringUnaryOperators.encodeBase64(Base64.getEncoder(), StandardCharsets.US_ASCII)));
        testFormat("ä ß €", StringDataTypeFormatter.passingNull(StringUnaryOperators.encodeBase64(Base64.getEncoder(), StandardCharsets.UTF_8)));

        System.out.println("---StringDataTypeParser Base64");
        testParse("VGVzdA==", new StringDataTypeParser(null, StringUnaryOperators.decodeBase64(Base64.getDecoder(), StandardCharsets.US_ASCII), Suppliers.constantNull(), null));
        testParse("VGVzdA==a", new StringDataTypeParser(null, StringUnaryOperators.decodeBase64(Base64.getDecoder(), StandardCharsets.US_ASCII), Suppliers.constantNull(), null));
        testParse("w6Qgw58g4oKs", new StringDataTypeParser(null, StringUnaryOperators.decodeBase64(Base64.getDecoder(), StandardCharsets.US_ASCII), Suppliers.constantNull(), null));
        testParse("w6Qgw58g4oKs", new StringDataTypeParser(null, StringUnaryOperators.decodeBase64(Base64.getDecoder(), StandardCharsets.UTF_8), Suppliers.constantNull(), null));
    }

}
