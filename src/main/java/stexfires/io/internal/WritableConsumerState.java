package stexfires.io.internal;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
enum WritableConsumerState {
    OPEN,
    WRITE_BEFORE,
    WRITE_RECORDS,
    WRITE_AFTER,
    CLOSE;

    public static void validateStates(WritableConsumerState currentState, WritableConsumerState newState) throws IllegalStateException {
        if (currentState.ordinal() + 1 != newState.ordinal()) {
            if (newState != CLOSE
                    && !(currentState == WRITE_BEFORE && newState == WRITE_AFTER)
                    && !(currentState == WRITE_RECORDS && newState == WRITE_RECORDS)) {
                throw new IllegalStateException("Wrong WritableConsumerState! " + currentState + " -> " + newState);
            }
        }
    }

    public void validateNotClosed() throws IllegalStateException {
        if (this == CLOSE) {
            throw new IllegalStateException("Wrong WritableConsumerState! " + this);
        }
    }

    public WritableConsumerState validate(WritableConsumerState currentState) throws IllegalStateException {
        validateStates(currentState, this);
        return this;
    }

}
