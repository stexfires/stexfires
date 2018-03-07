package stexfires.core.filter;

import stexfires.core.Record;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ClassFilter<T extends Record> implements RecordFilter<T> {

    private final Predicate<Class<? extends Record>> classPredicate;

    public ClassFilter(Predicate<Class<? extends Record>> classPredicate) {
        Objects.requireNonNull(classPredicate);
        this.classPredicate = classPredicate;
    }

    public static <T extends Record> ClassFilter<T> equalTo(Class<? extends Record> compareClass) {
        return new ClassFilter<>(Predicate.isEqual(compareClass));
    }

    public static <T extends Record> ClassFilter<T> containedIn(Collection<Class<? extends Record>> classes) {
        return new ClassFilter<>(classes::contains);
    }

    @Override
    public final boolean isValid(T record) {
        return classPredicate.test(record.getClass());
    }

}
