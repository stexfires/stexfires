package org.textfiledatatools.io.internal;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.consumer.ConsumerException;
import org.textfiledatatools.io.WritableRecordConsumer;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public abstract class AbstractWritableConsumer<T extends Record> implements WritableRecordConsumer<T> {

    protected final BufferedWriter writer;

    protected AbstractWritableConsumer(BufferedWriter writer) {
        this.writer = writer;
    }

    protected final void write(String str) throws IOException {
        writer.write(str);
    }

    @Override
    public void writeBefore() throws IOException {
    }

    @Override
    public void writeRecord(T record) throws IOException, ConsumerException {
    }

    @Override
    public void writeAfter() throws IOException {
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }

}
