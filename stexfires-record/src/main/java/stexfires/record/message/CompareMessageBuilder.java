package stexfires.record.message;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextField;
import stexfires.record.TextRecord;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @since 0.1
 */
public final class CompareMessageBuilder {

    private static final int INITIAL_STRING_BUILDER_CAPACITY = 64;

    private final SortedSet<Integer> textIndex;
    private boolean className;
    private boolean category;
    private @Nullable String categoryOther;
    private boolean recordId;
    private boolean size;
    private boolean texts;

    public CompareMessageBuilder() {
        textIndex = new TreeSet<>();
    }

    public synchronized CompareMessageBuilder className() {
        className = true;
        return this;
    }

    public synchronized CompareMessageBuilder category() {
        category = true;
        return this;
    }

    public synchronized CompareMessageBuilder category(@Nullable String other) {
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

    public synchronized CompareMessageBuilder texts() {
        texts = true;
        return this;
    }

    public synchronized CompareMessageBuilder textAt(int index) {
        if (index < TextField.FIRST_FIELD_INDEX) {
            throw new IllegalArgumentException("Wrong 'index'! " + index);
        }
        textIndex.add(index);
        return this;
    }

    public synchronized NotNullRecordMessage<TextRecord> build() {
        boolean buildClassName = className;
        boolean buildCategory = category;
        String buildCategoryOther = categoryOther;
        boolean buildRecordId = recordId;
        boolean buildSize = size;
        boolean buildTexts = texts;
        SortedSet<Integer> buildTextIndex = new TreeSet<>(textIndex);

        return record -> {
            StringBuilder builder = new StringBuilder(INITIAL_STRING_BUILDER_CAPACITY);
            if (buildClassName) {
                builder.append("className[");
                builder.append(record.getClass().getName());
                builder.append("]");
            }
            if (buildCategory) {
                builder.append("category[");
                if (record.categoryAsOptional().orElse(buildCategoryOther) != null) {
                    builder.append(record.categoryAsOptional().orElse(buildCategoryOther));
                }
                builder.append("]");
            }
            if (buildRecordId) {
                builder.append("recordId[");
                builder.append(record.recordId());
                builder.append("]");
            }
            if (buildSize) {
                builder.append("size[");
                builder.append(record.size());
                builder.append("]");
            }
            if (buildTexts) {
                builder.append("texts[");
                builder.append(record.streamOfFields().map(field -> {
                            if (field.isNull()) {
                                return "[" + field.index() + "]";
                            } else {
                                return "[" + field.index() + ",'" + field.text() + "']";
                            }
                        }
                ).collect(Collectors.joining(",")));
                builder.append("]");
            } else if (!buildTextIndex.isEmpty()) {
                builder.append("texts[");
                builder.append(buildTextIndex.stream().map(index -> {
                            if (!record.isValidIndex(index)) {
                                return "[" + index + " i]";
                            } else {
                                String text = record.textAt(index);
                                if (text == null) {
                                    return "[" + index + " n]";
                                } else {
                                    return "[" + index + ". '" + text + "']";
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
