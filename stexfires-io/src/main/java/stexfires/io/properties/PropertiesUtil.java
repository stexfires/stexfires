package stexfires.io.properties;

import org.jspecify.annotations.Nullable;
import stexfires.record.KeyRecord;
import stexfires.record.KeyValueRecord;
import stexfires.record.TextRecord;
import stexfires.record.TextRecordStreams;
import stexfires.record.ValueRecord;
import stexfires.record.consumer.MapConsumer;
import stexfires.record.consumer.RecordConsumer;
import stexfires.record.message.NotNullRecordMessage;
import stexfires.record.message.RecordMessage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * This class consists of {@code static} utility methods
 * for operating on {@code Properties}.
 *
 * @see java.util.Properties
 * @since 0.1
 */
public final class PropertiesUtil {

    private PropertiesUtil() {
    }

    public static Properties loadFromFile(Path path, Charset charset) throws IOException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(charset);
        return loadFromFile(new Properties(), path, charset);
    }

    public static Properties loadFromFile(Properties properties, Path path, Charset charset) throws IOException {
        Objects.requireNonNull(properties);
        Objects.requireNonNull(path);
        Objects.requireNonNull(charset);
        try (Reader reader = new FileReader(path.toFile(), charset)) {
            properties.load(reader);
        }
        return properties;
    }

    public static void storeToFile(Properties properties, Path path, Charset charset) throws IOException {
        Objects.requireNonNull(properties);
        Objects.requireNonNull(path);
        Objects.requireNonNull(charset);
        storeToFile(properties, path, charset, null);
    }

    public static void storeToFile(Properties properties, Path path, Charset charset, @Nullable String comments) throws IOException {
        Objects.requireNonNull(properties);
        Objects.requireNonNull(path);
        Objects.requireNonNull(charset);
        try (Writer writer = new FileWriter(path.toFile(), charset)) {
            properties.store(writer, comments);
        }
    }

    public static boolean propertiesAndMapEquals(Properties properties, Map<String, @Nullable String> map) {
        Objects.requireNonNull(properties);
        Objects.requireNonNull(map);
        Set<String> keys = properties.stringPropertyNames();
        if (keys.size() != map.size()) {
            return false;
        }
        return keys.stream().allMatch(key -> Objects.equals(properties.getProperty(key), map.get(key)));
    }

    public static <T extends KeyValueRecord> MapConsumer<T, NavigableMap<String, @Nullable String>> navigableMapConsumer(Comparator<String> stringComparator) {
        Objects.requireNonNull(stringComparator);
        return new MapConsumer<>(new TreeMap<>(stringComparator), KeyRecord::key, ValueRecord::value);
    }

    public static Properties convertMapToProperties(Map<String, @Nullable String> map) {
        Objects.requireNonNull(map);
        Properties properties = new Properties();
        properties.putAll(map);
        return properties;
    }

    public static NavigableMap<String, @Nullable String> convertPropertiesToNavigableMap(Properties properties, Comparator<String> stringComparator) {
        Objects.requireNonNull(properties);
        Objects.requireNonNull(stringComparator);
        NavigableMap<String, @Nullable String> map = new TreeMap<>(stringComparator);
        Set<String> keys = properties.stringPropertyNames();
        for (String key : keys) {
            String value = properties.getProperty(key);
            map.put(key, value);
        }
        return map;
    }

    public static <T extends TextRecord> Properties consumeIntoProperties(Stream<T> recordStream,
                                                                          NotNullRecordMessage<? super T> keyMessage,
                                                                          RecordMessage<? super T> valueMessage) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(keyMessage);
        Objects.requireNonNull(valueMessage);
        Properties properties = new Properties();
        RecordConsumer<T> recordConsumer = (T record) -> {
            String key = keyMessage.createMessage(record);
            String value = valueMessage.createMessage(record);
            if (!key.isEmpty() && value != null) {
                properties.setProperty(key, value);
            }
        };
        TextRecordStreams.consume(recordStream, recordConsumer);
        return properties;
    }

    public static <T extends KeyValueRecord> Properties consumeIntoProperties(Stream<T> recordStream) {
        Objects.requireNonNull(recordStream);
        Properties properties = new Properties();
        RecordConsumer<T> recordConsumer = (T record) -> {
            String key = record.key();
            String value = record.value();
            if (!key.isEmpty() && value != null) {
                properties.setProperty(key, value);
            }
        };
        TextRecordStreams.consume(recordStream, recordConsumer);
        return properties;
    }

}
