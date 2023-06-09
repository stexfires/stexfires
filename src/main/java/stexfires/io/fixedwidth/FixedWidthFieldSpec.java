package stexfires.io.fixedwidth;

import org.jetbrains.annotations.Nullable;
import stexfires.util.Alignment;

/**
 * @since 0.1
 */
public record FixedWidthFieldSpec(
        int startIndex,
        int width,
        @Nullable Alignment alignment,
        @Nullable Character fillCharacter
) {

    public FixedWidthFieldSpec {
        if (startIndex < 0) {
            throw new IllegalArgumentException("startIndex < 0");
        }
        if (width < 0) {
            throw new IllegalArgumentException("width < 0");
        }
    }

    public FixedWidthFieldSpec(int startIndex, int width) {
        this(startIndex, width, null, null);
    }

    Character determineFillCharacter(FixedWidthFileSpec fileSpec) {
        return fillCharacter != null ? fillCharacter : fileSpec.fillCharacter();
    }

    Alignment determineAlignment(FixedWidthFileSpec fileSpec) {
        return alignment != null ? alignment : fileSpec.alignment();
    }

}
