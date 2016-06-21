package stexfires.io.internal;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
enum ReadableProducerState {
    OPEN,
    READ_BEFORE,
    READ_RECORDS,
    READ_AFTER,
    CLOSE;

    public static void validateStates(ReadableProducerState currentState, ReadableProducerState newState) throws IllegalStateException {
        if (currentState.ordinal() + 1 != newState.ordinal()) {
            throw new IllegalStateException("currentState = " + currentState);
        }
    }

    public void validateNotClosed() throws IllegalStateException {
        if (this == CLOSE) {
            throw new IllegalStateException("CLOSE");
        }
    }

    public ReadableProducerState validate(ReadableProducerState currentState) throws IllegalStateException {
        validateStates(currentState, this);
        return this;
    }

}
