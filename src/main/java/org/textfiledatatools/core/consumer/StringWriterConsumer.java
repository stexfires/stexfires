package org.textfiledatatools.core.consumer;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.message.RecordMessage;

import java.io.StringWriter;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class StringWriterConsumer<T extends Record> extends WriterConsumer<T, StringWriter> {

    public StringWriterConsumer(RecordMessage<? super T> recordMessage, String recordSeparator) {
        super(new StringWriter(), recordMessage, recordSeparator);
    }

    public StringWriterConsumer(RecordMessage<? super T> recordMessage, String recordPrefix, String recordPostfix) {
        super(new StringWriter(), recordMessage, recordPrefix, recordPostfix);
    }

    public String getString() {
        return writer.toString();
    }

    @Override
    public void close() {
        // Closing a StringWriter has no effect.
    }

}
