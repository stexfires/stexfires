package stexfires.data;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * @since 0.1
 */
public final class DataTypes {

    private DataTypes() {
    }

    public static DataType<Boolean> booleanDataType(boolean defaultValue,
                                                    @NotNull String trueValue,
                                                    @NotNull String falseValue) {
        Objects.requireNonNull(trueValue);
        Objects.requireNonNull(falseValue);
        return booleanDataType(defaultValue, trueValue, falseValue, () -> defaultValue);
    }

    public static DataType<Boolean> booleanDataType(boolean defaultValue,
                                                    @NotNull String trueValue,
                                                    @NotNull String falseValue,
                                                    @NotNull Supplier<Boolean> supplier) {
        Objects.requireNonNull(trueValue);
        Objects.requireNonNull(falseValue);
        Objects.requireNonNull(supplier);
        return DataType.of(
                Boolean.class,
                defaultValue,
                BooleanDataTypeFormatter.of(trueValue, falseValue, defaultValue ? trueValue : falseValue),
                BooleanDataTypeParser.of(trueValue, falseValue, defaultValue, defaultValue),
                supplier);
    }

    public static DataType<Integer> integerDataType(int defaultValue,
                                                    @NotNull Locale locale) {
        Objects.requireNonNull(locale);
        return integerDataType(defaultValue, NumberFormat.getIntegerInstance(locale), () -> defaultValue);
    }

    public static DataType<Integer> integerDataType(int defaultValue,
                                                    @NotNull Locale locale,
                                                    @NotNull Supplier<Integer> supplier) {
        Objects.requireNonNull(locale);
        Objects.requireNonNull(supplier);
        return integerDataType(defaultValue, NumberFormat.getIntegerInstance(locale), supplier);
    }

    public static DataType<Integer> integerDataType(int defaultValue,
                                                    @NotNull NumberFormat integerFormat) {
        Objects.requireNonNull(integerFormat);
        return integerDataType(defaultValue, integerFormat, () -> defaultValue);
    }

    public static DataType<Integer> integerDataType(int defaultValue,
                                                    @NotNull NumberFormat integerFormat,
                                                    @NotNull Supplier<Integer> supplier) {
        Objects.requireNonNull(integerFormat);
        Objects.requireNonNull(supplier);

        String nullSourceString = integerFormat.format(defaultValue);
        return DataType.of(
                Integer.class,
                defaultValue,
                new NumberDataTypeFormatter<>(
                        integerFormat,
                        () -> nullSourceString),
                new NumberDataTypeParser<>(
                        integerFormat,
                        NumberDataTypeParser::toInteger,
                        () -> defaultValue,
                        () -> defaultValue),
                supplier);
    }

    public static DataType<String> stringDataType(@NotNull String defaultValue,
                                                  @NotNull StringDataTypeFormatter formatter,
                                                  @NotNull StringDataTypeParser parser,
                                                  @NotNull Supplier<String> supplier) {
        Objects.requireNonNull(defaultValue);
        Objects.requireNonNull(formatter);
        Objects.requireNonNull(parser);
        Objects.requireNonNull(supplier);
        return DataType.of(
                String.class,
                defaultValue,
                formatter,
                parser,
                supplier);
    }

    public static DataType<String> stringDataType(@NotNull String defaultValue,
                                                  @NotNull UnaryOperator<String> idempotentOperator,
                                                  @NotNull Supplier<String> supplier) {
        Objects.requireNonNull(defaultValue);
        Objects.requireNonNull(idempotentOperator);
        Objects.requireNonNull(supplier);

        if (!defaultValue.equals(idempotentOperator.apply(defaultValue))
                || !defaultValue.equals(idempotentOperator.apply(idempotentOperator.apply(defaultValue)))) {
            throw new IllegalArgumentException("idempotentOperator must be idempotent and defaultValue must be valid");
        }

        var formatter = new StringDataTypeFormatter(idempotentOperator, () -> defaultValue);
        var parser = new StringDataTypeParser(
                (s) -> s.equals(idempotentOperator.apply(s)),
                null,
                () -> defaultValue,
                () -> defaultValue);
        Supplier<String> formatterSupplier = () -> formatter.format(supplier.get());

        return DataType.of(
                String.class,
                defaultValue,
                formatter,
                parser,
                formatterSupplier);
    }

    public static DataType<String> stringDataType(@NotNull Supplier<String> supplier) {
        Objects.requireNonNull(supplier);
        return stringDataType(supplier.get(), supplier);
    }

    public static DataType<String> stringDataType(@NotNull String defaultValue,
                                                  @NotNull Supplier<String> supplier) {
        Objects.requireNonNull(defaultValue);
        Objects.requireNonNull(supplier);

        var formatter = new StringDataTypeFormatter(null,
                () -> defaultValue);
        var parser = new StringDataTypeParser(null,
                null,
                () -> defaultValue,
                () -> defaultValue);

        return DataType.of(
                String.class,
                defaultValue,
                formatter,
                parser,
                supplier);
    }

}
