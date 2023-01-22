package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class MappingDataTypeParser<T> implements DataTypeParser<T> {

    private final List<MappingDataTypePair<T>> pairList;
    private final Supplier<T> nullSourceSupplier;
    private final Supplier<T> emptySourceSupplier;

    public MappingDataTypeParser(@NotNull List<MappingDataTypePair<T>> pairList,
                                 @Nullable Supplier<T> nullSourceSupplier,
                                 @Nullable Supplier<T> emptySourceSupplier) {
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
