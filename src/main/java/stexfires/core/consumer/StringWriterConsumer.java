package stexfires.core.consumer;

import stexfires.core.TextRecord;
import stexfires.core.message.RecordMessage;

import java.io.StringWriter;

/**
 * @author Mathias Kalb
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
