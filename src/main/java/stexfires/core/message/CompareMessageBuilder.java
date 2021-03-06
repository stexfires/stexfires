package stexfires.core.message;

import stexfires.core.TextRecord;

import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class CompareMessageBuilder {

    private static final int INITIAL_STRING_BUILDER_CAPACITY = 64;

    private final SortedSet<Integer> valueIndex;
    private boolean className;
    private boolean category;
    private String categoryOther;
    private boolean recordId;
    private boolean size;
    private boolean values;

    public CompareMessageBuilder() {
        valueIndex = new TreeSet<>();
    }

    public synchronized CompareMessageBuilder className() {
        className = true;
        return this;
    }

    public synchronized CompareMessageBuilder category() {
        category = true;
        return this;
    }

    public synchronized CompareMessageBuilder category(String other) {
        category = true;
        categoryOther = other;
        return this;
    }

    public synchronized CompareMessageBuilder recordId() {
        recordId = true;
        return this;
    }

    public synchronized CompareMessageBuilder size() {
        size = true;
        return this;
    }

    public synchronized CompareMessageBuilder values() {
        values = true;
        return this;
    }

    public synchronized CompareMessageBuilder value(Integer index) {
        Objects.requireNonNull(index);
        valueIndex.add(index);
        return this;
    }

    public synchronized RecordMessage<TextRecord> build() {
        boolean buildClassName = className;
        boolean buildCategory = category;
        String buildCategoryOther = categoryOther;
        boolean buildRecordId = recordId;
        boolean buildSize = size;
        boolean buildValues = values;
        SortedSet<Integer> buildValueIndex = new TreeSet<>(valueIndex);

        return record -> {
            StringBuilder builder = new StringBuilder(INITIAL_STRING_BUILDER_CAPACITY);
            if (buildClassName) {
                builder.append("className[");
                builder.append(record.getClass().getName());
                builder.append("]");
            }
            if (buildCategory) {
                builder.append("category[");
                if (record.getCategoryOrElse(buildCategoryOther) != null) {
                    builder.append(record.getCategoryOrElse(buildCategoryOther));
                }
                builder.append("]");
            }
            if (buildRecordId) {
                builder.append("recordId[");
                builder.append(record.getRecordId());
                builder.append("]");
            }
            if (buildSize) {
                builder.append("size[");
                builder.append(record.size());
                builder.append("]");
            }
            if (buildValues) {
                builder.append("values[");
                builder.append(record.streamOfFields().map(field -> {
                            if (field.valueIsNull()) {
                                return "[" + field.getIndex() + "]";
                            } else {
                                return "[" + field.getIndex() + ",'" + field.getValue() + "']";
                            }
                        }
                ).collect(Collectors.joining(",")));
                builder.append("]");
            } else if (!buildValueIndex.isEmpty()) {
                builder.append("values[");
                builder.append(buildValueIndex.stream().map(index -> {
                            if (!record.isValidIndex(index)) {
                                return "[" + index + " i]";
                            } else {
                                String value = record.getValueAt(index);
                                if (value == null) {
                                    return "[" + index + " n]";
                                } else {
                                    return "[" + index + ". '" + value + "']";
                                }
                            }
                        }
                ).collect(Collectors.joining(",")));
                builder.append("]");
            }
            return builder.toString();
        };
    }

}
