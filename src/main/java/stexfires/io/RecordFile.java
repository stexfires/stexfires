package stexfires.io;

import stexfires.io.spec.RecordFileSpec;

import java.nio.file.Path;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface RecordFile<RFS extends RecordFileSpec> {

    Path path();

    RFS fileSpec();

}
