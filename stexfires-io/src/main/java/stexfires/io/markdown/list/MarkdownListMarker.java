package stexfires.io.markdown.list;

import org.jspecify.annotations.Nullable;
import stexfires.util.Strings;

import java.util.*;

/**
 * An enum of Markdown list markers.
 * <p>
 * There are two types of list markers:
 * <ul>
 *     <li>unordered (bullet)</li>
 *     <li>ordered (number)</li>
 * </ul>
 * <p>
 * Each list marker has a marker character and a boolean flag to indicate if the list is ordered.
 *
 * @see stexfires.io.markdown.list.MarkdownListFileSpec#LIST_MARKER_SEPARATOR
 * @since 0.1
 */
public enum MarkdownListMarker {

    BULLET_ASTERISK('*', false),
    BULLET_HYPHEN_MINUS('-', false),
    BULLET_PLUS_SIGN('+', false),
    ORDERED_PERIOD('.', true),
    ORDERED_PARENTHESIS(')', true);

    private final char markerCharacter;
    private final boolean ordered;

    MarkdownListMarker(char markerCharacter, boolean ordered) {
        this.markerCharacter = markerCharacter;
        this.ordered = ordered;
    }

    public static Optional<MarkdownListMarker> of(char character) {
        return switch (character) {
            case '*' -> Optional.of(BULLET_ASTERISK);
            case '-' -> Optional.of(BULLET_HYPHEN_MINUS);
            case '+' -> Optional.of(BULLET_PLUS_SIGN);
            case '.' -> Optional.of(ORDERED_PERIOD);
            case ')' -> Optional.of(ORDERED_PARENTHESIS);
            default -> Optional.empty();
        };
    }

    public static Optional<SplitResult> split(@Nullable String line) {
        if ((line == null) || line.isEmpty()) {
            return Optional.empty();
        }

        int firstNonWhitespaceIndex = -1;
        int markerIndex = -1;
        Optional<MarkdownListMarker> marker = Optional.empty();
        for (int index = 0; index < line.length(); index++) {
            char c = line.charAt(index);

            if (!Character.isWhitespace(c)) {
                if (firstNonWhitespaceIndex == -1) {
                    firstNonWhitespaceIndex = index;
                }
                marker = MarkdownListMarker.of(c);
                if (marker.isPresent()) {
                    markerIndex = index;
                    break;
                }
            }
        }

        if (marker.isPresent()) {
            boolean valid;

            Long number = null;
            if (marker.get().isOrdered()) {
                if (firstNonWhitespaceIndex < markerIndex) {
                    try {
                        number = Long.parseLong(line.substring(firstNonWhitespaceIndex, markerIndex));
                    } catch (NumberFormatException ignored) {
                    }
                }
                valid = (number != null) && (number > 0);
            } else {
                valid = (firstNonWhitespaceIndex == markerIndex);
            }

            String value = Strings.EMPTY;
            if (line.length() > (markerIndex + 1)) {
                if (Character.isWhitespace(line.charAt(markerIndex + 1))) {
                    if (line.length() > (markerIndex + 2)) {
                        value = line.substring(markerIndex + 2);
                    }
                } else {
                    valid = false;
                }
            }

            if (valid) {
                String linePrefix = line.substring(0, Math.min(line.length(), markerIndex + 2));
                String indentation = line.substring(0, firstNonWhitespaceIndex);
                return Optional.of(new SplitResult(linePrefix, indentation, number, marker.get(), value));
            }
        }

        return Optional.empty();
    }

    public final boolean isOrdered() {
        return ordered;
    }

    public final char markerCharacter() {
        return markerCharacter;
    }

    public final String markerWithSeparator() {
        return markerCharacter + MarkdownListFileSpec.LIST_MARKER_SEPARATOR;
    }

    public final String linePrefix(long lineNumber) {
        if (ordered) {
            return lineNumber + markerWithSeparator();
        } else {
            return markerWithSeparator();
        }
    }

    public record SplitResult(
            String linePrefix,
            String indentation,
            @Nullable Long number,
            MarkdownListMarker marker,
            String value) {

        public SplitResult {
            Objects.requireNonNull(linePrefix);
            Objects.requireNonNull(indentation);
            Objects.requireNonNull(marker);
            if (marker.isOrdered()) {
                Objects.requireNonNull(number);
            }
            Objects.requireNonNull(value);
        }

    }

}
