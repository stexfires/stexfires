package stexfires.io.path;

import stexfires.record.filter.RecordFilter;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class PathTypeFilter<T extends PathRecord> implements RecordFilter<T> {

    protected final PathType pathType;

    public PathTypeFilter(PathType pathType) {
        Objects.requireNonNull(pathType);
        this.pathType = pathType;
    }

    @Override
    public boolean isValid(T record) {
        return record.pathType() == pathType;
    }

}
