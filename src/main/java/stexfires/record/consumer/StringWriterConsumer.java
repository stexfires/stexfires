package stexfires.record.consumer;

import stexfires.record.TextRecord;
import stexfires.record.message.RecordMessage;

import java.io.StringWriter;

/**
 * @since 0.1
 */
public class StringWriterConsumer<T extends TextRecord> extends AppendableConsumer<T, StringWriter> {

    public StringWriterConsumer(RecordMessage<? super T> recordMessage) {
        super(new StringWriter(), recordMessage);
    }

    public final String getString() {
        return getAppendable().toString();
    }

}
