package stexfires.io.internal;

import stexfires.io.consumer.WritableRecordConsumer;
import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;
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

    private final BufferedWriter bufferedWriter;

    private WritableConsumerState state;

    protected AbstractWritableConsumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        this.bufferedWriter = bufferedWriter;
        state = OPEN;
    }

    protected final void writeString(String str) throws IOException {
        Objects.requireNonNull(str);
        state.validateNotClosed();
        bufferedWriter.write(str);
    }

    protected final void writeLineSeparator(LineSeparator lineSeparator) throws IOException {
        Objects.requireNonNull(lineSeparator);
        state.validateNotClosed();
        bufferedWriter.write(lineSeparator.string());
    }

    protected final void writeCharSequence(CharSequence charSequence) throws IOException {
        Objects.requireNonNull(charSequence);
        state.validateNotClosed();
        bufferedWriter.append(charSequence);
    }

    @Override
    public void writeBefore() throws ConsumerException, UncheckedConsumerException, IOException {
        state = WRITE_BEFORE.validate(state);
    }

    @Override
    public void writeRecord(T record) throws ConsumerException, UncheckedConsumerException, IOException {
        state = WRITE_RECORDS.validate(state);
    }

    @Override
    public void writeAfter() throws ConsumerException, UncheckedConsumerException, IOException {
        state = WRITE_AFTER.validate(state);
    }

    @Override
    public final void flush() throws IOException {
        state.validateNotClosed();
        bufferedWriter.flush();
    }

    @Override
    public final void close() throws IOException {
        state = CLOSE.validate(state);
        bufferedWriter.close();
    }

    protected final BufferedWriter bufferedWriter() {
        return bufferedWriter;
    }

}
