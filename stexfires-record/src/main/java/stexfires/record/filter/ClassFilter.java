package stexfires.record.filter;

import stexfires.record.TextRecord;

import java.util.*;
import java.util.function.*;

/**
 * @since 0.1
 */
public class ClassFilter<T extends TextRecord> implements RecordFilter<T> {

    private final Predicate<Class<? extends TextRecord>> classPredicate;

    public ClassFilter(Predicate<Class<? extends TextRecord>> classPredicate) {
        Objects.requireNonNull(classPredicate);
        this.classPredicate = classPredicate;
    }

    public static <T extends TextRecord> ClassFilter<T> equalTo(Class<? extends TextRecord> compareClass) {
        Objects.requireNonNull(compareClass);
        return new ClassFilter<>(Predicate.isEqual(compareClass));
    }

    public static <T extends TextRecord> ClassFilter<T> containedIn(Collection<Class<? extends TextRecord>> classes) {
        Objects.requireNonNull(classes);
        return new ClassFilter<>(classes::contains);
    }

    @Override
    public final boolean isValid(T record) {
        return classPredicate.test(record.getClass());
    }

}
