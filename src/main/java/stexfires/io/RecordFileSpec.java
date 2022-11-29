package stexfires.io;

import stexfires.util.CharsetCoding;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface RecordFileSpec {

    CharsetCoding DEFAULT_CHARSET_CODING = CharsetCoding.UTF_8_REPORTING;

    CharsetCoding charsetCoding();

}
