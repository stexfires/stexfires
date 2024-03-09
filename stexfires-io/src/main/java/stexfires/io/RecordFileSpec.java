package stexfires.io;

import stexfires.util.CharsetCoding;

/**
 * @since 0.1
 */
public sealed interface RecordFileSpec
        permits
        stexfires.io.producer.ReadableRecordFileSpec,
        stexfires.io.consumer.WritableRecordFileSpec {

    CharsetCoding DEFAULT_CHARSET_CODING = CharsetCoding.UTF_8_REPORTING;

    CharsetCoding charsetCoding();

}
