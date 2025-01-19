package stexfires.util.supplier;

import java.util.*;
import java.util.function.*;

/**
 * A SwitchingSupplier supplies endless values by switching between two given values.
 * <p>
 * The values are switched if the predicate returns true for the current index.
 * If the current index is {@link Integer#MAX_VALUE} it is reset to the startIndex
 * otherwise it is increased by 1.
 * <p>
 * It starts with the first value, but it is possible that it is switched immediately for the start index.
 * <p>
 * The {@link SwitchingSupplier#get()} method is {@code synchronized}.
 *
 * @see java.util.function.Supplier
 * @see java.util.function.IntPredicate
 * @since 0.1
 */
public final class SwitchingSupplier<T> implements Supplier<T> {

    public static final int DEFAULT_START_INDEX = 0;

    private final T firstValue;
    private final T secondValue;
    private final int startIndex;
    private final IntPredicate switchPredicate;

    private boolean currentIsFirst;
    private T currentValue;
    private int currentIndex;

    public SwitchingSupplier(T firstValue,
                             T secondValue,
                             IntPredicate switchPredicate) {
        this(firstValue, secondValue, DEFAULT_START_INDEX, switchPredicate);
    }

    public SwitchingSupplier(T firstValue,
                             T secondValue,
                             int startIndex,
                             IntPredicate switchPredicate) {
        Objects.requireNonNull(firstValue);
        Objects.requireNonNull(secondValue);
        Objects.requireNonNull(switchPredicate);
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.startIndex = startIndex;
        this.switchPredicate = switchPredicate;

        currentIsFirst = true;
        currentValue = firstValue;
        currentIndex = startIndex;
    }

    public static <T> SwitchingSupplier<T> atIndex(T firstValue,
                                                   T secondValue,
                                                   int index) {
        return new SwitchingSupplier<>(firstValue, secondValue,
                DEFAULT_START_INDEX, i -> i == index);
    }

    public static <T> SwitchingSupplier<T> everyTime(T firstValue,
                                                     T secondValue) {
        // Switch firstValue and secondValue, because the predicate will switch them back
        return new SwitchingSupplier<>(secondValue, firstValue,
                DEFAULT_START_INDEX, i -> true);
    }

    @Override
    public synchronized T get() {
        // test current index with predicate and switch if it is true
        if (switchPredicate.test(currentIndex)) {
            if (currentIsFirst) {
                currentIsFirst = false;
                currentValue = secondValue;
            } else {
                currentIsFirst = true;
                currentValue = firstValue;
            }
        }
        // increment index or reset to startIndex
        if (currentIndex == Integer.MAX_VALUE) {
            currentIndex = startIndex;
        } else {
            currentIndex++;
        }
        return currentValue;
    }

    @Override
    public String toString() {
        return "SwitchingSupplier{" +
                "currentIsFirst=" + currentIsFirst +
                ", currentValue=" + currentValue +
                ", currentIndex=" + currentIndex +
                '}';
    }

}
