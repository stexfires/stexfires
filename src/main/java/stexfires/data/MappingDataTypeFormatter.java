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
public final class MappingDataTypeFormatter<T> implements DataTypeFormatter<T> {

    private final List<MappingDataTypePair<T>> pairList;
    private final Supplier<String> nullSourceSupplier;

    public MappingDataTypeFormatter(@NotNull List<MappingDataTypePair<T>> pairList,
                                    @Nullable Supplier<String> nullSourceSupplier) {
        Objects.requireNonNull(pairList);
        this.pairList = pairList;
        this.nullSourceSupplier = nullSourceSupplier;
    }

    @Override
    public @Nullable String format(@Nullable T source) throws DataTypeFormatException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else {
            return pairList.stream()
                           .filter(pair -> source.equals(pair.dataValue()))
                           .findFirst()
                           .orElseThrow(() -> new DataTypeFormatException("Unsupported source: " + source))
                           .stringValue();
        }
    }

}
