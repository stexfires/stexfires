package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.util.Strings;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class ListDataTypeParser<T> implements DataTypeParser<List<T>> {

    private static final int REGEX_SPLIT_LIMIT = -1;

    private final String prefix;
    private final String suffix;
    private final Function<String, Stream<String>> stringSplitter;
    private final DataTypeParser<T> dataTypeParser;
    private final Supplier<List<T>> nullSourceSupplier;
    private final Supplier<List<T>> emptySourceSupplier;

    public ListDataTypeParser(@Nullable String prefix,
                              @Nullable String suffix,
                              @NotNull Function<String, Stream<String>> stringSplitter,
                              @NotNull DataTypeParser<T> dataTypeParser,
                              @Nullable Supplier<List<T>> nullSourceSupplier,
                              @Nullable Supplier<List<T>> emptySourceSupplier) {
        Objects.requireNonNull(stringSplitter);
        Objects.requireNonNull(dataTypeParser);
        this.prefix = prefix;
        this.suffix = suffix;
        this.stringSplitter = stringSplitter;
        this.dataTypeParser = dataTypeParser;
        this.nullSourceSupplier = nullSourceSupplier;
        this.emptySourceSupplier = emptySourceSupplier;
    }

    public static <T> ListDataTypeParser<T> withDelimiter(@Nullable String prefix,
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
        return new ListDataTypeParser<>(prefix, suffix,
                Strings.splitTextByRegexFunction(Pattern.quote(delimiter), REGEX_SPLIT_LIMIT), dataTypeParser,
                nullSourceSupplier, emptySourceSupplier);
    }

    public static <T> ListDataTypeParser<T> withValueLength(@Nullable String prefix,
                                                            @Nullable String suffix,
                                                            int valueLength,
                                                            @NotNull DataTypeParser<T> dataTypeParser,
                                                            @Nullable Supplier<List<T>> nullSourceSupplier,
                                                            @Nullable Supplier<List<T>> emptySourceSupplier) {
        Objects.requireNonNull(dataTypeParser);
        if (valueLength < 1) {
            throw new IllegalArgumentException("valueLength < 1");
        }
        return new ListDataTypeParser<>(prefix, suffix,
                Strings.splitTextByLengthFunction(valueLength), dataTypeParser,
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
                throw new DataTypeParseException("Source does not start with prefix.");
            }
        }
        // Suffix
        if (suffix != null && !suffix.isEmpty()) {
            if (source.endsWith(suffix)) {
                endIndex = source.length() - suffix.length();
                subString = true;
            } else {
                throw new DataTypeParseException("Source does not end with suffix.");
            }
        }

        return subString ? source.substring(beginIndex, endIndex) : source;
    }

    @Override
    public @Nullable List<T> parse(@Nullable String source) throws DataTypeParseException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else if (source.isEmpty()) {
            return handleEmptySource(emptySourceSupplier);
        } else {
            return stringSplitter.apply(removePrefixAndSuffix(source))
                                 .map(dataTypeParser::parse)
                                 .toList();
        }
    }

}
