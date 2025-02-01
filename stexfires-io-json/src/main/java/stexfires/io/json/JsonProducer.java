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
            if (!jsonStreamingFileSpec.recordSeparatorBefore()) {
                // Skip first lines by reading lines from the buffer without reading Records with the Iterator.
                if (jsonStreamingFileSpec.producerSkipFirstLines() > 0) {
                    for (int i = 0; i < jsonStreamingFileSpec.producerSkipFirstLines(); i++) {
                        bufferedReader().readLine();
                    }
                }
            } else {
                // TODO
            }
        }

        super.readBefore();
    }

    @Override
    protected AbstractRecordRawDataIterator createIterator() {
        if (fileSpec instanceof JsonStreamingFileSpec jsonStreamingFileSpec) {
            if (!jsonStreamingFileSpec.recordSeparatorBefore()) {
                return new JsonIterator(bufferedReader(), jsonStreamingFileSpec);
            }
        }
        return new JsonIterator(bufferedReader(), fileSpec);
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
                    if ((fieldElement == null) || fieldElement.isJsonNull()) {
                        if (fieldSpec.nullHandling() == JsonFieldSpec.NullHandling.NOT_ALLOWED) {
                            throw new UncheckedProducerException(new ProducerException("Missing or null field"));
                        }
                        texts.add(null);
                    } else {
                        switch (fieldSpec.valueType()) {
                            case BOOLEAN -> {
                                if (fieldElement.isJsonPrimitive() && (fieldElement instanceof JsonPrimitive jsonPrimitive) && jsonPrimitive.isBoolean()) {
                                    texts.add(jsonPrimitive.getAsBoolean() ? JsonUtil.LITERAL_TRUE : JsonUtil.LITERAL_FALSE);
                                } else {
                                    throw new UncheckedProducerException(new ProducerException("Not a boolean"));
                                }
                            }
                            case NUMBER -> {
                                if (fieldElement.isJsonPrimitive() && (fieldElement instanceof JsonPrimitive jsonPrimitive) && jsonPrimitive.isNumber()) {
                                    texts.add(jsonPrimitive.getAsNumber().toString());
                                } else {
                                    throw new UncheckedProducerException(new ProducerException("Not a number"));
                                }
                            }
                            case STRING_UNESCAPED -> {
                                if (fieldElement.isJsonPrimitive() && (fieldElement instanceof JsonPrimitive jsonPrimitive) && jsonPrimitive.isString()) {
                                    String jsonString = jsonPrimitive.getAsString();
                                    texts.add(jsonString);
                                } else {
                                    throw new UncheckedProducerException(new ProducerException("Not a string"));
                                }
                            }
                            case STRING_ESCAPED -> {
                                if (fieldElement.isJsonPrimitive() && (fieldElement instanceof JsonPrimitive jsonPrimitive) && jsonPrimitive.isString()) {
                                    String escapedJsonString = JsonUtil.escapeJsonString(jsonPrimitive.getAsString());
                                    texts.add(escapedJsonString);
                                } else {
                                    throw new UncheckedProducerException(new ProducerException("Not a string"));
                                }
                            }
                            case STRING_ESCAPED_WITH_QUOTATION_MARKS -> {
                                if (fieldElement.isJsonPrimitive() && (fieldElement instanceof JsonPrimitive jsonPrimitive) && jsonPrimitive.isString()) {
                                    String escapedJsonString = JsonUtil.escapeJsonString(jsonPrimitive.getAsString());
                                    texts.add(JsonUtil.QUOTATION_MARK + escapedJsonString + JsonUtil.QUOTATION_MARK);
                                } else {
                                    throw new UncheckedProducerException(new ProducerException("Not a string"));
                                }
                            }
                            case ARRAY_ELEMENTS -> {
                                if (fieldElement.isJsonArray() && (fieldElement instanceof JsonArray jsonArray)) {
                                    if (jsonArray.isEmpty()) {
                                        texts.add("");
                                    } else {
                                        String jsonArrayString = jsonArray.toString();
                                        texts.add(jsonArrayString.substring(1, jsonArrayString.length() - 1));
                                    }
                                } else {
                                    throw new UncheckedProducerException(new ProducerException("Not a JSON array"));
                                }
                            }
                            case ARRAY -> {
                                if (fieldElement.isJsonArray() && (fieldElement instanceof JsonArray jsonArray)) {
                                    if (jsonArray.isEmpty()) {
                                        texts.add(JsonUtil.BEGIN_ARRAY + JsonUtil.END_ARRAY);
                                    } else {
                                        String jsonArrayString = jsonArray.toString();
                                        texts.add(jsonArrayString);
                                    }
                                } else {
                                    throw new UncheckedProducerException(new ProducerException("Not a JSON array"));
                                }
                            }
                            case OBJECT_MEMBERS -> {
                                if (fieldElement.isJsonObject() && (fieldElement instanceof JsonObject jsonObject)) {
                                    if (jsonObject.isEmpty()) {
                                        texts.add("");
                                    } else {
                                        String jsonObjectString = jsonObject.toString();
                                        texts.add(jsonObjectString.substring(1, jsonObjectString.length() - 1));
                                    }
                                } else {
                                    throw new UncheckedProducerException(new ProducerException("Not a JSON object"));
                                }
                            }
                            case OBJECT -> {
                                if (fieldElement.isJsonObject() && (fieldElement instanceof JsonObject jsonObject)) {
                                    if (jsonObject.isEmpty()) {
                                        texts.add(JsonUtil.BEGIN_OBJECT + JsonUtil.END_OBJECT);
                                    } else {
                                        String jsonObjectString = jsonObject.toString();
                                        texts.add(jsonObjectString);
                                    }
                                } else {
                                    throw new UncheckedProducerException(new ProducerException("Not a JSON object"));
                                }
                            }
                        }
                    }
                }
            } else {
                throw new UncheckedProducerException(new ProducerException("Not a JSON object"));
            }
        } else if (fileSpec.recordJsonType() == JsonFileSpec.RecordJsonType.ARRAY) {
            if (recordJsonElement.isJsonArray() && (recordJsonElement instanceof JsonArray jsonArray)) {
                texts = new ArrayList<>();
                texts.add(recordJsonElement.toString()); // TODO
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

    private static final class JsonIterator extends AbstractRecordRawDataIterator {

        private final JsonFileSpec fileSpec;

        private JsonIterator(BufferedReader bufferedReader, JsonFileSpec fileSpec) {
            super(bufferedReader, DEFAULT_IGNORE_FIRST, DEFAULT_IGNORE_FIRST);
            this.fileSpec = fileSpec;
        }

        private JsonIterator(BufferedReader bufferedReader, JsonStreamingFileSpec fileSpec) {
            super(bufferedReader, fileSpec.producerIgnoreFirstRecords(), fileSpec.producerIgnoreLastRecords());
            this.fileSpec = fileSpec;
        }

        @Override
        protected Optional<RecordRawData> readNext(BufferedReader reader, long recordIndex) throws UncheckedProducerException {
            String rawData = null;

            if (fileSpec instanceof JsonStreamingFileSpec jsonStreamingFileSpec) {
                if (!jsonStreamingFileSpec.recordSeparatorBefore()) {
                    rawData = jsonStreamingFileSpec.producerReadLineHandling().readAndHandleLine(reader);
                }
            } else {
                // TODO
            }

            return RecordRawData.buildOptionalRecordRawData(null, recordIndex, rawData);
        }

    }

}
