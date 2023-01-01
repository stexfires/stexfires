package stexfires.data;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public record MappingDataTypePair<T>(@NotNull T dataValue,
                                     @NotNull String stringValue) {
    public MappingDataTypePair {
        Objects.requireNonNull(dataValue);
        Objects.requireNonNull(stringValue);
    }

    public static <T> List<MappingDataTypePair<T>> createPairList(Stream<T> dataValueStream,
                                                                  Function<T, String> mapper) {
        Objects.requireNonNull(dataValueStream);
        Objects.requireNonNull(mapper);
        return dataValueStream.map(dataValue -> new MappingDataTypePair<>(dataValue, mapper.apply(dataValue)))
                              .toList();
    }

}
