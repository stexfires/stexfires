package stexfires.data;

import java.text.NumberFormat;
import java.util.*;
import java.util.function.*;

/**
 * @since 0.1
 */
public final class DataTypes {

    private DataTypes() {
    }

    public static DataType<Boolean> booleanDataType(boolean defaultValue,
                                                    String trueValue,
                                                    String falseValue) {
        Objects.requireNonNull(trueValue);
        Objects.requireNonNull(falseValue);
        return booleanDataType(defaultValue, trueValue, falseValue, () -> defaultValue);
    }

    public static DataType<Boolean> booleanDataType(boolean defaultValue,
                                                    String trueValue,
                                                    String falseValue,
                                                    Supplier<Boolean> supplier) {
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
                                                    Locale locale) {
        Objects.requireNonNull(locale);
        return integerDataType(defaultValue, NumberFormat.getIntegerInstance(locale), () -> defaultValue);
    }

    public static DataType<Integer> integerDataType(int defaultValue,
                                                    Locale locale,
                                                    Supplier<Integer> supplier) {
        Objects.requireNonNull(locale);
        Objects.requireNonNull(supplier);
        return integerDataType(defaultValue, NumberFormat.getIntegerInstance(locale), supplier);
    }

    public static DataType<Integer> integerDataType(int defaultValue,
                                                    NumberFormat integerFormat) {
        Objects.requireNonNull(integerFormat);
        return integerDataType(defaultValue, integerFormat, () -> defaultValue);
    }

    public static DataType<Integer> integerDataType(int defaultValue,
                                                    NumberFormat integerFormat,
                                                    Supplier<Integer> supplier) {
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

    public static DataType<String> stringDataType(String defaultValue,
                                                  StringDataTypeFormatter formatter,
                                                  StringDataTypeParser parser,
                                                  Supplier<String> supplier) {
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

    public static DataType<String> stringDataType(String defaultValue,
                                                  UnaryOperator<String> idempotentOperator,
                                                  Supplier<String> supplier) {
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
        Supplier<String> formatterSupplier = () -> Objects.requireNonNull(formatter.format(supplier.get()));

        return DataType.of(
                String.class,
                defaultValue,
                formatter,
                parser,
                formatterSupplier);
    }

    public static DataType<String> stringDataType(Supplier<String> supplier) {
        Objects.requireNonNull(supplier);
        return stringDataType(supplier.get(), supplier);
    }

    public static DataType<String> stringDataType(String defaultValue,
                                                  Supplier<String> supplier) {
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
