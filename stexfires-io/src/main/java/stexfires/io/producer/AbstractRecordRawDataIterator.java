package stexfires.io.producer;

import stexfires.record.producer.ProducerException;
import stexfires.record.producer.UncheckedProducerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

/**
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

        // init
        queue = new ArrayBlockingQueue<>(ignoreLast + 1);
        first = new ArrayList<>(ignoreFirst);
        last = new ArrayList<>(ignoreLast);
        currentRecordIndex = FIRST_RECORD_INDEX;
    }

    @SuppressWarnings("RedundantThrows")
    protected abstract Optional<RecordRawData> readNext(BufferedReader reader, long recordIndex) throws ProducerException, UncheckedProducerException, IOException;

    final void fillQueue(boolean onlyFirst) throws UncheckedProducerException {
        try {
            while (!endIsReached && (queue.remainingCapacity() > 0)
                    && (!onlyFirst || (first.size() < ignoreFirst))) {
                Optional<RecordRawData> recordRawData = readNext(bufferedReader, currentRecordIndex);
                if (recordRawData.isPresent()) {
                    if ((currentRecordIndex - FIRST_RECORD_INDEX) >= ignoreFirst) {
                        queue.add(recordRawData.get()); // Can throw an IllegalStateException
                    } else {
                        first.add(recordRawData.get());
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
            throw new UncheckedProducerException(new ProducerException("IOException during fillQueue! currentRecordIndex=" + currentRecordIndex + " endIsReached=" + endIsReached, e));
        }
    }

    @Override
    public final boolean hasNext() throws UncheckedProducerException {
        fillQueue(false);
        return queue.size() > ignoreLast;
    }

    @Override
    public final RecordRawData next() throws UncheckedProducerException {
        if (!hasNext()) {
            throw new NoSuchElementException("queue size = " + queue.size());
        }
        RecordRawData recordRawData = queue.poll();
        if (recordRawData == null) {
            throw new NoSuchElementException("queue size = " + queue.size());
        }
        return recordRawData;
    }

    public final int ignoreFirst() {
        return ignoreFirst;
    }

    public final int ignoreLast() {
        return ignoreLast;
    }

    public final long currentRecordIndex() {
        return currentRecordIndex;
    }

    public final List<RecordRawData> first() {
        return new ArrayList<>(first);
    }

    public final List<RecordRawData> last() {
        return new ArrayList<>(last);
    }

}
