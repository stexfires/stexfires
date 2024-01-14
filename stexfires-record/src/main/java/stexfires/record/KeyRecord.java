package stexfires.record;

import java.util.stream.Stream;

/**
 * The text of a key field must not be null.
 *
 * @since 0.1
 */
public interface KeyRecord extends TextRecord {

    KeyRecord withKey(String key);

    TextField keyField();

    int keyIndex();

    default String key() {
        return keyField().orElseThrow();
    }

    default Stream<String> keyAsStream() {
        return keyField().stream();
    }

}
