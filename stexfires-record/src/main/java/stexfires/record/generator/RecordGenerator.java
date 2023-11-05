package stexfires.record.generator;

import org.jetbrains.annotations.NotNull;
import stexfires.record.KeyValueCommentRecord;
import stexfires.record.KeyValueRecord;
import stexfires.record.TextRecord;
import stexfires.record.ValueRecord;
import stexfires.record.impl.KeyValueCommentFieldsRecord;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;

import java.util.Objects;
import java.util.function.Function;

/**
 * A RecordGenerator generates a new {@link stexfires.record.TextRecord}.
 * It takes a {@link GeneratorContext} as input and returns a {@link stexfires.record.TextRecord}.
 * <p>
 * It must be {@code thread-safe} and {@code non-interfering}.
 * It should be {@code immutable} and {@code stateless}.
 * <p>
 * This is a {@code functional interface} whose functional method is {@link #generate(GeneratorContext)}.
 *
 * @see java.util.function.Function
 * @see stexfires.record.generator.CategoryGenerator
 * @see stexfires.record.generator.RecordIdGenerator
 * @since 0.1
 */
@FunctionalInterface
public interface RecordGenerator<T extends TextRecord> {

    int KEY_VALUE_RECORD_INDEX_KEY = 0;
    int KEY_VALUE_COMMENT_RECORD_INDEX_KEY = 0;
    int KEY_VALUE_COMMENT_RECORD_INDEX_VALUE = 1;

    static RecordGenerator<ValueRecord> valueRecord(@NotNull CategoryGenerator<ValueRecord> categoryGenerator,
                                                    @NotNull RecordIdGenerator<ValueRecord> recordIdGenerator,
                                                    @NotNull Function<GeneratorInterimResult<ValueRecord>, String> valueFunction) {
        Objects.requireNonNull(categoryGenerator);
        Objects.requireNonNull(recordIdGenerator);
        Objects.requireNonNull(valueFunction);
        return (GeneratorContext<ValueRecord> context) -> {
            String category = categoryGenerator.generateCategory(context);
            Long recordId = recordIdGenerator.generateRecordId(context);
            String value = valueFunction.apply(new GeneratorInterimResult<>(context, category, recordId, null));
            return new ValueFieldRecord(category, recordId, value);
        };
    }

    @SuppressWarnings("ReturnOfNull")
    static RecordGenerator<KeyValueRecord> keyValueRecord(@NotNull CategoryGenerator<KeyValueRecord> categoryGenerator,
                                                          @NotNull RecordIdGenerator<KeyValueRecord> recordIdGenerator,
                                                          @NotNull Function<GeneratorInterimResult<KeyValueRecord>, String> keyFunction,
                                                          @NotNull Function<GeneratorInterimResult<KeyValueRecord>, String> valueFunction) {
        Objects.requireNonNull(categoryGenerator);
        Objects.requireNonNull(recordIdGenerator);
        Objects.requireNonNull(keyFunction);
        Objects.requireNonNull(valueFunction);
        return (GeneratorContext<KeyValueRecord> context) -> {
            String category = categoryGenerator.generateCategory(context);
            Long recordId = recordIdGenerator.generateRecordId(context);
            String key = keyFunction.apply(new GeneratorInterimResult<>(context, category, recordId, null));
            String value = valueFunction.apply(new GeneratorInterimResult<>(context, category, recordId,
                    i -> switch (i) {
                        case KEY_VALUE_RECORD_INDEX_KEY -> key;
                        default -> null;
                    }));
            return new KeyValueFieldsRecord(category, recordId, key, value);
        };
    }

    @SuppressWarnings("ReturnOfNull")
    static RecordGenerator<KeyValueCommentRecord> keyValueCommentRecord(@NotNull CategoryGenerator<KeyValueCommentRecord> categoryGenerator,
                                                                        @NotNull RecordIdGenerator<KeyValueCommentRecord> recordIdGenerator,
                                                                        @NotNull Function<GeneratorInterimResult<KeyValueCommentRecord>, String> keyFunction,
                                                                        @NotNull Function<GeneratorInterimResult<KeyValueCommentRecord>, String> valueFunction,
                                                                        @NotNull Function<GeneratorInterimResult<KeyValueCommentRecord>, String> commentFunction) {
        Objects.requireNonNull(categoryGenerator);
        Objects.requireNonNull(recordIdGenerator);
        Objects.requireNonNull(keyFunction);
        Objects.requireNonNull(valueFunction);
        Objects.requireNonNull(commentFunction);
        return (GeneratorContext<KeyValueCommentRecord> context) -> {
            String category = categoryGenerator.generateCategory(context);
            Long recordId = recordIdGenerator.generateRecordId(context);
            String key = keyFunction.apply(new GeneratorInterimResult<>(context, category, recordId, null));
            String value = valueFunction.apply(new GeneratorInterimResult<>(context, category, recordId,
                    i -> switch (i) {
                        case KEY_VALUE_COMMENT_RECORD_INDEX_KEY -> key;
                        default -> null;
                    }));
            String comment = commentFunction.apply(new GeneratorInterimResult<>(context, category, recordId,
                    i -> switch (i) {
                        case KEY_VALUE_COMMENT_RECORD_INDEX_KEY -> key;
                        case KEY_VALUE_COMMENT_RECORD_INDEX_VALUE -> value;
                        default -> null;
                    }
            ));
            return new KeyValueCommentFieldsRecord(category, recordId, key, value, comment);
        };
    }

    @NotNull T generate(@NotNull GeneratorContext<T> context);

}
