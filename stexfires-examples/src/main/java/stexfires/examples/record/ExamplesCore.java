package stexfires.examples.record;

import stexfires.record.TextField;
import stexfires.record.TextRecord;
import stexfires.record.TextRecords;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesCore {

    private ExamplesCore() {
    }

    private static Stream<TextField> generateFieldStream() {
        return Stream.of(
                new TextField(0, 0, "value A"),
                new TextField(0, 2, "value B"),
                new TextField(0, 0, null),
                new TextField(1, 1, "value C"),
                new TextField(1, 1, ""),
                new TextField(1, 2, "value D")
        );
    }

    private static void printFieldStream(String title, Function<Stream<TextField>, Stream<Object>> fieldStreamFunction) {
        System.out.println("--" + title);
        try {
            System.out.println(fieldStreamFunction.apply(generateFieldStream()).toList());
        } catch (NullPointerException e) {
            System.out.println("Caught NullPointerException: " + e.getMessage());
            System.out.println("Filtered result: " + fieldStreamFunction.apply(generateFieldStream().filter(TextField::isNotNull)).toList());
        }
    }

    private static void printRecord(String title, TextRecord record) {
        System.out.println("--" + title);
        System.out.println(record);
    }

    private static void showField() {
        System.out.println("-showField---");

        printFieldStream("index", stream -> stream.map(TextField::index));
        printFieldStream("maxIndex", stream -> stream.map(TextField::maxIndex));
        printFieldStream("isFirstField", stream -> stream.map(TextField::isFirstField));
        printFieldStream("isLastField", stream -> stream.map(TextField::isLastField));
        printFieldStream("recordSize", stream -> stream.map(TextField::recordSize));

        printFieldStream("text", stream -> stream.map(TextField::text));
        printFieldStream("orElse", stream -> stream.map(field -> field.orElse("<NULL>")));
        printFieldStream("orElseThrow", stream -> stream.map(TextField::orElseThrow));
        printFieldStream("asOptional", stream -> stream.map(TextField::asOptional).map(optional -> optional.orElse("<NULL>")));
        printFieldStream("isNotNull", stream -> stream.map(TextField::isNotNull));
        printFieldStream("isNull", stream -> stream.map(TextField::isNull));
        printFieldStream("isEmpty", stream -> stream.map(TextField::isEmpty));
        printFieldStream("isNullOrEmpty", stream -> stream.map(TextField::isNullOrEmpty));
        printFieldStream("length", stream -> stream.map(TextField::length));
        printFieldStream("stream", stream -> stream.flatMap(TextField::stream));

        printFieldStream("toString", stream -> stream.map(TextField::toString));
        printFieldStream("hashCode", stream -> stream.map(TextField::hashCode));

    }

    private static void showFields() {
        System.out.println("-showFields---");
    }

    private static void showRecord() {
        System.out.println("-showRecord---");
    }

    private static void showRecords() {
        System.out.println("-showRecords---");
    }

    private static void showRecordsBuilder() {
        System.out.println("-showRecordsBuilder---");

        printRecord(".", TextRecords.builder().build());
        printRecord(".category", TextRecords.builder().category("category").build());
        printRecord(".recordId", TextRecords.builder().recordId(1_000L).build());
        printRecord(".category.recordId", TextRecords.builder().category("category").recordId(0L).build());
        printRecord(".add null", TextRecords.builder().add(null).build());
        printRecord(".add empty", TextRecords.builder().add("").build());
        printRecord(".add value", TextRecords.builder().add("value").build());
        printRecord(".add values", TextRecords.builder().add("firstValue").add("lastValue").build());
        printRecord(".add values", TextRecords.builder().add("firstValue")
                                              .addAll(List.of("secondValue", "thirdValue")).add("lastValue").build());
    }

    private static void showRecordStreams() {
        System.out.println("-RecordStreams---");
    }

    public static void main(String... args) {
        showField();
        showFields();
        showRecord();
        showRecords();
        showRecordsBuilder();
        showRecordStreams();
    }

}
