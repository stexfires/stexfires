package stexfires.record;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TextFieldTest {

    private static final String TEXT_VALUE = "value";
    private static final String TEXT_EMPTY = "";
    private static final String TEXT_BLANK = " ";

    private final TextField textFieldValue = new TextField(0, 0, TEXT_VALUE);
    private final TextField textFieldNull = new TextField(0, 0, null);
    private final TextField textFieldEmpty = new TextField(0, 0, TEXT_EMPTY);
    private final TextField textFieldBlank = new TextField(0, 0, TEXT_BLANK);
    private final TextField textFieldValueMiddle = new TextField(1, 2, TEXT_VALUE);
    private final TextField textFieldValueLast = new TextField(2, 2, TEXT_VALUE);

    @Test
    void isFirstField() {
        assertTrue(textFieldValue.isFirstField());
        assertTrue(textFieldNull.isFirstField());
        assertTrue(textFieldEmpty.isFirstField());
        assertTrue(textFieldBlank.isFirstField());
        assertFalse(textFieldValueMiddle.isFirstField());
        assertFalse(textFieldValueLast.isFirstField());
    }

    @Test
    void isLastField() {
        assertTrue(textFieldValue.isLastField());
        assertTrue(textFieldNull.isLastField());
        assertTrue(textFieldEmpty.isLastField());
        assertTrue(textFieldBlank.isLastField());
        assertFalse(textFieldValueMiddle.isLastField());
        assertTrue(textFieldValueLast.isLastField());
    }

    @Test
    void recordSize() {
        assertEquals(1, textFieldValue.recordSize());
        assertEquals(1, textFieldNull.recordSize());
        assertEquals(1, textFieldEmpty.recordSize());
        assertEquals(1, textFieldBlank.recordSize());
        assertEquals(3, textFieldValueMiddle.recordSize());
        assertEquals(3, textFieldValueLast.recordSize());
    }

    @Test
    void text() {
        assertEquals(TEXT_VALUE, textFieldValue.text());
        assertNull(textFieldNull.text());
        assertEquals(TEXT_EMPTY, textFieldEmpty.text());
        assertEquals(TEXT_BLANK, textFieldBlank.text());
        assertEquals(TEXT_VALUE, textFieldValueMiddle.text());
        assertEquals(TEXT_VALUE, textFieldValueLast.text());
    }

    @Test
    void orElse() {
        assertEquals(TEXT_VALUE, textFieldValue.orElse("else"));
        assertEquals("else", textFieldNull.orElse("else"));
        assertEquals(TEXT_EMPTY, textFieldEmpty.orElse("else"));
        assertEquals(TEXT_BLANK, textFieldBlank.orElse("else"));
        assertEquals(TEXT_VALUE, textFieldValueMiddle.orElse("else"));
        assertEquals(TEXT_VALUE, textFieldValueLast.orElse("else"));
    }

    @Test
    void asOptional() {
        assertEquals(Optional.of(TEXT_VALUE), textFieldValue.asOptional());
        assertEquals(Optional.empty(), textFieldNull.asOptional());
        assertEquals(Optional.of(TEXT_EMPTY), textFieldEmpty.asOptional());
        assertEquals(Optional.of(TEXT_BLANK), textFieldBlank.asOptional());
        assertEquals(Optional.of(TEXT_VALUE), textFieldValueMiddle.asOptional());
        assertEquals(Optional.of(TEXT_VALUE), textFieldValueLast.asOptional());
    }

    @Test
    void isNotNull() {
        assertTrue(textFieldValue.isNotNull());
        assertFalse(textFieldNull.isNotNull());
        assertTrue(textFieldEmpty.isNotNull());
        assertTrue(textFieldBlank.isNotNull());
        assertTrue(textFieldValueMiddle.isNotNull());
        assertTrue(textFieldValueLast.isNotNull());
    }

    @Test
    void isNull() {
        assertFalse(textFieldValue.isNull());
        assertTrue(textFieldNull.isNull());
        assertFalse(textFieldEmpty.isNull());
        assertFalse(textFieldBlank.isNull());
        assertFalse(textFieldValueMiddle.isNull());
        assertFalse(textFieldValueLast.isNull());
    }

    @Test
    void isEmpty() {
        assertFalse(textFieldValue.isEmpty());
        assertFalse(textFieldNull.isEmpty());
        assertTrue(textFieldEmpty.isEmpty());
        assertFalse(textFieldBlank.isEmpty());
        assertFalse(textFieldValueMiddle.isEmpty());
        assertFalse(textFieldValueLast.isEmpty());
    }

    @Test
    void isNullOrEmpty() {
        assertFalse(textFieldValue.isNullOrEmpty());
        assertTrue(textFieldNull.isNullOrEmpty());
        assertTrue(textFieldEmpty.isNullOrEmpty());
        assertFalse(textFieldBlank.isNullOrEmpty());
        assertFalse(textFieldValueMiddle.isNullOrEmpty());
        assertFalse(textFieldValueLast.isNullOrEmpty());
    }

    @Test
    void length() {
        assertEquals(TEXT_VALUE.length(), textFieldValue.length());
        assertEquals(0, textFieldNull.length());
        assertEquals(TEXT_EMPTY.length(), textFieldEmpty.length());
        assertEquals(TEXT_BLANK.length(), textFieldBlank.length());
        assertEquals(TEXT_VALUE.length(), textFieldValueMiddle.length());
        assertEquals(TEXT_VALUE.length(), textFieldValueLast.length());
    }

    @Test
    void stream() {
        assertEquals(1, textFieldValue.stream().count());
        assertEquals(0, textFieldNull.stream().count());
        assertEquals(1, textFieldEmpty.stream().count());
        assertEquals(1, textFieldBlank.stream().count());
        assertEquals(1, textFieldValueMiddle.stream().count());
        assertEquals(1, textFieldValueLast.stream().count());

        assertEquals(Optional.of(TEXT_VALUE), textFieldValue.stream().findFirst());
        assertEquals(Optional.empty(), textFieldNull.stream().findFirst());
        assertEquals(Optional.of(TEXT_EMPTY), textFieldEmpty.stream().findFirst());
        assertEquals(Optional.of(TEXT_BLANK), textFieldBlank.stream().findFirst());
        assertEquals(Optional.of(TEXT_VALUE), textFieldValueMiddle.stream().findFirst());
        assertEquals(Optional.of(TEXT_VALUE), textFieldValueLast.stream().findFirst());

        assertFalse(textFieldValue.stream().isParallel());
        assertFalse(textFieldNull.stream().isParallel());
        assertFalse(textFieldEmpty.stream().isParallel());
        assertFalse(textFieldBlank.stream().isParallel());
        assertFalse(textFieldValueMiddle.stream().isParallel());
        assertFalse(textFieldValueLast.stream().isParallel());
    }

    @Test
    void index() {
        assertEquals(0, textFieldValue.index());
        assertEquals(0, textFieldNull.index());
        assertEquals(0, textFieldEmpty.index());
        assertEquals(0, textFieldBlank.index());
        assertEquals(1, textFieldValueMiddle.index());
        assertEquals(2, textFieldValueLast.index());
    }

    @Test
    void maxIndex() {
        assertEquals(0, textFieldValue.maxIndex());
        assertEquals(0, textFieldNull.maxIndex());
        assertEquals(0, textFieldEmpty.maxIndex());
        assertEquals(0, textFieldBlank.maxIndex());
        assertEquals(2, textFieldValueMiddle.maxIndex());
        assertEquals(2, textFieldValueLast.maxIndex());
    }

}