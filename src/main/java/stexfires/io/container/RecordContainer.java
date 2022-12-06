package stexfires.io.container;

import stexfires.record.TextRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public sealed interface RecordContainer
        permits RecordContainerSmall, RecordContainerMedium, RecordContainerLarge {

    TextRecord pack(TextRecord originalTextRecord);

    UnpackResult<? extends TextRecord> unpack(TextRecord packedTextRecord);

}
