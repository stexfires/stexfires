package stexfires.examples.javatest;

import stexfires.io.RecordIOStreams;
import stexfires.io.markdown.table.MarkdownTableConsumer;
import stexfires.io.markdown.table.MarkdownTableFieldSpec;
import stexfires.io.markdown.table.MarkdownTableFileSpec;
import stexfires.record.TextRecord;
import stexfires.record.consumer.UncheckedConsumerException;
import stexfires.record.filter.RecordFilter;
import stexfires.record.filter.TextFilter;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.util.Alignment;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;
import stexfires.util.function.StringPredicates;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "SpellCheckingInspection"})
public final class CharacterTest {

    private static final String MISSING = "-----";
    private static final String NOT_PRINTABLE = "-----";
    private static final int INDEX_MIRRORED = 7;
    private static final int INDEX_TYPE = 15;
    private static final int INDEX_BLOCK = 16;

    private CharacterTest() {
    }

    public static String convertTypeIntoString(int characterType) {
        return switch (characterType) {
            case Character.COMBINING_SPACING_MARK -> "COMBINING_SPACING_MARK";
            case Character.CONNECTOR_PUNCTUATION -> "CONNECTOR_PUNCTUATION";
            case Character.CONTROL -> "CONTROL";
            case Character.CURRENCY_SYMBOL -> "CURRENCY_SYMBOL";
            case Character.DASH_PUNCTUATION -> "DASH_PUNCTUATION";
            case Character.DECIMAL_DIGIT_NUMBER -> "DECIMAL_DIGIT_NUMBER";
            case Character.ENCLOSING_MARK -> "ENCLOSING_MARK";
            case Character.END_PUNCTUATION -> "END_PUNCTUATION";
            case Character.FINAL_QUOTE_PUNCTUATION -> "FINAL_QUOTE_PUNCTUATION";
            case Character.FORMAT -> "FORMAT";
            case Character.INITIAL_QUOTE_PUNCTUATION -> "INITIAL_QUOTE_PUNCTUATION";
            case Character.LETTER_NUMBER -> "LETTER_NUMBER";
            case Character.LINE_SEPARATOR -> "LINE_SEPARATOR";
            case Character.LOWERCASE_LETTER -> "LOWERCASE_LETTER";
            case Character.MATH_SYMBOL -> "MATH_SYMBOL";
            case Character.MODIFIER_LETTER -> "MODIFIER_LETTER";
            case Character.MODIFIER_SYMBOL -> "MODIFIER_SYMBOL";
            case Character.NON_SPACING_MARK -> "NON_SPACING_MARK";
            case Character.OTHER_LETTER -> "OTHER_LETTER";
            case Character.OTHER_NUMBER -> "OTHER_NUMBER";
            case Character.OTHER_PUNCTUATION -> "OTHER_PUNCTUATION";
            case Character.OTHER_SYMBOL -> "OTHER_SYMBOL";
            case Character.PARAGRAPH_SEPARATOR -> "PARAGRAPH_SEPARATOR";
            case Character.PRIVATE_USE -> "PRIVATE_USE";
            case Character.SPACE_SEPARATOR -> "SPACE_SEPARATOR";
            case Character.START_PUNCTUATION -> "START_PUNCTUATION";
            case Character.SURROGATE -> "SURROGATE";
            case Character.TITLECASE_LETTER -> "TITLECASE_LETTER";
            case Character.UNASSIGNED -> "UNASSIGNED";
            case Character.UPPERCASE_LETTER -> "UPPERCASE_LETTER";
            default -> MISSING;
        };
    }

    public static String convertDirectionalityIntoString(byte unicodeDirectionality) {
        return switch (unicodeDirectionality) {
            case Character.DIRECTIONALITY_UNDEFINED -> "DIRECTIONALITY_UNDEFINED";
            case Character.DIRECTIONALITY_LEFT_TO_RIGHT -> "DIRECTIONALITY_LEFT_TO_RIGHT";
            case Character.DIRECTIONALITY_RIGHT_TO_LEFT -> "DIRECTIONALITY_RIGHT_TO_LEFT";
            case Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC -> "DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC";
            case Character.DIRECTIONALITY_EUROPEAN_NUMBER -> "DIRECTIONALITY_EUROPEAN_NUMBER";
            case Character.DIRECTIONALITY_EUROPEAN_NUMBER_SEPARATOR -> "DIRECTIONALITY_EUROPEAN_NUMBER_SEPARATOR";
            case Character.DIRECTIONALITY_EUROPEAN_NUMBER_TERMINATOR -> "DIRECTIONALITY_EUROPEAN_NUMBER_TERMINATOR";
            case Character.DIRECTIONALITY_ARABIC_NUMBER -> "DIRECTIONALITY_ARABIC_NUMBER";
            case Character.DIRECTIONALITY_COMMON_NUMBER_SEPARATOR -> "DIRECTIONALITY_COMMON_NUMBER_SEPARATOR";
            case Character.DIRECTIONALITY_NONSPACING_MARK -> "DIRECTIONALITY_NONSPACING_MARK";
            case Character.DIRECTIONALITY_BOUNDARY_NEUTRAL -> "DIRECTIONALITY_BOUNDARY_NEUTRAL";
            case Character.DIRECTIONALITY_PARAGRAPH_SEPARATOR -> "DIRECTIONALITY_PARAGRAPH_SEPARATOR";
            case Character.DIRECTIONALITY_SEGMENT_SEPARATOR -> "DIRECTIONALITY_SEGMENT_SEPARATOR";
            case Character.DIRECTIONALITY_WHITESPACE -> "DIRECTIONALITY_WHITESPACE";
            case Character.DIRECTIONALITY_OTHER_NEUTRALS -> "DIRECTIONALITY_OTHER_NEUTRALS";
            case Character.DIRECTIONALITY_LEFT_TO_RIGHT_EMBEDDING -> "DIRECTIONALITY_LEFT_TO_RIGHT_EMBEDDING";
            case Character.DIRECTIONALITY_LEFT_TO_RIGHT_OVERRIDE -> "DIRECTIONALITY_LEFT_TO_RIGHT_OVERRIDE";
            case Character.DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING -> "DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING";
            case Character.DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE -> "DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE";
            case Character.DIRECTIONALITY_POP_DIRECTIONAL_FORMAT -> "DIRECTIONALITY_POP_DIRECTIONAL_FORMAT";
            case Character.DIRECTIONALITY_LEFT_TO_RIGHT_ISOLATE -> "DIRECTIONALITY_LEFT_TO_RIGHT_ISOLATE";
            case Character.DIRECTIONALITY_RIGHT_TO_LEFT_ISOLATE -> "DIRECTIONALITY_RIGHT_TO_LEFT_ISOLATE";
            case Character.DIRECTIONALITY_FIRST_STRONG_ISOLATE -> "DIRECTIONALITY_FIRST_STRONG_ISOLATE";
            case Character.DIRECTIONALITY_POP_DIRECTIONAL_ISOLATE -> "DIRECTIONALITY_POP_DIRECTIONAL_ISOLATE";
            default -> MISSING;
        };
    }

    public static TextRecord generateCodePointRecord(int codePoint) {
        int characterType = Character.getType(codePoint);
        String codePointAsPrintableString = (characterType == Character.CONTROL || characterType == Character.SURROGATE
                || characterType == Character.UNASSIGNED || characterType == Character.PRIVATE_USE)
                ? NOT_PRINTABLE : Character.toString(codePoint);
        UnicodeBlock unicodeBlock = UnicodeBlock.of(codePoint);
        String block = (unicodeBlock == null) ? MISSING : unicodeBlock.toString();

        return new ManyFieldsRecord(
                // category
                String.valueOf(characterType),
                // recordId
                (long) codePoint,
                // text fields
                String.valueOf(codePoint),
                Integer.toHexString(codePoint),
                codePointAsPrintableString,
                String.valueOf(Character.charCount(codePoint)),
                Character.getName(codePoint),
                String.valueOf(Character.isDefined(codePoint)),
                String.valueOf(Character.isValidCodePoint(codePoint)),
                String.valueOf(Character.isMirrored(codePoint)),
                String.valueOf(Character.isISOControl(codePoint)),
                String.valueOf(Character.isAlphabetic(codePoint)),
                String.valueOf(Character.isLetter(codePoint)),
                String.valueOf(Character.isSpaceChar(codePoint)),
                String.valueOf(Character.isDigit(codePoint)),
                String.valueOf(Character.digit(codePoint, 10)),
                String.valueOf(Character.getNumericValue(codePoint)),
                convertTypeIntoString(characterType),
                block,
                convertDirectionalityIntoString(Character.getDirectionality(codePoint))
        );
    }

    public static Stream<TextRecord> generateCodePointRecordStream(int lowestCodePoint, int highestCodePoint) {
        return IntStream.rangeClosed(lowestCodePoint, highestCodePoint)
                        .mapToObj(CharacterTest::generateCodePointRecord);
    }

    @SuppressWarnings({"MagicNumber", "OverlyBroadThrowsClause"})
    private static void writeMarkdownTableFile(File outputFile, Stream<TextRecord> recordStream)
            throws IOException {
        Objects.requireNonNull(outputFile);
        Objects.requireNonNull(recordStream);

        List<MarkdownTableFieldSpec> fieldSpecsConsumer = new ArrayList<>();
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Decimal", Alignment.END));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Hex", Alignment.END));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Char", Alignment.START));

        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Count", Alignment.END));

        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Name", 75, Alignment.START));

        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Def?", 7, Alignment.START));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Valid?", 7, Alignment.START));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Mirror?", 7, Alignment.START));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("ISO?", 7, Alignment.START));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Alpha?", 7, Alignment.START));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Letter?", 7, Alignment.START));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Space?", 7, Alignment.START));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Digit?", 7, Alignment.START));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Digit", 7, Alignment.END));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("NumVal", 10, Alignment.END));

        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Type", 30, Alignment.START));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Block", 45, Alignment.START));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Directionality", 45, Alignment.START));

        MarkdownTableFileSpec consumerFileSpec = MarkdownTableFileSpec.consumerFileSpec(
                CharsetCoding.UTF_8_REPORTING,
                LineSeparator.CR_LF,
                fieldSpecsConsumer
        );

        try (MarkdownTableConsumer consumer = consumerFileSpec.consumer(new FileOutputStream(outputFile))) {
            RecordIOStreams.writeStream(consumer, recordStream);
        }
    }

    private static void writeFilteredFile(File outputMarkdownFile, RecordFilter<TextRecord> recordFilter)
            throws IOException {
        Objects.requireNonNull(outputMarkdownFile);
        Objects.requireNonNull(recordFilter);

        System.out.println("Generate MarkdownTable file: " + outputMarkdownFile);

        writeMarkdownTableFile(outputMarkdownFile,
                generateCodePointRecordStream(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT)
                        .filter(recordFilter.asPredicate()));
    }

    public static void main(String... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Missing valid output directory parameter!");
        }
        File outputDirectory = new File(args[0]);
        if (!outputDirectory.exists() || !outputDirectory.isDirectory()) {
            throw new IllegalArgumentException("Missing valid output directory parameter! " + outputDirectory);
        }

        try {
            // LETTER
            writeFilteredFile(new File(outputDirectory,
                            "Character_Markdown_Table_LETTER.md"),
                    TextFilter.containedIn(INDEX_TYPE,
                            List.of(
                                    "LOWERCASE_LETTER",
                                    "MODIFIER_LETTER",
                                    "OTHER_LETTER",
                                    "LETTER_NUMBER",
                                    "TITLECASE_LETTER",
                                    "UPPERCASE_LETTER"
                            )));

            // NUMBER
            writeFilteredFile(new File(outputDirectory,
                            "Character_Markdown_Table_NUMBER.md"),
                    TextFilter.containedIn(INDEX_TYPE,
                            List.of(
                                    "DECIMAL_DIGIT_NUMBER",
                                    "OTHER_NUMBER"
                            )));

            // SYMBOL_PUNCTUATION
            writeFilteredFile(new File(outputDirectory,
                            "Character_Markdown_Table_SYMBOL_PUNCTUATION.md"),
                    TextFilter.containedIn(INDEX_TYPE,
                            List.of(
                                    "CURRENCY_SYMBOL",
                                    "MATH_SYMBOL",
                                    "MODIFIER_SYMBOL",
                                    "OTHER_SYMBOL",
                                    "CONNECTOR_PUNCTUATION",
                                    "DASH_PUNCTUATION",
                                    "END_PUNCTUATION",
                                    "FINAL_QUOTE_PUNCTUATION",
                                    "INITIAL_QUOTE_PUNCTUATION",
                                    "OTHER_PUNCTUATION",
                                    "START_PUNCTUATION"
                            )));

            // MISC
            writeFilteredFile(new File(outputDirectory,
                            "Character_Markdown_Table_MISC.md"),
                    TextFilter.containedIn(INDEX_TYPE,
                            List.of(
                                    "LINE_SEPARATOR",
                                    "PARAGRAPH_SEPARATOR",
                                    "SPACE_SEPARATOR",
                                    "COMBINING_SPACING_MARK",
                                    "ENCLOSING_MARK",
                                    "FORMAT",
                                    "NON_SPACING_MARK",
                                    "CONTROL"
                            )));

            // Block_LATIN
            writeFilteredFile(new File(outputDirectory,
                            "Character_Markdown_Table_Block_LATIN.md"),
                    new TextFilter<>(INDEX_BLOCK,
                            StringPredicates.contains(
                                    "LATIN"
                            )));

            // IsMirrored
            writeFilteredFile(new File(outputDirectory,
                            "Character_Markdown_Table_IsMirrored.md"),
                    new TextFilter<>(INDEX_MIRRORED,
                            StringPredicates.equals(
                                    "true"
                            )));
        } catch (IOException | UncheckedConsumerException e) {
            System.out.println("Cannot generate MarkdownTable file! " + e.getMessage());
        }
    }

}
