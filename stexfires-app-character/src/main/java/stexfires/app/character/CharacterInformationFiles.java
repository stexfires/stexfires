package stexfires.app.character;

import stexfires.io.RecordIOStreams;
import stexfires.io.markdown.table.MarkdownTableFieldSpec;
import stexfires.io.markdown.table.MarkdownTableFileSpec;
import stexfires.record.TextRecord;
import stexfires.record.comparator.RecordComparators;
import stexfires.record.consumer.UncheckedConsumerException;
import stexfires.record.filter.RecordFilter;
import stexfires.record.filter.TextFilter;
import stexfires.util.Alignment;
import stexfires.util.CharsetCoding;
import stexfires.util.CodePoint;
import stexfires.util.LineSeparator;
import stexfires.util.SortNulls;
import stexfires.util.StringComparators;
import stexfires.util.function.StringPredicates;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @since 0.1
 */
public final class CharacterInformationFiles {

    public static final String ALTERNATIVE_VALUE = "";

    public static final LineSeparator LINE_SEPARATOR = LineSeparator.CR_LF;

    private CharacterInformationFiles() {
    }

    @SuppressWarnings({"UseOfSystemOutOrSystemErr", "OverlyBroadThrowsClause"})
    private static void writeFile(File outputMarkdownFile,
                                  String textBefore,
                                  List<MarkdownTableFieldSpec> fieldSpecs,
                                  RecordFilter<TextRecord> recordFilter,
                                  Comparator<TextRecord> recordComparator)
            throws IOException {
        Objects.requireNonNull(outputMarkdownFile);
        Objects.requireNonNull(textBefore);
        Objects.requireNonNull(fieldSpecs);
        Objects.requireNonNull(recordFilter);
        Objects.requireNonNull(recordComparator);

        System.out.println("Generate MarkdownTable file: " + outputMarkdownFile);

        var consumerFileSpec = MarkdownTableFileSpec.consumerFileSpec(
                CharsetCoding.UTF_8_REPORTING,
                LINE_SEPARATOR,
                textBefore,
                null,
                Alignment.CENTER,
                fieldSpecs
        );

        try (var consumer = consumerFileSpec.consumer(new FileOutputStream(outputMarkdownFile))) {
            RecordIOStreams.writeStream(consumer,
                    CodePointRecordFields.generateCodePointRecordStream(
                                                 CodePoint.MIN_VALUE,
                                                 CodePoint.MAX_VALUE,
                                                 ALTERNATIVE_VALUE)
                                         .filter(recordFilter.asPredicate())
                                         .sorted(recordComparator));
        }
    }

    @SuppressWarnings({"UseOfSystemOutOrSystemErr", "StringConcatenationMissingWhitespace", "SpellCheckingInspection"})
    public static void main(String... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Missing valid output directory parameter!");
        }
        File outputDirectory = new File(args[0]);
        if (!outputDirectory.exists() || !outputDirectory.isDirectory()) {
            throw new IllegalArgumentException("Missing valid output directory parameter! " + outputDirectory);
        }

        var fieldSpecs = Arrays.stream(CodePointRecordFields.values())
                               .map(field -> new MarkdownTableFieldSpec(
                                       field.fieldName(),
                                       field.minWidth(),
                                       field.alignment()))
                               .toList();

        try {
            // LETTER_LEFT_TO_RIGHT
            writeFile(new File(outputDirectory,
                            "Character_Markdown_Table_LETTER_LEFT_TO_RIGHT.md"),
                    "List of Unicode characters with the following types and directionality 'left to right'." + LINE_SEPARATOR
                            + "LOWERCASE_LETTER, MODIFIER_LETTER, OTHER_LETTER, TITLECASE_LETTER, UPPERCASE_LETTER" + LINE_SEPARATOR,
                    fieldSpecs,
                    TextFilter.containedIn(CodePointRecordFields.TYPE.ordinal(),
                                      List.of(
                                              "LOWERCASE_LETTER",
                                              "MODIFIER_LETTER",
                                              "OTHER_LETTER",
                                              "TITLECASE_LETTER",
                                              "UPPERCASE_LETTER"
                                      ))
                              .and(TextFilter.equalTo(CodePointRecordFields.DIRECTIONALITY.ordinal(),
                                      "DIRECTIONALITY_LEFT_TO_RIGHT"
                              )),
                    RecordComparators.textAt(CodePointRecordFields.PRINTABLE_STRING.ordinal(), StringComparators.compareToIgnoreCase(), SortNulls.FIRST)
                                     .thenComparing(RecordComparators.textAt(CodePointRecordFields.TYPE.ordinal(), String::compareTo, SortNulls.FIRST))
                                     .thenComparing(RecordComparators.recordId(SortNulls.FIRST))
            );

            // LETTER_NOT_LEFT_TO_RIGHT
            writeFile(new File(outputDirectory,
                            "Character_Markdown_Table_LETTER_NOT_LEFT_TO_RIGHT.md"),
                    "List of Unicode characters with the following types and directionality NOT 'left to right'." + LINE_SEPARATOR
                            + "LOWERCASE_LETTER, MODIFIER_LETTER, OTHER_LETTER, TITLECASE_LETTER, UPPERCASE_LETTER" + LINE_SEPARATOR,
                    fieldSpecs,
                    TextFilter.containedIn(CodePointRecordFields.TYPE.ordinal(),
                                      List.of(
                                              "LOWERCASE_LETTER",
                                              "MODIFIER_LETTER",
                                              "OTHER_LETTER",
                                              "TITLECASE_LETTER",
                                              "UPPERCASE_LETTER"
                                      ))
                              .and(TextFilter.equalTo(CodePointRecordFields.DIRECTIONALITY.ordinal(),
                                      "DIRECTIONALITY_LEFT_TO_RIGHT"
                              ).negate()),
                    RecordComparators.textAt(CodePointRecordFields.PRINTABLE_STRING.ordinal(), StringComparators.compareToIgnoreCase(), SortNulls.FIRST)
                                     .thenComparing(RecordComparators.textAt(CodePointRecordFields.TYPE.ordinal(), String::compareTo, SortNulls.FIRST))
                                     .thenComparing(RecordComparators.recordId(SortNulls.FIRST))
            );

            // NUMBER
            writeFile(new File(outputDirectory,
                            "Character_Markdown_Table_NUMBER.md"),
                    "List of Unicode characters with the following types." + LINE_SEPARATOR
                            + "DECIMAL_DIGIT_NUMBER, LETTER_NUMBER, OTHER_NUMBER" + LINE_SEPARATOR,
                    fieldSpecs,
                    TextFilter.containedIn(CodePointRecordFields.TYPE.ordinal(),
                            List.of(
                                    "DECIMAL_DIGIT_NUMBER",
                                    "LETTER_NUMBER",
                                    "OTHER_NUMBER"
                            )),
                    RecordComparators.textAt(CodePointRecordFields.DECIMAL_DIGIT.ordinal(), StringComparators.integerComparator(null, null, Comparator.nullsLast(Integer::compare)), SortNulls.LAST)
                                     .thenComparing(RecordComparators.textAt(CodePointRecordFields.NUMERIC_VALUE.ordinal(), StringComparators.integerComparator(null, null, Comparator.nullsLast(Integer::compare)), SortNulls.LAST))
                                     .thenComparing(RecordComparators.recordId(SortNulls.FIRST))
            );

            // SYMBOL_FORMAT
            writeFile(new File(outputDirectory,
                            "Character_Markdown_Table_SYMBOL_FORMAT.md"),
                    "List of Unicode characters with the following types." + LINE_SEPARATOR
                            + "CURRENCY_SYMBOL, MATH_SYMBOL, MODIFIER_SYMBOL, OTHER_SYMBOL, FORMAT" + LINE_SEPARATOR,
                    fieldSpecs,
                    TextFilter.containedIn(CodePointRecordFields.TYPE.ordinal(),
                            List.of(
                                    "CURRENCY_SYMBOL",
                                    "MATH_SYMBOL",
                                    "MODIFIER_SYMBOL",
                                    "OTHER_SYMBOL",
                                    "FORMAT"
                            )),
                    RecordComparators.textAt(CodePointRecordFields.TYPE.ordinal(), String::compareTo, SortNulls.FIRST)
                                     .thenComparing(RecordComparators.textAt(CodePointRecordFields.NAME.ordinal(), String::compareTo, SortNulls.FIRST))
            );

            // PUNCTUATION
            writeFile(new File(outputDirectory,
                            "Character_Markdown_Table_PUNCTUATION.md"),
                    "List of Unicode characters with the following types." + LINE_SEPARATOR
                            + "CONNECTOR_PUNCTUATION, DASH_PUNCTUATION, END_PUNCTUATION, FINAL_QUOTE_PUNCTUATION, INITIAL_QUOTE_PUNCTUATION, OTHER_PUNCTUATION, START_PUNCTUATION" + LINE_SEPARATOR,
                    fieldSpecs,
                    TextFilter.containedIn(CodePointRecordFields.TYPE.ordinal(),
                            List.of(
                                    "CONNECTOR_PUNCTUATION",
                                    "DASH_PUNCTUATION",
                                    "END_PUNCTUATION",
                                    "FINAL_QUOTE_PUNCTUATION",
                                    "INITIAL_QUOTE_PUNCTUATION",
                                    "OTHER_PUNCTUATION",
                                    "START_PUNCTUATION"
                            )),
                    RecordComparators.textAt(CodePointRecordFields.DIRECTIONALITY.ordinal(), String::compareTo, SortNulls.FIRST)
                                     .thenComparing(RecordComparators.textAt(CodePointRecordFields.UNICODE_BLOCK.ordinal(), String::compareTo, SortNulls.FIRST))
                                     .thenComparing(RecordComparators.recordId(SortNulls.FIRST))
            );

            // CONTROL
            writeFile(new File(outputDirectory,
                            "Character_Markdown_Table_CONTROL.md"),
                    "List of Unicode characters with the following types." + LINE_SEPARATOR
                            + "CONTROL" + LINE_SEPARATOR,
                    fieldSpecs,
                    TextFilter.equalTo(CodePointRecordFields.TYPE.ordinal(),
                            "CONTROL"
                    ),
                    RecordComparators.recordId(SortNulls.FIRST)
            );

            // SEPARATOR_MARK
            writeFile(new File(outputDirectory,
                            "Character_Markdown_Table_SEPARATOR_MARK.md"),
                    "List of Unicode characters with the following types." + LINE_SEPARATOR
                            + "LINE_SEPARATOR, PARAGRAPH_SEPARATOR, SPACE_SEPARATOR, COMBINING_SPACING_MARK, ENCLOSING_MARK, NON_SPACING_MARK" + LINE_SEPARATOR,
                    fieldSpecs,
                    TextFilter.containedIn(CodePointRecordFields.TYPE.ordinal(),
                            List.of(
                                    "LINE_SEPARATOR",
                                    "PARAGRAPH_SEPARATOR",
                                    "SPACE_SEPARATOR",
                                    "COMBINING_SPACING_MARK",
                                    "ENCLOSING_MARK",
                                    "NON_SPACING_MARK"
                            )),
                    RecordComparators.textAt(CodePointRecordFields.TYPE.ordinal(), String::compareTo, SortNulls.FIRST)
                                     .thenComparing(RecordComparators.recordId(SortNulls.FIRST))
            );

            // Block_LATIN
            writeFile(new File(outputDirectory,
                            "Character_Markdown_Table_Block_LATIN.md"),
                    "List of Unicode characters whose block contains the word \"LATIN\"." + LINE_SEPARATOR,
                    fieldSpecs,
                    new TextFilter<>(CodePointRecordFields.UNICODE_BLOCK.ordinal(),
                            StringPredicates.contains(
                                    "LATIN"
                            )),
                    RecordComparators.recordId(SortNulls.FIRST)
            );

            // IsMirrored
            writeFile(new File(outputDirectory,
                            "Character_Markdown_Table_IsMirrored.md"),
                    "List of mirrored Unicode characters." + LINE_SEPARATOR,
                    fieldSpecs,
                    new TextFilter<>(CodePointRecordFields.IS_MIRRORED.ordinal(),
                            StringPredicates.equals(
                                    "true"
                            )),
                    RecordComparators.textAt(CodePointRecordFields.UNICODE_BLOCK.ordinal(), String::compareTo, SortNulls.FIRST)
                                     .thenComparing(RecordComparators.recordId(SortNulls.FIRST))
            );

            // IsIdeographic
            writeFile(new File(outputDirectory,
                            "Character_Markdown_Table_IsIdeographic.md"),
                    "List of ideographic Unicode characters." + LINE_SEPARATOR,
                    fieldSpecs,
                    new TextFilter<>(CodePointRecordFields.IS_IDEOGRAPHIC.ordinal(),
                            StringPredicates.equals(
                                    "true"
                            )),
                    RecordComparators.textAt(CodePointRecordFields.UNICODE_BLOCK.ordinal(), String::compareTo, SortNulls.FIRST)
                                     .thenComparing(RecordComparators.recordId(SortNulls.FIRST))
            );

            // Numeric_3
            writeFile(new File(outputDirectory,
                            "Character_Markdown_Table_Numeric_3_or_13_or_30_or_300.md"),
                    "List of Unicode characters with a numeric value of '3', '13', '30' or '300'." + LINE_SEPARATOR,
                    fieldSpecs,
                    TextFilter.containedIn(CodePointRecordFields.NUMERIC_VALUE.ordinal(),
                            List.of(
                                    "3",
                                    "13",
                                    "30",
                                    "300"
                            )),
                    RecordComparators.textAt(CodePointRecordFields.NUMERIC_VALUE.ordinal(), StringComparators.integerComparator(null, null, Comparator.nullsLast(Integer::compare)), SortNulls.LAST)
                                     .thenComparing(RecordComparators.textAt(CodePointRecordFields.CHAR_COUNT.ordinal(), StringComparators.integerComparator(null, null, Comparator.nullsLast(Integer::compare)), SortNulls.FIRST))
                                     .thenComparing(RecordComparators.textAt(CodePointRecordFields.DECIMAL_DIGIT.ordinal(), StringComparators.integerComparator(null, null, Comparator.nullsLast(Integer::compare)), SortNulls.FIRST))
                                     .thenComparing(RecordComparators.textAt(CodePointRecordFields.DIRECTIONALITY.ordinal(), String::compareTo, SortNulls.FIRST))
                                     .thenComparing(RecordComparators.textAt(CodePointRecordFields.UNICODE_BLOCK.ordinal(), String::compareTo, SortNulls.FIRST))
                                     .thenComparing(RecordComparators.textAt(CodePointRecordFields.TYPE.ordinal(), String::compareTo, SortNulls.FIRST))
                                     .thenComparing(RecordComparators.textAt(CodePointRecordFields.NAME.ordinal(), String::compareTo, SortNulls.FIRST))
            );
        } catch (IOException | UncheckedConsumerException e) {
            System.out.println("Cannot generate MarkdownTable file! " + e.getMessage());
        }
    }

}
