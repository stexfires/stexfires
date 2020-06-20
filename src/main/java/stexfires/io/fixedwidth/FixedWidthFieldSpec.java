package stexfires.io.fixedwidth;

import org.jetbrains.annotations.Nullable;
import stexfires.util.Alignment;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class FixedWidthFieldSpec {

    private final int startIndex;
    private final int width;
    private final Alignment alignment;
    private final Character fillCharacter;

    public FixedWidthFieldSpec(int startIndex,
                               int width) {
        this(startIndex, width, null, null);
    }

    public FixedWidthFieldSpec(int startIndex,
                               int width,
                               @Nullable Alignment alignment,
                               @Nullable Character fillCharacter) {
        this.startIndex = startIndex;
        this.width = width;
        this.alignment = alignment;
        this.fillCharacter = fillCharacter;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getWidth() {
        return width;
    }

    public @Nullable Alignment getAlignment() {
        return alignment;
    }

    public @Nullable Character getFillCharacter() {
        return fillCharacter;
    }

}
