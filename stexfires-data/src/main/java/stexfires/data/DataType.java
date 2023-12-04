package stexfires.data;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;
import stexfires.record.generator.GeneratorInterimResult;
import stexfires.record.mapper.field.FieldTextMapper;
import stexfires.record.mapper.field.StringOperationFieldTextMapper;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

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

    public UnaryOperator<String> textModifier(@NotNull UnaryOperator<T> typeOperator) {
        Objects.requireNonNull(typeOperator);
        return (String s) -> format(typeOperator.apply(parse(s)));
    }

    public FieldTextMapper fieldTextMapper(@NotNull UnaryOperator<T> typeOperator) {
        return new StringOperationFieldTextMapper(textModifier(typeOperator));
    }

    public UnaryOperator<String> textModifier(@NotNull DataType<T> targetDataType) {
        Objects.requireNonNull(targetDataType);
        return (String s) -> targetDataType.format(parse(s));
    }

    public FieldTextMapper fieldTextMapper(@NotNull DataType<T> targetDataType) {
        return new StringOperationFieldTextMapper(textModifier(targetDataType));
    }

    public UnaryOperator<String> textModifier(@NotNull DataType<T> targetDataType,
                                              @NotNull UnaryOperator<T> typeOperator) {
        Objects.requireNonNull(targetDataType);
        Objects.requireNonNull(typeOperator);
        return (String s) -> targetDataType.format(typeOperator.apply(parse(s)));
    }

    public FieldTextMapper fieldTextMapper(@NotNull DataType<T> targetDataType,
                                           @NotNull UnaryOperator<T> typeOperator) {
        return new StringOperationFieldTextMapper(textModifier(targetDataType, typeOperator));
    }

    public <R> UnaryOperator<String> textModifierDifferentClass(@NotNull DataType<R> targetDataType,
                                                                @NotNull Function<T, R> convertFunction) {
        Objects.requireNonNull(targetDataType);
        Objects.requireNonNull(convertFunction);
        return (String s) -> targetDataType.format(convertFunction.apply(parse(s)));
    }

    public <R> FieldTextMapper fieldTextMapperDifferentClass(@NotNull DataType<R> targetDataType,
                                                             @NotNull Function<T, R> convertFunction) {
        return new StringOperationFieldTextMapper(textModifierDifferentClass(targetDataType, convertFunction));
    }

    public <R> UnaryOperator<String> textModifierDifferentClass(@NotNull DataType<R> targetDataType,
                                                                @NotNull Function<T, R> convertFunction,
                                                                @NotNull UnaryOperator<R> targetOperator) {
        Objects.requireNonNull(targetDataType);
        Objects.requireNonNull(convertFunction);
        Objects.requireNonNull(targetOperator);
        return (String s) -> targetDataType.format(targetOperator.apply(convertFunction.apply(parse(s))));
    }

    public <R> FieldTextMapper fieldTextMapperDifferentClass(@NotNull DataType<R> targetDataType,
                                                             @NotNull Function<T, R> convertFunction,
                                                             @NotNull UnaryOperator<R> targetOperator) {
        return new StringOperationFieldTextMapper(textModifierDifferentClass(targetDataType, convertFunction, targetOperator));
    }

    public <R> UnaryOperator<String> textModifierDifferentClass(@NotNull DataType<R> targetDataType,
                                                                @NotNull UnaryOperator<T> sourceOperator,
                                                                @NotNull Function<T, R> convertFunction,
                                                                @NotNull UnaryOperator<R> targetOperator) {
        Objects.requireNonNull(targetDataType);
        Objects.requireNonNull(sourceOperator);
        Objects.requireNonNull(convertFunction);
        Objects.requireNonNull(targetOperator);
        return (String s) -> targetDataType.format(targetOperator.apply(convertFunction.apply(sourceOperator.apply(parse(s)))));
    }

    public <R> FieldTextMapper fieldTextMapperDifferentClass(@NotNull DataType<R> targetDataType,
                                                             @NotNull UnaryOperator<T> sourceOperator,
                                                             @NotNull Function<T, R> convertFunction,
                                                             @NotNull UnaryOperator<R> targetOperator) {
        return new StringOperationFieldTextMapper(textModifierDifferentClass(targetDataType, sourceOperator, convertFunction, targetOperator));
    }

    public DataType<T> withSupplier(@NotNull Supplier<T> newSupplier) {
        Objects.requireNonNull(newSupplier);
        return new DataType<>(typeClass, defaultValue, formatter, parser, newSupplier);
    }

}
