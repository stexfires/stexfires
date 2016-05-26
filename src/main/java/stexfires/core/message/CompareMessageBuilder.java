package stexfires.core.message;

import stexfires.core.Field;
import stexfires.core.Record;

import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class CompareMessageBuilder {

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

    public synchronized RecordMessage<Record> build() {
        boolean buildClassName = className;
        boolean buildCategory = category;
        String buildCategoryOther = categoryOther;
        boolean buildRecordId = recordId;
        boolean buildSize = size;
        boolean buildValues = values;
        SortedSet<Integer> buildValueIndex = new TreeSet<>(valueIndex);

        return (Record record) -> {
            StringBuilder b = new StringBuilder();
            if (buildClassName) {
                b.append("className[");
                b.append(record.getClass().getName());
                b.append("]");
            }
            if (buildCategory) {
                b.append("category[");
                if (record.getCategoryOrElse(buildCategoryOther) != null) {
                    b.append(record.getCategoryOrElse(buildCategoryOther));
                }
                b.append("]");
            }
            if (buildRecordId) {
                b.append("recordId[");
                b.append(record.getRecordId());
                b.append("]");
            }
            if (buildSize) {
                b.append("size[");
                b.append(record.size());
                b.append("]");
            }
            if (buildValues) {
                b.append("values[");
                b.append(record.streamOfFields().map(field -> {
                            if (field.valueIsNull()) {
                                return "[" + field.getIndex() + "]";
                            } else {
                                return "[" + field.getIndex() + ",'" + field.getValue() + "']";
                            }
                        }
                ).collect(Collectors.joining(",")));
                b.append("]");
            } else if (!buildValueIndex.isEmpty()) {
                b.append("values[");
                b.append(buildValueIndex.stream().map(index -> {
                            Field field = record.getFieldAt(index);
                            if (field == null || field.valueIsNull()) {
                                return "[" + index + "]";
                            } else {
                                return "[" + field.getIndex() + ",'" + field.getValue() + "']";
                            }
                        }
                ).collect(Collectors.joining(",")));
                b.append("]");
            }
            return b.toString();
        };
    }

}
