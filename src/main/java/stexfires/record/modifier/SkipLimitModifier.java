package stexfires.record.modifier;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;

import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SkipLimitModifier<T extends TextRecord> implements RecordStreamModifier<T, T> {

    public static final long NO_SKIP = -1L;
    public static final long NO_LIMIT = -1L;

    protected static final long MIN_SKIP = 1L;
    protected static final long MIN_LIMIT = 0L;

    private final long skipFirst;
    private final long limitMaxSize;

    public SkipLimitModifier(long skipFirst,
                             long limitMaxSize) {
        this.skipFirst = skipFirst;
        this.limitMaxSize = limitMaxSize;
    }

    public static <T extends TextRecord> SkipLimitModifier<T> skip(long skipFirst) {
        return new SkipLimitModifier<>(skipFirst, NO_LIMIT);
    }

    public static <T extends TextRecord> SkipLimitModifier<T> limit(long limitMaxSize) {
        return new SkipLimitModifier<>(NO_SKIP, limitMaxSize);
    }

    @Override
    public final @NotNull Stream<T> modify(Stream<T> recordStream) {
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
