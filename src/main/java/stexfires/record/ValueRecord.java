package stexfires.record;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface ValueRecord extends TextRecord {

    ValueRecord newValueRecord(@Nullable String value);

    @NotNull Field valueField();

    default @Nullable String valueOfValueField() {
        return valueField().value();
    }

}
