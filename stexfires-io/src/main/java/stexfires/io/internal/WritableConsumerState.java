package stexfires.io.internal;

import java.util.*;

/**
 * @since 0.1
 */
enum WritableConsumerState {
    OPEN,
    WRITE_BEFORE,
    WRITE_RECORDS,
    WRITE_AFTER,
    CLOSE;

    private static void validateStates(WritableConsumerState currentState, WritableConsumerState newState) throws IllegalStateException {
        Objects.requireNonNull(currentState);
        Objects.requireNonNull(newState);
        if (currentState.ordinal() + 1 != newState.ordinal()) {
            if (newState != CLOSE
                    && !(currentState == WRITE_BEFORE && newState == WRITE_AFTER)
                    && !(currentState == WRITE_RECORDS && newState == WRITE_RECORDS)) {
                throw new IllegalStateException("Wrong WritableConsumerState! " + currentState + " -> " + newState);
            }
        }
    }

    final void validateNotClosed() throws IllegalStateException {
        if (this == CLOSE) {
            throw new IllegalStateException("Wrong WritableConsumerState! " + this);
        }
    }

    final WritableConsumerState validate(WritableConsumerState currentState) throws IllegalStateException {
        validateStates(currentState, this);
        return this;
    }

}
