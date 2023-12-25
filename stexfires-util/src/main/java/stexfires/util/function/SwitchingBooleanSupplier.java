package stexfires.util.function;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.IntPredicate;
import java.util.function.Supplier;

/**
 * @since 0.1
 */
public final class SwitchingBooleanSupplier implements Supplier<Boolean> {

    public static final int DEFAULT_START_INDEX = 0;

    private final IntPredicate switchPredicate;

    private Boolean currentValue;
    private int currentIndex;

    public SwitchingBooleanSupplier(Boolean initialBooleanValue,
                                    int startIndex,
                                    IntPredicate switchPredicate) {
        Objects.requireNonNull(initialBooleanValue);
        Objects.requireNonNull(switchPredicate);
        currentValue = initialBooleanValue;
        currentIndex = startIndex;
        this.switchPredicate = switchPredicate;
    }

    public static SwitchingBooleanSupplier onlyOnce(Boolean initialBooleanValue, int index) {
        return new SwitchingBooleanSupplier(initialBooleanValue, DEFAULT_START_INDEX, i -> i == index);
    }

    public static SwitchingBooleanSupplier everyTime(Boolean initialBooleanValue) {
        return new SwitchingBooleanSupplier(initialBooleanValue, DEFAULT_START_INDEX, i -> true);
    }

    public BooleanSupplier asPrimitiveBooleanSupplier() {
        return this::get;
    }

    @Override
    public synchronized Boolean get() {
        if (switchPredicate.test(currentIndex)) {
            currentValue = currentValue ? Boolean.FALSE : Boolean.TRUE;
        }
        currentIndex++;
        return currentValue;
    }

}
