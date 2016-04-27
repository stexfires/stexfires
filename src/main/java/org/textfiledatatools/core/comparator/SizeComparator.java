package org.textfiledatatools.core.comparator;

import org.textfiledatatools.core.Record;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Note: this comparator imposes orderings that are inconsistent with equals.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
public class SizeComparator<T extends Record> implements Comparator<T>, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public int compare(T record1, T record2) {
        return Integer.compare(record1.size(), record2.size());
    }

}
