package stexfires.record;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * A {@link TextRecord} consists of {@link TextField}s, each of which contains a text.
 * It can also contain a {@code category} and a {@code recordId}.
 * <p>
 * It must be {@code immutable} and {@code thread-safe}.
 * The implementation can be a 'Java' {@code record}.
 * The indexes of the contained fields must be ascending and start with {@code 0}.
 *
 * @author Mathias Kalb
 * @see TextField
 * @see TextRecords
 * @see TextFields#FIRST_FIELD_INDEX
 * @since 0.1
 */
public interface TextRecord {

    @NotNull TextField[] arrayOfFields();

    default @NotNull List<TextField> listOfFields() {
        return switch (size()) {
            case 0 -> Collections.emptyList();
            case 1 -> Collections.singletonList(firstField());
            default -> Arrays.asList(arrayOfFields());
        };
    }

    default @NotNull List<TextField> listOfFieldsReversed() {
        if (size() == 0) {
            return Collections.emptyList();
        } else if (size() == 1) {
            return Collections.singletonList(firstField());
        } else {
            var fieldList = Arrays.asList(arrayOfFields());
            Collections.reverse(fieldList);
            return fieldList;
        }
    }

    default @NotNull Stream<TextField> streamOfFields() {
        return switch (size()) {
            case 0 -> Stream.empty();
            case 1 -> Stream.of(firstField());
            default -> Arrays.stream(arrayOfFields());
        };
    }

    default @NotNull Stream<String> streamOfTexts() {
        return streamOfFields().map(TextField::text);
    }

    default @NotNull Stream<Optional<String>> streamOfTextsAsOptional() {
        return streamOfFields().map(TextField::asOptional);
    }

    @Nullable String category();

    default boolean hasCategory() {
        return category() != null;
    }

    default @NotNull Optional<String> categoryAsOptional() {
        return Optional.ofNullable(category());
    }

    default @NotNull Stream<String> categoryAsStream() {
        return Stream.ofNullable(category());
    }

    @Nullable Long recordId();

    default boolean hasRecordId() {
        return recordId() != null;
    }

    default @NotNull Optional<Long> recordIdAsOptional() {
        return Optional.ofNullable(recordId());
    }

    @SuppressWarnings("DataFlowIssue")
    default @NotNull OptionalLong recordIdAsOptionalLong() {
        return hasRecordId() ? OptionalLong.of(recordId()) : OptionalLong.empty();
    }

    default @NotNull Stream<Long> recordIdAsStream() {
        return Stream.ofNullable(recordId());
    }

    @SuppressWarnings("DataFlowIssue")
    default @NotNull LongStream recordIdAsLongStream() {
        return hasRecordId() ? LongStream.of(recordId()) : LongStream.empty();
    }

    /**
     * Returns the number of contained fields.
     * It must not be negative.
     *
     * @return number of contained fields
     */
    int size();

    default boolean isNotEmpty() {
        return size() > 0;
    }

    default boolean isEmpty() {
        return size() <= 0;
    }

    default boolean isValidIndex(int index) {
        return (index >= 0) && (index < size());
    }

    @Nullable TextField fieldAt(int index);

    default @Nullable TextField firstField() {
        return fieldAt(TextFields.FIRST_FIELD_INDEX);
    }

    default @Nullable TextField lastField() {
        return fieldAt(size() - 1);
    }

    @SuppressWarnings("DataFlowIssue")
    default @Nullable String textAt(int index) {
        return isValidIndex(index) ? fieldAt(index).text() : null;
    }

    default @Nullable String textAtOrElse(int index, @Nullable String otherText) {
        String textAt = textAt(index);
        return textAt != null ? textAt : otherText;
    }

    @SuppressWarnings("DataFlowIssue")
    default @Nullable String firstText() {
        return isNotEmpty() ? firstField().text() : null;
    }

    @SuppressWarnings("DataFlowIssue")
    default @Nullable String lastText() {
        return isNotEmpty() ? lastField().text() : null;
    }

}
