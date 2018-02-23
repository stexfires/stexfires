package stexfires.core.modifier;

import stexfires.core.Record;

import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class IdentityModifier<T extends Record> implements RecordStreamModifier<T, T> {

    public IdentityModifier() {
    }

    @Override
    public Stream<T> modify(Stream<T> recordStream) {
        return recordStream;
    }

}
