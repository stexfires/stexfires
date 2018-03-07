package stexfires.core.modifier;

import stexfires.core.Record;

import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SkipLimitModifier<T extends Record> implements RecordStreamModifier<T, T> {

    public static final long NO_SKIP = -1L;
    public static final long NO_LIMIT = -1L;

    protected static final long MIN_SKIP = 1L;
    protected static final long MIN_LIMIT = 0L;

    protected final long skipFirst;
    protected final long limitMaxSize;

    public SkipLimitModifier(long skipFirst,
                             long limitMaxSize) {
        this.skipFirst = skipFirst;
        this.limitMaxSize = limitMaxSize;
    }

    public static <T extends Record> SkipLimitModifier<T> skip(long skipFirst) {
        return new SkipLimitModifier<>(skipFirst, NO_LIMIT);
    }

    public static <T extends Record> SkipLimitModifier<T> limit(long limitMaxSize) {
        return new SkipLimitModifier<>(NO_SKIP, limitMaxSize);
    }

    @Override
    public final Stream<T> modify(Stream<T> recordStream) {
        Stream<T> returnStream = recordStream;
        if (skipFirst >= MIN_SKIP) {
            returnStream = returnStream.skip(skipFirst);
        }
        if (limitMaxSize >= MIN_LIMIT) {
            returnStream = returnStream.limit(limitMaxSize);
        }
        return returnStream;
    }

}
