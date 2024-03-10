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

import static stexfires.io.json.JsonFieldSpec.NullHandling.ALLOWED_USE_LITERAL;
import static stexfires.io.json.JsonFieldSpec.NullHandling.NOT_ALLOWED;
import static stexfires.io.json.JsonFieldSpec.ValueType.ARRAY_WITHOUT_BRACKETS;
import static stexfires.io.json.JsonFieldSpec.ValueType.BOOLEAN;
import static stexfires.io.json.JsonFieldSpec.ValueType.NUMBER;
import static stexfires.io.json.JsonFieldSpec.ValueType.STRING_ESCAPED;
import static stexfires.io.json.JsonFieldSpec.ValueType.STRING_UNESCAPED;
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
                               String value)
            throws ConsumerException {
        Objects.requireNonNull(type);
        Objects.requireNonNull(value);

        // TODO implement and optimize value checks

        if (type == BOOLEAN) {
            // Check is value is a valid JSON boolean literal. Throws a ConsumerException if not.
            if (!LITERAL_TRUE.equals(value) && !LITERAL_FALSE.equals(value)) {
                throw new ConsumerException("Invalid boolean value");
            }
        } else if (type == NUMBER) {
            // Check is value is a valid JSON number. Throws a ConsumerException if not.
            try {
                BigDecimal bigDecimal = new BigDecimal(value);
            } catch (NumberFormatException e) {
                throw new ConsumerException("Invalid number value");
            }
        }
    }

    static String convertFieldTextIntoJsonValue(JsonFieldSpec.ValueType type,
                                                @Nullable String fieldText) {
        Objects.requireNonNull(type);

        String jsonValue;
        if (fieldText == null) {
            jsonValue = LITERAL_NULL;
        } else if (type == STRING_UNESCAPED || type == STRING_ESCAPED) {
            jsonValue = buildJsonString(fieldText);
        } else if (type == ARRAY_WITHOUT_BRACKETS) {
            jsonValue = buildJsonArray(fieldText);
        } else {
            jsonValue = fieldText;
        }

        return jsonValue;
    }

    static Optional<String> checkFieldTextAndCreateJsonMember(JsonFieldSpec fieldSpec,
                                                              @Nullable String fieldText)
            throws ConsumerException {
        Objects.requireNonNull(fieldSpec);

        String jsonMember = null;
        if ((fieldText == null) && (fieldSpec.nullHandling() == NOT_ALLOWED)) {
            throw new ConsumerException("Field text is null for " + fieldSpec);
        }
        if ((fieldText != null) || (fieldSpec.nullHandling() == ALLOWED_USE_LITERAL)) {
            String preparedFieldText = fieldText;
            // Escape or check fieldText if it is not null
            if (preparedFieldText != null) {
                if (fieldSpec.valueType() == STRING_UNESCAPED) {
                    preparedFieldText = escapeJsonString(preparedFieldText);
                } else if (fieldSpec.checkValueNecessary()) { // check STRING only if it was not escaped before
                    checkJsonValue(fieldSpec.valueType(), preparedFieldText);
                }
            }
            jsonMember = buildJsonMember(fieldSpec.escapedName(),
                    convertFieldTextIntoJsonValue(fieldSpec.valueType(), preparedFieldText));
        }
        return Optional.ofNullable(jsonMember);
    }

    static List<String> createJsonMemberList(List<JsonFieldSpec> fieldSpecs,
                                             TextRecord record)
            throws ConsumerException {
        Objects.requireNonNull(fieldSpecs);
        Objects.requireNonNull(record);

        List<String> jsonMembers = new ArrayList<>(fieldSpecs.size());
        for (int fieldIndex = 0; fieldIndex < fieldSpecs.size(); fieldIndex++) {
            checkFieldTextAndCreateJsonMember(fieldSpecs.get(fieldIndex), record.textAt(fieldIndex))
                    .ifPresent(jsonMembers::add);
        }

        return jsonMembers;
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

    @Override
    public void writeBefore() throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeBefore();

        // write text before
        if (fileSpec.consumerTextBefore() != null) {
            //noinspection DataFlowIssue
            writeString(fileSpec.consumerTextBefore());
            writeLineSeparator(fileSpec.consumerLineSeparator());
        }

        if (fileSpec.embeddedInJsonObject()) {
            writeString(BEGIN_OBJECT);
        }

        // Write JSON before the records depending on the fileSpec type.
        if (fileSpec instanceof JsonArrayFileSpec jsonArrayFileSpec) {
            writeString(buildJsonString(jsonArrayFileSpec.consumerEscapedArrayName()));
            writeString(NAME_SEPARATOR);
            writeString(BEGIN_ARRAY);
        }
    }

    @Override
    public void writeRecord(TextRecord record) throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeRecord(record);

        // Convert TextRecord into JSON object. Throws ConsumerException if a field value is invalid.
        String recordJsonObject = createRecordJsonObject(fieldSpecs, record, fileSpec.whitespacesAfterValueSeparator());

        // Write JSON object depending on the fileSpec type.
        switch (fileSpec) {
            case JsonArrayFileSpec fs -> {
                if (!firstRecord) {
                    writeString(VALUE_SEPARATOR);
                }
                writeLineSeparator(fileSpec.consumerLineSeparator());
                writeString(recordJsonObject);
            }
            case JsonMembersFileSpec fs -> {
                String escapedJsonName = fs.escapedJsonNameByMessage(record)
                                           .orElseThrow(() -> new ConsumerException("The name of the member generated by the RecordMessage is null.", record));
                String jsonMember = buildJsonMember(escapedJsonName, recordJsonObject);

                if (!firstRecord) {
                    writeString(VALUE_SEPARATOR);
                }
                writeLineSeparator(fileSpec.consumerLineSeparator());
                writeString(jsonMember);
            }
            case JsonStreamingFileSpec fs -> {
                if (fs.recordSeparatorBeforeJsonObject()) {
                    writeString(RECORD_SEPARATOR);
                }
                writeString(recordJsonObject);
                writeLineSeparator(fileSpec.consumerLineSeparator());
            }
        }

        if (firstRecord) {
            firstRecord = false;
        }
    }

    @Override
    public void writeAfter() throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeAfter();

        // Write JSON after records depending on the fileSpec type.
        switch (fileSpec) {
            case JsonArrayFileSpec fs -> {
                writeLineSeparator(fileSpec.consumerLineSeparator());
                writeString(END_ARRAY);
            }
            case JsonMembersFileSpec fs -> writeLineSeparator(fileSpec.consumerLineSeparator());
            case JsonStreamingFileSpec fs -> {
            }
        }

        if (fileSpec.embeddedInJsonObject()) {
            writeString(END_OBJECT);
            writeLineSeparator(fileSpec.consumerLineSeparator());
        }

        // write text after
        if (fileSpec.consumerTextAfter() != null) {
            //noinspection DataFlowIssue
            writeString(fileSpec.consumerTextAfter());
            writeLineSeparator(fileSpec.consumerLineSeparator());
        }
    }

}
