package stexfires.io;

import stexfires.record.TextRecord;

import java.io.IOException;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface ReadableRecordFile<PTR extends TextRecord, RFS extends RecordFileSpec> extends RecordFile<RFS> {

    ReadableRecordProducer<PTR> openProducer() throws IOException;

}
