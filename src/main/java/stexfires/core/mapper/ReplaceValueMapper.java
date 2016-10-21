package stexfires.core.mapper;

import stexfires.core.record.ValueRecord;
import stexfires.util.StringUnaryOperatorType;

import java.util.Locale;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ReplaceValueMapper<T extends ValueRecord> implements RecordMapper<T, ValueRecord> {

    protected final Predicate<String> condition;
    protected final UnaryOperator<String> trueOperator;
    protected final UnaryOperator<String> falseOperator;

    public ReplaceValueMapper(String value) {
        this(v -> true, v -> value, v -> v);
    }

    public ReplaceValueMapper(Predicate<String> condition,
                              String value) {
        this(condition, v -> value, v -> v);
    }

    public ReplaceValueMapper(Predicate<String> condition,
                              String trueValue,
                              String falseValue) {
        this(condition, v -> trueValue, v -> falseValue);
    }

    public ReplaceValueMapper(Supplier<String> valueSupplier) {
        this(v -> true, v -> valueSupplier.get(), v -> v);
    }

    public ReplaceValueMapper(Predicate<String> condition,
                              Supplier<String> valueSupplier) {
        this(condition, v -> valueSupplier.get(), v -> v);
    }

    public ReplaceValueMapper(Predicate<String> condition,
                              Supplier<String> trueValueSupplier,
                              Supplier<String> falseValueSupplier) {
        this(condition, v -> trueValueSupplier.get(), v -> falseValueSupplier.get());
    }

    public ReplaceValueMapper(StringUnaryOperatorType operator) {
        this(v -> true, operator.stringUnaryOperator(), v -> v);
    }

    public ReplaceValueMapper(Predicate<String> condition,
                              StringUnaryOperatorType operator) {
        this(condition, operator.stringUnaryOperator(), v -> v);
    }

    public ReplaceValueMapper(Predicate<String> condition,
                              StringUnaryOperatorType trueOperator,
                              StringUnaryOperatorType falseOperator) {
        this(condition, trueOperator.stringUnaryOperator(), falseOperator.stringUnaryOperator());
    }

    public ReplaceValueMapper(StringUnaryOperatorType operator,
                              Locale locale) {
        this(v -> true, operator.stringUnaryOperator(locale), v -> v);
    }

    public ReplaceValueMapper(Predicate<String> condition,
                              StringUnaryOperatorType operator,
                              Locale locale) {
        this(condition, operator.stringUnaryOperator(locale), v -> v);
    }

    public ReplaceValueMapper(Predicate<String> condition,
                              StringUnaryOperatorType trueOperator,
                              StringUnaryOperatorType falseOperator,
                              Locale locale) {
        this(condition, trueOperator.stringUnaryOperator(locale), falseOperator.stringUnaryOperator(locale));
    }

    public ReplaceValueMapper(UnaryOperator<String> operator) {
        this(v -> true, operator, v -> v);
    }

    public ReplaceValueMapper(Predicate<String> condition,
                              UnaryOperator<String> operator) {
        this(condition, operator, v -> v);
    }

    public ReplaceValueMapper(Predicate<String> condition,
                              UnaryOperator<String> trueOperator,
                              UnaryOperator<String> falseOperator) {
        Objects.requireNonNull(condition);
        Objects.requireNonNull(trueOperator);
        Objects.requireNonNull(falseOperator);
        this.condition = condition;
        this.trueOperator = trueOperator;
        this.falseOperator = falseOperator;
    }

    @Override
    public ValueRecord map(T record) {
        String value = record.getValueOfValueField();
        return record.newValueRecord(condition.test(value) ? trueOperator.apply(value) : falseOperator.apply(value));
    }

}
