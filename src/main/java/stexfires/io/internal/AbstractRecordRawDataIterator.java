package stexfires.io.internal;

import stexfires.core.producer.ProducerException;
import stexfires.core.producer.UncheckedProducerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public abstract class AbstractRecordRawDataIterator implements Iterator<RecordRawData> {

    public static final long FIRST_RECORD_INDEX = 0L;
    public static final int DEFAULT_IGNORE_FIRST = 0;
    public static final int DEFAULT_IGNORE_LAST = 0;

    private final BufferedReader bufferedReader;
    private final int ignoreFirst;
    private final int ignoreLast;
    private final ArrayBlockingQueue<RecordRawData> queue;
    private final List<RecordRawData> first;
    private final List<RecordRawData> last;

    private long currentRecordIndex;
    private boolean endIsReached;

    protected AbstractRecordRawDataIterator(BufferedReader bufferedReader) {
        this(bufferedReader, DEFAULT_IGNORE_FIRST, DEFAULT_IGNORE_LAST);
    }

    protected AbstractRecordRawDataIterator(BufferedReader bufferedReader, int ignoreFirst, int ignoreLast) {
        Objects.requireNonNull(bufferedReader);
        if (ignoreFirst < 0) {
            throw new IllegalArgumentException("ignoreFirst < 0");
        }
        if (ignoreLast < 0) {
            throw new IllegalArgumentException("ignoreLast < 0");
        }
        this.bufferedReader = bufferedReader;
        this.ignoreFirst = ignoreFirst;
        this.ignoreLast = ignoreLast;

        queue = new ArrayBlockingQueue<>(ignoreLast + 1);
        first = new ArrayList<>(ignoreFirst);
        last = new ArrayList<>(ignoreLast);
        currentRecordIndex = FIRST_RECORD_INDEX;
    }

    protected abstract RecordRawData readNext(BufferedReader reader, long recordIndex) throws ProducerException, IOException;

    public final void fillQueue(boolean onlyFirst) throws UncheckedProducerException {
        try {
            while (!endIsReached && (queue.remainingCapacity() > 0)
                    && (!onlyFirst || first.size() < ignoreFirst)) {
                RecordRawData recordRawData = readNext(bufferedReader, currentRecordIndex);
                if (recordRawData != null) {
                    if ((currentRecordIndex - FIRST_RECORD_INDEX) >= ignoreFirst) {
                        queue.add(recordRawData); // Can throw an IllegalStateException
                    } else {
                        first.add(recordRawData);
                    }
                    currentRecordIndex++;
                } else {
                    endIsReached = true;
                    queue.drainTo(last);
                }
            }
        } catch (ProducerException e) {
            throw new UncheckedProducerException(e);
        } catch (IOException e) {
            throw new UncheckedProducerException("IOException during fillQueue! currentRecordIndex=" + currentRecordIndex, e);
        }
    }

    @Override
    public final boolean hasNext() {
        fillQueue(false);
        return queue.size() > ignoreLast;
    }

    @Override
    public final RecordRawData next() {
        if (!hasNext()) {
            throw new NoSuchElementException("queue size = " + queue.size());
        }
        return queue.poll();
    }

    public final int getIgnoreFirst() {
        return ignoreFirst;
    }

    public final int getIgnoreLast() {
        return ignoreLast;
    }

    public final long getCurrentRecordIndex() {
        return currentRecordIndex;
    }

    public final List<RecordRawData> getFirst() {
        return new ArrayList<>(first);
    }

    public final List<RecordRawData> getLast() {
        return new ArrayList<>(last);
    }

}
