package stexfires.io.internal;

import stexfires.io.consumer.AbstractWritableConsumer;
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
 * @since 0.1
 */
@SuppressWarnings("resource")
public abstract sealed class AbstractInternalWritableConsumer<T extends TextRecord> extends AbstractWritableConsumer<T>
        permits
        stexfires.io.config.ConfigConsumer,
        stexfires.io.delimited.simple.SimpleDelimitedConsumer,
        stexfires.io.fixedwidth.FixedWidthConsumer,
        stexfires.io.html.table.HtmlTableConsumer,
        stexfires.io.markdown.list.MarkdownListConsumer,
        stexfires.io.markdown.table.MarkdownTableConsumer,
        stexfires.io.message.RecordMessageConsumer,
        stexfires.io.properties.PropertiesConsumer,
        stexfires.io.singlevalue.SingleValueConsumer {

    private WritableConsumerState state;

    protected AbstractInternalWritableConsumer(BufferedWriter bufferedWriter) {
        super(bufferedWriter);
        state = OPEN;
    }

    protected final void writeString(String str) throws IOException {
        Objects.requireNonNull(str);
        state.validateNotClosed();
        bufferedWriter().write(str);
    }

    protected final void writeLineSeparator(LineSeparator lineSeparator) throws IOException {
        Objects.requireNonNull(lineSeparator);
        state.validateNotClosed();
        bufferedWriter().write(lineSeparator.string());
    }

    protected final void writeCharSequence(CharSequence charSequence) throws IOException {
        Objects.requireNonNull(charSequence);
        state.validateNotClosed();
        bufferedWriter().append(charSequence);
    }

    @Override
    public void writeBefore() throws ConsumerException, UncheckedConsumerException, IOException {
        state = WRITE_BEFORE.validate(state);
        super.writeBefore();
    }

    @Override
    public void writeRecord(T record) throws ConsumerException, UncheckedConsumerException, IOException {
        Objects.requireNonNull(record);
        state = WRITE_RECORDS.validate(state);
    }

    @Override
    public void writeAfter() throws ConsumerException, UncheckedConsumerException, IOException {
        state = WRITE_AFTER.validate(state);
        super.writeAfter();
    }

    @Override
    public final void flush() throws IOException {
        state.validateNotClosed();
        super.flush();
    }

    @Override
    public final void close() throws IOException {
        state = CLOSE.validate(state);
        super.close();
    }

}
