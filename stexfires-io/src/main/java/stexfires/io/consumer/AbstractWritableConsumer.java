package stexfires.io.consumer;

import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;
import stexfires.util.LineSeparator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

/**
 * @since 0.1
 */
public abstract class AbstractWritableConsumer<T extends TextRecord> implements WritableRecordConsumer<T> {

    private enum WritableConsumerState {
        OPEN,
        WRITE_BEFORE,
        WRITE_RECORDS,
        WRITE_AFTER,
        CLOSE;

        private static void validateStates(WritableConsumerState currentState, WritableConsumerState newState) throws IllegalStateException {
            Objects.requireNonNull(currentState);
            Objects.requireNonNull(newState);
            if ((currentState.ordinal() + 1) != newState.ordinal()) {
                if ((newState != CLOSE)
                        && !((currentState == WRITE_BEFORE) && (newState == WRITE_AFTER))
                        && !((currentState == WRITE_RECORDS) && (newState == WRITE_RECORDS))) {
                    throw new IllegalStateException("Illegal state of AbstractWritableConsumer! " + currentState + " -> " + newState);
                }
            }
        }

        private void validateNotClosed() throws IllegalStateException {
            if (this == CLOSE) {
                throw new IllegalStateException("Illegal state of AbstractWritableConsumer! " + this);
            }
        }

        private WritableConsumerState validate(WritableConsumerState currentState) throws IllegalStateException {
            validateStates(currentState, this);
            return this;
        }

    }

    private WritableConsumerState state;

    private final BufferedWriter bufferedWriter;

    protected AbstractWritableConsumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        this.bufferedWriter = bufferedWriter;

        state = WritableConsumerState.OPEN;
    }

    public final BufferedWriter bufferedWriter() {
        return bufferedWriter;
    }

    protected final void writeString(String str) throws IOException, IllegalStateException {
        Objects.requireNonNull(str);
        state.validateNotClosed();
        bufferedWriter().write(str);
    }

    protected final void writeLineSeparator(LineSeparator lineSeparator) throws IOException, IllegalStateException {
        Objects.requireNonNull(lineSeparator);
        state.validateNotClosed();
        bufferedWriter().write(lineSeparator.string());
    }

    protected final void writeCharSequence(CharSequence charSequence) throws IOException, IllegalStateException {
        Objects.requireNonNull(charSequence);
        state.validateNotClosed();
        bufferedWriter().append(charSequence);
    }

    @Override
    public void writeBefore() throws ConsumerException, UncheckedConsumerException, IOException, IllegalStateException {
        // Validate and adjust state
        state = WritableConsumerState.WRITE_BEFORE.validate(state);
    }

    @Override
    public void writeRecord(T record) throws ConsumerException, UncheckedConsumerException, IOException, IllegalStateException {
        Objects.requireNonNull(record);
        // Validate and adjust state
        state = WritableConsumerState.WRITE_RECORDS.validate(state);
    }

    @Override
    public void writeAfter() throws ConsumerException, UncheckedConsumerException, IOException, IllegalStateException {
        // Validate and adjust state
        state = WritableConsumerState.WRITE_AFTER.validate(state);
    }

    @Override
    public void flush() throws IOException, IllegalStateException {
        state.validateNotClosed();

        bufferedWriter.flush();
    }

    @Override
    public void close() throws IOException, IllegalStateException {
        // Validate and adjust state
        state = WritableConsumerState.CLOSE.validate(state);

        bufferedWriter.close();
    }

}
