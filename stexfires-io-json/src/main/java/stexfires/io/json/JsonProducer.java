package stexfires.io.json;

import com.google.gson.*;
import org.jspecify.annotations.Nullable;
import stexfires.io.producer.AbstractReadableProducer;
import stexfires.io.producer.AbstractRecordRawDataIterator;
import stexfires.io.producer.RecordRawData;
import stexfires.record.TextRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.producer.ProducerException;
import stexfires.record.producer.UncheckedProducerException;
import stexfires.util.CodePoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * @since 0.1
 */
public final class JsonProducer extends AbstractReadableProducer<TextRecord> {

    private final JsonFileSpec fileSpec;
    private final Gson gson;

    public JsonProducer(BufferedReader bufferedReader, JsonFileSpec fileSpec) {
        super(bufferedReader);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
        gson = new GsonBuilder()
                .disableJdkUnsafe()
                .setStrictness(Strictness.STRICT)
                .serializeNulls()
                .create();
    }

    @Override
    public void readBefore() throws ProducerException, UncheckedProducerException, IOException {
        if (fileSpec instanceof JsonStreamingFileSpec jsonStreamingFileSpec) {
            // Skip first lines by reading lines from the buffer without reading Records with the Iterator.
            if (jsonStreamingFileSpec.producerSkipFirstLines() > 0) {
                for (int i = 0; i < jsonStreamingFileSpec.producerSkipFirstLines(); i++) {
                    bufferedReader().readLine();
                }
            }
            if (jsonStreamingFileSpec.recordSeparatorBefore()) {
                // Read first record separator.
                int firstCodePoint = bufferedReader().read();
                if ((firstCodePoint != -1) && !JsonUtil.CODE_POINT_RECORD_SEPARATOR.equalsInt(firstCodePoint)) {
                    throw new UncheckedProducerException(new ProducerException("First character is not a record separator"));
                }
            }
        } else {
            // TODO JsonArrayFileSpec and JsonMembersFileSpec
        }

        super.readBefore();
    }

    @Override
    protected AbstractRecordRawDataIterator createIterator() {
        if (fileSpec instanceof JsonStreamingFileSpec jsonStreamingFileSpec) {
            return new JsonIterator(
                    bufferedReader(),
                    jsonStreamingFileSpec,
                    jsonStreamingFileSpec.producerIgnoreFirstRecords(),
                    jsonStreamingFileSpec.producerIgnoreLastRecords());
        } else {
            // TODO JsonArrayFileSpec and JsonMembersFileSpec
            return new JsonIterator(bufferedReader(), fileSpec);
        }
    }

    @Override
    protected Optional<TextRecord> createRecord(RecordRawData recordRawData) throws UncheckedProducerException {
        Objects.requireNonNull(recordRawData);

        JsonElement recordJsonElement;
        try {
            recordJsonElement = gson.fromJson(recordRawData.rawData(), JsonElement.class);
        } catch (JsonSyntaxException e) {
            throw new UncheckedProducerException(new ProducerException("Parse Exception"));
        }

        Collection<@Nullable String> texts = null;
        if (fileSpec.recordJsonType() == JsonFileSpec.RecordJsonType.OBJECT) {
            if (recordJsonElement.isJsonObject() && (recordJsonElement instanceof JsonObject recordJsonObject)) {
                texts = new ArrayList<>();
                for (JsonFieldSpec fieldSpec : fileSpec.fieldSpecs()) {
                    JsonElement fieldElement = recordJsonObject.get(fieldSpec.unescapedName());
                    texts.add(extractTextFromJson(fieldSpec, fieldElement));
                }
            } else {
                throw new UncheckedProducerException(new ProducerException("Not a JSON object"));
            }
        } else if (fileSpec.recordJsonType() == JsonFileSpec.RecordJsonType.ARRAY) {
            if (recordJsonElement.isJsonArray() && (recordJsonElement instanceof JsonArray jsonArray)) {
                if (fileSpec.fieldSpecs().size() != jsonArray.size()) {
                    throw new UncheckedProducerException(new ProducerException("Wrong number of fields"));
                }
                texts = new ArrayList<>();
                for (int index = 0; index < fileSpec.fieldSpecs().size(); index++) {
                    JsonFieldSpec fieldSpec = fileSpec.fieldSpecs().get(index);
                    JsonElement fieldElement = jsonArray.get(index);
                    texts.add(extractTextFromJson(fieldSpec, fieldElement));
                }
            } else {
                throw new UncheckedProducerException(new ProducerException("Not a JSON array"));
            }
        }

        TextRecord record = null;
        if (texts != null) {
            record = new ManyFieldsRecord(recordRawData.category(), recordRawData.recordId(), texts);
        }

        return Optional.ofNullable(record);
    }

    private static @Nullable String extractTextFromJson(JsonFieldSpec fieldSpec, @Nullable JsonElement fieldElement)
            throws UncheckedProducerException {
        Objects.requireNonNull(fieldSpec);

        if ((fieldElement == null) || fieldElement.isJsonNull()) {
            if (fieldSpec.nullHandling() == JsonFieldSpec.NullHandling.NOT_ALLOWED) {
                throw new UncheckedProducerException(new ProducerException("Missing or null field"));
            }
            return null;
        } else {
            return switch (fieldSpec.valueType()) {
                case BOOLEAN -> {
                    if (fieldElement.isJsonPrimitive() && (fieldElement instanceof JsonPrimitive jsonPrimitive) && jsonPrimitive.isBoolean()) {
                        yield jsonPrimitive.getAsBoolean() ? JsonUtil.LITERAL_TRUE : JsonUtil.LITERAL_FALSE;
                    } else {
                        throw new UncheckedProducerException(new ProducerException("Not a boolean"));
                    }
                }
                case NUMBER -> {
                    if (fieldElement.isJsonPrimitive() && (fieldElement instanceof JsonPrimitive jsonPrimitive) && jsonPrimitive.isNumber()) {
                        yield jsonPrimitive.getAsNumber().toString();
                    } else {
                        throw new UncheckedProducerException(new ProducerException("Not a number"));
                    }
                }
                case STRING_UNESCAPED -> {
                    if (fieldElement.isJsonPrimitive() && (fieldElement instanceof JsonPrimitive jsonPrimitive) && jsonPrimitive.isString()) {
                        yield jsonPrimitive.getAsString();
                    } else {
                        throw new UncheckedProducerException(new ProducerException("Not a string"));
                    }
                }
                case STRING_ESCAPED -> {
                    if (fieldElement.isJsonPrimitive() && (fieldElement instanceof JsonPrimitive jsonPrimitive) && jsonPrimitive.isString()) {
                        yield JsonUtil.escapeJsonString(jsonPrimitive.getAsString());
                    } else {
                        throw new UncheckedProducerException(new ProducerException("Not a string"));
                    }
                }
                case STRING_ESCAPED_WITH_QUOTATION_MARKS -> {
                    if (fieldElement.isJsonPrimitive() && (fieldElement instanceof JsonPrimitive jsonPrimitive) && jsonPrimitive.isString()) {
                        String escapedJsonString = JsonUtil.escapeJsonString(jsonPrimitive.getAsString());
                        yield JsonUtil.QUOTATION_MARK + escapedJsonString + JsonUtil.QUOTATION_MARK;
                    } else {
                        throw new UncheckedProducerException(new ProducerException("Not a string"));
                    }
                }
                case ARRAY_ELEMENTS -> {
                    if (fieldElement.isJsonArray() && (fieldElement instanceof JsonArray jsonArray)) {
                        if (jsonArray.isEmpty()) {
                            yield "";
                        } else {
                            String jsonArrayString = jsonArray.toString();
                            yield jsonArrayString.substring(1, jsonArrayString.length() - 1);
                        }
                    } else {
                        throw new UncheckedProducerException(new ProducerException("Not a JSON array"));
                    }
                }
                case ARRAY -> {
                    if (fieldElement.isJsonArray() && (fieldElement instanceof JsonArray jsonArray)) {
                        if (jsonArray.isEmpty()) {
                            yield JsonUtil.BEGIN_ARRAY + JsonUtil.END_ARRAY;
                        } else {
                            yield jsonArray.toString();
                        }
                    } else {
                        throw new UncheckedProducerException(new ProducerException("Not a JSON array"));
                    }
                }
                case OBJECT_MEMBERS -> {
                    if (fieldElement.isJsonObject() && (fieldElement instanceof JsonObject jsonObject)) {
                        if (jsonObject.isEmpty()) {
                            yield "";
                        } else {
                            String jsonObjectString = jsonObject.toString();
                            yield jsonObjectString.substring(1, jsonObjectString.length() - 1);
                        }
                    } else {
                        throw new UncheckedProducerException(new ProducerException("Not a JSON object"));
                    }
                }
                case OBJECT -> {
                    if (fieldElement.isJsonObject() && (fieldElement instanceof JsonObject jsonObject)) {
                        if (jsonObject.isEmpty()) {
                            yield JsonUtil.BEGIN_OBJECT + JsonUtil.END_OBJECT;
                        } else {
                            yield jsonObject.toString();
                        }
                    } else {
                        throw new UncheckedProducerException(new ProducerException("Not a JSON object"));
                    }
                }
            };
        }
    }

    private static final class JsonIterator extends AbstractRecordRawDataIterator {

        private static final int INITIAL_STRING_BUILDER_CAPACITY = 1_024;

        private final JsonFileSpec fileSpec;

        private JsonIterator(BufferedReader bufferedReader, JsonFileSpec fileSpec) {
            this(bufferedReader, fileSpec, DEFAULT_IGNORE_FIRST, DEFAULT_IGNORE_LAST);
        }

        private JsonIterator(BufferedReader bufferedReader, JsonFileSpec fileSpec, int ignoreFirst, int ignoreLast) {
            super(bufferedReader, ignoreFirst, ignoreLast);
            this.fileSpec = fileSpec;
        }

        @Override
        protected Optional<RecordRawData> readNext(BufferedReader reader, long recordIndex)
                throws ProducerException, UncheckedProducerException, IOException {
            Objects.requireNonNull(reader);

            String rawData = null;

            switch (fileSpec) {
                case JsonArrayFileSpec jsonArrayFileSpec -> {
                    // TODO read next from JsonArrayFileSpec
                }
                case JsonMembersFileSpec jsonMembersFileSpec -> {
                    // TODO read next from JsonMembersFileSpec
                }
                case JsonStreamingFileSpec jsonStreamingFileSpec -> {
                    if (jsonStreamingFileSpec.recordSeparatorBefore()) {
                        rawData = readStreamingWithRecordSeparator(reader);
                    } else {
                        rawData = jsonStreamingFileSpec.producerReadLineHandling().readAndHandleLine(reader);
                    }
                    rawData = validateStreaming(rawData, jsonStreamingFileSpec);
                }
            }

            return RecordRawData.buildOptionalRecordRawData(null, recordIndex, rawData);
        }

        private @Nullable String readStreamingWithRecordSeparator(BufferedReader reader)
                throws IOException {
            // Read characters from BufferedReader into String until the next record separator is reached.
            StringBuilder rawDataBuilder = new StringBuilder(INITIAL_STRING_BUILDER_CAPACITY);
            int codePoint;
            // noinspection NestedAssignment
            while ((codePoint = reader.read()) != -1) {
                if (JsonUtil.CODE_POINT_RECORD_SEPARATOR.equalsInt(codePoint)) {
                    break;
                }
                rawDataBuilder.appendCodePoint(codePoint);
            }
            if (rawDataBuilder.isEmpty() && (codePoint == -1)) {
                // The end of the reader has been reached and no characters have been read.
                // Returning null will end the iteration.
                return null;
            }

            return rawDataBuilder.toString();
        }

        private @Nullable String validateStreaming(@Nullable String rawData,
                                                   JsonStreamingFileSpec jsonStreamingFileSpec)
                throws ProducerException {
            String result = rawData;
            if (rawData != null) {
                Optional<CodePoint> firstCodePoint = JsonUtil.findFirstNonWhiteSpace(rawData);
                Optional<CodePoint> lastCodePoint = JsonUtil.findLastNonWhiteSpace(rawData);
                boolean validJson = switch (jsonStreamingFileSpec.recordJsonType()) {
                    case OBJECT -> firstCodePoint.map(JsonUtil.CODE_POINT_BEGIN_OBJECT::equals).orElse(false)
                            && lastCodePoint.map(JsonUtil.CODE_POINT_END_OBJECT::equals).orElse(false);
                    case ARRAY -> firstCodePoint.map(JsonUtil.CODE_POINT_BEGIN_ARRAY::equals).orElse(false)
                            && lastCodePoint.map(JsonUtil.CODE_POINT_END_ARRAY::equals).orElse(false);
                };
                if (!validJson) {
                    switch (jsonStreamingFileSpec.producerNotJsonHandling()) {
                        case THROW_EXCEPTION -> throw new ProducerException("Not a JSON object or array");
                        case TREAT_LIKE_END -> result = null;
                    }
                }
            }
            return result;
        }

    }

}
