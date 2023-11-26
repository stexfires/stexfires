package stexfires.data;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;
import stexfires.record.generator.GeneratorInterimResult;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @since 0.1
 */
public record DataType<T>(
        @NotNull Class<T> typeClass,
        @NotNull T defaultValue,
        @NotNull DataTypeFormatter<T> formatter,
        @NotNull DataTypeParser<T> parser,
        @NotNull Supplier<T> supplier) {

    public DataType {
        Objects.requireNonNull(typeClass);
        Objects.requireNonNull(defaultValue);
        Objects.requireNonNull(formatter);
        Objects.requireNonNull(parser);
        Objects.requireNonNull(supplier);

        if (typeClass != defaultValue.getClass()) {
            throw new IllegalArgumentException("defaultValue must be of class typeClass");
        }
    }

    public static <T> DataType<T> of(@NotNull Class<T> typeClass,
                                     @NotNull T defaultValue,
                                     @NotNull DataTypeFormatter<T> formatter,
                                     @NotNull DataTypeParser<T> parser,
                                     @NotNull Supplier<T> supplier) {
        Objects.requireNonNull(typeClass);
        Objects.requireNonNull(defaultValue);
        Objects.requireNonNull(formatter);
        Objects.requireNonNull(parser);
        Objects.requireNonNull(supplier);
        return new DataType<>(typeClass, defaultValue, formatter, parser, supplier);
    }

    public static <T> DataType<T> of(@NotNull Class<T> typeClass,
                                     @NotNull T defaultValue,
                                     @NotNull DataTypeFormatter<T> formatter,
                                     @NotNull DataTypeParser<T> parser) {
        Objects.requireNonNull(typeClass);
        Objects.requireNonNull(defaultValue);
        Objects.requireNonNull(formatter);
        Objects.requireNonNull(parser);
        return new DataType<>(typeClass, defaultValue, formatter, parser, () -> defaultValue);
    }

    public static <T> DataType<T> of(@NotNull Class<T> typeClass,
                                     @NotNull T defaultValue,
                                     @NotNull DataTypeFormatter<T> formatter,
                                     @NotNull Supplier<T> supplier) {
        Objects.requireNonNull(typeClass);
        Objects.requireNonNull(defaultValue);
        Objects.requireNonNull(formatter);
        Objects.requireNonNull(supplier);
        return new DataType<>(typeClass, defaultValue, formatter, (String source) -> defaultValue, supplier);
    }

    public static <T> DataType<T> of(@NotNull Class<T> typeClass,
                                     @NotNull T defaultValue,
                                     @NotNull DataTypeFormatter<T> formatter) {
        Objects.requireNonNull(typeClass);
        Objects.requireNonNull(defaultValue);
        Objects.requireNonNull(formatter);
        return new DataType<>(typeClass, defaultValue, formatter, (String source) -> defaultValue, () -> defaultValue);
    }

    public T cast(@NotNull Object value) throws ClassCastException {
        Objects.requireNonNull(value);
        return typeClass.cast(value);
    }

    public boolean isInstance(@NotNull Object value) {
        Objects.requireNonNull(value);
        return typeClass.isInstance(value);
    }

    public String defaultTextValue() throws DataTypeConverterException {
        return formatter.format(defaultValue);
    }

    public String format(T dataValue) throws DataTypeConverterException {
        return formatter.format(dataValue);
    }

    public T parse(String textValue) throws DataTypeConverterException {
        return parser.parse(textValue);
    }

    public Supplier<String> newTextSupplier() {
        return () -> formatter.format(supplier.get());
    }

    public <TR extends TextRecord> Function<GeneratorInterimResult<TR>, String> newTextGeneratorFunction() {
        return (GeneratorInterimResult<TR> interimResult) -> formatter.format(supplier.get());
    }

    public <TR extends TextRecord> Function<GeneratorInterimResult<TR>, String> newTextGeneratorFunction(@NotNull Function<GeneratorInterimResult<TR>, T> function) {
        Objects.requireNonNull(function);
        return (GeneratorInterimResult<TR> interimResult) -> formatter.format(function.apply(interimResult));
    }

    public Function<String, T> parserAsFunction() {
        return parser.asFunction();
    }

    public DataType<T> withSupplier(@NotNull Supplier<T> newSupplier) {
        Objects.requireNonNull(newSupplier);
        return new DataType<>(typeClass, defaultValue, formatter, parser, newSupplier);
    }

}
