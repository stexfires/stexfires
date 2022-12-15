package stexfires.io;

import org.jetbrains.annotations.Nullable;
import stexfires.record.producer.ProducerException;
import stexfires.record.producer.UncheckedProducerException;
import stexfires.util.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public enum ProducerReadLineHandling {

    NO_HANDLING(r -> {
        try {
            return r.readLine();
        } catch (IOException e) {
            throw new UncheckedProducerException(new ProducerException("An IOException occurred while reading the line.", e));
        }
    }),
    TREAT_EMPTY_LINE_LIKE_END(r -> {
        try {
            String line = r.readLine();

            if (line != null && line.isEmpty()) {
                line = null;
            }
            return line;
        } catch (IOException e) {
            throw new UncheckedProducerException(new ProducerException("An IOException occurred while reading the line.", e));
        }
    }),
    TREAT_BLANK_LINE_LIKE_END(r -> {
        try {
            String line = r.readLine();

            if (line != null && line.isBlank()) {
                line = null;
            }
            return line;
        } catch (IOException e) {
            throw new UncheckedProducerException(new ProducerException("An IOException occurred while reading the line.", e));
        }
    }),
    THROW_EXCEPTION_ON_EMPTY_LINE(r -> {
        try {
            String line = r.readLine();

            if (line != null && line.isEmpty()) {
                throw new UncheckedProducerException(new ProducerException("An empty line was read in."));
            }
            return line;
        } catch (IOException e) {
            throw new UncheckedProducerException(new ProducerException("An IOException occurred while reading the line.", e));
        }
    }),
    THROW_EXCEPTION_ON_BLANK_LINE(r -> {
        try {
            String line = r.readLine();

            if (line != null && line.isBlank()) {
                throw new UncheckedProducerException(new ProducerException("A blank line was read in."));
            }
            return line;
        } catch (IOException e) {
            throw new UncheckedProducerException(new ProducerException("An IOException occurred while reading the line.", e));
        }
    }),
    CONVERT_EMPTY_LINE_TO_SPACE(r -> {
        try {
            String line = r.readLine();

            if (line != null && line.isEmpty()) {
                line = Strings.SPACE;
            }
            return line;
        } catch (IOException e) {
            throw new UncheckedProducerException(new ProducerException("An IOException occurred while reading the line.", e));
        }
    }),
    CONVERT_BLANK_LINE_TO_SPACE(r -> {
        try {
            String line = r.readLine();

            if (line != null && line.isBlank()) {
                line = Strings.SPACE;
            }
            return line;
        } catch (IOException e) {
            throw new UncheckedProducerException(new ProducerException("An IOException occurred while reading the line.", e));
        }
    }),
    CONVERT_BLANK_LINE_TO_EMPTY(r -> {
        try {
            String line = r.readLine();

            if (line != null && line.isBlank()) {
                return Strings.EMPTY;
            }
            return line;
        } catch (IOException e) {
            throw new UncheckedProducerException(new ProducerException("An IOException occurred while reading the line.", e));
        }
    }),
    SKIP_EMPTY_LINE(r -> {
        try {
            String line;
            do {
                line = r.readLine();
            } while (line != null && line.isEmpty());

            return line;
        } catch (IOException e) {
            throw new UncheckedProducerException(new ProducerException("An IOException occurred while reading the line.", e));
        }
    }),
    SKIP_BLANK_LINE(r -> {
        try {
            String line;
            do {
                line = r.readLine();
            } while (line != null && line.isBlank());

            return line;
        } catch (IOException e) {
            throw new UncheckedProducerException(new ProducerException("An IOException occurred while reading the line.", e));
        }
    });

    private final Function<BufferedReader, String> function;

    ProducerReadLineHandling(Function<BufferedReader, String> function) {
        Objects.requireNonNull(function);
        this.function = function;
    }

    public @Nullable String readAndHandleLine(BufferedReader bufferedReader) throws UncheckedProducerException {
        Objects.requireNonNull(bufferedReader);
        return function.apply(bufferedReader);
    }

}
