package stexfires.io.json;

import java.util.*;

/**
 * This class consists of {@code static} utility methods
 * for operating with JSON files and streams.
 * <p>
 * RFC 8259: The JavaScript Object Notation (JSON) Data Interchange Format
 *
 * @see <a href="https://www.json.org/json-en.html">json.org</a>
 * @see <a href="https://www.rfc-editor.org/info/rfc8259">RFC 8259 (rfc-editor)</a>
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc8259">RFC 8259 (ietf)</a>
 * @see <a href="https://www.rfc-editor.org/rfc/rfc7464.html">RFC 7464 (rfc-editor)</a>
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc7464">RFC 7464 (ietf)</a>
 * @see <a href="https://en.wikipedia.org/wiki/JSON">JSON (wikipedia)</a>
 * @see <a href="https://en.wikipedia.org/wiki/JSON_streaming">JSON streaming (wikipedia)</a>
 * @since 0.1
 */
@SuppressWarnings({"HardcodedLineSeparator", "GrazieInspection"})
public final class JsonUtil {

    // Literal name
    public static final String LITERAL_FALSE = "false"; // false = %x66.61.6c.73.65   ; false
    public static final String LITERAL_NULL = "null"; // null  = %x6e.75.6c.6c      ; null
    public static final String LITERAL_TRUE = "true"; // true  = %x74.72.75.65      ; true

    // Whitespace
    public static final String WS_SPACE = " "; // %x20 ; Space
    public static final String WS_HORIZONTAL_TAB = "\t"; // %x09 ; Horizontal tab
    public static final String WS_LINE_FEED = "\n"; // %x0A ; Line feed or New line
    public static final String WS_CARRIAGE_RETURN = "\r"; //  %x0D ; Carriage return

    // Separator
    public static final String NAME_SEPARATOR = ":"; // name-separator  = ws %x3A ws  ; : colon
    public static final String VALUE_SEPARATOR = ","; // value-separator = ws %x2C ws  ; , comma

    // Begin and end symbols
    public static final String BEGIN_OBJECT = "{"; // begin-object    = ws %x7B ws  ; { left curly bracket
    public static final String END_OBJECT = "}"; // end-object      = ws %x7D ws  ; } right curly bracket
    public static final String BEGIN_ARRAY = "["; // begin-array     = ws %x5B ws  ; [ left square bracket
    public static final String END_ARRAY = "]"; // end-array       = ws %x5D ws  ; ] right square bracket

    // Special characters
    public static final String QUOTATION_MARK = "\""; // quotation-mark = %x22      ; "
    public static final String RECORD_SEPARATOR = "\u001E"; // record-separator = %x1E ; Record Separator (RS)

    private JsonUtil() {
    }

    @SuppressWarnings({"HardcodedLineSeparator"})
    public static String escapeJsonString(String unescapedJsonString) {
        Objects.requireNonNull(unescapedJsonString);
        StringBuilder escapedJsonString = new StringBuilder(unescapedJsonString.length() + 2);
        unescapedJsonString.codePoints().forEach(codePoint -> {
            switch (codePoint) {
                case '"' -> escapedJsonString.append("\\\"");
                case '\\' -> escapedJsonString.append("\\\\");
                case '\b' -> escapedJsonString.append("\\b");
                case '\f' -> escapedJsonString.append("\\f");
                case '\n' -> escapedJsonString.append("\\n");
                case '\r' -> escapedJsonString.append("\\r");
                case '\t' -> escapedJsonString.append("\\t");
                default -> {
                    if (Character.isISOControl(codePoint)) {
                        escapedJsonString.append(String.format("\\u%04x", codePoint));
                    } else {
                        escapedJsonString.appendCodePoint(codePoint);
                    }
                }
            }
        });
        return escapedJsonString.toString();
    }

    @SuppressWarnings("MagicNumber")
    public static String unescapeJsonString(String escapedJsonString) {
        Objects.requireNonNull(escapedJsonString);
        StringBuilder unescapedJsonString = new StringBuilder(escapedJsonString.length());
        int i = 0;
        while (i < escapedJsonString.length()) {
            char c = escapedJsonString.charAt(i);
            if (c == '\\') {
                i++;
                if (i < escapedJsonString.length()) {
                    char nextChar = escapedJsonString.charAt(i);
                    switch (nextChar) {
                        case '"' -> unescapedJsonString.append('"');
                        case '\\' -> unescapedJsonString.append('\\');
                        case '/' -> unescapedJsonString.append('/');
                        case 'b' -> unescapedJsonString.append('\b');
                        case 'f' -> unescapedJsonString.append('\f');
                        case 'n' -> unescapedJsonString.append('\n');
                        case 'r' -> unescapedJsonString.append('\r');
                        case 't' -> unescapedJsonString.append('\t');
                        case 'u' -> {
                            if ((i + 4) < escapedJsonString.length()) {
                                String hex = escapedJsonString.substring(i + 1, i + 5);
                                try {
                                    int codePoint = Integer.parseInt(hex, 16);
                                    unescapedJsonString.appendCodePoint(codePoint);
                                    i += 4;
                                } catch (NumberFormatException e) {
                                    // ERROR: Invalid Unicode escape sequence
                                    unescapedJsonString.append("\\u");
                                }
                            } else {
                                // ERROR: Not enough characters for a Unicode escape sequence
                                unescapedJsonString.append("\\u");
                            }
                        }
                        default -> unescapedJsonString.append(nextChar); // ERROR: Invalid escape sequence
                    }
                } else {
                    // ERROR: Backslash at the end of the string
                    unescapedJsonString.append('\\');
                }
            } else {
                unescapedJsonString.append(c);
            }
            i++;
        }
        return unescapedJsonString.toString();
    }

    public static String buildJsonString(String escapedJsonCharacters) {
        Objects.requireNonNull(escapedJsonCharacters);
        return QUOTATION_MARK + escapedJsonCharacters + QUOTATION_MARK;
    }

    public static String buildJsonMember(String escapedJsonName, String jsonValue) {
        Objects.requireNonNull(escapedJsonName);
        Objects.requireNonNull(jsonValue);
        return buildJsonString(escapedJsonName) + NAME_SEPARATOR + jsonValue;
    }

    public static String buildJsonMember(String escapedJsonName, String jsonValue, String whitespacesAfterNameSeparator) {
        Objects.requireNonNull(escapedJsonName);
        Objects.requireNonNull(jsonValue);
        Objects.requireNonNull(whitespacesAfterNameSeparator);
        return buildJsonString(escapedJsonName) + NAME_SEPARATOR + whitespacesAfterNameSeparator + jsonValue;
    }

    public static String joinJsonMembers(Collection<String> jsonMembers, String whitespacesAfterValueSeparator) {
        Objects.requireNonNull(jsonMembers);
        Objects.requireNonNull(whitespacesAfterValueSeparator);
        return String.join(VALUE_SEPARATOR + whitespacesAfterValueSeparator, jsonMembers);
    }

    public static String joinJsonElements(Collection<String> jsonElements, String whitespacesAfterValueSeparator) {
        Objects.requireNonNull(jsonElements);
        Objects.requireNonNull(whitespacesAfterValueSeparator);
        return String.join(VALUE_SEPARATOR + whitespacesAfterValueSeparator, jsonElements);
    }

    public static String buildJsonObject(String jsonMembers) {
        Objects.requireNonNull(jsonMembers);
        return BEGIN_OBJECT + jsonMembers + END_OBJECT;
    }

    public static String buildJsonObject(String jsonMembers, String whitespacesAfterBegin, String whitespacesBeforeEnd) {
        Objects.requireNonNull(jsonMembers);
        Objects.requireNonNull(whitespacesAfterBegin);
        Objects.requireNonNull(whitespacesBeforeEnd);
        return BEGIN_OBJECT + whitespacesAfterBegin + jsonMembers + whitespacesBeforeEnd + END_OBJECT;
    }

    public static String buildJsonArray(String jsonElements) {
        Objects.requireNonNull(jsonElements);
        return BEGIN_ARRAY + jsonElements + END_ARRAY;
    }

    public static String buildJsonArray(String jsonElements, String whitespacesAfterBegin, String whitespacesBeforeEnd) {
        Objects.requireNonNull(jsonElements);
        Objects.requireNonNull(whitespacesAfterBegin);
        Objects.requireNonNull(whitespacesBeforeEnd);
        return BEGIN_ARRAY + whitespacesAfterBegin + jsonElements + whitespacesBeforeEnd + END_ARRAY;
    }

}
