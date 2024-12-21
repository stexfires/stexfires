package stexfires.examples.record;

import stexfires.record.*;
import stexfires.record.filter.ClassFilter;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.TwoFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.mapper.field.AddPrefixFieldTextMapper;
import stexfires.record.message.*;
import stexfires.util.Strings;
import stexfires.util.supplier.SequenceSupplier;

import java.util.Locale;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesMessage {

    private ExamplesMessage() {
    }

    private static Stream<TextRecord> generateStream() {
        return Stream.of(
                new ValueFieldRecord("category", 0L, "A"),
                new ValueFieldRecord("category", 1L, "B"),
                new ValueFieldRecord("", 2L, "C"),
                new ValueFieldRecord("Category", 3L, "D"),
                new ValueFieldRecord(null, 4L, "E"),
                new ValueFieldRecord("c", 5L, "F"),
                new TwoFieldsRecord("c", 6L, "X", "Y"),
                new KeyValueFieldsRecord("key", "value"),
                new ManyFieldsRecord("Category", 7L, "S", "t", "a", "n", "d", "a", "r", "d"),
                new ManyFieldsRecord("S", "t", "a", "n", "d", "a", "r", "d"),
                new ManyFieldsRecord(),
                new ManyFieldsRecord("Category", 8L, null, null, null, null),
                TextRecords.empty()
        );
    }

    private static Stream<ValueRecord> generateStreamOneValueRecord() {
        return Stream.of(
                new ValueFieldRecord("value1"),
                new ValueFieldRecord(null, 2L, "value2"),
                new ValueFieldRecord("category", 3L, "value3")
        );
    }

    private static Stream<KeyValueRecord> generateStreamKeyValueRecord() {
        return Stream.of(
                new KeyValueFieldsRecord("key1", "value1"),
                new KeyValueFieldsRecord(null, 2L, "key2", "value2"),
                new KeyValueFieldsRecord("category", 3L, "key3", "value3")
        );
    }

    private static void printMessage(String title, RecordMessage<TextRecord> recordMessage) {
        System.out.println("--" + title);
        Strings.printLine(TextRecordStreams.mapToMessage(generateStream(), recordMessage), Strings.DEFAULT_DELIMITER);
    }

    private static void printMessageOneValueRecord(String title, RecordMessage<? super ValueRecord> recordMessage) {
        System.out.println("--" + title);
        Strings.printLine(TextRecordStreams.mapToMessage(generateStreamOneValueRecord(), recordMessage), Strings.DEFAULT_DELIMITER);
    }

    private static void printMessageKeyValueRecord(String title, RecordMessage<? super KeyValueRecord> recordMessage) {
        System.out.println("--" + title);
        Strings.printLine(TextRecordStreams.mapToMessage(generateStreamKeyValueRecord(), recordMessage), Strings.DEFAULT_DELIMITER);
    }

    private static void showCategoryMessage() {
        System.out.println("-showCategoryMessage---");

        printMessage("constructor",
                new CategoryMessage<>());
        printMessage("constructor nullCategoryValue",
                new CategoryMessage<>("<NULL>"));
        printMessageOneValueRecord("constructor ValueFieldRecord",
                new CategoryMessage<>());
        printMessageKeyValueRecord("constructor KeyValueFieldsRecord",
                new CategoryMessage<>());
    }

    private static void showClassNameMessage() {
        System.out.println("-showClassNameMessage---");

        printMessage("constructor",
                new ClassNameMessage<>());
        printMessage("constructor hashCode",
                new ClassNameMessage<>(true));
        printMessageOneValueRecord("constructor ValueFieldRecord",
                new ClassNameMessage<>());
        printMessageKeyValueRecord("constructor KeyValueFieldsRecord",
                new ClassNameMessage<>());
    }

    private static void showCompareMessageBuilder() {
        System.out.println("-showCompareMessageBuilder---");

        printMessage("category / size",
                new CompareMessageBuilder().category().size().build());
        printMessageOneValueRecord("className / category(other)",
                new CompareMessageBuilder().className().category("<NULL>>").build());
        printMessage("values",
                new CompareMessageBuilder().texts().build());
    }

    private static void showConditionalMessage() {
        System.out.println("-showConditionalMessage---");

        printMessage("constructor",
                new ConditionalMessage<>(ClassFilter.equalTo(ValueFieldRecord.class),
                        new ConstantMessage<>("single"), new ShortMessage<>()));
        printMessageOneValueRecord("constructor ValueFieldRecord",
                new ConditionalMessage<>(ClassFilter.equalTo(ValueFieldRecord.class),
                        new ConstantMessage<>("single"), new ShortMessage<>()));
        printMessageKeyValueRecord("constructor KeyValueFieldsRecord",
                new ConditionalMessage<>(ClassFilter.equalTo(ValueFieldRecord.class),
                        new ConstantMessage<>("single"), new ShortMessage<>()));
    }

    private static void showConstantMessage() {
        System.out.println("-showConstantMessage---");

        printMessage("constructor",
                new ConstantMessage<>("message"));
        printMessageKeyValueRecord("constructor KeyValueFieldsRecord",
                new ConstantMessage<>("KeyValueFieldsRecord"));
    }

    private static void showExtendedValuesMessage() {
        System.out.println("-showExtendedValuesMessage---");

        printMessage("constructor",
                new ExtendedTextsMessage<>("(", ")"));
        printMessage("constructor first/last",
                new ExtendedTextsMessage<>("", "", "<", ">"));
        printMessageKeyValueRecord("constructor KeyValueFieldsRecord",
                new ExtendedTextsMessage<>("(", ")"));
    }

    private static void showFormatterMessage() {
        System.out.println("-showFormatterMessage---");

        printMessage("constructor",
                new FormatterMessage<>("%nClassName: %1$-40s Category: %2$-10S RecordId: %3$5d Size: %4$2d Values: (%5$-3s | %6$S)",
                        Locale.getDefault(), 2, "<>"));
        printMessage("constructor withoutValues",
                FormatterMessage.withoutTexts("%4$d",
                        Locale.getDefault()));
        printMessageKeyValueRecord("constructor KeyValueFieldsRecord",
                new FormatterMessage<>("%5$s=%6$s",
                        Locale.getDefault(), 2, ""));
    }

    private static void showJoinedValuesMessage() {
        System.out.println("-showJoinedValuesMessage---");

        printMessage("constructor",
                new JoinedTextsMessage<>());
        printMessage("constructor delimiter",
                new JoinedTextsMessage<>(""));
        printMessageKeyValueRecord("constructor delimiter showMessageKeyValueRecord",
                new JoinedTextsMessage<>("="));
    }

    private static void showRecordIdMessage() {
        System.out.println("-showRecordIdMessage---");

        printMessage("constructor",
                new RecordIdMessage<>());
        printMessage("constructor prefix missingRecordIdMessage",
                new RecordIdMessage<>("", "-"));
        printMessage("constructor prefix missingRecordIdMessage",
                new RecordIdMessage<>(null, null));
        printMessageKeyValueRecord("constructor prefix missingRecordIdMessage KeyValueFieldsRecord",
                new RecordIdMessage<>(RecordIdMessage.DEFAULT_PREFIX, "-"));
    }

    private static void showRecordMessage() {
        System.out.println("-showRecordMessage---");

        printMessage("category",
                RecordMessage.category());
        printMessage("firstText",
                RecordMessage.firstText());
        printMessage("lastText",
                RecordMessage.lastText());

        printMessage("prepend / append",
                new SizeMessage<>().prepend("Size:").append(" RecordId:").append(new RecordIdMessage<>()));
    }

    private static void showShortMessage() {
        System.out.println("-showShortMessage---");

        printMessage("constructor",
                new ShortMessage<>());
        printMessageKeyValueRecord("constructor KeyValueFieldsRecord",
                new ShortMessage<>());
    }

    private static void showSizeMessage() {
        System.out.println("-showSizeMessage---");

        printMessage("constructor",
                new SizeMessage<>());
    }

    private static void showSupplierMessage() {
        System.out.println("-showSupplierMessage---");

        printMessage("constructor",
                new SupplierMessage<>(() -> "message"));
        printMessage("constructor sequenceAsString",
                new SupplierMessage<>(SequenceSupplier.asString(0L)));
    }

    private static void showToIdentityStringMessage() {
        System.out.println("-showToIdentityStringMessage---");

        printMessage("constructor",
                new ToIdentityStringMessage<>());
        printMessageKeyValueRecord("constructor KeyValueFieldsRecord",
                new ToIdentityStringMessage<>());
    }

    private static void showToStringMessage() {
        System.out.println("-showToStringMessage---");

        printMessage("constructor",
                new ToStringMessage<>());
        printMessageKeyValueRecord("constructor KeyValueFieldsRecord",
                new ToStringMessage<>());
    }

    private static void showValueMessage() {
        System.out.println("-showValueMessage---");

        printMessage("constructor index",
                new TextMessage<>(1));
        printMessage("constructor index nullFieldMessage",
                new TextMessage<>(1, "<NULL>"));
        printMessage("constructor index nullFieldMessage fieldValueMapper",
                new TextMessage<>(1, "<NULL>", new AddPrefixFieldTextMapper("new value 1: ")));
        printMessage("constructor function",
                new TextMessage<>(TextRecord::lastField));
        printMessage("constructor function nullFieldMessage",
                new TextMessage<>(TextRecord::lastField, "<NULL>"));
        printMessage("constructor function nullFieldMessage fieldValueMapper",
                new TextMessage<>(TextRecord::lastField, "<NULL>", new AddPrefixFieldTextMapper("new value 1: ")));
        printMessageKeyValueRecord("key",
                TextMessage.key());
        printMessageKeyValueRecord("keyField",
                TextMessage.keyField(new AddPrefixFieldTextMapper("new key: ")));
        printMessageOneValueRecord("value",
                TextMessage.value());
        printMessageOneValueRecord("valueField",
                TextMessage.valueField(new AddPrefixFieldTextMapper("new value: ")));
    }

    public static void main(String... args) {
        showCategoryMessage();
        showClassNameMessage();
        showCompareMessageBuilder();
        showConditionalMessage();
        showConstantMessage();
        showExtendedValuesMessage();
        showFormatterMessage();
        showJoinedValuesMessage();
        showRecordIdMessage();
        showRecordMessage();
        showShortMessage();
        showSizeMessage();
        showSupplierMessage();
        showToIdentityStringMessage();
        showToStringMessage();
        showValueMessage();
    }

}
