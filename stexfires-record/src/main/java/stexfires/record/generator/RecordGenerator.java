package stexfires.record.generator;

import org.jetbrains.annotations.NotNull;
import stexfires.record.KeyValueCommentRecord;
import stexfires.record.KeyValueRecord;
import stexfires.record.TextRecord;
import stexfires.record.TextRecords;
import stexfires.record.ValueRecord;
import stexfires.record.impl.KeyValueCommentFieldsRecord;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.SequencedCollection;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

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

    @SafeVarargs
    static RecordGenerator<TextRecord> textRecordOfSuppliers(@NotNull CategoryGenerator<TextRecord> categoryGenerator,
                                                             @NotNull RecordIdGenerator<TextRecord> recordIdGenerator,
                                                             @NotNull Supplier<String>... textSuppliers) {
        Objects.requireNonNull(categoryGenerator);
        Objects.requireNonNull(recordIdGenerator);
        Objects.requireNonNull(textSuppliers);
        return textRecordOfListFunction(categoryGenerator, recordIdGenerator,
                (interimResult) -> Arrays.stream(textSuppliers)
                                         .map(Supplier::get)
                                         .toList());
    }

    static RecordGenerator<TextRecord> textRecordOfSupplierStreamFunction(@NotNull CategoryGenerator<TextRecord> categoryGenerator,
                                                                          @NotNull RecordIdGenerator<TextRecord> recordIdGenerator,
                                                                          @NotNull Function<GeneratorInterimResult<TextRecord>, Stream<Supplier<String>>> textFunction) {
        Objects.requireNonNull(categoryGenerator);
        Objects.requireNonNull(recordIdGenerator);
        Objects.requireNonNull(textFunction);
        return textRecordOfListFunction(categoryGenerator, recordIdGenerator,
                (interimResult) -> textFunction.apply(interimResult)
                                               .map(Supplier::get)
                                               .toList());
    }

    static RecordGenerator<TextRecord> textRecordOfListFunction(@NotNull CategoryGenerator<TextRecord> categoryGenerator,
                                                                @NotNull RecordIdGenerator<TextRecord> recordIdGenerator,
                                                                @NotNull Function<GeneratorInterimResult<TextRecord>, SequencedCollection<String>> textFunction) {
        Objects.requireNonNull(categoryGenerator);
        Objects.requireNonNull(recordIdGenerator);
        Objects.requireNonNull(textFunction);
        return (GeneratorContext<TextRecord> context) -> {
            String category = categoryGenerator.generateCategory(context);
            Long recordId = recordIdGenerator.generateRecordId(context);
            GeneratorInterimResult<TextRecord> interimResult = new GeneratorInterimResult<>(
                    context, category, recordId, null);
            SequencedCollection<String> texts = textFunction.apply(interimResult);

            return TextRecords.ofNullable(category, recordId, texts);
        };
    }

    @SuppressWarnings("ReturnOfNull")
    static RecordGenerator<TextRecord> textRecordOfFunctions(@NotNull CategoryGenerator<TextRecord> categoryGenerator,
                                                             @NotNull RecordIdGenerator<TextRecord> recordIdGenerator,
                                                             @NotNull List<Function<GeneratorInterimResult<TextRecord>, String>> textFunctions) {
        Objects.requireNonNull(categoryGenerator);
        Objects.requireNonNull(recordIdGenerator);
        Objects.requireNonNull(textFunctions);
        return (GeneratorContext<TextRecord> context) -> {
            String category = categoryGenerator.generateCategory(context);
            Long recordId = recordIdGenerator.generateRecordId(context);
            List<String> texts = new ArrayList<>(textFunctions.size());
            GeneratorInterimResult<TextRecord> interimResult = new GeneratorInterimResult<>(
                    context, category, recordId, index -> index < texts.size() ? texts.get(index) : null);
            textFunctions.stream()
                         .map(textFunction -> textFunction.apply(interimResult))
                         .forEachOrdered(texts::add);

            return TextRecords.ofNullable(category, recordId, texts);
        };
    }

    @NotNull T generate(@NotNull GeneratorContext<T> context);

}
