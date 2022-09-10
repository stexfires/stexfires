package stexfires.record;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A {@link Field} is part of a {@link TextRecord}.
 * <p>
 * It consists of a text, an index and the maxIndex of the {@link TextRecord}.
 * <p>
 * It is {@code immutable} and {@code thread-safe}.
 * The {@code text} can be {@code null}.
 * The {@code index} of the first field is always {@code 0}.
 * The {@code index} of the last field is always identical with the {@code maxIndex}.
 * The {@code index} must never be less than {@code 0} or greater than the {@code maxIndex}.
 *
 * @author Mathias Kalb
 * @see TextRecord
 * @see Fields
 * @see Fields#FIRST_FIELD_INDEX
 * @since 0.1
 */
public record Field(int index, int maxIndex, @Nullable String text)
        implements Serializable {

    public Field {
        if (index < Fields.FIRST_FIELD_INDEX) {
            throw new IllegalArgumentException("Illegal field index! index=" + index + " maxIndex=" + maxIndex);
        }
        if (index > maxIndex) {
            throw new IllegalArgumentException("Illegal field index! index=" + index + " maxIndex=" + maxIndex);
        }
    }

    public boolean isFirstField() {
        return index == Fields.FIRST_FIELD_INDEX;
    }

    public boolean isLastField() {
        return index == maxIndex;
    }

    public int recordSize() {
        return maxIndex + 1;
    }

    @Override
    public @Nullable String text() {
        return text;
    }

    public @Nullable String orElse(@Nullable String otherText) {
        return text != null ? text : otherText;
    }

    public @NotNull Optional<String> asOptional() {
        return Optional.ofNullable(text);
    }

    public boolean isNotNull() {
        return text != null;
    }

    public boolean isNull() {
        return text == null;
    }

    public boolean isEmpty() {
        return text != null && text.isEmpty();
    }

    public boolean isNullOrEmpty() {
        return text == null || text.isEmpty();
    }

    /**
     * @return the length of the text or '0' if the text is 'null'
     * @see java.lang.String#length()
     */
    public int length() {
        return text != null ? text.length() : 0;
    }

    public @NotNull Stream<String> stream() {
        return Stream.ofNullable(text);
    }

}
