package stexfires.io.json;

import com.google.gson.*;
import org.jspecify.annotations.Nullable;
import stexfires.io.consumer.AbstractWritableConsumer;
import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.UncheckedConsumerException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

import static stexfires.io.json.JsonFieldSpec.ValueType.STRING_UNESCAPED;
import static stexfires.io.json.JsonUtil.*;

/**
 * @since 0.1
 */
public final class JsonConsumer extends AbstractWritableConsumer<TextRecord> {

    private final JsonFileSpec fileSpec;
    private final List<JsonFieldSpec> fieldSpecs;
    private final Gson gson;
    private boolean firstRecord;

    public JsonConsumer(BufferedWriter bufferedWriter,
                        JsonFileSpec fileSpec,
                        List<JsonFieldSpec> fieldSpecs) {
        super(bufferedWriter);
        Objects.requireNonNull(fileSpec);
        Objects.requireNonNull(fieldSpecs);
        this.fileSpec = fileSpec;
        this.fieldSpecs = fieldSpecs;
        gson = new GsonBuilder()
                .disableJdkUnsafe()
                .setStrictness(Strictness.STRICT)
                .serializeNulls()
                .create();
        firstRecord = true;
    }

    private String convertFieldTextIntoJsonValue(JsonFieldSpec.ValueType type,
                                                 String fieldText) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(fieldText);

        return switch (type) {
            case STRING_UNESCAPED, STRING_ESCAPED -> buildJsonString(fieldText);
            case ARRAY_ELEMENTS -> buildJsonArray(fieldText);
            case OBJECT_MEMBERS -> buildJsonObject(fieldText);
            default -> fieldText;
        };
    }

    private JsonElement parseJsonElement(String exceptionMessage,
                                         Class<? extends JsonElement> jsonElementClass,
                                         String jsonElementString)
            throws ConsumerException {
        Objects.requireNonNull(exceptionMessage);
        Objects.requireNonNull(jsonElementClass);
        Objects.requireNonNull(jsonElementString);

        try {
            JsonElement jsonElement = gson.fromJson(jsonElementString, jsonElementClass);
            // noinspection ConstantValue
            if (jsonElement == null) {
                throw new ConsumerException(exceptionMessage + "Parsed to null");
            }
            return jsonElement;
        } catch (JsonSyntaxException e) {
            throw new ConsumerException(exceptionMessage + "Parse exception");
        }
    }

    private void checkJsonValue(JsonFieldSpec.ValueType type,
                                String jsonValue)
            throws ConsumerException {
        Objects.requireNonNull(type);
        Objects.requireNonNull(jsonValue);

        String exceptionMessage = "Invalid JSON Element (" + type + "): ";

        if (jsonValue.isEmpty()) {
            throw new ConsumerException(exceptionMessage + "Empty");
        } else if (jsonValue.isBlank()) {
            throw new ConsumerException(exceptionMessage + "Blank");
        }

        switch (type) {
            case BOOLEAN -> {
                JsonElement jsonElement = parseJsonElement(exceptionMessage, JsonPrimitive.class, jsonValue);
                if (!jsonElement.isJsonPrimitive() || !((JsonPrimitive) jsonElement).isBoolean()) {
                    throw new ConsumerException(exceptionMessage + "Not a boolean");
                }
            }
            case NUMBER -> {
                JsonElement jsonElement = parseJsonElement(exceptionMessage, JsonPrimitive.class, jsonValue);
                if (!jsonElement.isJsonPrimitive() || !((JsonPrimitive) jsonElement).isNumber()) {
                    throw new ConsumerException(exceptionMessage + "Not a number");
                }
            }
            case STRING_UNESCAPED, STRING_ESCAPED, STRING_ESCAPED_WITH_QUOTATION_MARKS -> {
                JsonElement jsonElement = parseJsonElement(exceptionMessage, JsonPrimitive.class, jsonValue);
                if (!jsonElement.isJsonPrimitive() || !((JsonPrimitive) jsonElement).isString()) {
                    throw new ConsumerException(exceptionMessage + "Not a string");
                }
            }
            case ARRAY_ELEMENTS, ARRAY -> {
                JsonElement jsonElement = parseJsonElement(exceptionMessage, JsonArray.class, jsonValue);
                if (!jsonElement.isJsonArray()) {
                    throw new ConsumerException(exceptionMessage + "Not an array");
                }
            }
            case OBJECT_MEMBERS, OBJECT -> {
                JsonElement jsonElement = parseJsonElement(exceptionMessage, JsonObject.class, jsonValue);
                if (!jsonElement.isJsonObject()) {
                    throw new ConsumerException(exceptionMessage + "Not an object");
                }
            }
        }
    }

    private Optional<String> checkAndConvertFieldTextIntoJsonValue(JsonFieldSpec fieldSpec,
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
            if (fieldSpec.checkValueNecessary()) {
                checkJsonValue(fieldSpec.valueType(), jsonValue);
            }
        }

        return Optional.ofNullable(jsonValue);
    }

    private List<String> createJsonMemberList(TextRecord record)
            throws ConsumerException {
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

    private List<String> createJsonElementList(TextRecord record)
            throws ConsumerException {
        Objects.requireNonNull(record);

        List<String> jsonElements = new ArrayList<>(fieldSpecs.size());
        for (int fieldIndex = 0; fieldIndex < fieldSpecs.size(); fieldIndex++) {
            JsonFieldSpec fieldSpec = fieldSpecs.get(fieldIndex);
            checkAndConvertFieldTextIntoJsonValue(fieldSpec, record.textAt(fieldIndex))
                    .ifPresent(jsonElements::add);
        }

        return jsonElements;
    }

    private String createRecordJsonObject(TextRecord record,
                                          String whitespacesAfterValueSeparator)
            throws ConsumerException {
        Objects.requireNonNull(record);
        Objects.requireNonNull(whitespacesAfterValueSeparator);

        List<String> jsonMemberList = createJsonMemberList(record);
        String jsonMembers = joinJsonMembers(jsonMemberList, whitespacesAfterValueSeparator);
        return buildJsonObject(jsonMembers);
    }

    private String createRecordJsonArray(TextRecord record,
                                         String whitespacesAfterValueSeparator)
            throws ConsumerException {
        Objects.requireNonNull(record);
        Objects.requireNonNull(whitespacesAfterValueSeparator);

        List<String> jsonElementList = createJsonElementList(record);
        String jsonElements = joinJsonElements(jsonElementList, whitespacesAfterValueSeparator);
        return buildJsonArray(jsonElements);
    }

    @SuppressWarnings("unused")
    @Override
    public void writeBefore() throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeBefore();

        // write text before
        if (fileSpec.consumerTextBefore() != null) {
            // noinspection DataFlowIssue
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

    @SuppressWarnings("unused")
    @Override
    public void writeRecord(TextRecord record) throws ConsumerException, UncheckedConsumerException, IOException {
        super.writeRecord(record);

        // convert TextRecord into JSON object or JSON array. Throws ConsumerException if a field value is invalid.
        String recordJson = switch (fileSpec.recordJsonType()) {
            case OBJECT -> createRecordJsonObject(record, fileSpec.whitespacesAfterValueSeparator());
            case ARRAY -> createRecordJsonArray(record, fileSpec.whitespacesAfterValueSeparator());
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

    @SuppressWarnings("unused")
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
            // noinspection DataFlowIssue
            writeString(fileSpec.consumerTextAfter());
            writeLineSeparator(fileSpec.consumerLineSeparator());
        }
    }

}
