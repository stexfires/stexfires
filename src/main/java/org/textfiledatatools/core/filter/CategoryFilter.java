package org.textfiledatatools.core.filter;

import org.textfiledatatools.core.Record;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class CategoryFilter implements RecordFilter<Record> {

    private final String category;

    public CategoryFilter(String category) {
        this.category = category;
    }

    @Override
    public boolean isValid(Record record) {
        return Objects.equals(record.getCategory(), category);
    }

}
