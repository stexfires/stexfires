package stexfires.record.logger;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;
import stexfires.record.message.RecordMessage;

import java.util.Collection;
import java.util.Objects;

/**
 * @since 0.1
 */
public class CollectionLogger<T extends TextRecord, R extends Collection<@Nullable String>> implements RecordLogger<T> {

    protected final Object lock = new Object();

    private final R collection;
    private final RecordMessage<? super T> recordMessage;

    public CollectionLogger(R collection, RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(collection);
        Objects.requireNonNull(recordMessage);
        this.collection = collection;
        this.recordMessage = recordMessage;
    }

    @Override
    public final void log(T record) {
        try {
            String message = recordMessage.createMessage(record);
            synchronized (lock) {
                collection.add(message);
            }
        } catch (NullPointerException | UnsupportedOperationException | ClassCastException | IllegalArgumentException |
                 IllegalStateException e) {
            // Ignore Exception
        }
    }

    public final R getCollection() {
        return collection;
    }

}
