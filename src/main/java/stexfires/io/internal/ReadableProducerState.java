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
            if (newState != CLOSE) {
                throw new IllegalStateException("Wrong ReadableProducerState! " + currentState + " -> " + newState);
            }
        }
    }

    public ReadableProducerState validate(ReadableProducerState currentState) throws IllegalStateException {
        validateStates(currentState, this);
        return this;
    }

}
