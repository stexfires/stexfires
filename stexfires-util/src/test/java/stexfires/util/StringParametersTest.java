package stexfires.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link StringParameters}.
 */
@SuppressWarnings({"AccessOfSystemProperties", "UnnecessaryUnicodeEscape", "AssignmentToNull"})
final class StringParametersTest {

    private StringParameters stringParameters01;
    private StringParameters stringParameters02;
    private StringParameters stringParameters03;

    /**
     * Set up before each test.
     */
    @BeforeEach
    void setUp() {
        stringParameters01 = new StringParameters.Builder()
                .addParameter("key1", "value1")
                .addParameters(Map.of("key2", "value2", "key3", "value3"))
                .addParameter("key3", "value3b") // overwrite value of 'key3'
                .build();
        stringParameters02 = new StringParameters.Builder()
                .addSystemProperties()
                .build();
        stringParameters03 = new StringParameters.Builder()
                .addSystemEnvironments()
                .build();
    }

    /**
     * Tear down after each test.
     */
    @AfterEach
    void tearDown() {
        stringParameters01 = null;
        stringParameters02 = null;
        stringParameters03 = null;
    }

    /**
     * Test method for {@link StringParameters#checkValidityOfKey(String)}.
     */
    @SuppressWarnings("HardcodedLineSeparator")
    @Test
    void checkValidityOfKey() {
        // check null
        assertThrows(IllegalArgumentException.class, () -> StringParameters.checkValidityOfKey(null));
        // check empty
        assertThrows(IllegalArgumentException.class, () -> StringParameters.checkValidityOfKey(""));
        // check trim
        assertThrows(IllegalArgumentException.class, () -> StringParameters.checkValidityOfKey(" "));
        assertThrows(IllegalArgumentException.class, () -> StringParameters.checkValidityOfKey("key1 "));
        assertThrows(IllegalArgumentException.class, () -> StringParameters.checkValidityOfKey(" key1"));
        assertThrows(IllegalArgumentException.class, () -> StringParameters.checkValidityOfKey(" key1 "));
        // check whitespace
        assertThrows(IllegalArgumentException.class, () -> StringParameters.checkValidityOfKey("key1 key2"));
        assertThrows(IllegalArgumentException.class, () -> StringParameters.checkValidityOfKey("key1\tkey2"));
        assertThrows(IllegalArgumentException.class, () -> StringParameters.checkValidityOfKey("key1\nkey2"));
        assertThrows(IllegalArgumentException.class, () -> StringParameters.checkValidityOfKey("key1\rkey2"));
        assertThrows(IllegalArgumentException.class, () -> StringParameters.checkValidityOfKey("key1\fkey2"));
        // check some valid keys
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("key1"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("key1.key2"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("key1-key2"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("key1_key2"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("key1/key2"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("key1\\key2"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("key1:key2"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("key1;key2"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("key1,key2"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("key1|key2"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("key1&key2"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("key1%key2"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("key1!key2"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("a"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("1"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("."));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("-"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("_"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("/"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("\\"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey(":"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey(";"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey(","));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("|"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("&"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("%"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("!"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("ä"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("\uD83D\uDE00"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("\uD83C\uDDFA\uD83C\uDDF8"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67\u200D\uD83D\uDC66"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("o\u0308"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("€"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("𐀀"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("🀀"));
        assertDoesNotThrow(() -> StringParameters.checkValidityOfKey("🏿"));
    }

    /**
     * Test method for {@link StringParameters#value(String)}.
     */
    @Test
    void value() {
        // stringParameters01
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> stringParameters01.value(null));
        assertEquals(Optional.empty(), stringParameters01.value("unknownKey"));
        assertEquals(Optional.of("value1"), stringParameters01.value("key1"));
        assertEquals(Optional.of("value2"), stringParameters01.value("key2"));
        assertEquals(Optional.of("value3b"), stringParameters01.value("key3"));
        // stringParameters02
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> stringParameters02.value(null));
        assertEquals(Optional.empty(), stringParameters02.value("unknownKey"));
        System.getProperties().forEach((k, v) -> assertEquals(Optional.of(String.valueOf(v)), stringParameters02.value(String.valueOf(k))));
        assertEquals(Optional.of(System.getProperty("java.version")), stringParameters02.value("java.version"));
        assertEquals(Optional.of(System.getProperty("user.name")), stringParameters02.value("user.name"));
        assertEquals(Optional.of(System.getProperty("java.home")), stringParameters02.value("java.home"));
        // stringParameters03
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> stringParameters03.value(null));
        assertEquals(Optional.empty(), stringParameters03.value("unknownKey"));
        System.getenv().forEach((k, v) -> assertEquals(Optional.of(v), stringParameters03.value(k)));
    }

    /**
     * Test method for {@link StringParameters#containsKey(String)}.
     */
    @Test
    void containsKey() {
        // stringParameters01
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> stringParameters01.containsKey(null));
        assertFalse(stringParameters01.containsKey("unknownKey"));
        assertTrue(stringParameters01.containsKey("key1"));
        assertTrue(stringParameters01.containsKey("key2"));
        assertTrue(stringParameters01.containsKey("key3"));
        // stringParameters02
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> stringParameters02.containsKey(null));
        assertFalse(stringParameters02.containsKey("unknownKey"));
        System.getProperties().forEach((k, v) -> assertTrue(stringParameters02.containsKey(String.valueOf(k))));
        assertTrue(stringParameters02.containsKey("java.version"));
        assertTrue(stringParameters02.containsKey("user.name"));
        assertTrue(stringParameters02.containsKey("java.home"));
        // stringParameters03
        // noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> stringParameters03.containsKey(null));
        assertFalse(stringParameters03.containsKey("unknownKey"));
        System.getenv().forEach((k, v) -> assertTrue(stringParameters03.containsKey(k)));
    }

    /**
     * Test method for {@link StringParameters#keySet()}.
     */
    @Test
    void keySet() {
        // stringParameters01
        Set<String> keySet01 = stringParameters01.keySet();
        assertNotNull(keySet01);
        // noinspection DataFlowIssue
        assertThrows(UnsupportedOperationException.class, () -> keySet01.add("newKey"));
        assertFalse(keySet01.contains("unknownKey"));
        assertEquals(3, keySet01.size()); // contains three keys
        assertTrue(keySet01.contains("key1"));
        assertTrue(keySet01.contains("key2"));
        assertTrue(keySet01.contains("key3"));
        // stringParameters02
        Set<String> keySet02 = stringParameters02.keySet();
        assertNotNull(keySet02);
        // noinspection DataFlowIssue
        assertThrows(UnsupportedOperationException.class, () -> keySet02.add("newKey"));
        assertFalse(keySet02.contains("unknownKey"));
        assertEquals(System.getProperties().size(), keySet02.size());
        System.getProperties().forEach((k, v) -> assertTrue(keySet02.contains(String.valueOf(k))));
        assertTrue(keySet02.contains("java.version"));
        assertTrue(keySet02.contains("user.name"));
        assertTrue(keySet02.contains("java.home"));
        // stringParameters03
        Set<String> keySet03 = stringParameters03.keySet();
        assertNotNull(keySet03);
        // noinspection DataFlowIssue
        assertThrows(UnsupportedOperationException.class, () -> keySet03.add("newKey"));
        assertFalse(keySet03.contains("unknownKey"));
        assertEquals(System.getenv().size(), keySet03.size());
        System.getenv().forEach((k, v) -> assertTrue(keySet03.contains(k)));
    }

    /**
     * Test method for {@link StringParameters#size()}.
     */
    @Test
    void size() {
        // stringParameters01
        assertEquals(3, stringParameters01.size()); // contains three keys
        // stringParameters02
        assertEquals(System.getProperties().size(), stringParameters02.size());
        // stringParameters03
        assertEquals(System.getenv().size(), stringParameters03.size());
    }

    /**
     * Test method for {@link StringParameters#isEmpty()}.
     */
    @Test
    void isEmpty() {
        // empty
        assertTrue(new StringParameters.Builder().build().isEmpty());

        // stringParameters01
        assertFalse(stringParameters01.isEmpty());
        // stringParameters02
        assertFalse(stringParameters02.isEmpty());
        // stringParameters03
        assertFalse(stringParameters03.isEmpty());
    }

    /**
     * Test method for {@link StringParameters#toProperties()}.
     */
    @Test
    void toProperties() {
        // stringParameters01
        assertEquals(3, stringParameters01.toProperties().size());
        assertEquals("value1", stringParameters01.toProperties().getProperty("key1"));
        assertEquals("value2", stringParameters01.toProperties().getProperty("key2"));
        assertEquals("value3b", stringParameters01.toProperties().getProperty("key3"));
        // stringParameters02
        assertEquals(System.getProperties().size(), stringParameters02.toProperties().size());
        System.getProperties().forEach((k, v) -> assertEquals(String.valueOf(v), stringParameters02.toProperties().getProperty(String.valueOf(k))));
        assertEquals(System.getProperty("java.version"), stringParameters02.toProperties().getProperty("java.version"));
        assertEquals(System.getProperty("user.name"), stringParameters02.toProperties().getProperty("user.name"));
        assertEquals(System.getProperty("java.home"), stringParameters02.toProperties().getProperty("java.home"));
        // stringParameters03
        assertEquals(System.getenv().size(), stringParameters03.toProperties().size());
        System.getenv().forEach((k, v) -> assertEquals(v, stringParameters03.toProperties().getProperty(k)));
    }

}