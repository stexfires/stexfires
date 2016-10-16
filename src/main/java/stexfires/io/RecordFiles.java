package stexfires.io;

import stexfires.core.Record;
import stexfires.core.RecordStreams;
import stexfires.core.consumer.LoggerConsumer;
import stexfires.core.consumer.RecordConsumer;
import stexfires.core.logger.RecordLogger;
import stexfires.core.mapper.RecordMapper;
import stexfires.core.modifier.RecordStreamModifier;
import stexfires.core.producer.RecordProducer;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * This class consists of {@code static} utility methods for operating on record files.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
public final class RecordFiles {

    private RecordFiles() {
    }

    public static <R extends Record, T extends R> RecordConsumer<R> read(ReadableRecordFile<T> readableFile,
                                                                         RecordConsumer<R> consumer) throws IOException {
        try (ReadableRecordProducer<T> producer = readableFile.openProducer()) {
            producer.readBefore();
            producer.produceStream().forEachOrdered(consumer::consume);
            producer.readAfter();
        }
        return consumer;
    }

    public static <R extends Record, T extends Record> RecordConsumer<R> read(ReadableRecordFile<T> readableFile,
                                                                              RecordMapper<? super T, ? extends R> recordMapper,
                                                                              RecordConsumer<R> consumer) throws IOException {
        try (ReadableRecordProducer<T> producer = readableFile.openProducer()) {
            producer.readBefore();
            producer.produceStream().map(recordMapper::map).forEachOrdered(consumer::consume);
            producer.readAfter();
        }
        return consumer;
    }

    public static <R extends Record, T extends Record> RecordConsumer<R> read(ReadableRecordFile<T> readableFile,
                                                                              RecordStreamModifier<T, ? extends R> recordStreamModifier,
                                                                              RecordConsumer<R> consumer) throws IOException {
        try (ReadableRecordProducer<T> producer = readableFile.openProducer()) {
            producer.readBefore();
            recordStreamModifier.modify(producer.produceStream()).forEachOrdered(consumer::consume);
            producer.readAfter();
        }
        return consumer;
    }

    public static <R extends Record, T extends R> RecordLogger<R> log(ReadableRecordFile<T> readableFile,
                                                                      RecordLogger<R> logger) throws IOException {
        LoggerConsumer<R> consumer = new LoggerConsumer<>(logger);
        try (ReadableRecordProducer<T> producer = readableFile.openProducer()) {
            producer.readBefore();
            producer.produceStream().forEachOrdered(consumer::consume);
            producer.readAfter();
        }
        return logger;
    }

    public static <R extends Record, T extends R> void write(Stream<T> stream,
                                                             WritableRecordFile<R> writableFile) throws IOException {
        try (WritableRecordConsumer<R> consumer = writableFile.openConsumer()) {
            consumer.writeBefore();
            stream.forEachOrdered(consumer::consume);
            consumer.writeAfter();
        }
    }

    public static <R extends Record, T extends Record> void write(Stream<T> stream,
                                                                  RecordMapper<? super T, ? extends R> recordMapper,
                                                                  WritableRecordFile<R> writableFile) throws IOException {
        try (WritableRecordConsumer<R> consumer = writableFile.openConsumer()) {
            consumer.writeBefore();
            stream.map(recordMapper::map).forEachOrdered(consumer::consume);
            consumer.writeAfter();
        }
    }

    public static <R extends Record, T extends Record> void write(Stream<T> stream,
                                                                  RecordStreamModifier<T, ? extends R> recordStreamModifier,
                                                                  WritableRecordFile<R> writableFile) throws IOException {
        try (WritableRecordConsumer<R> consumer = writableFile.openConsumer()) {
            consumer.writeBefore();
            recordStreamModifier.modify(stream).forEachOrdered(consumer::consume);
            consumer.writeAfter();
        }
    }

    public static <R extends Record, T extends R> void write(RecordProducer<T> producer,
                                                             WritableRecordFile<R> writableFile) throws IOException {
        try (WritableRecordConsumer<R> consumer = writableFile.openConsumer()) {
            consumer.writeBefore();
            producer.produceStream().forEachOrdered(consumer::consume);
            consumer.writeAfter();
        }
    }

    public static <R extends Record, T extends Record> void write(RecordProducer<T> producer,
                                                                  RecordMapper<? super T, ? extends R> recordMapper,
                                                                  WritableRecordFile<R> writableFile) throws IOException {
        try (WritableRecordConsumer<R> consumer = writableFile.openConsumer()) {
            consumer.writeBefore();
            producer.produceStream().map(recordMapper::map).forEachOrdered(consumer::consume);
            consumer.writeAfter();
        }
    }

    public static <R extends Record, T extends Record> void write(RecordProducer<T> producer,
                                                                  RecordStreamModifier<T, ? extends R> recordStreamModifier,
                                                                  WritableRecordFile<R> writableFile) throws IOException {
        try (WritableRecordConsumer<R> consumer = writableFile.openConsumer()) {
            consumer.writeBefore();
            recordStreamModifier.modify(producer.produceStream()).forEachOrdered(consumer::consume);
            consumer.writeAfter();
        }
    }

    public static <R extends Record, T extends R> void write(T record,
                                                             WritableRecordFile<R> writableFile) throws IOException {
        try (WritableRecordConsumer<R> consumer = writableFile.openConsumer()) {
            consumer.writeBefore();
            RecordStreams.of(record).forEachOrdered(consumer::consume);
            consumer.writeAfter();
        }
    }

    public static <R extends Record, T extends R> void convert(ReadableRecordFile<T> readableFile,
                                                               WritableRecordFile<R> writableFile) throws IOException {
        try (ReadableRecordProducer<T> producer = readableFile.openProducer();
             WritableRecordConsumer<R> consumer = writableFile.openConsumer()) {
            producer.readBefore();
            consumer.writeBefore();
            producer.produceStream().forEachOrdered(consumer::consume);
            producer.readAfter();
            consumer.writeAfter();
        }
    }

    public static <R extends Record, T extends R> void convert(ReadableRecordFile<T> readableFile,
                                                               RecordMapper<? super T, ? extends R> recordMapper,
                                                               WritableRecordFile<R> writableFile) throws IOException {
        try (ReadableRecordProducer<T> producer = readableFile.openProducer();
             WritableRecordConsumer<R> consumer = writableFile.openConsumer()) {
            producer.readBefore();
            consumer.writeBefore();
            producer.produceStream().map(recordMapper::map).forEachOrdered(consumer::consume);
            producer.readAfter();
            consumer.writeAfter();
        }
    }

    public static <R extends Record, T extends R> void convert(ReadableRecordFile<T> readableFile,
                                                               RecordStreamModifier<T, ? extends R> recordStreamModifier,
                                                               WritableRecordFile<R> writableFile) throws IOException {
        try (ReadableRecordProducer<T> producer = readableFile.openProducer();
             WritableRecordConsumer<R> consumer = writableFile.openConsumer()) {
            producer.readBefore();
            consumer.writeBefore();
            recordStreamModifier.modify(producer.produceStream()).forEachOrdered(consumer::consume);
            producer.readAfter();
            consumer.writeAfter();
        }
    }

}
