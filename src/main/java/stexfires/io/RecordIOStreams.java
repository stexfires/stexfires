package stexfires.io;

import org.jetbrains.annotations.Nullable;
import stexfires.data.CollectionDataTypeFormatter;
import stexfires.data.CollectionDataTypeParser;
import stexfires.data.ConvertingDataTypeFormatter;
import stexfires.data.ConvertingDataTypeParser;
import stexfires.data.DataTypeConverterException;
import stexfires.data.DataTypeFormatter;
import stexfires.data.DataTypeParser;
import stexfires.data.GenericDataTypeFormatter;
import stexfires.data.GenericDataTypeParser;
import stexfires.data.StringDataTypeFormatter;
import stexfires.data.StringDataTypeParser;
import stexfires.io.consumer.StringWritableRecordConsumer;
import stexfires.io.consumer.WritableRecordConsumer;
import stexfires.io.consumer.WritableRecordFileSpec;
import stexfires.io.producer.ReadableRecordFileSpec;
import stexfires.io.producer.ReadableRecordProducer;
import stexfires.record.KeyValueCommentRecord;
import stexfires.record.KeyValueRecord;
import stexfires.record.TextField;
import stexfires.record.TextRecord;
import stexfires.record.TextRecordStreams;
import stexfires.record.TextRecords;
import stexfires.record.ValueRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.RecordConsumer;
import stexfires.record.consumer.UncheckedConsumerException;
import stexfires.record.impl.KeyValueCommentFieldsRecord;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.mapper.RecordMapper;
import stexfires.record.message.RecordMessage;
import stexfires.record.modifier.RecordStreamModifier;
import stexfires.record.producer.ProducerException;
import stexfires.record.producer.UncheckedProducerException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * This class consists of {@code static} utility methods
 * for input and output of {@code TextRecords}.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
@SuppressWarnings("UnusedReturnValue")
public final class RecordIOStreams {

    private RecordIOStreams() {
    }

    public static List<String> toStringList(TextRecord record) {
        Objects.requireNonNull(record);
        List<String> stringList = new ArrayList<>(2 + record.size());
        stringList.add(record.category());
        stringList.add(record.hasRecordId() ? String.valueOf(record.recordId()) : null);
        stringList.addAll(record.streamOfTexts().toList());
        return stringList;
    }

    @SuppressWarnings("DuplicateThrows")
    public static TextRecord fromStringList(List<String> stringList) throws IllegalArgumentException, NumberFormatException {
        Objects.requireNonNull(stringList);
        if (stringList.size() < 2) {
            throw new IllegalArgumentException("List contains too few data.");
        }
        String category = stringList.get(0);
        Long recordId;
        String recordIdString = stringList.get(1);
        if (recordIdString != null && !recordIdString.isEmpty()) {
            recordId = Long.valueOf(recordIdString);
        } else {
            recordId = null;
        }
        List<String> texts;
        if (stringList.size() > 2) {
            texts = stringList.subList(2, stringList.size());
        } else {
            texts = Collections.emptyList();
        }
        if (texts.size() == 1) {
            return new ValueFieldRecord(category, recordId, texts.get(0));
        }
        return new ManyFieldsRecord(category, recordId, texts);
    }

    public static Function<TextRecord, Stream<ValueRecord>> splitIntoValueRecords() {
        return textRecord -> textRecord.streamOfFields().map(textField ->
                new ValueFieldRecord(
                        textField.text()));
    }

    public static Function<TextRecord, Stream<ValueRecord>> splitIntoValueRecords(BiFunction<TextRecord, TextField, String> categoryFunction,
                                                                                  BiFunction<TextRecord, TextField, Long> recordIdFunction) {
        Objects.requireNonNull(categoryFunction);
        Objects.requireNonNull(recordIdFunction);
        return textRecord -> textRecord.streamOfFields().map(textField ->
                new ValueFieldRecord(
                        categoryFunction.apply(textRecord, textField),
                        recordIdFunction.apply(textRecord, textField),
                        textField.text()));
    }

    public static Function<TextRecord, Stream<KeyValueRecord>> splitIntoKeyValueRecords(BiFunction<TextRecord, TextField, String> keyFunction) {
        Objects.requireNonNull(keyFunction);
        return textRecord -> textRecord.streamOfFields().map(textField ->
                new KeyValueFieldsRecord(
                        keyFunction.apply(textRecord, textField),
                        textField.text()));
    }

    public static Function<TextRecord, Stream<KeyValueRecord>> splitIntoKeyValueRecords(BiFunction<TextRecord, TextField, String> categoryFunction,
                                                                                        BiFunction<TextRecord, TextField, Long> recordIdFunction,
                                                                                        BiFunction<TextRecord, TextField, String> keyFunction) {
        Objects.requireNonNull(categoryFunction);
        Objects.requireNonNull(recordIdFunction);
        Objects.requireNonNull(keyFunction);
        return textRecord -> textRecord.streamOfFields().map(textField ->
                new KeyValueFieldsRecord(
                        categoryFunction.apply(textRecord, textField),
                        recordIdFunction.apply(textRecord, textField),
                        keyFunction.apply(textRecord, textField),
                        textField.text()));
    }

    public static Function<TextRecord, Stream<KeyValueCommentRecord>> splitIntoKeyValueCommentRecords(BiFunction<TextRecord, TextField, String> keyFunction,
                                                                                                      BiFunction<TextRecord, TextField, String> commentFunction) {
        Objects.requireNonNull(keyFunction);
        Objects.requireNonNull(commentFunction);
        return textRecord -> textRecord.streamOfFields().map(textField ->
                new KeyValueCommentFieldsRecord(
                        keyFunction.apply(textRecord, textField),
                        textField.text(),
                        commentFunction.apply(textRecord, textField)));
    }

    public static Function<TextRecord, Stream<KeyValueCommentRecord>> splitIntoKeyValueCommentRecords(BiFunction<TextRecord, TextField, String> categoryFunction,
                                                                                                      BiFunction<TextRecord, TextField, Long> recordIdFunction,
                                                                                                      BiFunction<TextRecord, TextField, String> keyFunction,
                                                                                                      BiFunction<TextRecord, TextField, String> commentFunction) {
        Objects.requireNonNull(categoryFunction);
        Objects.requireNonNull(recordIdFunction);
        Objects.requireNonNull(keyFunction);
        Objects.requireNonNull(commentFunction);
        return textRecord -> textRecord.streamOfFields().map(textField ->
                new KeyValueCommentFieldsRecord(
                        categoryFunction.apply(textRecord, textField),
                        recordIdFunction.apply(textRecord, textField),
                        keyFunction.apply(textRecord, textField),
                        textField.text(),
                        commentFunction.apply(textRecord, textField)));
    }

    // ReadableRecordProducer ------------------------------------------------------------------------------------------

    public static <R, PTR extends TextRecord> R read(
            ReadableRecordProducer<PTR> readableRecordProducer,
            Function<Stream<PTR>, R> streamFunction)
            throws UncheckedProducerException {
        Objects.requireNonNull(readableRecordProducer);
        Objects.requireNonNull(streamFunction);

        R result;
        try {
            readableRecordProducer.readBefore();
            result = streamFunction.apply(readableRecordProducer.produceStream());
            readableRecordProducer.readAfter();
        } catch (ProducerException e) {
            throw new UncheckedProducerException(e);
        } catch (IOException e) {
            throw new UncheckedProducerException(new ProducerException(e));
        }

        return result;
    }

    public static <R, PTR extends TextRecord> R readFromString(
            ReadableRecordFileSpec<PTR, ?> readableRecordFileSpec,
            String sourceString,
            Function<Stream<PTR>, R> streamFunction)
            throws UncheckedProducerException {
        Objects.requireNonNull(readableRecordFileSpec);
        Objects.requireNonNull(sourceString);
        Objects.requireNonNull(streamFunction);

        R result;
        try (var readableRecordProducer = readableRecordFileSpec.producer(sourceString)) {
            result = read(readableRecordProducer, streamFunction);
        } catch (IOException e) {
            throw new UncheckedProducerException(new ProducerException(e));
        }

        return result;
    }

    public static DataTypeParser<TextRecord> newRecordDataTypeParser(String delimiter,
                                                                     @Nullable String prefix,
                                                                     @Nullable String suffix,
                                                                     @Nullable Supplier<TextRecord> nullSourceSupplier,
                                                                     @Nullable Supplier<TextRecord> emptySourceSupplier) {
        Objects.requireNonNull(delimiter);
        return new ConvertingDataTypeParser<>(
                null,
                CollectionDataTypeParser.withDelimiterAsList(delimiter, prefix, suffix,
                        StringDataTypeParser.identity(),
                        null, null),
                RecordIOStreams::fromStringList,
                nullSourceSupplier,
                emptySourceSupplier);
    }

    public static <PTR extends TextRecord> DataTypeParser<PTR> newRecordDataTypeParser(ReadableRecordFileSpec<PTR, ?> readableRecordFileSpec,
                                                                                       Function<Stream<PTR>, PTR> streamFunction,
                                                                                       @Nullable Supplier<PTR> nullSourceSupplier,
                                                                                       @Nullable Supplier<PTR> emptySourceSupplier) {
        Objects.requireNonNull(readableRecordFileSpec);
        Objects.requireNonNull(streamFunction);
        return new GenericDataTypeParser<>(source -> {
            try {
                return RecordIOStreams.readFromString(readableRecordFileSpec, source, streamFunction);
            } catch (UncheckedProducerException e) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, e);
            }
        }, nullSourceSupplier, emptySourceSupplier);
    }

    public static <PTR extends TextRecord> DataTypeParser<PTR> newRecordDataTypeParser(ReadableRecordFileSpec<PTR, ?> readableRecordFileSpec,
                                                                                       @Nullable Supplier<PTR> nullSourceSupplier,
                                                                                       @Nullable Supplier<PTR> emptySourceSupplier) {
        return newRecordDataTypeParser(readableRecordFileSpec,
                stream -> stream.findFirst().orElseThrow(() -> new DataTypeConverterException(DataTypeConverterException.Type.Parser, "No record could be parsed.")),
                nullSourceSupplier, emptySourceSupplier);
    }

    public static <R extends TextRecord, T extends R> Function<Stream<T>, RecordConsumer<R>> andConsume(
            RecordConsumer<R> recordConsumer) {
        Objects.requireNonNull(recordConsumer);

        return s -> {
            s.forEachOrdered(recordConsumer::consume);
            return recordConsumer;
        };
    }

    public static <R extends TextRecord, T extends TextRecord> Function<Stream<T>, RecordConsumer<R>> andConsumeMapped(
            RecordConsumer<R> recordConsumer,
            RecordMapper<? super T, ? extends R> recordMapper) {
        Objects.requireNonNull(recordConsumer);
        Objects.requireNonNull(recordMapper);

        return s -> {
            s.map(recordMapper::map).forEachOrdered(recordConsumer::consume);
            return recordConsumer;
        };
    }

    public static <R extends TextRecord, T extends TextRecord> Function<Stream<T>, RecordConsumer<R>> andConsumeModified(
            RecordConsumer<R> recordConsumer,
            RecordStreamModifier<T, ? extends R> recordStreamModifier) {
        Objects.requireNonNull(recordConsumer);
        Objects.requireNonNull(recordStreamModifier);

        return s -> {
            recordStreamModifier.modify(s).forEachOrdered(recordConsumer::consume);
            return recordConsumer;
        };
    }

    public static <R, T extends TextRecord> Function<Stream<T>, R> andCollect(
            Collector<? super T, ?, R> collector) {
        Objects.requireNonNull(collector);

        return s -> s.collect(collector);
    }

    public static <R, V extends TextRecord, T extends TextRecord> Function<Stream<T>, R> andCollectMapped(
            Collector<? super V, ?, R> collector,
            RecordMapper<? super T, V> recordMapper) {
        Objects.requireNonNull(collector);
        Objects.requireNonNull(recordMapper);

        return s -> s.map(recordMapper::map).collect(collector);
    }

    public static <R, V extends TextRecord, T extends TextRecord> Function<Stream<T>, R> andCollectModified(
            Collector<? super V, ?, R> collector,
            RecordStreamModifier<T, V> recordStreamModifier) {
        Objects.requireNonNull(collector);
        Objects.requireNonNull(recordStreamModifier);

        return s -> recordStreamModifier.modify(s).collect(collector);
    }

    public static <T extends TextRecord> Function<Stream<T>, Optional<T>> andFindFirst() {
        return Stream::findFirst;
    }

    public static <R extends TextRecord, T extends TextRecord> Function<Stream<T>, Optional<R>> andFindFirstMapped(
            RecordMapper<? super T, R> recordMapper) {
        Objects.requireNonNull(recordMapper);

        return s -> s.map(recordMapper::map).findFirst();
    }

    public static <R extends TextRecord, T extends TextRecord> Function<Stream<T>, Optional<R>> andFindFirstModified(
            RecordStreamModifier<T, R> recordStreamModifier) {
        Objects.requireNonNull(recordStreamModifier);

        return s -> recordStreamModifier.modify(s).findFirst();
    }

    public static <T extends TextRecord> Function<Stream<T>, Stream<T>> andFindFirstAsStream() {
        return s -> s.findFirst().stream();
    }

    public static <R extends TextRecord, PTR extends R> RecordConsumer<R> readAndConsume(
            ReadableRecordProducer<PTR> readableRecordProducer,
            RecordConsumer<R> recordConsumer)
            throws UncheckedProducerException {
        Objects.requireNonNull(readableRecordProducer);
        Objects.requireNonNull(recordConsumer);

        return read(readableRecordProducer, andConsume(recordConsumer));
    }

    // WritableRecordConsumer ------------------------------------------------------------------------------------------

    public static <CTR extends TextRecord, TR extends CTR, WRC extends WritableRecordConsumer<CTR>> WRC writeStream(
            WRC writableRecordConsumer,
            Stream<TR> recordStream)
            throws UncheckedConsumerException {
        Objects.requireNonNull(writableRecordConsumer);
        Objects.requireNonNull(recordStream);

        try {
            writableRecordConsumer.writeBefore();
            recordStream.forEachOrdered(writableRecordConsumer::consume);
            writableRecordConsumer.writeAfter();
            writableRecordConsumer.flush();
        } catch (ConsumerException e) {
            throw new UncheckedConsumerException(e);
        } catch (IOException e) {
            throw new UncheckedConsumerException(new ConsumerException(e));
        }

        return writableRecordConsumer;
    }

    public static <CTR extends TextRecord, TR extends CTR, WRC extends WritableRecordConsumer<CTR>> WRC writeRecord(
            WRC writableRecordConsumer,
            TR textRecord)
            throws UncheckedConsumerException {
        Objects.requireNonNull(writableRecordConsumer);
        Objects.requireNonNull(textRecord);

        try {
            writableRecordConsumer.writeBefore();
            writableRecordConsumer.writeRecord(textRecord);
            writableRecordConsumer.writeAfter();
            writableRecordConsumer.flush();
        } catch (ConsumerException e) {
            throw new UncheckedConsumerException(e);
        } catch (IOException e) {
            throw new UncheckedConsumerException(new ConsumerException(e));
        }

        return writableRecordConsumer;
    }

    public static <CTR extends TextRecord, TR extends CTR> String writeStreamIntoString(
            WritableRecordFileSpec<CTR, ?> writableRecordFileSpec,
            boolean removeLastLineSeparator,
            Stream<TR> recordStream)
            throws UncheckedConsumerException {
        Objects.requireNonNull(writableRecordFileSpec);
        Objects.requireNonNull(recordStream);

        String result;
        try (var consumer = new StringWritableRecordConsumer<>(writableRecordFileSpec)) {
            result = writeStream(consumer, recordStream).consumedString(removeLastLineSeparator);
        } catch (IOException e) {
            throw new UncheckedConsumerException(new ConsumerException(e));
        }
        return result;
    }

    public static <CTR extends TextRecord, TR extends CTR> String writeRecordIntoString(
            WritableRecordFileSpec<CTR, ?> writableRecordFileSpec,
            boolean removeLastLineSeparator,
            TR textRecord)
            throws UncheckedConsumerException {
        Objects.requireNonNull(writableRecordFileSpec);
        Objects.requireNonNull(textRecord);

        String result;
        try (var consumer = new StringWritableRecordConsumer<>(writableRecordFileSpec)) {
            result = writeRecord(consumer, textRecord).consumedString(removeLastLineSeparator);
        } catch (IOException e) {
            throw new UncheckedConsumerException(new ConsumerException(e));
        }
        return result;
    }

    public static DataTypeFormatter<TextRecord> newRecordDataTypeFormatter(String delimiter,
                                                                           @Nullable String prefix,
                                                                           @Nullable String suffix,
                                                                           @Nullable Supplier<String> nullSourceSupplier) {
        Objects.requireNonNull(delimiter);
        return new ConvertingDataTypeFormatter<>(
                RecordIOStreams::toStringList,
                CollectionDataTypeFormatter.withDelimiter(delimiter, prefix, suffix,
                        StringDataTypeFormatter.identity(), null),
                null,
                nullSourceSupplier);
    }

    public static <CTR extends TextRecord, TR extends CTR> DataTypeFormatter<TR> newRecordDataTypeFormatter(WritableRecordFileSpec<CTR, ?> writableRecordFileSpec,
                                                                                                            boolean removeLastLineSeparator,
                                                                                                            @Nullable Supplier<String> nullSourceSupplier) {
        Objects.requireNonNull(writableRecordFileSpec);
        return new GenericDataTypeFormatter<>(source -> {
            try {
                return RecordIOStreams.writeRecordIntoString(
                        writableRecordFileSpec,
                        removeLastLineSeparator,
                        source);
            } catch (UncheckedConsumerException e) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Formatter, e);
            }
        }, nullSourceSupplier);
    }

    public static <CTR extends TextRecord, TR extends CTR> RecordMessage<TR> writeRecordIntoStringMessage(
            WritableRecordFileSpec<CTR, ?> writableRecordFileSpec,
            boolean removeLastLineSeparator) {
        Objects.requireNonNull(writableRecordFileSpec);
        return r -> writeRecordIntoString(writableRecordFileSpec, removeLastLineSeparator, r);
    }

    public static <CTR extends TextRecord, TR extends CTR> TextRecord writeStreamIntoRecord(
            WritableRecordFileSpec<CTR, ?> writableRecordFileSpec,
            boolean removeLastLineSeparator,
            Stream<TR> recordStream)
            throws UncheckedConsumerException {
        Objects.requireNonNull(writableRecordFileSpec);
        Objects.requireNonNull(recordStream);
        return TextRecords.ofTexts(
                TextRecordStreams.mapToMessage(
                        recordStream,
                        writeRecordIntoStringMessage(writableRecordFileSpec, removeLastLineSeparator)));
    }

    // ReadableRecordProducer and WritableRecordConsumer ---------------------------------------------------------------

    public static <CTR extends TextRecord, PTR extends CTR, WRC extends WritableRecordConsumer<CTR>> WRC transfer(
            ReadableRecordProducer<PTR> readableRecordProducer,
            WRC writableRecordConsumer)
            throws ProducerException, ConsumerException, IOException {
        Objects.requireNonNull(readableRecordProducer);
        Objects.requireNonNull(writableRecordConsumer);

        try {
            readableRecordProducer.readBefore();
            writableRecordConsumer.writeBefore();
            readableRecordProducer.produceStream().forEachOrdered(writableRecordConsumer::consume);
            readableRecordProducer.readAfter();
            writableRecordConsumer.writeAfter();
            writableRecordConsumer.flush();
        } catch (UncheckedProducerException e) {
            throw e.getCause();
        } catch (UncheckedConsumerException e) {
            throw e.getCause();
        }

        return writableRecordConsumer;
    }

    public static <CTR extends TextRecord, PTR extends TextRecord, WRC extends WritableRecordConsumer<CTR>>
    WRC transferMapped(
            ReadableRecordProducer<PTR> readableRecordProducer,
            WRC writableRecordConsumer,
            RecordMapper<? super PTR, ? extends CTR> recordMapper)
            throws ProducerException, ConsumerException, IOException {
        Objects.requireNonNull(readableRecordProducer);
        Objects.requireNonNull(writableRecordConsumer);
        Objects.requireNonNull(recordMapper);

        try {
            readableRecordProducer.readBefore();
            writableRecordConsumer.writeBefore();
            readableRecordProducer.produceStream().map(recordMapper::map).forEachOrdered(writableRecordConsumer::consume);
            readableRecordProducer.readAfter();
            writableRecordConsumer.writeAfter();
            writableRecordConsumer.flush();
        } catch (UncheckedProducerException e) {
            throw e.getCause();
        } catch (UncheckedConsumerException e) {
            throw e.getCause();
        }

        return writableRecordConsumer;
    }

    public static <CTR extends TextRecord, PTR extends TextRecord, WRC extends WritableRecordConsumer<CTR>>
    WRC transferModified(
            ReadableRecordProducer<PTR> readableRecordProducer,
            WRC writableRecordConsumer,
            RecordStreamModifier<PTR, ? extends CTR> recordStreamModifier)
            throws ProducerException, ConsumerException, IOException {
        Objects.requireNonNull(readableRecordProducer);
        Objects.requireNonNull(writableRecordConsumer);
        Objects.requireNonNull(recordStreamModifier);

        try {
            readableRecordProducer.readBefore();
            writableRecordConsumer.writeBefore();
            recordStreamModifier.modify(readableRecordProducer.produceStream()).forEachOrdered(writableRecordConsumer::consume);
            readableRecordProducer.readAfter();
            writableRecordConsumer.writeAfter();
            writableRecordConsumer.flush();
        } catch (UncheckedProducerException e) {
            throw e.getCause();
        } catch (UncheckedConsumerException e) {
            throw e.getCause();
        }

        return writableRecordConsumer;
    }

}
