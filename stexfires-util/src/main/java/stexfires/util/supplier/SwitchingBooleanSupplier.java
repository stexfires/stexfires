package stexfires.util.supplier;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.IntPredicate;
import java.util.function.Supplier;

/**
 * A SwitchingBooleanSupplier supplies endless {@link java.lang.Boolean} values
 * by switching a given initial value with a given predicate and an increasing index.
 * <p>
 * The value is switched if the predicate returns true for the current index.
 * If the current index is {@link Integer#MAX_VALUE} it is reset to the startIndex
 * otherwise it is increased by 1.
 * <p>
 * The {@link SwitchingBooleanSupplier#get()} method is {@code synchronized}.
 *
 * @see java.util.function.Supplier
 * @see java.util.function.BooleanSupplier
 * @see java.lang.Boolean
 * @see java.util.function.IntPredicate
 * @since 0.1
 */
public final class SwitchingBooleanSupplier implements Supplier<Boolean> {

    public static final int DEFAULT_START_INDEX = 0;

    private final int startIndex;
    private final IntPredicate switchPredicate;

    private Boolean currentValue;
    private int currentIndex;

    public SwitchingBooleanSupplier(Boolean initialBooleanValue,
                                    int startIndex,
                                    IntPredicate switchPredicate) {
        Objects.requireNonNull(initialBooleanValue);
        Objects.requireNonNull(switchPredicate);
        this.startIndex = startIndex;
        this.switchPredicate = switchPredicate;
        currentValue = initialBooleanValue;
        currentIndex = startIndex;
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
        // test current index and switch current value if switchPredicate is true
        if (switchPredicate.test(currentIndex)) {
            currentValue = currentValue ? Boolean.FALSE : Boolean.TRUE;
        }
        // increment index or reset to startIndex
        if (currentIndex == Integer.MAX_VALUE) {
            currentIndex = startIndex;
        } else {
            currentIndex++;
        }
        return currentValue;
    }

}
