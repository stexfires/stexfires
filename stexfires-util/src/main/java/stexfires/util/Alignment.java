package stexfires.util;

/**
 * Enum with three alignment types: {@code START, CENTER, END}.
 *
 * @since 0.1
 */
public enum Alignment {

    START, CENTER, END;

    /**
     * Shifts the alignment to the start.
     * <p>
     * {@code START -> START}
     * <br>
     * {@code CENTER -> START}
     * <br>
     * {@code END -> CENTER}
     *
     * @return alignment shifted to the start
     */
    public final Alignment shiftToStart() {
        return switch (this) {
            case START, CENTER -> START;
            case END -> CENTER;
        };
    }

    /**
     * Shifts the alignment to the end.
     * <p>
     * {@code START -> CENTER}
     * <br>
     * {@code CENTER -> END}
     * <br>
     * {@code END -> END}
     *
     * @return alignment shifted to the end
     */
    public final Alignment shiftToEnd() {
        return switch (this) {
            case START -> CENTER;
            case CENTER, END -> END;
        };
    }

    /**
     * Mirrors the alignment.
     * <p>
     * {@code START -> END}
     * <br>
     * {@code CENTER -> CENTER}
     * <br>
     * {@code END -> START}
     *
     * @return alignment mirrored
     */
    public final Alignment mirror() {
        return switch (this) {
            case START -> END;
            case CENTER -> CENTER;
            case END -> START;
        };
    }

}
