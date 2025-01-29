package stexfires.io.json;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link stexfires.io.json.JsonUtil}.
 */
@SuppressWarnings({"MagicNumber", "HardcodedLineSeparator"})
final class JsonUtilTest {

    @SuppressWarnings({"UnnecessaryUnicodeEscape", "SpellCheckingInspection"})
    private static final String SPECIAL_CODEPOINTS = "\uD83D\uDE00 üo\u0308A\u030a \uD83C\uDDFA\uD83C\uDDF8 \uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67\u200D\uD83D\uDC66";

    /**
     * Test method for {@link stexfires.io.json.JsonUtil#escapeJsonString(String)}.
     */
    @Test
    void escapeJsonString() {
        assertEquals("", JsonUtil.escapeJsonString(""));
        assertEquals(" ", JsonUtil.escapeJsonString(" "));
        assertEquals("a", JsonUtil.escapeJsonString("a"));
        assertEquals("abc123", JsonUtil.escapeJsonString("abc123"));
        assertEquals("Ä", JsonUtil.escapeJsonString("Ä"));
        assertEquals("€", JsonUtil.escapeJsonString("€"));
        assertEquals(SPECIAL_CODEPOINTS, JsonUtil.escapeJsonString(SPECIAL_CODEPOINTS));
        assertEquals("/", JsonUtil.escapeJsonString("/")); // This method does not escape '/'
        assertEquals("\\\"", JsonUtil.escapeJsonString("\""));
        assertEquals("\\\\", JsonUtil.escapeJsonString("\\"));
        assertEquals("\\b", JsonUtil.escapeJsonString("\b"));
        assertEquals("\\f", JsonUtil.escapeJsonString("\f"));
        assertEquals("\\n", JsonUtil.escapeJsonString("\n"));
        assertEquals("\\r", JsonUtil.escapeJsonString("\r"));
        assertEquals("\\t", JsonUtil.escapeJsonString("\t"));
        for (char control = 0; control < 32; control++) {
            if ((control != '\b') && (control != '\f') && (control != '\n') && (control != '\r') && (control != '\t')) {
                assertEquals(String.format("\\u%04x", (int) control), JsonUtil.escapeJsonString(String.valueOf(control)));
            }
        }
        for (char control = 0x7F; control <= 0x9F; control++) {
            assertEquals(String.format("\\u%04x", (int) control), JsonUtil.escapeJsonString(String.valueOf(control)));
        }
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.escapeJsonString(null));
    }

    /**
     * Test method for {@link stexfires.io.json.JsonUtil#unescapeJsonString(String)}.
     */
    @Test
    void unescapeJsonString() {
        assertEquals("", JsonUtil.unescapeJsonString(""));
        assertEquals(" ", JsonUtil.unescapeJsonString(" "));
        assertEquals("a", JsonUtil.unescapeJsonString("a"));
        assertEquals("abc123", JsonUtil.unescapeJsonString("abc123"));
        assertEquals("Ä", JsonUtil.unescapeJsonString("Ä"));
        assertEquals("€", JsonUtil.unescapeJsonString("€"));
        assertEquals(SPECIAL_CODEPOINTS, JsonUtil.unescapeJsonString(SPECIAL_CODEPOINTS));
        assertEquals("/", JsonUtil.unescapeJsonString("/"));
        assertEquals("/", JsonUtil.unescapeJsonString("\\/"));
        assertEquals("\"", JsonUtil.unescapeJsonString("\\\""));
        assertEquals("\\", JsonUtil.unescapeJsonString("\\\\"));
        assertEquals("\b", JsonUtil.unescapeJsonString("\\b"));
        assertEquals("\f", JsonUtil.unescapeJsonString("\\f"));
        assertEquals("\n", JsonUtil.unescapeJsonString("\\n"));
        assertEquals("\r", JsonUtil.unescapeJsonString("\\r"));
        assertEquals("\t", JsonUtil.unescapeJsonString("\\t"));
        for (char c = Character.MIN_VALUE; c < Character.MAX_VALUE; c++) {
            assertEquals(String.valueOf(c), JsonUtil.unescapeJsonString(String.format("\\u%04x", (int) c)));
        }
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.unescapeJsonString(null));
    }

    /**
     * Test method for {@link stexfires.io.json.JsonUtil#buildJsonString(String)}.
     */
    @Test
    void buildJsonString() {
        assertEquals("\"\"", JsonUtil.buildJsonString(""));
        assertEquals("\" \"", JsonUtil.buildJsonString(" "));
        assertEquals("\"abc\"", JsonUtil.buildJsonString("abc"));
        assertEquals("\"Ä\"", JsonUtil.buildJsonString("Ä"));
        assertEquals("\"\\\\\"", JsonUtil.buildJsonString("\\\\"));
        assertEquals("\"\\\"\"", JsonUtil.buildJsonString("\\\""));
        assertEquals("\"\\n\"", JsonUtil.buildJsonString("\\n"));
        assertEquals("\"\\\"", JsonUtil.buildJsonString("\\")); // Not a valid json string
        assertEquals("\"\"\"", JsonUtil.buildJsonString("\"")); // Not a valid json string
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.buildJsonString(null));
    }

    /**
     * Test method for {@link stexfires.io.json.JsonUtil#buildJsonMember(String, String)}.
     */
    @Test
    void buildJsonMember() {
        assertEquals("\"name\":null", JsonUtil.buildJsonMember("name", "null"));
        assertEquals("\"\":null", JsonUtil.buildJsonMember("", "null"));
        assertEquals("\"NAME\":null", JsonUtil.buildJsonMember("NAME", "null"));
        assertEquals("\"\\\":null", JsonUtil.buildJsonMember("\\", "null")); // Not a valid json name
        assertEquals("\"\"\":null", JsonUtil.buildJsonMember("\"", "null")); // Not a valid json name
        assertEquals("\"name\":", JsonUtil.buildJsonMember("name", "")); // Not a valid json value
        assertEquals("\"name\": ", JsonUtil.buildJsonMember("name", " ")); // Not a valid json value
        assertEquals("\"name\":true", JsonUtil.buildJsonMember("name", "true"));
        assertEquals("\"name\":1234.5678", JsonUtil.buildJsonMember("name", "1234.5678"));
        assertEquals("\"name\":\"value\"", JsonUtil.buildJsonMember("name", "\"value\""));
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.buildJsonMember(null, "true"));
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.buildJsonMember("name", null));
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.buildJsonMember(null, null));
    }

    /**
     * Test method for {@link stexfires.io.json.JsonUtil#buildJsonMember(String, String, String)}.
     */
    @Test
    void buildJsonMember_spaces() {
        assertEquals("\"name\": null", JsonUtil.buildJsonMember("name", "null", " "));
        assertEquals("\"\": null", JsonUtil.buildJsonMember("", "null", " "));
        assertEquals("\"NAME\": null", JsonUtil.buildJsonMember("NAME", "null", " "));
        assertEquals("\"\\\": null", JsonUtil.buildJsonMember("\\", "null", " ")); // Not a valid json name
        assertEquals("\"\"\": null", JsonUtil.buildJsonMember("\"", "null", " ")); // Not a valid json name
        assertEquals("\"name\": ", JsonUtil.buildJsonMember("name", "", " ")); // Not a valid json value
        assertEquals("\"name\":  ", JsonUtil.buildJsonMember("name", " ", " ")); // Not a valid json value
        assertEquals("\"name\":-null", JsonUtil.buildJsonMember("name", "null", "-")); // Not a valid json whitespace
        assertEquals("\"name\": true", JsonUtil.buildJsonMember("name", "true", " "));
        assertEquals("\"name\": 1234.5678", JsonUtil.buildJsonMember("name", "1234.5678", " "));
        assertEquals("\"name\": \"value\"", JsonUtil.buildJsonMember("name", "\"value\"", " "));
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.buildJsonMember(null, "true", " "));
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.buildJsonMember("name", null, " "));
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.buildJsonMember("name", "true", null));
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.buildJsonMember(null, null, " "));
    }

    /**
     * Test method for {@link stexfires.io.json.JsonUtil#joinJsonMembers(java.util.Collection, String)}.
     */
    @Test
    void joinJsonMembers() {
        assertEquals("", JsonUtil.joinJsonMembers(List.of(), " "));
        assertEquals("\"name0\":null, \"name1\":null, \"name2\":null", JsonUtil.joinJsonMembers(List.of("\"name0\":null", "\"name1\":null", "\"name2\":null"), " "));
        assertEquals("\"name0\":true, \"name1\":1, \"name2\":\"value\"", JsonUtil.joinJsonMembers(List.of("\"name0\":true", "\"name1\":1", "\"name2\":\"value\""), " "));
        assertEquals("a, b, c", JsonUtil.joinJsonMembers(List.of("a", "b", "c"), " ")); // Not a valid json member
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.joinJsonMembers(null, " "));
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.joinJsonMembers(List.of(), null));
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.joinJsonMembers(null, null));
    }

    /**
     * Test method for {@link stexfires.io.json.JsonUtil#joinJsonElements(java.util.Collection, String)}.
     */
    @Test
    void joinJsonElements() {
        assertEquals("", JsonUtil.joinJsonElements(List.of(), " "));
        assertEquals("\"name0\":null, \"name1\":null, \"name2\":null", JsonUtil.joinJsonElements(List.of("\"name0\":null", "\"name1\":null", "\"name2\":null"), " "));
        assertEquals("\"name0\":true, \"name1\":1, \"name2\":\"value\"", JsonUtil.joinJsonElements(List.of("\"name0\":true", "\"name1\":1", "\"name2\":\"value\""), " "));
        assertEquals("a, b, c", JsonUtil.joinJsonElements(List.of("a", "b", "c"), " ")); // Not a valid json member
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.joinJsonElements(null, " "));
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.joinJsonElements(List.of(), null));
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.joinJsonElements(null, null));
    }

    /**
     * Test method for {@link stexfires.io.json.JsonUtil#buildJsonObject(String)}.
     */
    @Test
    void buildJsonObject() {
        assertEquals("{}", JsonUtil.buildJsonObject(""));
        assertEquals("{ }", JsonUtil.buildJsonObject(" "));
        assertEquals("{\"name0\":null,\"name1\":null,\"name2\":null}", JsonUtil.buildJsonObject("\"name0\":null,\"name1\":null,\"name2\":null"));
        assertEquals("{\"name0\":true,\"name1\":1,\"name2\":\"value\"}", JsonUtil.buildJsonObject("\"name0\":true,\"name1\":1,\"name2\":\"value\""));
        assertEquals("{a,b,c}", JsonUtil.buildJsonObject("a,b,c")); // Not a valid json member
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.buildJsonObject(null));
    }

    /**
     * Test method for {@link stexfires.io.json.JsonUtil#buildJsonObject(String, String, String)}.
     */
    @Test
    void buildJsonObject_spaces() {
        assertEquals("{}", JsonUtil.buildJsonObject("", "", ""));
        assertEquals("{ }", JsonUtil.buildJsonObject("", "", " "));
        assertEquals("{ }", JsonUtil.buildJsonObject("", " ", ""));
        assertEquals("{\r\n\r\n}", JsonUtil.buildJsonObject("", "\r\n", "\r\n"));
        assertEquals("{ \"name0\":null,\"name1\":null,\"name2\":null }", JsonUtil.buildJsonObject("\"name0\":null,\"name1\":null,\"name2\":null", " ", " "));
        assertEquals("{ \"name0\":true,\"name1\":1,\"name2\":\"value\" }", JsonUtil.buildJsonObject("\"name0\":true,\"name1\":1,\"name2\":\"value\"", " ", " "));
        assertEquals("{ a,b,c }", JsonUtil.buildJsonObject("a,b,c", " ", " ")); // Not a valid json member
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.buildJsonObject(null, " ", " "));
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.buildJsonObject("", null, " "));
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.buildJsonObject("", " ", null));
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.buildJsonObject(null, null, null));
    }

    /**
     * Test method for {@link stexfires.io.json.JsonUtil#buildJsonArray(String)}.
     */
    @Test
    void buildJsonArray() {
        assertEquals("[]", JsonUtil.buildJsonArray(""));
        assertEquals("[ ]", JsonUtil.buildJsonArray(" "));
        assertEquals("[\"number0\":0,\"number1\":1,\"number2\":2]", JsonUtil.buildJsonArray("\"number0\":0,\"number1\":1,\"number2\":2"));
        assertEquals("[a,b,c]", JsonUtil.buildJsonArray("a,b,c")); // Not a valid json value
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.buildJsonArray(null));
    }

    /**
     * Test method for {@link stexfires.io.json.JsonUtil#buildJsonArray(String, String, String)}.
     */
    @Test
    void buildJsonArray_spaces() {
        assertEquals("[]", JsonUtil.buildJsonArray("", "", ""));
        assertEquals("[ ]", JsonUtil.buildJsonArray("", "", " "));
        assertEquals("[ ]", JsonUtil.buildJsonArray("", " ", ""));
        assertEquals("[\r\n\r\n]", JsonUtil.buildJsonArray("", "\r\n", "\r\n"));
        assertEquals("[ \"number0\":0,\"number1\":1,\"number2\":2 ]", JsonUtil.buildJsonArray("\"number0\":0,\"number1\":1,\"number2\":2", " ", " "));
        assertEquals("[ a,b,c ]", JsonUtil.buildJsonArray("a,b,c", " ", " ")); // Not a valid json value
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.buildJsonArray(null, " ", " "));
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.buildJsonArray("", null, " "));
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.buildJsonArray("", " ", null));
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> JsonUtil.buildJsonArray(null, null, null));
    }

}