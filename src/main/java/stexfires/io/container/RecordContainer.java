package stexfires.io.container;

import org.jetbrains.annotations.NotNull;
import stexfires.io.ReadableRecordFileSpec;
import stexfires.io.WritableRecordFileSpec;
import stexfires.record.TextRecord;
import stexfires.record.consumer.UncheckedConsumerException;
import stexfires.record.producer.UncheckedProducerException;

import java.util.Objects;
import java.util.stream.Stream;

import static stexfires.io.RecordIOStreams.andFindFirstAsStream;
import static stexfires.io.RecordIOStreams.readFromString;
import static stexfires.io.RecordIOStreams.writeStreamIntoRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface RecordContainer {

    @NotNull TextRecord pack(@NotNull TextRecord originalTextRecord);

    @NotNull UnpackResult unpack(@NotNull TextRecord packedTextRecord);

    default @NotNull <T extends TextRecord> Stream<TextRecord> packStream(Stream<T> recordStream) {
        Objects.requireNonNull(recordStream);
        return recordStream.map(this::pack);
    }

    default @NotNull <T extends TextRecord> Stream<TextRecord> unpackAsStream(T packedTextRecord) {
        Objects.requireNonNull(packedTextRecord);
        return unpack(packedTextRecord).recordStream();
    }

    default @NotNull <T extends TextRecord> TextRecord packRecordOfRecords(WritableRecordFileSpec<TextRecord, ?> writableRecordFileSpec,
                                                                           Stream<T> recordStream)
            throws UncheckedConsumerException {
        Objects.requireNonNull(writableRecordFileSpec);
        Objects.requireNonNull(recordStream);
        return writeStreamIntoRecord(writableRecordFileSpec, true, packStream(recordStream));
    }

    /**
     * This method hides errors.
     */
    default @NotNull <T extends TextRecord> Stream<TextRecord> unpackRecordOfRecords(ReadableRecordFileSpec<TextRecord, ?> readableRecordFileSpec,
                                                                                     T recordOfRecords)
            throws UncheckedProducerException {
        Objects.requireNonNull(readableRecordFileSpec);
        Objects.requireNonNull(recordOfRecords);
        return recordOfRecords.streamOfTexts()
                              .flatMap(text -> readFromString(readableRecordFileSpec, text, andFindFirstAsStream()))
                              .flatMap(this::unpackAsStream);
    }

}
