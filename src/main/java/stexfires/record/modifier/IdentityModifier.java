package stexfires.record.modifier;

import stexfires.record.TextRecord;

import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class IdentityModifier<T extends TextRecord> implements RecordStreamModifier<T, T> {

    public IdentityModifier() {
    }

    @Override
    public final Stream<T> modify(Stream<T> recordStream) {
        return recordStream;
    }

}
