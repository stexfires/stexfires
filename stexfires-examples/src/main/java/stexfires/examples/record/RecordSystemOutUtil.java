package stexfires.examples.record;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.TextFields;
import stexfires.record.TextRecord;
import stexfires.record.consumer.SystemOutConsumer;
import stexfires.record.logger.SystemOutLogger;
import stexfires.record.message.JoinedTextsMessage;
import stexfires.record.message.NullSafeMessage;
import stexfires.record.message.RecordMessage;
import stexfires.record.message.ShortMessage;
import stexfires.util.Strings;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "OptionalUsedAsFieldOrParameterType"})
public final class RecordSystemOutUtil {

    public static final RecordMessage<TextRecord> RECORD_MESSAGE =
            new NullSafeMessage<>(
                    new ShortMessage<>()
                            .append(" [",
                                    new JoinedTextsMessage<>(", "))
                            .append("]"));
    public static final SystemOutLogger<TextRecord> RECORD_LOGGER = new SystemOutLogger<>(RECORD_MESSAGE);
    public static final SystemOutConsumer<TextRecord> RECORD_CONSUMER = new SystemOutConsumer<>(RECORD_MESSAGE);

    private RecordSystemOutUtil() {
    }

    public static void printlnRecord(@Nullable TextRecord record) {
        RECORD_LOGGER.log(record);
    }

    public static void printlnOptionalRecord(@NotNull Optional<? extends TextRecord> record) {
        record.ifPresentOrElse(RECORD_LOGGER::log, () -> System.out.println("Optional TextRecord is NULL!"));
    }

    public static void printlnRecordList(@Nullable List<? extends TextRecord> records) {
        if (records == null) {
            System.out.println("<<<List<TextRecord> is NULL!");
        } else {
            System.out.println("<<<List size=" + records.size());
            records.forEach(RECORD_CONSUMER::consume);
        }
        System.out.println(">>>");
    }

    public static void printlnRecordStream(@Nullable Stream<? extends TextRecord> records) {
        if (records == null) {
            System.out.println("<<<Stream<TextRecord> is NULL!");
        } else {
            System.out.println("<<<Stream");
            records.forEachOrdered(RECORD_CONSUMER::consume);
        }
        System.out.println(">>>");
    }

    @SuppressWarnings("UnnecessaryToStringCall")
    public static void printlnRecordExtended(@NotNull TextRecord record) {
        Objects.requireNonNull(record);
        System.out.println("RecordMessage              " + RECORD_MESSAGE.createMessage(record));
        System.out.println("arrayOfFields:             " + Arrays.toString(record.arrayOfFields()));
        System.out.println("listOfFields:              " + record.listOfFields());
        System.out.println("listOfFieldsReversed:      " + record.listOfFieldsReversed());
        System.out.println("streamOfFields:            " + TextFields.collectFields(record.streamOfFields()));
        System.out.println("streamOfTexts:             " + Strings.join(record.streamOfTexts()));
        System.out.println("streamOfTextsAsOptional:   " + Strings.join(record.streamOfTextsAsOptional().flatMap(Optional::stream)));
        System.out.println("category:                  " + record.category());
        System.out.println("hasCategory:               " + record.hasCategory());
        System.out.println("categoryAsOptional:        " + record.categoryAsOptional());
        System.out.println("categoryAsStream:          " + record.categoryAsStream().findFirst());
        System.out.println("recordId:                  " + record.recordId());
        System.out.println("hasRecordId:               " + record.hasRecordId());
        System.out.println("recordIdAsOptional:        " + record.recordIdAsOptional());
        System.out.println("recordIdAsOptionalLong:    " + record.recordIdAsOptionalLong());
        System.out.println("recordIdAsStream:          " + record.recordIdAsStream().findFirst());
        System.out.println("recordIdAsLongStream:      " + record.recordIdAsLongStream().findFirst());
        System.out.println("recordIdAsString:          " + record.recordIdAsString());
        System.out.println("size:                      " + record.size());
        System.out.println("isNotEmpty:                " + record.isNotEmpty());
        System.out.println("isEmpty:                   " + record.isEmpty());
        System.out.println("isValidIndex:              " + record.isValidIndex(0));
        System.out.println("fieldAt:                   " + record.fieldAt(0));
        System.out.println("firstField:                " + record.firstField());
        System.out.println("lastField:                 " + record.lastField());
        System.out.println("textAt:                    " + record.textAt(0));
        System.out.println("textAtOrElse:              " + record.textAtOrElse(0, "else"));
        System.out.println("firstText:                 " + record.firstText());
        System.out.println("lastText:                  " + record.lastText());
        System.out.println("hashCode:                  " + record.hashCode());
        System.out.println("toString:                  " + record.toString());
    }

}
