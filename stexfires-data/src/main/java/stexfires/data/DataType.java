package stexfires.data;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;
import stexfires.record.generator.GeneratorInterimResult;
import stexfires.record.mapper.field.FieldTextMapper;
import stexfires.record.mapper.field.StringOperationFieldTextMapper;

import java.util.*;
import java.util.function.*;

/**
 * @since 0.1
 */
public record DataType<T>(
        Class<T> typeClass,
        T defaultValue,
        DataTypeFormatter<T> formatter,
        DataTypeParser<T> parser,
        Supplier<T> supplier) {

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

    public static <T> DataType<T> of(Class<T> typeClass,
                                     T defaultValue,
                                     DataTypeFormatter<T> formatter,
                                     DataTypeParser<T> parser,
                                     Supplier<T> supplier) {
        Objects.requireNonNull(typeClass);
        Objects.requireNonNull(defaultValue);
        Objects.requireNonNull(formatter);
        Objects.requireNonNull(parser);
        Objects.requireNonNull(supplier);
        return new DataType<>(typeClass, defaultValue, formatter, parser, supplier);
    }

    public static <T> DataType<T> of(Class<T> typeClass,
                                     T defaultValue,
                                     DataTypeFormatter<T> formatter,
                                     DataTypeParser<T> parser) {
        Objects.requireNonNull(typeClass);
        Objects.requireNonNull(defaultValue);
        Objects.requireNonNull(formatter);
        Objects.requireNonNull(parser);
        return new DataType<>(typeClass, defaultValue, formatter, parser, () -> defaultValue);
    }

    public static <T> DataType<T> of(Class<T> typeClass,
                                     T defaultValue,
                                     DataTypeFormatter<T> formatter,
                                     Supplier<T> supplier) {
        Objects.requireNonNull(typeClass);
        Objects.requireNonNull(defaultValue);
        Objects.requireNonNull(formatter);
        Objects.requireNonNull(supplier);
        return new DataType<>(typeClass, defaultValue, formatter, (String source) -> defaultValue, supplier);
    }

    public static <T> DataType<T> of(Class<T> typeClass,
                                     T defaultValue,
                                     DataTypeFormatter<T> formatter) {
        Objects.requireNonNull(typeClass);
        Objects.requireNonNull(defaultValue);
        Objects.requireNonNull(formatter);
        return new DataType<>(typeClass, defaultValue, formatter, (String source) -> defaultValue, () -> defaultValue);
    }

    public T cast(Object value) throws ClassCastException {
        Objects.requireNonNull(value);
        return typeClass.cast(value);
    }

    public boolean isInstance(Object value) {
        Objects.requireNonNull(value);
        return typeClass.isInstance(value);
    }

    public @Nullable String defaultTextValue() throws DataTypeConverterException {
        return formatter.format(defaultValue);
    }

    public @Nullable String format(@Nullable T dataValue) throws DataTypeConverterException {
        return formatter.format(dataValue);
    }

    public @Nullable T parse(@Nullable String textValue) throws DataTypeConverterException {
        return parser.parse(textValue);
    }

    public Supplier<@Nullable String> newTextSupplier() {
        return () -> formatter.format(supplier.get());
    }

    public <TR extends TextRecord> Function<GeneratorInterimResult<TR>, @Nullable String> newTextGeneratorFunction() {
        return (GeneratorInterimResult<TR> interimResult) -> formatter.format(supplier.get());
    }

    public <TR extends TextRecord> Function<GeneratorInterimResult<TR>, @Nullable String> newTextGeneratorFunction(Function<GeneratorInterimResult<TR>, T> function) {
        Objects.requireNonNull(function);
        return (GeneratorInterimResult<TR> interimResult) -> formatter.format(function.apply(interimResult));
    }

    public Function<@Nullable String, @Nullable T> parserAsFunction() {
        return parser.asFunction();
    }

    public UnaryOperator<@Nullable String> textModifier(UnaryOperator<@Nullable T> typeOperator) {
        Objects.requireNonNull(typeOperator);
        return (@Nullable String s) -> format(typeOperator.apply(parse(s)));
    }

    public FieldTextMapper fieldTextMapper(UnaryOperator<T> typeOperator) {
        return new StringOperationFieldTextMapper(textModifier(typeOperator));
    }

    public UnaryOperator<@Nullable String> textModifier(DataType<T> targetDataType) {
        Objects.requireNonNull(targetDataType);
        return (@Nullable String s) -> targetDataType.format(parse(s));
    }

    public FieldTextMapper fieldTextMapper(DataType<T> targetDataType) {
        return new StringOperationFieldTextMapper(textModifier(targetDataType));
    }

    public UnaryOperator<@Nullable String> textModifier(DataType<T> targetDataType,
                                                        UnaryOperator<@Nullable T> typeOperator) {
        Objects.requireNonNull(targetDataType);
        Objects.requireNonNull(typeOperator);
        return (@Nullable String s) -> targetDataType.format(typeOperator.apply(parse(s)));
    }

    public FieldTextMapper fieldTextMapper(DataType<T> targetDataType,
                                           UnaryOperator<T> typeOperator) {
        return new StringOperationFieldTextMapper(textModifier(targetDataType, typeOperator));
    }

    public <R> UnaryOperator<@Nullable String> textModifierDifferentClass(DataType<R> targetDataType,
                                                                          Function<@Nullable T, @Nullable R> convertFunction) {
        Objects.requireNonNull(targetDataType);
        Objects.requireNonNull(convertFunction);
        return (@Nullable String s) -> targetDataType.format(convertFunction.apply(parse(s)));
    }

    public <R> FieldTextMapper fieldTextMapperDifferentClass(DataType<R> targetDataType,
                                                             Function<T, R> convertFunction) {
        return new StringOperationFieldTextMapper(textModifierDifferentClass(targetDataType, convertFunction));
    }

    public <R> UnaryOperator<@Nullable String> textModifierDifferentClass(DataType<R> targetDataType,
                                                                          Function<@Nullable T, @Nullable R> convertFunction,
                                                                          UnaryOperator<@Nullable R> targetOperator) {
        Objects.requireNonNull(targetDataType);
        Objects.requireNonNull(convertFunction);
        Objects.requireNonNull(targetOperator);
        return (@Nullable String s) -> targetDataType.format(targetOperator.apply(convertFunction.apply(parse(s))));
    }

    public <R> FieldTextMapper fieldTextMapperDifferentClass(DataType<R> targetDataType,
                                                             Function<T, R> convertFunction,
                                                             UnaryOperator<R> targetOperator) {
        return new StringOperationFieldTextMapper(textModifierDifferentClass(targetDataType, convertFunction, targetOperator));
    }

    public <R> UnaryOperator<@Nullable String> textModifierDifferentClass(DataType<R> targetDataType,
                                                                          UnaryOperator<@Nullable T> sourceOperator,
                                                                          Function<@Nullable T, @Nullable R> convertFunction,
                                                                          UnaryOperator<@Nullable R> targetOperator) {
        Objects.requireNonNull(targetDataType);
        Objects.requireNonNull(sourceOperator);
        Objects.requireNonNull(convertFunction);
        Objects.requireNonNull(targetOperator);
        return (@Nullable String s) -> targetDataType.format(targetOperator.apply(convertFunction.apply(sourceOperator.apply(parse(s)))));
    }

    public <R> FieldTextMapper fieldTextMapperDifferentClass(DataType<R> targetDataType,
                                                             UnaryOperator<T> sourceOperator,
                                                             Function<T, R> convertFunction,
                                                             UnaryOperator<R> targetOperator) {
        return new StringOperationFieldTextMapper(textModifierDifferentClass(targetDataType, sourceOperator, convertFunction, targetOperator));
    }

    public DataType<T> withSupplier(Supplier<T> newSupplier) {
        Objects.requireNonNull(newSupplier);
        return new DataType<>(typeClass, defaultValue, formatter, parser, newSupplier);
    }

}
