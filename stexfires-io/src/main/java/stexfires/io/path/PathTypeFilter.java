package stexfires.io.path;

import stexfires.record.filter.RecordFilter;

import java.util.Objects;

/**
 * @since 0.1
 */
public final class PathTypeFilter<T extends PathRecord> implements RecordFilter<T> {

    private final PathType pathType;

    public PathTypeFilter(PathType pathType) {
        Objects.requireNonNull(pathType);
        this.pathType = pathType;
    }

    @Override
    public boolean isValid(T record) {
        return record.pathType() == pathType;
    }

}
