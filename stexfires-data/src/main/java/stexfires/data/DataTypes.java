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

    public static DataType<Boolean> booleanDataType(@NotNull String trueValue,
                                                    @NotNull String falseValue,
                                                    boolean defaultOrNullOrEmptyValue,
                                                    @NotNull Supplier<Boolean> supplier) {
        Objects.requireNonNull(trueValue);
        Objects.requireNonNull(falseValue);
        Objects.requireNonNull(supplier);
        return DataType.of(
                Boolean.class,
                defaultOrNullOrEmptyValue,
                BooleanDataTypeFormatter.of(trueValue, falseValue, defaultOrNullOrEmptyValue ? trueValue : falseValue),
                BooleanDataTypeParser.of(trueValue, falseValue, defaultOrNullOrEmptyValue, defaultOrNullOrEmptyValue),
                supplier);
    }

    public static DataType<Integer> integerDataType(@NotNull Locale locale,
                                                    int defaultOrNullOrEmptyValue,
                                                    @NotNull Supplier<Integer> supplier) {
        Objects.requireNonNull(locale);
        Objects.requireNonNull(supplier);
        return integerDataType(NumberFormat.getIntegerInstance(locale), defaultOrNullOrEmptyValue, supplier);
    }

    public static DataType<Integer> integerDataType(@NotNull NumberFormat integerFormat,
                                                    int defaultOrNullOrEmptyValue,
                                                    @NotNull Supplier<Integer> supplier) {
        Objects.requireNonNull(integerFormat);
        Objects.requireNonNull(supplier);

        String nullSourceString = integerFormat.format(defaultOrNullOrEmptyValue);
        return DataType.of(
                Integer.class,
                defaultOrNullOrEmptyValue,
                new NumberDataTypeFormatter<>(
                        integerFormat,
                        () -> nullSourceString),
                new NumberDataTypeParser<>(
                        integerFormat,
                        NumberDataTypeParser::toInteger,
                        () -> defaultOrNullOrEmptyValue,
                        () -> defaultOrNullOrEmptyValue),
                supplier);
    }

    public static DataType<String> stringDataType(@NotNull String defaultOrNullOrEmptyValue,
                                                  @NotNull StringDataTypeFormatter formatter,
                                                  @NotNull StringDataTypeParser parser,
                                                  @NotNull Supplier<String> supplier) {
        Objects.requireNonNull(defaultOrNullOrEmptyValue);
        Objects.requireNonNull(formatter);
        Objects.requireNonNull(parser);
        Objects.requireNonNull(supplier);
        return DataType.of(
                String.class,
                defaultOrNullOrEmptyValue,
                formatter,
                parser,
                supplier);
    }

    public static DataType<String> stringDataType(@NotNull UnaryOperator<String> idempotentOperator,
                                                  @NotNull String defaultOrNullOrEmptyValue,
                                                  @NotNull Supplier<String> supplier) {
        Objects.requireNonNull(idempotentOperator);
        Objects.requireNonNull(defaultOrNullOrEmptyValue);
        Objects.requireNonNull(supplier);

        if (!defaultOrNullOrEmptyValue.equals(idempotentOperator.apply(defaultOrNullOrEmptyValue))
                || !defaultOrNullOrEmptyValue.equals(idempotentOperator.apply(idempotentOperator.apply(defaultOrNullOrEmptyValue)))) {
            throw new IllegalArgumentException("idempotentOperator must be idempotent and defaultOrNullOrEmptyValue must be valid");
        }

        var formatter = new StringDataTypeFormatter(idempotentOperator, () -> defaultOrNullOrEmptyValue);
        var parser = new StringDataTypeParser((s) -> s.equals(idempotentOperator.apply(s)),
                null,
                () -> defaultOrNullOrEmptyValue,
                () -> defaultOrNullOrEmptyValue);
        Supplier<String> formatterSupplier = () -> formatter.format(supplier.get());

        return DataType.of(
                String.class,
                defaultOrNullOrEmptyValue,
                formatter,
                parser,
                formatterSupplier);
    }

    public static DataType<String> stringDataType(@NotNull Supplier<String> supplier) {
        Objects.requireNonNull(supplier);
        return stringDataType(supplier.get(), supplier);
    }

    public static DataType<String> stringDataType(@NotNull String defaultOrNullOrEmptyValue,
                                                  @NotNull Supplier<String> supplier) {
        Objects.requireNonNull(defaultOrNullOrEmptyValue);
        Objects.requireNonNull(supplier);

        var formatter = new StringDataTypeFormatter(null,
                () -> defaultOrNullOrEmptyValue);
        var parser = new StringDataTypeParser(null,
                null,
                () -> defaultOrNullOrEmptyValue,
                () -> defaultOrNullOrEmptyValue);

        return DataType.of(
                String.class,
                defaultOrNullOrEmptyValue,
                formatter,
                parser,
                supplier);
    }

}
