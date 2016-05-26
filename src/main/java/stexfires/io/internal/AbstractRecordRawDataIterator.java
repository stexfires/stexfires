package stexfires.io.internal;

import stexfires.core.producer.ProducerException;
import stexfires.core.producer.UncheckedProducerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public abstract class AbstractRecordRawDataIterator implements Iterator<RecordRawData> {

    private static final long FIRST_RECORD_INDEX = 0L;

    private final BufferedReader reader;
    private final int ignoreFirst;
    private final int ignoreLast;

    private final ArrayBlockingQueue<RecordRawData> queue;

    private long recordIndex;
    private boolean endIsReached;

    protected AbstractRecordRawDataIterator(BufferedReader reader) {
        this(reader, 0, 0);
    }

    protected AbstractRecordRawDataIterator(BufferedReader reader, int ignoreFirst, int ignoreLast) {
        Objects.requireNonNull(reader);
        if (ignoreFirst < 0) {
            throw new IllegalArgumentException("ignoreFirst < 0");
        }
        if (ignoreLast < 0) {
            throw new IllegalArgumentException("ignoreLast < 0");
        }
        this.reader = reader;
        this.ignoreFirst = ignoreFirst;
        this.ignoreLast = ignoreLast;

        queue = new ArrayBlockingQueue<>(ignoreLast + 1);
        recordIndex = FIRST_RECORD_INDEX;
    }

    protected abstract RecordRawData readNext(BufferedReader reader, long recordIndex) throws ProducerException, IOException;

    protected void fillQueue() throws UncheckedProducerException {
        try {
            while (!endIsReached && (queue.remainingCapacity() > 0)) {
                RecordRawData recordRawData = readNext(reader, recordIndex);
                if (recordRawData != null) {
                    if ((recordIndex - FIRST_RECORD_INDEX) >= ignoreFirst) {
                        queue.add(recordRawData); // Can throw an IllegalStateException
                    }
                    recordIndex++;
                } else {
                    endIsReached = true;
                }
            }
        } catch (ProducerException e) {
            throw new UncheckedProducerException(e);
        } catch (IOException e) {
            throw new UncheckedProducerException("IOException during fillQueue! recordIndex=" + recordIndex, e);
        }
    }

    @Override
    public boolean hasNext() {
        fillQueue();
        return queue.size() > ignoreLast;
    }

    @Override
    public RecordRawData next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return queue.poll();
    }

}
