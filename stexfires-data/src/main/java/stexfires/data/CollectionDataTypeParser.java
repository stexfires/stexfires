package stexfires.data;

import org.jspecify.annotations.Nullable;
import stexfires.util.TextSplitters;

import java.util.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;

/**
 * @since 0.1
 */
public final class CollectionDataTypeParser<T, C extends Collection<@Nullable T>> implements DataTypeParser<C> {

    private static final int REGEX_SPLIT_LIMIT = -1;

    private final @Nullable String prefix;
    private final @Nullable String suffix;
    private final Function<String, Stream<@Nullable String>> stringSplitter;
    private final DataTypeParser<T> dataTypeParser;
    private final Function<Stream<@Nullable T>, @Nullable C> streamConverter;
    private final @Nullable Supplier<@Nullable C> nullSourceSupplier;
    private final @Nullable Supplier<@Nullable C> emptySourceSupplier;

    public CollectionDataTypeParser(@Nullable String prefix,
                                    @Nullable String suffix,
                                    Function<String, Stream<@Nullable String>> stringSplitter,
                                    DataTypeParser<T> dataTypeParser,
                                    Function<Stream<@Nullable T>, @Nullable C> streamConverter,
                                    @Nullable Supplier<@Nullable C> nullSourceSupplier,
                                    @Nullable Supplier<@Nullable C> emptySourceSupplier) {
        Objects.requireNonNull(stringSplitter);
        Objects.requireNonNull(dataTypeParser);
        Objects.requireNonNull(streamConverter);
        this.prefix = prefix;
        this.suffix = suffix;
        this.stringSplitter = stringSplitter;
        this.dataTypeParser = dataTypeParser;
        this.streamConverter = streamConverter;
        this.nullSourceSupplier = nullSourceSupplier;
        this.emptySourceSupplier = emptySourceSupplier;
    }

    public static <T> Function<Stream<@Nullable T>, List<@Nullable T>> streamToListConverter() {
        return Stream::toList;
    }

    public static <T, S extends Set<@Nullable T>> Function<Stream<@Nullable T>, S> streamToSetConverter(S resultSet,
                                                                                                        ConverterValidator converterValidator) {
        Objects.requireNonNull(resultSet);
        Objects.requireNonNull(converterValidator);
        return stream -> {
            if (converterValidator.checkInitialSize() && !resultSet.isEmpty()) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Set is not empty: size=" + resultSet.size());
            }
            List<@Nullable T> list = stream.toList();
            resultSet.addAll(list);
            if (converterValidator.checkIdenticalSize() && (list.size() != resultSet.size())) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Different size: " + resultSet.size() + " != " + list.size());
            }
            if (converterValidator.checkOrder()) {
                int index = 0;
                for (@Nullable T setElement : resultSet) {
                    if (!Objects.equals(setElement, list.get(index))) {
                        throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Different order: index=" + index);
                    }
                    index++;
                }
            }
            return resultSet;
        };
    }

    public static <T> CollectionDataTypeParser<T, List<T>> withDelimiterAsList(String delimiter,
                                                                               @Nullable String prefix,
                                                                               @Nullable String suffix,
                                                                               DataTypeParser<T> dataTypeParser,
                                                                               @Nullable Supplier<@Nullable List<T>> nullSourceSupplier,
                                                                               @Nullable Supplier<@Nullable List<T>> emptySourceSupplier) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(dataTypeParser);
        if (delimiter.isEmpty()) {
            throw new IllegalArgumentException("delimiter is empty");
        }
        return new CollectionDataTypeParser<>(prefix, suffix,
                TextSplitters.splitByRegexFunction(Pattern.quote(delimiter), REGEX_SPLIT_LIMIT),
                dataTypeParser,
                streamToListConverter(),
                nullSourceSupplier, emptySourceSupplier);
    }

    public static <T> CollectionDataTypeParser<T, List<T>> withLengthAsList(int length,
                                                                            @Nullable String prefix,
                                                                            @Nullable String suffix,
                                                                            DataTypeParser<T> dataTypeParser,
                                                                            @Nullable Supplier<@Nullable List<T>> nullSourceSupplier,
                                                                            @Nullable Supplier<@Nullable List<T>> emptySourceSupplier) {
        Objects.requireNonNull(dataTypeParser);
        if (length < 1) {
            throw new IllegalArgumentException("length < 1");
        }
        return new CollectionDataTypeParser<>(prefix, suffix,
                TextSplitters.splitByLengthFunction(length),
                dataTypeParser,
                streamToListConverter(),
                nullSourceSupplier, emptySourceSupplier);
    }

    private String removePrefixAndSuffix(String source) {
        int beginIndex = 0;
        int endIndex = source.length();
        boolean subString = false;

        // Prefix
        if ((prefix != null) && !prefix.isEmpty()) {
            if (source.startsWith(prefix)) {
                beginIndex = prefix.length();
                subString = true;
            } else {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Source does not start with prefix.");
            }
        }
        // Suffix
        if ((suffix != null) && !suffix.isEmpty()) {
            if (source.endsWith(suffix)) {
                endIndex = source.length() - suffix.length();
                subString = true;
            } else {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Source does not end with suffix.");
            }
        }

        return subString ? source.substring(beginIndex, endIndex) : source;
    }

    @Override
    public @Nullable C parse(@Nullable String source) throws DataTypeConverterException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else if (source.isEmpty()) {
            return handleEmptySource(emptySourceSupplier);
        } else {
            return streamConverter.apply(stringSplitter.apply(removePrefixAndSuffix(source))
                                                       .map(dataTypeParser::parse));
        }
    }

    public enum ConverterValidator {

        NO_VALIDATION,
        INITIALLY_EMPTY,
        IDENTICAL_SIZE,
        SAME_ORDER;

        public final boolean checkInitialSize() {
            return (this == INITIALLY_EMPTY) || (this == IDENTICAL_SIZE) || (this == SAME_ORDER);
        }

        public final boolean checkIdenticalSize() {
            return (this == IDENTICAL_SIZE) || (this == SAME_ORDER);
        }

        public final boolean checkOrder() {
            return this == SAME_ORDER;
        }

    }

}
