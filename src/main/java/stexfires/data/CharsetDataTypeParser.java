package stexfires.data;

import org.jetbrains.annotations.Nullable;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class CharsetDataTypeParser implements DataTypeParser<Charset> {

    private final Supplier<Charset> nullSourceSupplier;
    private final Supplier<Charset> emptySourceSupplier;

    public CharsetDataTypeParser(@Nullable Supplier<Charset> nullSourceSupplier,
                                 @Nullable Supplier<Charset> emptySourceSupplier) {
        this.nullSourceSupplier = nullSourceSupplier;
        this.emptySourceSupplier = emptySourceSupplier;
    }

    @Override
    public @Nullable Charset parse(@Nullable String source) throws DataTypeParseException {
        if (source == null) {
            if (nullSourceSupplier == null) {
                throw new DataTypeParseException("Source is null.");
            } else {
                return nullSourceSupplier.get();
            }
        } else if (source.isEmpty()) {
            if (emptySourceSupplier == null) {
                throw new DataTypeParseException("Source is empty.");
            } else {
                return emptySourceSupplier.get();
            }
        } else {
            try {
                return Charset.forName(source);
            } catch (IllegalCharsetNameException | UnsupportedCharsetException e) {
                throw new DataTypeParseException("Illegal or unsupported charset: " + e.getMessage());
            }
        }
    }

}
