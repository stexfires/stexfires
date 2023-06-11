package stexfires.examples.record;

import stexfires.record.TextRecord;
import stexfires.record.TextRecordStreams;
import stexfires.record.TextRecords;
import stexfires.record.consumer.SystemOutConsumer;
import stexfires.record.impl.KeyValueCommentFieldsRecord;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.TwoFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.mapper.RecordMapper;
import stexfires.record.mapper.impl.ToEmptyRecordMapper;
import stexfires.record.mapper.impl.ToKeyValueCommentFieldsRecordMapper;
import stexfires.record.mapper.impl.ToKeyValueFieldsRecordMapper;
import stexfires.record.mapper.impl.ToManyFieldsRecordMapper;
import stexfires.record.mapper.impl.ToTwoFieldsRecordMapper;
import stexfires.record.mapper.impl.ToValueFieldRecordMapper;
import stexfires.record.message.JoinedTextsMessage;
import stexfires.record.message.ShortMessage;
import stexfires.record.message.TextMessage;
import stexfires.util.Strings;

import java.util.stream.Stream;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public final class ExamplesToMapper {

    private ExamplesToMapper() {
    }

    private static Stream<TextRecord> generateStream() {
        return Stream.of(
                TextRecords.empty(),
                new KeyValueFieldsRecord("key", "value"),
                new KeyValueFieldsRecord("category", 0L, "key", "value"),
                new KeyValueCommentFieldsRecord("category", 0L, "key", "value", "comment"),
                new TwoFieldsRecord("first", "second"),
                new TwoFieldsRecord("category", 0L, "first", "second"),
                new ValueFieldRecord("value"),
                new ValueFieldRecord("category", 0L, "value"),
                new ManyFieldsRecord(),
                new ManyFieldsRecord(Strings.list("value1", "value2", "value3")),
                new ManyFieldsRecord("category", 0L, Strings.list("value1", "value2", "value3")),
                new ManyFieldsRecord("category", 0L, Strings.list("value1", "value2", "value3", "value4"))
        );
    }

    private static void printRecordMapper(String title, RecordMapper<TextRecord, ? extends TextRecord> recordMapper) {
        System.out.println("--" + title);
        TextRecordStreams.mapAndConsume(generateStream(), recordMapper, new SystemOutConsumer<>(new ShortMessage<>().append(" [", new JoinedTextsMessage<>(", ")).append("]")));
    }

    private static void showToEmptyRecordMapper() {
        System.out.println("-showToEmptyRecordMapper---");

        printRecordMapper("constructor", new ToEmptyRecordMapper<>());
    }

    private static void showToKeyValueCommentFieldsRecordMapper() {
        System.out.println("-showToKeyValueCommentFieldsRecordMapper---");

        printRecordMapper("constructor (0, 1, 3)", new ToKeyValueCommentFieldsRecordMapper<>(
                new TextMessage<>(0, "missing key"),
                new TextMessage<>(1),
                new TextMessage<>(3)));
    }

    private static void showToKeyValueFieldsRecordMapper() {
        System.out.println("-showToKeyValueFieldsRecordMapper---");

        printRecordMapper("constructor (0, 1)", new ToKeyValueFieldsRecordMapper<>(
                new TextMessage<>(0, "missing key"),
                new TextMessage<>(1)));
    }

    private static void showToManyFieldsRecordMapper() {
        System.out.println("-showToManyFieldsRecordMapper---");

        printRecordMapper("constructor", new ToManyFieldsRecordMapper<>());
    }

    private static void showToTwoFieldsRecordMapper() {
        System.out.println("-showToTwoFieldsRecordMapper---");

        printRecordMapper("constructor (0, 1", new ToTwoFieldsRecordMapper<>(new TextMessage<>(0), new TextMessage<>(1)));
        printRecordMapper("constructor (2, 0)", new ToTwoFieldsRecordMapper<>(new TextMessage<>(2), new TextMessage<>(0)));
    }

    private static void showToValueFieldRecordMapper() {
        System.out.println("-showToValueFieldRecordMapper---");

        printRecordMapper("constructor (0)", new ToValueFieldRecordMapper<>(new TextMessage<>(0)));
        printRecordMapper("constructor (2)", new ToValueFieldRecordMapper<>(new TextMessage<>(2)));
    }

    public static void main(String... args) {
        showToEmptyRecordMapper();
        showToKeyValueCommentFieldsRecordMapper();
        showToKeyValueFieldsRecordMapper();
        showToManyFieldsRecordMapper();
        showToTwoFieldsRecordMapper();
        showToValueFieldRecordMapper();
    }

}
