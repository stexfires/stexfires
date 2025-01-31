package stexfires.data;

import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.function.*;

/**
 * @since 0.1
 */
public final class MappingDataTypeFormatter<T> implements DataTypeFormatter<T> {

    private final List<MappingDataTypePair<T>> pairList;
    private final @Nullable Supplier<@Nullable String> nullSourceSupplier;

    public MappingDataTypeFormatter(List<MappingDataTypePair<T>> pairList,
                                    @Nullable Supplier<@Nullable String> nullSourceSupplier) {
        Objects.requireNonNull(pairList);
        this.pairList = pairList;
        this.nullSourceSupplier = nullSourceSupplier;
    }

    @Override
    public @Nullable String format(@Nullable T source) throws DataTypeConverterException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else {
            return pairList.stream()
                           .filter(pair -> source.equals(pair.dataValue()))
                           .findFirst()
                           .orElseThrow(() -> new DataTypeConverterException(DataTypeConverterException.Type.Formatter, "Unsupported source: " + source))
                           .stringValue();
        }
    }

}
