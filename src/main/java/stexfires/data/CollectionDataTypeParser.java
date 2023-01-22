package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.util.Strings;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class CollectionDataTypeParser<T, C extends Collection<T>> implements DataTypeParser<C> {

    private static final int REGEX_SPLIT_LIMIT = -1;

    private final String prefix;
    private final String suffix;
    private final Function<String, Stream<String>> stringSplitter;
    private final DataTypeParser<T> dataTypeParser;
    private final Function<Stream<T>, C> streamConverter;
    private final Supplier<C> nullSourceSupplier;
    private final Supplier<C> emptySourceSupplier;

    public CollectionDataTypeParser(@Nullable String prefix,
                                    @Nullable String suffix,
                                    @NotNull Function<String, Stream<String>> stringSplitter,
                                    @NotNull DataTypeParser<T> dataTypeParser,
                                    @NotNull Function<Stream<T>, C> streamConverter,
                                    @Nullable Supplier<C> nullSourceSupplier,
                                    @Nullable Supplier<C> emptySourceSupplier) {
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

    public static <T> Function<Stream<T>, List<T>> streamToListConverter() {
        return Stream::toList;
    }

    public static <T, S extends Set<T>> Function<Stream<T>, S> streamToSetConverter(S resultSet, ConverterValidator converterValidator) {
        Objects.requireNonNull(resultSet);
        Objects.requireNonNull(converterValidator);
        return stream -> {
            if (converterValidator.checkInitialSize() && !resultSet.isEmpty()) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Set is not empty: size=" + resultSet.size());
            }
            List<T> list = stream.toList();
            resultSet.addAll(list);
            if (converterValidator.checkIdenticalSize() && (list.size() != resultSet.size())) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Different size: " + resultSet.size() + " != " + list.size());
            }
            if (converterValidator.checkOrder()) {
                int index = 0;
                for (T setElement : resultSet) {
                    T listElement = list.get(index);
                    if (!Objects.equals(setElement, listElement)) {
                        throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Different order: index=" + index);
                    }
                    index++;
                }
            }
            return resultSet;
        };
    }

    public static <T> CollectionDataTypeParser<T, List<T>> withDelimiterAsList(@Nullable String prefix,
                                                                               @Nullable String suffix,
                                                                               @NotNull String delimiter,
                                                                               @NotNull DataTypeParser<T> dataTypeParser,
                                                                               @Nullable Supplier<List<T>> nullSourceSupplier,
                                                                               @Nullable Supplier<List<T>> emptySourceSupplier) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(dataTypeParser);
        if (delimiter.isEmpty()) {
            throw new IllegalArgumentException("delimiter is empty");
        }
        return new CollectionDataTypeParser<>(prefix, suffix,
                Strings.splitTextByRegexFunction(Pattern.quote(delimiter), REGEX_SPLIT_LIMIT),
                dataTypeParser,
                streamToListConverter(),
                nullSourceSupplier, emptySourceSupplier);
    }

    public static <T> CollectionDataTypeParser<T, List<T>> withLengthAsList(@Nullable String prefix,
                                                                            @Nullable String suffix,
                                                                            int length,
                                                                            @NotNull DataTypeParser<T> dataTypeParser,
                                                                            @Nullable Supplier<List<T>> nullSourceSupplier,
                                                                            @Nullable Supplier<List<T>> emptySourceSupplier) {
        Objects.requireNonNull(dataTypeParser);
        if (length < 1) {
            throw new IllegalArgumentException("length < 1");
        }
        return new CollectionDataTypeParser<>(prefix, suffix,
                Strings.splitTextByLengthFunction(length),
                dataTypeParser,
                streamToListConverter(),
                nullSourceSupplier, emptySourceSupplier);
    }

    private @NotNull String removePrefixAndSuffix(@NotNull String source) {
        int beginIndex = 0;
        int endIndex = source.length();
        boolean subString = false;

        // Prefix
        if (prefix != null && !prefix.isEmpty()) {
            if (source.startsWith(prefix)) {
                beginIndex = prefix.length();
                subString = true;
            } else {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Source does not start with prefix.");
            }
        }
        // Suffix
        if (suffix != null && !suffix.isEmpty()) {
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
            return streamConverter.apply(
                    stringSplitter.apply(removePrefixAndSuffix(source))
                                  .map(dataTypeParser::parse));
        }
    }

    public enum ConverterValidator {

        NO_VALIDATION,
        INITIALLY_EMPTY,
        IDENTICAL_SIZE,
        SAME_ORDER;

        public final boolean checkInitialSize() {
            return this == INITIALLY_EMPTY || this == IDENTICAL_SIZE || this == SAME_ORDER;
        }

        public final boolean checkIdenticalSize() {
            return this == IDENTICAL_SIZE || this == SAME_ORDER;
        }

        public final boolean checkOrder() {
            return this == SAME_ORDER;
        }

    }

}
