package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.util.Strings;
import stexfires.util.function.Suppliers;

import java.io.UncheckedIOException;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * @since 0.1
 */
public final class StringDataTypeParser implements DataTypeParser<String> {

    public static final Supplier<String> THROW_ERROR_FOR_NULL = null;
    public static final Supplier<String> THROW_ERROR_FOR_EMPTY = null;

    private final Predicate<String> checkPredicate;
    private final UnaryOperator<String> operatorAfterCheck;
    private final Supplier<String> nullSourceSupplier;
    private final Supplier<String> emptySourceSupplier;

    public StringDataTypeParser(@Nullable Predicate<String> checkPredicate,
                                @Nullable UnaryOperator<String> operatorAfterCheck,
                                @Nullable Supplier<String> nullSourceSupplier,
                                @Nullable Supplier<String> emptySourceSupplier) {
        this.checkPredicate = checkPredicate;
        this.operatorAfterCheck = operatorAfterCheck;
        this.nullSourceSupplier = nullSourceSupplier;
        this.emptySourceSupplier = emptySourceSupplier;
    }

    public static StringDataTypeParser identity() {
        return new StringDataTypeParser(
                null,
                null,
                Suppliers.constantNull(),
                Suppliers.constant(Strings.EMPTY));
    }

    public static StringDataTypeParser withCheck(@NotNull Predicate<String> checkPredicate) {
        Objects.requireNonNull(checkPredicate);
        return new StringDataTypeParser(checkPredicate,
                null,
                Suppliers.constantNull(),
                Suppliers.constant(Strings.EMPTY));
    }

    public static StringDataTypeParser withEqualityCheck(@NotNull UnaryOperator<String> operatorForEqualityCheck,
                                                         @Nullable UnaryOperator<String> operatorAfterCheck,
                                                         @Nullable Supplier<String> nullSourceSupplier,
                                                         @Nullable Supplier<String> emptySourceSupplier) {
        Objects.requireNonNull(operatorForEqualityCheck);
        return new StringDataTypeParser(s -> s.equals(operatorForEqualityCheck.apply(s)),
                operatorAfterCheck,
                nullSourceSupplier,
                emptySourceSupplier);
    }

    public static StringDataTypeParser withEqualityCheck(@NotNull UnaryOperator<String> operatorForEqualityCheck) {
        Objects.requireNonNull(operatorForEqualityCheck);
        return withEqualityCheck(operatorForEqualityCheck,
                null,
                Suppliers.constantNull(),
                Suppliers.constant(Strings.EMPTY));
    }

    @Override
    public @Nullable String parse(@Nullable String source) throws DataTypeConverterException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else if (source.isEmpty()) {
            return handleEmptySource(emptySourceSupplier);
        } else {
            try {
                if (checkPredicate != null && !checkPredicate.test(source)) {
                    throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Source is not formatted correctly.");
                }
                if (operatorAfterCheck != null) {
                    return operatorAfterCheck.apply(source);
                }
            } catch (IllegalArgumentException | NullPointerException | UncheckedIOException | ClassCastException |
                     IllegalStateException | IndexOutOfBoundsException | ArithmeticException e) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Cannot parse source: " + e.getClass().getName());
            }
            return source;
        }
    }

}
