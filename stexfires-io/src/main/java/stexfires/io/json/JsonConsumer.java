package stexfires.io.json;

import org.jspecify.annotations.Nullable;
import stexfires.io.internal.AbstractInternalWritableConsumer;
import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static stexfires.io.json.JsonFieldSpec.ValueType.*;
import static stexfires.io.json.JsonUtil.*;

/**
 * @since 0.1
 */
public final class JsonConsumer extends AbstractInternalWritableConsumer<TextRecord> {

    private final JsonFileSpec fileSpec;
    private final List<JsonFieldSpec> fieldSpecs;
    private boolean firstRecord;

    public JsonConsumer(BufferedWriter bufferedWriter,
                        JsonFileSpec fileSpec,
                        List<JsonFieldSpec> fieldSpecs) {
        super(bufferedWriter);
        Objects.requireNonNull(fileSpec);
        Objects.requireNonNull(fieldSpecs);
        this.fileSpec = fileSpec;
        this.fieldSpecs = fieldSpecs;
        firstRecord = true;
    }

    static void checkJsonValue(JsonFieldSpec.ValueType type,
                               String jsonValue)
            throws ConsumerException {
        Objects.requireNonNull(type);
        Objects.requireNonNull(jsonValue);

        switch (type) {
            case BOOLEAN -> {
                // Checks if the string contains a correct JSON boolean literal.
                if (!LITERAL_TRUE.equals(jsonValue) && !LITERAL_FALSE.equals(jsonValue)) {
                    throw new ConsumerException("Invalid boolean value");
                }
            }
            case NUMBER -> {
                // Checks if the string is a valid JSON number.
                try {
                    BigDecimal bigDecimal = new BigDecimal(jsonValue);
                } catch (NumberFormatException e) {
                    throw new ConsumerException("Invalid number value");
                }
            }
            case STRING_UNESCAPED, STRING_ESCAPED, STRING_ESCAPED_WITH_QUOTATION_MARKS -> {
                // Checks if the string is a valid JSON string with correct escape sequences and if it is enclosed in quotation marks.
                // TODO Implement check
            }
            case ARRAY_ELEMENTS, ARRAY -> {
                // Checks if the string is a valid JSON array with correct elements.
                // TODO Implement check
            }
            case OBJECT_MEMBERS, OBJECT -> {
                // Checks if the string is a valid JSON object with correct members.
                // TODO Implement check
            }
        }
    }

    static String convertFieldTextIntoJsonValue(JsonFieldSpec.ValueType type,
                                                String fieldText) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(fieldText);

        String jsonValue;
        if (type == STRING_UNESCAPED || type == STRING_ESCAPED) {
            jsonValue = buildJsonString(fieldText);
        } else if (type == ARRAY_ELEMENTS) {
            jsonValue = buildJsonArray(fieldText);
        } else if (type == OBJECT_MEMBERS) {
            jsonValue = buildJsonObject(fieldText);
        } else {
            jsonValue = fieldText;
        }

        return jsonValue;
    }

    static Optional<String> checkAndConvertFieldTextIntoJsonValue(JsonFieldSpec fieldSpec,
                                                                  @Nullable String fieldText)
            throws ConsumerException {
        Objects.requireNonNull(fieldSpec);

        String jsonValue;
        if (fieldText == null) {
            jsonValue = switch (fieldSpec.nullHandling()) {
                case NOT_ALLOWED -> throw new ConsumerException("Field text is null for " + fieldSpec);
                case ALLOWED_USE_LITERAL -> LITERAL_NULL;
                case ALLOWED_OMIT_FIELD -> null;
            };
        } else {
            String preparedFieldText;
            if (fieldSpec.valueType() == STRING_UNESCAPED) {
                preparedFieldText = escapeJsonString(fieldText);
            } else {
                preparedFieldText = fieldText;
            }
            jsonValue = convertFieldTextIntoJsonValue(fieldSpec.valueType(), preparedFieldText);
            checkJsonValue(fieldSpec.valueType(), jsonValue);
        }

        return Optional.ofNullable(jsonValue);
    }

    static List<String> createJsonMemberList(List<JsonFieldSpec> fieldSpecs,
                                             TextRecord record)
            throws ConsumerException {
        Objects.requireNonNull(fieldSpecs);
        Objects.requireNonNull(record);

        List<String> jsonMembers = new ArrayList<>(fieldSpecs.size());
        for (int fieldIndex = 0; fieldIndex < fieldSpecs.size(); fieldIndex++) {
            JsonFieldSpec fieldSpec = fieldSpecs.get(fieldIndex);
            checkAndConvertFieldTextIntoJsonValue(fieldSpec, record.textAt(fieldIndex))
                    .map(jsonValue -> buildJsonMember(fieldSpec.escapedName(), jsonValue))
                    .ifPresent(jsonMembers::add);
        }

        return jsonMembers;
    }

    static List<String> createJsonElementList(List<JsonFieldSpec> fieldSpecs,
                                              TextRecord record)
            throws ConsumerException {
        Objects.requireNonNull(fieldSpecs);
        Objects.requireNonNull(record);

        List<String> jsonElements = new ArrayList<>(fieldSpecs.size());
        for (int fieldIndex = 0; fieldIndex < fieldSpecs.size(); fieldIndex++) {
            JsonFieldSpec fieldSpec = fieldSpecs.get(fieldIndex);
            checkAndConvertFieldTextIntoJsonValue(fieldSpec, record.textAt(fieldIndex))
                    .ifPresent(jsonElements::add);
        }

        return jsonElements;
    }

    static String createRecordJsonObject(List<JsonFieldSpec> fieldSpecs,
                                         TextRecord record,
                                         String whitespacesAfterValueSeparator)
            throws ConsumerException {
        Objects.requireNonNull(fieldSpecs);
        Objects.requireNonNull(record);
        Objects.requireNonNull(whitespacesAfterValueSeparator);

        List<String> jsonMemberList = createJsonMemberList(fieldSpecs, record);
        String jsonMembers = joinJsonMembers(jsonMemberList, whitespacesAfterValueSeparator);
        return buildJsonObject(jsonMembers);
    }

    static String createRecordJsonArray(List<JsonFieldSpec> fieldSpecs,
                                        TextRecord record,
                                        String whitespacesAfterValueSeparator)
            throws ConsumerException {
        Objects.requireNonNull(fieldSpecs);
        Objects.requireNonNull(record);
        Objects.requireNonNull(whitespacesAfterValueSeparator);

        List<String> jsonElementList = createJsonElementList(fieldSpecs, record);
        String jsonElements = joinJsonElements(jsonElementList, whitespacesAfterValueSeparator);
        return buildJsonArray(jsonElements);
    }

    @Override
    public void writeBefore() throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeBefore();

        // write text before
        if (fileSpec.consumerTextBefore() != null) {
            //noinspection DataFlowIssue
            writeString(fileSpec.consumerTextBefore());
            writeLineSeparator(fileSpec.consumerLineSeparator());
        }

        // write JSON before records depending on the fileSpec type.
        switch (fileSpec) {
            case JsonArrayFileSpec fs -> {
                if (fileSpec.embeddedInJsonObject()) {
                    writeString(BEGIN_OBJECT);
                }
                if (fs.namedMember()) {
                    writeString(buildJsonString(fs.consumerEscapedArrayName()));
                    writeString(NAME_SEPARATOR);
                }
                writeString(BEGIN_ARRAY);
                writeLineSeparator(fileSpec.consumerLineSeparator());
            }
            case JsonMembersFileSpec fs -> {
                if (fileSpec.embeddedInJsonObject()) {
                    writeString(BEGIN_OBJECT);
                    writeLineSeparator(fileSpec.consumerLineSeparator());
                }
            }
            case JsonStreamingFileSpec fs -> {
                // nothing to do
            }
        }
    }

    @Override
    public void writeRecord(TextRecord record) throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeRecord(record);

        // convert TextRecord into JSON object or JSON array. Throws ConsumerException if a field value is invalid.
        String recordJson = switch (fileSpec.recordJsonType()) {
            case OBJECT -> createRecordJsonObject(fieldSpecs, record, fileSpec.whitespacesAfterValueSeparator());
            case ARRAY -> createRecordJsonArray(fieldSpecs, record, fileSpec.whitespacesAfterValueSeparator());
        };

        // write JSON depending on the fileSpec type.
        switch (fileSpec) {
            case JsonArrayFileSpec fs -> {
                if (!firstRecord) {
                    writeString(VALUE_SEPARATOR);
                    writeLineSeparator(fileSpec.consumerLineSeparator());
                }
                writeString(recordJson);
            }
            case JsonMembersFileSpec fs -> {
                // create name and build JSON member (name and value)
                String escapedJsonName = fs.escapedJsonNameByMessage(record)
                                           .orElseThrow(() -> new ConsumerException("The name of the member generated by the RecordMessage is null.", record));
                String jsonMember = buildJsonMember(escapedJsonName, recordJson);

                if (!firstRecord) {
                    writeString(VALUE_SEPARATOR);
                    writeLineSeparator(fileSpec.consumerLineSeparator());
                }
                writeString(jsonMember);
            }
            case JsonStreamingFileSpec fs -> {
                if (fs.recordSeparatorBefore()) {
                    writeString(RECORD_SEPARATOR);
                }
                writeString(recordJson);
                writeLineSeparator(fileSpec.consumerLineSeparator());
            }
        }

        // set firstRecord to false after the first record was written
        if (firstRecord) {
            firstRecord = false;
        }
    }

    @Override
    public void writeAfter() throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeAfter();

        // write JSON after records depending on the fileSpec type.
        switch (fileSpec) {
            case JsonArrayFileSpec fs -> {
                if (!firstRecord) {
                    writeLineSeparator(fileSpec.consumerLineSeparator());
                }
                writeString(END_ARRAY);
                if (fileSpec.embeddedInJsonObject()) {
                    writeString(END_OBJECT);
                }
                writeLineSeparator(fileSpec.consumerLineSeparator());
            }
            case JsonMembersFileSpec fs -> {
                if (!firstRecord) {
                    writeLineSeparator(fileSpec.consumerLineSeparator());
                }
                if (fileSpec.embeddedInJsonObject()) {
                    writeString(END_OBJECT);
                    writeLineSeparator(fileSpec.consumerLineSeparator());
                }
            }
            case JsonStreamingFileSpec fs -> {
                // nothing to do
            }
        }

        // write text after
        if (fileSpec.consumerTextAfter() != null) {
            //noinspection DataFlowIssue
            writeString(fileSpec.consumerTextAfter());
            writeLineSeparator(fileSpec.consumerLineSeparator());
        }
    }

}
