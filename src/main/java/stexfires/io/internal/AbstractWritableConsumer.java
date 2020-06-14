package stexfires.io.internal;

import stexfires.core.TextRecord;
import stexfires.core.consumer.ConsumerException;
import stexfires.io.WritableRecordConsumer;
import stexfires.util.LineSeparator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Objects;

import static stexfires.io.internal.WritableConsumerState.CLOSE;
import static stexfires.io.internal.WritableConsumerState.OPEN;
import static stexfires.io.internal.WritableConsumerState.WRITE_AFTER;
import static stexfires.io.internal.WritableConsumerState.WRITE_BEFORE;
import static stexfires.io.internal.WritableConsumerState.WRITE_RECORDS;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public abstract class AbstractWritableConsumer<T extends TextRecord> implements WritableRecordConsumer<T> {

    protected final BufferedWriter writer;

    protected WritableConsumerState state;

    protected AbstractWritableConsumer(BufferedWriter writer) {
        Objects.requireNonNull(writer);
        this.writer = writer;
        state = OPEN;
    }

    protected final void write(String str) throws IOException {
        state.validateNotClosed();
        writer.write(str);
    }

    protected final void write(LineSeparator lineSeparator) throws IOException {
        state.validateNotClosed();
        writer.write(lineSeparator.string());
    }

    @SuppressWarnings("OverloadedMethodsWithSameNumberOfParameters")
    protected final void write(CharSequence charSequence) throws IOException {
        state.validateNotClosed();
        writer.append(charSequence);
    }

    @Override
    public void writeBefore() throws IOException {
        state = WRITE_BEFORE.validate(state);
    }

    @Override
    public void writeRecord(T record) throws IOException, ConsumerException {
        state = WRITE_RECORDS.validate(state);
    }

    @Override
    public void writeAfter() throws IOException {
        state = WRITE_AFTER.validate(state);
    }

    @Override
    public void flush() throws IOException {
        state.validateNotClosed();
        writer.flush();
    }

    @Override
    public void close() throws IOException {
        state = CLOSE.validate(state);
        writer.close();
    }

}
