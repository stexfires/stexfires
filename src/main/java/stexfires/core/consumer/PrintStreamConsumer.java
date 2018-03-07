package stexfires.core.consumer;

import stexfires.core.Record;
import stexfires.core.message.RecordMessage;

import java.io.PrintStream;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class PrintStreamConsumer<T extends Record> implements RecordConsumer<T> {

    public static final boolean DEFAULT_TERMINATE_LINE = true;

    protected final PrintStream printStream;
    protected final RecordMessage<? super T> recordMessage;
    protected final boolean terminateLine;

    public PrintStreamConsumer(PrintStream printStream,
                               RecordMessage<? super T> recordMessage) {
        this(printStream, recordMessage, DEFAULT_TERMINATE_LINE);
    }

    public PrintStreamConsumer(PrintStream printStream,
                               RecordMessage<? super T> recordMessage,
                               boolean terminateLine) {
        Objects.requireNonNull(printStream);
        Objects.requireNonNull(recordMessage);
        this.printStream = printStream;
        this.recordMessage = recordMessage;
        this.terminateLine = terminateLine;
    }

    @Override
    public final void consume(T record) {
        String message = recordMessage.createMessage(record);
        // println() and print() are both 'synchronized'
        if (terminateLine) {
            printStream.println(message);
        } else {
            printStream.print(message);
        }
    }

    public final PrintStream getPrintStream() {
        return printStream;
    }

}
