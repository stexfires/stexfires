package stexfires.core.filter;

import stexfires.core.Record;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class CategoriesFilter<T extends Record> implements RecordFilter<T> {

    protected final Collection<String> categories;
    protected final String orElseCategory;

    public CategoriesFilter(Collection<String> categories, String orElseCategory) {
        Objects.requireNonNull(categories);
        this.categories = Collections.synchronizedSet(new HashSet<>(categories));
        this.orElseCategory = orElseCategory;
    }

    @Override
    public boolean isValid(T record) {
        return categories.contains(record.getCategoryOrElse(orElseCategory));
    }

}
