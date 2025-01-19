package stexfires.record.generator;

import org.jspecify.annotations.Nullable;
import stexfires.record.*;
import stexfires.record.impl.KeyValueCommentFieldsRecord;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

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
@SuppressWarnings("ReturnOfNull")
@FunctionalInterface
public interface RecordGenerator<T extends TextRecord> {

    int KEY_VALUE_RECORD_INDEX_KEY = 0;
    int KEY_VALUE_COMMENT_RECORD_INDEX_KEY = 0;
    int KEY_VALUE_COMMENT_RECORD_INDEX_VALUE = 1;

    static RecordGenerator<ValueRecord> valueRecord(CategoryGenerator<ValueRecord> categoryGenerator,
                                                    RecordIdGenerator<ValueRecord> recordIdGenerator,
                                                    Function<GeneratorInterimResult<ValueRecord>, @Nullable String> valueFunction) {
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

    static RecordGenerator<KeyValueRecord> keyValueRecord(CategoryGenerator<KeyValueRecord> categoryGenerator,
                                                          RecordIdGenerator<KeyValueRecord> recordIdGenerator,
                                                          Function<GeneratorInterimResult<KeyValueRecord>, String> keyFunction,
                                                          Function<GeneratorInterimResult<KeyValueRecord>, @Nullable String> valueFunction) {
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

    static RecordGenerator<KeyValueCommentRecord> keyValueCommentRecord(CategoryGenerator<KeyValueCommentRecord> categoryGenerator,
                                                                        RecordIdGenerator<KeyValueCommentRecord> recordIdGenerator,
                                                                        Function<GeneratorInterimResult<KeyValueCommentRecord>, String> keyFunction,
                                                                        Function<GeneratorInterimResult<KeyValueCommentRecord>, @Nullable String> valueFunction,
                                                                        Function<GeneratorInterimResult<KeyValueCommentRecord>, @Nullable String> commentFunction) {
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
    static RecordGenerator<TextRecord> textRecordOfSuppliers(CategoryGenerator<TextRecord> categoryGenerator,
                                                             RecordIdGenerator<TextRecord> recordIdGenerator,
                                                             Supplier<@Nullable String>... textSuppliers) {
        Objects.requireNonNull(categoryGenerator);
        Objects.requireNonNull(recordIdGenerator);
        Objects.requireNonNull(textSuppliers);
        return textRecordOfListFunction(categoryGenerator, recordIdGenerator,
                (interimResult) -> Arrays.stream(textSuppliers)
                                         .map(Supplier::get)
                                         .toList());
    }

    static RecordGenerator<TextRecord> textRecordOfSupplierStreamFunction(CategoryGenerator<TextRecord> categoryGenerator,
                                                                          RecordIdGenerator<TextRecord> recordIdGenerator,
                                                                          Function<GeneratorInterimResult<TextRecord>, Stream<Supplier<@Nullable String>>> textFunction) {
        Objects.requireNonNull(categoryGenerator);
        Objects.requireNonNull(recordIdGenerator);
        Objects.requireNonNull(textFunction);
        return textRecordOfListFunction(categoryGenerator, recordIdGenerator,
                (interimResult) -> textFunction.apply(interimResult)
                                               .map(Supplier::get)
                                               .toList());
    }

    static RecordGenerator<TextRecord> textRecordOfListFunction(CategoryGenerator<TextRecord> categoryGenerator,
                                                                RecordIdGenerator<TextRecord> recordIdGenerator,
                                                                Function<GeneratorInterimResult<TextRecord>, SequencedCollection<@Nullable String>> textFunction) {
        Objects.requireNonNull(categoryGenerator);
        Objects.requireNonNull(recordIdGenerator);
        Objects.requireNonNull(textFunction);
        return (GeneratorContext<TextRecord> context) -> {
            String category = categoryGenerator.generateCategory(context);
            Long recordId = recordIdGenerator.generateRecordId(context);
            GeneratorInterimResult<TextRecord> interimResult = new GeneratorInterimResult<>(
                    context, category, recordId, null);
            SequencedCollection<@Nullable String> texts = textFunction.apply(interimResult);
            return TextRecords.ofNullable(category, recordId, texts);
        };
    }

    static RecordGenerator<TextRecord> textRecordOfFunctions(CategoryGenerator<TextRecord> categoryGenerator,
                                                             RecordIdGenerator<TextRecord> recordIdGenerator,
                                                             List<Function<GeneratorInterimResult<TextRecord>, @Nullable String>> textFunctions) {
        Objects.requireNonNull(categoryGenerator);
        Objects.requireNonNull(recordIdGenerator);
        Objects.requireNonNull(textFunctions);
        return (GeneratorContext<TextRecord> context) -> {
            String category = categoryGenerator.generateCategory(context);
            Long recordId = recordIdGenerator.generateRecordId(context);
            List<@Nullable String> texts = new ArrayList<>(textFunctions.size());
            GeneratorInterimResult<TextRecord> interimResult = new GeneratorInterimResult<>(
                    context, category, recordId, index -> (index < texts.size()) ? texts.get(index) : null);
            textFunctions.stream()
                         .map(textFunction -> textFunction.apply(interimResult))
                         .forEachOrdered(texts::add);

            return TextRecords.ofNullable(category, recordId, texts);
        };
    }

    T generate(GeneratorContext<T> context);

}
