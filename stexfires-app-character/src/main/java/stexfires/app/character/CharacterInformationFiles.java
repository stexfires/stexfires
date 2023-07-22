package stexfires.app.character;

import stexfires.io.RecordIOStreams;
import stexfires.io.markdown.table.MarkdownTableConsumer;
import stexfires.io.markdown.table.MarkdownTableFieldSpec;
import stexfires.io.markdown.table.MarkdownTableFileSpec;
import stexfires.record.TextRecord;
import stexfires.record.comparator.RecordComparators;
import stexfires.record.consumer.UncheckedConsumerException;
import stexfires.record.filter.RecordFilter;
import stexfires.record.filter.TextFilter;
import stexfires.util.Alignment;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;
import stexfires.util.SortNulls;
import stexfires.util.StringComparators;
import stexfires.util.function.StringPredicates;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @since 0.1
 */
@SuppressWarnings("SpellCheckingInspection")
public final class CharacterInformationFiles {

    public static final String MISSING = "";
    public static final String NOT_PRINTABLE = "-----";
    public static final LineSeparator LINE_SEPARATOR = LineSeparator.CR_LF;

    private CharacterInformationFiles() {
    }

    @SuppressWarnings({"MagicNumber", "OverlyBroadThrowsClause"})
    private static void writeMarkdownTableFile(File outputFile,
                                               Stream<TextRecord> recordStream,
                                               String textBefore)
            throws IOException {
        Objects.requireNonNull(outputFile);
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(textBefore);

        List<MarkdownTableFieldSpec> fieldSpecsConsumer = new ArrayList<>();
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Decimal", Alignment.END));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Hex", Alignment.END));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Char", Alignment.START));

        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Count", Alignment.END));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Digit", 7, Alignment.END));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("NumVal", 10, Alignment.END));

        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Name", 75, Alignment.START));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Type", 30, Alignment.START));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Block", 45, Alignment.START));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Script", 24, Alignment.START));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Directionality", 45, Alignment.START));

        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Def?", 7, Alignment.START));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Mirror?", 7, Alignment.START));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("ISO?", 7, Alignment.START));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Alpha?", 7, Alignment.START));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Letter?", 7, Alignment.START));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Space?", 7, Alignment.START));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec("Digit?", 7, Alignment.START));

        MarkdownTableFileSpec consumerFileSpec = MarkdownTableFileSpec.consumerFileSpec(
                CharsetCoding.UTF_8_REPORTING,
                LINE_SEPARATOR,
                textBefore,
                null,
                Alignment.CENTER,
                fieldSpecsConsumer
        );

        try (MarkdownTableConsumer consumer = consumerFileSpec.consumer(new FileOutputStream(outputFile))) {
            RecordIOStreams.writeStream(consumer, recordStream);
        }
    }

    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    private static void writeFilteredFile(File outputMarkdownFile,
                                          RecordFilter<TextRecord> recordFilter,
                                          Comparator<TextRecord> recordComparator,
                                          String textBefore)
            throws IOException {
        Objects.requireNonNull(outputMarkdownFile);
        Objects.requireNonNull(recordFilter);
        Objects.requireNonNull(recordComparator);
        Objects.requireNonNull(textBefore);

        System.out.println("Generate MarkdownTable file: " + outputMarkdownFile);

        writeMarkdownTableFile(
                outputMarkdownFile,
                CodePointRecordHelper.generateCodePointRecordStream(Character.MIN_CODE_POINT,
                                             Character.MAX_CODE_POINT,
                                             NOT_PRINTABLE,
                                             MISSING)
                                     .filter(recordFilter.asPredicate())
                                     .sorted(recordComparator),
                textBefore);
    }

    @SuppressWarnings({"UseOfSystemOutOrSystemErr", "StringConcatenationMissingWhitespace"})
    public static void main(String... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Missing valid output directory parameter!");
        }
        File outputDirectory = new File(args[0]);
        if (!outputDirectory.exists() || !outputDirectory.isDirectory()) {
            throw new IllegalArgumentException("Missing valid output directory parameter! " + outputDirectory);
        }

        try {
            // LETTER_LEFT_TO_RIGHT
            writeFilteredFile(new File(outputDirectory,
                            "Character_Markdown_Table_LETTER_LEFT_TO_RIGHT.md"),
                    TextFilter.containedIn(CodePointRecordHelper.INDEX_TYPE,
                                      List.of(
                                              "LOWERCASE_LETTER",
                                              "MODIFIER_LETTER",
                                              "OTHER_LETTER",
                                              "TITLECASE_LETTER",
                                              "UPPERCASE_LETTER"
                                      ))
                              .and(TextFilter.equalTo(CodePointRecordHelper.INDEX_DIRECTIONALITY,
                                      "DIRECTIONALITY_LEFT_TO_RIGHT"
                              )),
                    RecordComparators.textAt(CodePointRecordHelper.INDEX_PRINTABLE_STRING, StringComparators.compareToIgnoreCase(), SortNulls.FIRST)
                                     .thenComparing(RecordComparators.textAt(CodePointRecordHelper.INDEX_TYPE, String::compareTo, SortNulls.FIRST))
                                     .thenComparing(RecordComparators.recordId(SortNulls.FIRST)),
                    "List of Unicode characters with the following types and directionality 'left to right'." + LINE_SEPARATOR
                            + "LOWERCASE_LETTER, MODIFIER_LETTER, OTHER_LETTER, TITLECASE_LETTER, UPPERCASE_LETTER" + LINE_SEPARATOR);

            // LETTER_NOT_LEFT_TO_RIGHT
            writeFilteredFile(new File(outputDirectory,
                            "Character_Markdown_Table_LETTER_NOT_LEFT_TO_RIGHT.md"),
                    TextFilter.containedIn(CodePointRecordHelper.INDEX_TYPE,
                                      List.of(
                                              "LOWERCASE_LETTER",
                                              "MODIFIER_LETTER",
                                              "OTHER_LETTER",
                                              "TITLECASE_LETTER",
                                              "UPPERCASE_LETTER"
                                      ))
                              .and(TextFilter.equalTo(CodePointRecordHelper.INDEX_DIRECTIONALITY,
                                      "DIRECTIONALITY_LEFT_TO_RIGHT"
                              ).negate()),
                    RecordComparators.textAt(CodePointRecordHelper.INDEX_PRINTABLE_STRING, StringComparators.compareToIgnoreCase(), SortNulls.FIRST)
                                     .thenComparing(RecordComparators.textAt(CodePointRecordHelper.INDEX_TYPE, String::compareTo, SortNulls.FIRST))
                                     .thenComparing(RecordComparators.recordId(SortNulls.FIRST)),
                    "List of Unicode characters with the following types and directionality NOT 'left to right'." + LINE_SEPARATOR
                            + "LOWERCASE_LETTER, MODIFIER_LETTER, OTHER_LETTER, TITLECASE_LETTER, UPPERCASE_LETTER" + LINE_SEPARATOR);

            // NUMBER
            writeFilteredFile(new File(outputDirectory,
                            "Character_Markdown_Table_NUMBER.md"),
                    TextFilter.containedIn(CodePointRecordHelper.INDEX_TYPE,
                            List.of(
                                    "DECIMAL_DIGIT_NUMBER",
                                    "LETTER_NUMBER",
                                    "OTHER_NUMBER"
                            )),
                    RecordComparators.textAt(CodePointRecordHelper.INDEX_DECIMAL_DIGIT, StringComparators.integerComparator(null, null, Comparator.nullsLast(Integer::compare)), SortNulls.LAST)
                                     .thenComparing(RecordComparators.textAt(CodePointRecordHelper.INDEX_NUMERIC_VALUE, StringComparators.integerComparator(null, null, Comparator.nullsLast(Integer::compare)), SortNulls.LAST))
                                     .thenComparing(RecordComparators.recordId(SortNulls.FIRST)),
                    "List of Unicode characters with the following types." + LINE_SEPARATOR
                            + "DECIMAL_DIGIT_NUMBER, LETTER_NUMBER, OTHER_NUMBER" + LINE_SEPARATOR);

            // SYMBOL_FORMAT
            writeFilteredFile(new File(outputDirectory,
                            "Character_Markdown_Table_SYMBOL_FORMAT.md"),
                    TextFilter.containedIn(CodePointRecordHelper.INDEX_TYPE,
                            List.of(
                                    "CURRENCY_SYMBOL",
                                    "MATH_SYMBOL",
                                    "MODIFIER_SYMBOL",
                                    "OTHER_SYMBOL",
                                    "FORMAT"
                            )),
                    RecordComparators.textAt(CodePointRecordHelper.INDEX_TYPE, String::compareTo, SortNulls.FIRST)
                                     .thenComparing(RecordComparators.textAt(CodePointRecordHelper.INDEX_CHARACTER_NAME, String::compareTo, SortNulls.FIRST)),
                    "List of Unicode characters with the following types." + LINE_SEPARATOR
                            + "CURRENCY_SYMBOL, MATH_SYMBOL, MODIFIER_SYMBOL, OTHER_SYMBOL, FORMAT" + LINE_SEPARATOR);

            // PUNCTUATION
            writeFilteredFile(new File(outputDirectory,
                            "Character_Markdown_Table_PUNCTUATION.md"),
                    TextFilter.containedIn(CodePointRecordHelper.INDEX_TYPE,
                            List.of(
                                    "CONNECTOR_PUNCTUATION",
                                    "DASH_PUNCTUATION",
                                    "END_PUNCTUATION",
                                    "FINAL_QUOTE_PUNCTUATION",
                                    "INITIAL_QUOTE_PUNCTUATION",
                                    "OTHER_PUNCTUATION",
                                    "START_PUNCTUATION"
                            )),
                    RecordComparators.textAt(CodePointRecordHelper.INDEX_DIRECTIONALITY, String::compareTo, SortNulls.FIRST)
                                     .thenComparing(RecordComparators.textAt(CodePointRecordHelper.INDEX_BLOCK, String::compareTo, SortNulls.FIRST))
                                     .thenComparing(RecordComparators.recordId(SortNulls.FIRST)),
                    "List of Unicode characters with the following types." + LINE_SEPARATOR
                            + "CONNECTOR_PUNCTUATION, DASH_PUNCTUATION, END_PUNCTUATION, FINAL_QUOTE_PUNCTUATION, INITIAL_QUOTE_PUNCTUATION, OTHER_PUNCTUATION, START_PUNCTUATION" + LINE_SEPARATOR);

            // CONTROL
            writeFilteredFile(new File(outputDirectory,
                            "Character_Markdown_Table_CONTROL.md"),
                    TextFilter.equalTo(CodePointRecordHelper.INDEX_TYPE,
                            "CONTROL"
                    ),
                    RecordComparators.recordId(SortNulls.FIRST),
                    "List of Unicode characters with the following types." + LINE_SEPARATOR
                            + "CONTROL" + LINE_SEPARATOR);

            // SEPARATOR_MARK
            writeFilteredFile(new File(outputDirectory,
                            "Character_Markdown_Table_SEPARATOR_MARK.md"),
                    TextFilter.containedIn(CodePointRecordHelper.INDEX_TYPE,
                            List.of(
                                    "LINE_SEPARATOR",
                                    "PARAGRAPH_SEPARATOR",
                                    "SPACE_SEPARATOR",
                                    "COMBINING_SPACING_MARK",
                                    "ENCLOSING_MARK",
                                    "NON_SPACING_MARK"
                            )),
                    RecordComparators.textAt(CodePointRecordHelper.INDEX_TYPE, String::compareTo, SortNulls.FIRST)
                                     .thenComparing(RecordComparators.recordId(SortNulls.FIRST)),
                    "List of Unicode characters with the following types." + LINE_SEPARATOR
                            + "LINE_SEPARATOR, PARAGRAPH_SEPARATOR, SPACE_SEPARATOR, COMBINING_SPACING_MARK, ENCLOSING_MARK, NON_SPACING_MARK" + LINE_SEPARATOR);

            // Block_LATIN
            writeFilteredFile(new File(outputDirectory,
                            "Character_Markdown_Table_Block_LATIN.md"),
                    new TextFilter<>(CodePointRecordHelper.INDEX_BLOCK,
                            StringPredicates.contains(
                                    "LATIN"
                            )),
                    RecordComparators.recordId(SortNulls.FIRST),
                    "List of Unicode characters whose block contains the word \"LATIN\"." + LINE_SEPARATOR);

            // IsMirrored
            writeFilteredFile(new File(outputDirectory,
                            "Character_Markdown_Table_IsMirrored.md"),
                    new TextFilter<>(CodePointRecordHelper.INDEX_IS_MIRRORED,
                            StringPredicates.equals(
                                    "true"
                            )),
                    RecordComparators.textAt(CodePointRecordHelper.INDEX_BLOCK, String::compareTo, SortNulls.FIRST)
                                     .thenComparing(RecordComparators.recordId(SortNulls.FIRST)),
                    "List of mirrored Unicode characters." + LINE_SEPARATOR);

            // Numeric_3
            writeFilteredFile(new File(outputDirectory,
                            "Character_Markdown_Table_Numeric_3_or_13.md"),
                    TextFilter.containedIn(CodePointRecordHelper.INDEX_NUMERIC_VALUE,
                            List.of(
                                    "3",
                                    "13"
                            )),
                    RecordComparators.textAt(CodePointRecordHelper.INDEX_NUMERIC_VALUE, StringComparators.integerComparator(null, null, Comparator.nullsLast(Integer::compare)), SortNulls.LAST)
                                     .thenComparing(RecordComparators.textAt(CodePointRecordHelper.INDEX_CHARACTER_COUNT, StringComparators.integerComparator(null, null, Comparator.nullsLast(Integer::compare)), SortNulls.FIRST))
                                     .thenComparing(RecordComparators.textAt(CodePointRecordHelper.INDEX_DECIMAL_DIGIT, StringComparators.integerComparator(null, null, Comparator.nullsLast(Integer::compare)), SortNulls.FIRST))
                                     .thenComparing(RecordComparators.textAt(CodePointRecordHelper.INDEX_DIRECTIONALITY, String::compareTo, SortNulls.FIRST))
                                     .thenComparing(RecordComparators.textAt(CodePointRecordHelper.INDEX_BLOCK, String::compareTo, SortNulls.FIRST))
                                     .thenComparing(RecordComparators.textAt(CodePointRecordHelper.INDEX_TYPE, String::compareTo, SortNulls.FIRST))
                                     .thenComparing(RecordComparators.textAt(CodePointRecordHelper.INDEX_CHARACTER_NAME, String::compareTo, SortNulls.FIRST)),
                    "List of Unicode characters with a numeric value of '3' or '13'." + LINE_SEPARATOR);
        } catch (IOException | UncheckedConsumerException e) {
            System.out.println("Cannot generate MarkdownTable file! " + e.getMessage());
        }
    }

}
