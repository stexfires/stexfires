package stexfires.io.fixedwidth;

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
                               Alignment alignment,
                               Character fillCharacter) {
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

    public Alignment getAlignment() {
        return alignment;
    }

    public Character getFillCharacter() {
        return fillCharacter;
    }

}
