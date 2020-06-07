package stexfires.core.logger;

import stexfires.core.TextRecord;
import stexfires.core.message.RecordMessage;

import java.io.PrintStream;
import java.util.Objects;

/**
 * @author Mathias Kalb
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
