package stexfires.core.consumer;

import stexfires.core.Record;
import stexfires.core.message.RecordMessage;

import java.io.StringWriter;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class StringWriterConsumer<T extends Record> extends WriterConsumer<T, StringWriter> {

    public StringWriterConsumer(RecordMessage<? super T> recordMessage) {
        super(new StringWriter(), recordMessage);
    }

    public String getString() {
        return getWriter().toString();
    }

    @Override
    public void close() {
        // Closing a StringWriter has no effect.
    }

}
