package stexfires.data;

import java.nio.charset.CodingErrorAction;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public record MappingDataTypePair<T>(T dataValue,
                                     String stringValue) {

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

    public static List<MappingDataTypePair<CodingErrorAction>> createPairListCodingErrorAction() {
        return MappingDataTypePair.createPairList(Stream.of(
                CodingErrorAction.IGNORE, CodingErrorAction.REPLACE, CodingErrorAction.REPORT
        ), CodingErrorAction::toString);
    }

}
