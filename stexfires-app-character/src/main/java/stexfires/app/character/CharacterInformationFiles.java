package stexfires.app.character;

import stexfires.io.RecordIOStreams;
import stexfires.io.html.table.HtmlTableFieldSpec;
import stexfires.io.html.table.HtmlTableFileSpec;
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
@SuppressWarnings("HardcodedLineSeparator")
public final class CharacterInformationFiles {

    public static final String ALTERNATIVE_VALUE = "";

    public static final LineSeparator LINE_SEPARATOR = LineSeparator.CR_LF;

    private CharacterInformationFiles() {
    }

    @SuppressWarnings({"UseOfSystemOutOrSystemErr", "OverlyBroadThrowsClause"})
    private static void writeMarkdownFile(File outputFile,
                                          String textBefore,
                                          List<MarkdownTableFieldSpec> fieldSpecs,
                                          RecordFilter<TextRecord> recordFilter,
                                          Comparator<TextRecord> recordComparator)
            throws IOException {
        Objects.requireNonNull(outputFile);
        Objects.requireNonNull(textBefore);
        Objects.requireNonNull(fieldSpecs);
        Objects.requireNonNull(recordFilter);
        Objects.requireNonNull(recordComparator);

        System.out.println("Generate MarkdownTable file: " + outputFile);

        var consumerFileSpec = MarkdownTableFileSpec.consumerFileSpec(
                CharsetCoding.UTF_8_REPORTING,
                LINE_SEPARATOR,
                textBefore + LINE_SEPARATOR,
                null,
                Alignment.CENTER,
                fieldSpecs
        );

        try (var consumer = consumerFileSpec.consumer(new FileOutputStream(outputFile))) {
            RecordIOStreams.writeStream(consumer,
                    CodePointRecordFields.generateCodePointRecordStream(
                                                 CodePoint.MIN_VALUE,
                                                 CodePoint.MAX_VALUE,
                                                 ALTERNATIVE_VALUE)
                                         .filter(recordFilter.asPredicate())
                                         .sorted(recordComparator));
        }
    }

    @SuppressWarnings({"UseOfSystemOutOrSystemErr", "OverlyBroadThrowsClause", "SpellCheckingInspection"})
    private static void writeHtmlFile(File outputFile,
                                      String textBefore,
                                      List<HtmlTableFieldSpec> fieldSpecs,
                                      RecordFilter<TextRecord> recordFilter,
                                      Comparator<TextRecord> recordComparator)
            throws IOException {
        Objects.requireNonNull(outputFile);
        Objects.requireNonNull(textBefore);
        Objects.requireNonNull(fieldSpecs);
        Objects.requireNonNull(recordFilter);
        Objects.requireNonNull(recordComparator);

        System.out.println("Generate HtmlTable file: " + outputFile);

        var consumerFileSpec = HtmlTableFileSpec.consumerFileSpec(
                CharsetCoding.UTF_8_REPORTING,
                LINE_SEPARATOR,
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "    <head>\n" +
                        "        <meta charset=\"UTF-8\" />\n" +
                        "        <title>" + textBefore + "</title>\n" +
                        "        <style>\n" +
                        "            body {\n" +
                        "                font-family: Arial, Helvetica, sans-serif;\n" +
                        "            }\n" +
                        "            th {\n" +
                        "                white-space: nowrap;\n" +
                        "                padding: 8px 8px;\n" +
                        "                background: #dddddd;\n" +
                        "            }\n" +
                        "            td {\n" +
                        "                white-space: nowrap;\n" +
                        "                padding: 4px 6pxx;\n" +
                        "                border: 1px solid #000000;\n" +
                        "            }\n" +
                        "        </style>\n" +
                        "    </head>\n" +
                        "    <body>\n" +
                        "        <h1>" + textBefore + "</h1>",
                "    </body>\n" +
                        "</html>",
                "    ",
                fieldSpecs
        );

        try (var consumer = consumerFileSpec.consumer(new FileOutputStream(outputFile))) {
            RecordIOStreams.writeStream(consumer,
                    CodePointRecordFields.generateCodePointRecordStream(
                                                 CodePoint.MIN_VALUE,
                                                 CodePoint.MAX_VALUE,
                                                 ALTERNATIVE_VALUE)
                                         .filter(recordFilter.asPredicate())
                                         .sorted(recordComparator));
        }
    }

    private static void writeFiles(File outputDirectory,
                                   String fileName,
                                   String textBefore,
                                   RecordFilter<TextRecord> recordFilter,
                                   Comparator<TextRecord> recordComparator) throws IOException {
        // Markdown small
        var fieldSpecsMarkdownSmall = Arrays.stream(CodePointRecordFields.values())
                                            .limit(8) // only the first 8 fields
                                            .map(field -> new MarkdownTableFieldSpec(
                                                    field.fieldName(),
                                                    field.minWidth(),
                                                    field.alignment()))
                                            .toList();
        writeMarkdownFile(new File(outputDirectory, fileName + "_small.md"), textBefore, fieldSpecsMarkdownSmall, recordFilter, recordComparator);

        // Markdown
        var fieldSpecsMarkdown = Arrays.stream(CodePointRecordFields.values())
                                       .map(field -> new MarkdownTableFieldSpec(
                                               field.fieldName(),
                                               field.minWidth(),
                                               field.alignment()))
                                       .toList();
        writeMarkdownFile(new File(outputDirectory, fileName + ".md"), textBefore, fieldSpecsMarkdown, recordFilter, recordComparator);

        // Html
        var fieldSpecsHtml = Arrays.stream(CodePointRecordFields.values())
                                   .map(field -> new HtmlTableFieldSpec(
                                           field.fieldName()))
                                   .toList();
        writeHtmlFile(new File(outputDirectory, fileName + ".html"), textBefore, fieldSpecsHtml, recordFilter, recordComparator);
    }

    @SuppressWarnings({"UseOfSystemOutOrSystemErr", "SpellCheckingInspection"})
    public static void main(String... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Missing valid output directory parameter!");
        }
        File outputDirectory = new File(args[0]);
        if (!outputDirectory.exists() || !outputDirectory.isDirectory()) {
            throw new IllegalArgumentException("Missing valid output directory parameter! " + outputDirectory);
        }

        var fieldSpecs = Arrays.stream(CodePointRecordFields.values())
                               .limit(8)
                               .map(field -> new MarkdownTableFieldSpec(
                                       field.fieldName(),
                                       field.minWidth(),
                                       field.alignment()))
                               .toList();

        try {
            // LETTER_LEFT_TO_RIGHT
            writeFiles(outputDirectory,
                    "Character_Markdown_Table_LETTER_LEFT_TO_RIGHT",
                    "List of Unicode characters with the following types and directionality 'left to right': LOWERCASE_LETTER, MODIFIER_LETTER, OTHER_LETTER, TITLECASE_LETTER, UPPERCASE_LETTER",
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
            writeFiles(outputDirectory,
                    "Character_Markdown_Table_LETTER_NOT_LEFT_TO_RIGHT",
                    "List of Unicode characters with the following types and directionality NOT 'left to right': LOWERCASE_LETTER, MODIFIER_LETTER, OTHER_LETTER, TITLECASE_LETTER, UPPERCASE_LETTER",
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
            writeFiles(outputDirectory,
                    "Character_Markdown_Table_NUMBER",
                    "List of Unicode characters with the following types: DECIMAL_DIGIT_NUMBER, LETTER_NUMBER, OTHER_NUMBER",
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
            writeFiles(outputDirectory,
                    "Character_Markdown_Table_SYMBOL_FORMAT",
                    "List of Unicode characters with the following types: CURRENCY_SYMBOL, MATH_SYMBOL, MODIFIER_SYMBOL, OTHER_SYMBOL, FORMAT",
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
            writeFiles(outputDirectory,
                    "Character_Markdown_Table_PUNCTUATION",
                    "List of Unicode characters with the following types: CONNECTOR_PUNCTUATION, DASH_PUNCTUATION, END_PUNCTUATION, FINAL_QUOTE_PUNCTUATION, INITIAL_QUOTE_PUNCTUATION, OTHER_PUNCTUATION, START_PUNCTUATION",
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
            writeFiles(outputDirectory,
                    "Character_Markdown_Table_CONTROL",
                    "List of Unicode characters with the following types: CONTROL",
                    TextFilter.equalTo(CodePointRecordFields.TYPE.ordinal(),
                            "CONTROL"
                    ),
                    RecordComparators.recordId(SortNulls.FIRST)
            );

            // SEPARATOR_MARK
            writeFiles(outputDirectory,
                    "Character_Markdown_Table_SEPARATOR_MARK",
                    "List of Unicode characters with the following types: LINE_SEPARATOR, PARAGRAPH_SEPARATOR, SPACE_SEPARATOR, COMBINING_SPACING_MARK, ENCLOSING_MARK, NON_SPACING_MARK",
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
            writeFiles(outputDirectory,
                    "Character_Markdown_Table_Block_LATIN",
                    "List of Unicode characters whose block contains the word \"LATIN\"",
                    new TextFilter<>(CodePointRecordFields.UNICODE_BLOCK.ordinal(),
                            StringPredicates.contains(
                                    "LATIN"
                            )),
                    RecordComparators.recordId(SortNulls.FIRST)
            );

            // IsMirrored
            writeFiles(outputDirectory,
                    "Character_Markdown_Table_IsMirrored",
                    "List of mirrored Unicode characters",
                    new TextFilter<>(CodePointRecordFields.IS_MIRRORED.ordinal(),
                            StringPredicates.equals(
                                    "true"
                            )),
                    RecordComparators.textAt(CodePointRecordFields.UNICODE_BLOCK.ordinal(), String::compareTo, SortNulls.FIRST)
                                     .thenComparing(RecordComparators.recordId(SortNulls.FIRST))
            );

            // IsIdeographic
            writeFiles(outputDirectory,
                    "Character_Markdown_Table_IsIdeographic",
                    "List of ideographic Unicode characters",
                    new TextFilter<>(CodePointRecordFields.IS_IDEOGRAPHIC.ordinal(),
                            StringPredicates.equals(
                                    "true"
                            )),
                    RecordComparators.textAt(CodePointRecordFields.UNICODE_BLOCK.ordinal(), String::compareTo, SortNulls.FIRST)
                                     .thenComparing(RecordComparators.recordId(SortNulls.FIRST))
            );

            // Emoji
            writeFiles(outputDirectory,
                    "Character_Markdown_Table_isEmoji",
                    "List of emoji Unicode characters",
                    new TextFilter<>(CodePointRecordFields.IS_EMOJI.ordinal(), StringPredicates.equals("true")),
                    RecordComparators.recordId(SortNulls.FIRST)
            );

            // Numeric_3
            writeFiles(outputDirectory,
                    "Character_Markdown_Table_Numeric_3_or_13_or_30_or_300",
                    "List of Unicode characters with a numeric value of '3', '13', '30' or '300'",
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
