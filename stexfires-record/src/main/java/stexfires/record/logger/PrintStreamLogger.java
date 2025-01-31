package stexfires.record.logger;

import stexfires.record.TextRecord;
import stexfires.record.message.RecordMessage;

import java.io.PrintStream;
import java.util.*;

/**
 * @since 0.1
 */
public class PrintStreamLogger<T extends TextRecord> implements RecordLogger<T> {

    public static final boolean DEFAULT_TERMINATE_LINE = true;

    private final PrintStream printStream;
    private final RecordMessage<? super T> recordMessage;
    private final boolean terminateLine;

    public PrintStreamLogger(PrintStream printStream,
                             RecordMessage<? super T> recordMessage) {
        this(printStream, recordMessage, DEFAULT_TERMINATE_LINE);
    }

    public PrintStreamLogger(PrintStream printStream,
                             RecordMessage<? super T> recordMessage,
                             boolean terminateLine) {
        Objects.requireNonNull(printStream);
        Objects.requireNonNull(recordMessage);
        this.printStream = printStream;
        this.recordMessage = recordMessage;
        this.terminateLine = terminateLine;
    }

    @Override
    public final void log(T record) {
        try {
            String message = recordMessage.createMessage(record);
            // println() and print() are both 'synchronized' and null-safe
            if (terminateLine) {
                printStream.println(message);
            } else {
                printStream.print(message);
            }
        } catch (NullPointerException | UnsupportedOperationException | ClassCastException | IllegalArgumentException |
                 IllegalStateException e) {
            // Ignore Exception
        }
    }

    public final PrintStream getPrintStream() {
        return printStream;
    }

}
