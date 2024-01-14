package stexfires.io.container;

import stexfires.io.consumer.WritableRecordFileSpec;
import stexfires.io.producer.ReadableRecordFileSpec;
import stexfires.record.TextRecord;
import stexfires.record.consumer.UncheckedConsumerException;
import stexfires.record.producer.UncheckedProducerException;

import java.util.Objects;
import java.util.stream.Stream;

import static stexfires.io.RecordIOStreams.andFindFirstAsStream;
import static stexfires.io.RecordIOStreams.readFromString;
import static stexfires.io.RecordIOStreams.writeStreamIntoRecord;

/**
 * @since 0.1
 */
public interface RecordContainer {

    TextRecord pack(TextRecord originalTextRecord);

    UnpackResult unpack(TextRecord packedTextRecord);

    default <T extends TextRecord> Stream<TextRecord> packStream(Stream<T> recordStream) {
        Objects.requireNonNull(recordStream);
        return recordStream.map(this::pack);
    }

    default Stream<TextRecord> unpackAsStream(TextRecord packedTextRecord) {
        Objects.requireNonNull(packedTextRecord);
        return unpack(packedTextRecord).recordStream();
    }

    default <T extends TextRecord> TextRecord packRecordOfRecords(WritableRecordFileSpec<TextRecord, ?> writableRecordFileSpec,
                                                                  Stream<T> recordStream)
            throws UncheckedConsumerException {
        Objects.requireNonNull(writableRecordFileSpec);
        Objects.requireNonNull(recordStream);
        return writeStreamIntoRecord(writableRecordFileSpec, true, packStream(recordStream));
    }

    /**
     * This method hides errors.
     */
    default <T extends TextRecord> Stream<TextRecord> unpackRecordOfRecords(ReadableRecordFileSpec<TextRecord, ?> readableRecordFileSpec,
                                                                            T recordOfRecords)
            throws UncheckedProducerException {
        Objects.requireNonNull(readableRecordFileSpec);
        Objects.requireNonNull(recordOfRecords);
        return recordOfRecords.streamOfTexts()
                              .filter(Objects::nonNull)
                              .flatMap(text -> readFromString(readableRecordFileSpec, text, andFindFirstAsStream()))
                              .flatMap(this::unpackAsStream);
    }

}
