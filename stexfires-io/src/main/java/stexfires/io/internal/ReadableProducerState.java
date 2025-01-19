package stexfires.io.internal;

import java.util.*;

/**
 * @since 0.1
 */
enum ReadableProducerState {
    OPEN,
    READ_BEFORE,
    READ_RECORDS,
    READ_AFTER,
    CLOSE;

    private static void validateStates(ReadableProducerState currentState, ReadableProducerState newState) throws IllegalStateException {
        Objects.requireNonNull(currentState);
        Objects.requireNonNull(newState);
        if (currentState.ordinal() + 1 != newState.ordinal()) {
            if (newState != CLOSE) {
                throw new IllegalStateException("Wrong ReadableProducerState! " + currentState + " -> " + newState);
            }
        }
    }

    final ReadableProducerState validate(ReadableProducerState currentState) throws IllegalStateException {
        validateStates(currentState, this);
        return this;
    }

}
