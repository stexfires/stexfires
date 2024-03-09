package stexfires.io.json;

import java.util.Collection;
import java.util.Objects;

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

    @SuppressWarnings({"HardcodedLineSeparator", "MagicNumber"})
    public static String escapeJsonString(String unescapedJsonString) {
        // TODO optimize and review escapeJsonString
        Objects.requireNonNull(unescapedJsonString);
        StringBuilder escapedJsonString = new StringBuilder(unescapedJsonString.length() + 2);
        for (int i = 0; i < unescapedJsonString.length(); i++) {
            char c = unescapedJsonString.charAt(i);
            switch (c) {
                case '"':
                    escapedJsonString.append("\\\"");
                    break;
                case '\\':
                    escapedJsonString.append("\\\\");
                    break;
                case '\b':
                    escapedJsonString.append("\\b");
                    break;
                case '\f':
                    escapedJsonString.append("\\f");
                    break;
                case '\n':
                    escapedJsonString.append("\\n");
                    break;
                case '\r':
                    escapedJsonString.append("\\r");
                    break;
                case '\t':
                    escapedJsonString.append("\\t");
                    break;
                default:
                    if ((c < 0x20) || (c > 0x7F)) {
                        escapedJsonString.append(String.format("\\u%04x", (int) c));
                    } else {
                        escapedJsonString.append(c);
                    }
                    break;
            }
        }
        return escapedJsonString.toString();
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

    public static String joinJsonMembers(Collection<String> jsonMembers) {
        Objects.requireNonNull(jsonMembers);
        return String.join(VALUE_SEPARATOR, jsonMembers);
    }

    public static String joinJsonMembers(Collection<String> jsonMembers, String whitespacesAfterValueSeparator) {
        Objects.requireNonNull(jsonMembers);
        Objects.requireNonNull(whitespacesAfterValueSeparator);
        return String.join(VALUE_SEPARATOR + whitespacesAfterValueSeparator, jsonMembers);
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

    public static String buildJsonArray(String jsonValues) {
        Objects.requireNonNull(jsonValues);
        return BEGIN_ARRAY + jsonValues + END_ARRAY;
    }

    public static String buildJsonArray(String jsonValues, String whitespacesAfterBegin, String whitespacesBeforeEnd) {
        Objects.requireNonNull(jsonValues);
        Objects.requireNonNull(whitespacesAfterBegin);
        Objects.requireNonNull(whitespacesBeforeEnd);
        return BEGIN_ARRAY + whitespacesAfterBegin + jsonValues + whitespacesBeforeEnd + END_ARRAY;
    }

    public enum StringEscape {

        ESCAPE_STRING, ESCAPE_NOT_NECESSARY

    }

    public enum ValidityCheck {

        CHECK_VALUE, CHECK_NOT_NECESSARY

    }

    public enum ValueType {

        OBJECT, ARRAY, NUMBER, STRING, BOOLEAN

    }

    public enum NullHandling {

        OMIT_JSON_MEMBER, USE_NULL_LITERAL_FOR_VALUE

    }

}
