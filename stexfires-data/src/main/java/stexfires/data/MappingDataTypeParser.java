package stexfires.data;

import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @since 0.1
 */
public final class MappingDataTypeParser<T> implements DataTypeParser<T> {

    private final List<MappingDataTypePair<T>> pairList;
    private final @Nullable Supplier<@Nullable T> nullSourceSupplier;
    private final @Nullable Supplier<@Nullable T> emptySourceSupplier;

    public MappingDataTypeParser(List<MappingDataTypePair<T>> pairList,
                                 @Nullable Supplier<@Nullable T> nullSourceSupplier,
                                 @Nullable Supplier<@Nullable T> emptySourceSupplier) {
        Objects.requireNonNull(pairList);
        this.pairList = pairList;
        this.nullSourceSupplier = nullSourceSupplier;
        this.emptySourceSupplier = emptySourceSupplier;
    }

    @Override
    public @Nullable T parse(@Nullable String source) throws DataTypeConverterException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else if (source.isEmpty()) {
            return handleEmptySource(emptySourceSupplier);
        } else {
            return pairList.stream()
                           .filter(pair -> source.equals(pair.stringValue()))
                           .findFirst()
                           .orElseThrow(() -> new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Unsupported source: " + source))
                           .dataValue();
        }
    }

}
