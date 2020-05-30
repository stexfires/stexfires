package stexfires.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@SuppressWarnings({"MagicNumber", "OptionalGetWithoutIsPresent"})
public class FieldTest {

    private Field fieldNullValue1;
    private Field fieldNullValue2;
    private Field fieldEmptyValue;
    private Field fieldValueFirst;
    private Field fieldValueFirstLast;
    private Field fieldValueLast;

    public FieldTest() {
    }

    @Before
    public void setUp() {
        fieldNullValue1 = new Field(0, true);
        fieldNullValue2 = new Field(0, true, null);
        fieldEmptyValue = new Field(0, true, "");
        fieldValueFirst = new Field(0, false, "test");
        fieldValueFirstLast = new Field(0, true, "test");
        fieldValueLast = new Field(1, true, "test");
    }

    @Test
    public void testGetIndex() {
        assertEquals(0L, fieldNullValue1.getIndex());
        assertEquals(0L, fieldNullValue2.getIndex());
        assertEquals(0L, fieldEmptyValue.getIndex());
        assertEquals(0L, fieldValueFirst.getIndex());
        assertEquals(0L, fieldValueFirstLast.getIndex());
        assertEquals(1L, fieldValueLast.getIndex());
    }

    @Test
    public void testIsFirst() {
        assertTrue(fieldNullValue1.isFirst());
        assertTrue(fieldNullValue2.isFirst());
        assertTrue(fieldEmptyValue.isFirst());
        assertTrue(fieldValueFirst.isFirst());
        assertTrue(fieldValueFirstLast.isFirst());
        assertFalse(fieldValueLast.isFirst());
    }

    @Test
    public void testIsLast() {
        assertTrue(fieldNullValue1.isLast());
        assertTrue(fieldNullValue2.isLast());
        assertTrue(fieldEmptyValue.isLast());
        assertFalse(fieldValueFirst.isLast());
        assertTrue(fieldValueFirstLast.isLast());
        assertTrue(fieldValueLast.isLast());
    }

    @Test
    public void testGetValue() {
        assertNull(fieldNullValue1.getValue());
        assertNull(fieldNullValue2.getValue());
        assertEquals("", fieldEmptyValue.getValue());
        assertEquals("test", fieldValueFirst.getValue());
        assertEquals("test", fieldValueFirstLast.getValue());
        assertEquals("test", fieldValueLast.getValue());
    }

    @Test
    public void testGetValueOrElse() {
        assertEquals("else", fieldNullValue1.getValueOrElse("else"));
        assertEquals("else", fieldNullValue2.getValueOrElse("else"));
        assertEquals("", fieldEmptyValue.getValueOrElse("else"));
        assertEquals("test", fieldValueFirst.getValueOrElse("else"));
        assertEquals("test", fieldValueFirstLast.getValueOrElse("else"));
        assertEquals("test", fieldValueLast.getValueOrElse("else"));
    }

    @Test
    public void testGetValueAsOptional() {
        assertFalse(fieldNullValue1.getValueAsOptional().isPresent());
        assertFalse(fieldNullValue2.getValueAsOptional().isPresent());
        assertEquals("", fieldEmptyValue.getValueAsOptional().get());
        assertEquals("test", fieldValueFirst.getValueAsOptional().get());
        assertEquals("test", fieldValueFirstLast.getValueAsOptional().get());
        assertEquals("test", fieldValueLast.getValueAsOptional().get());
    }

    @Test
    public void testValueEquals() {
        assertTrue(fieldNullValue1.valueEquals(null));
        assertTrue(fieldNullValue2.valueEquals(null));
        assertFalse(fieldEmptyValue.valueEquals(null));
        assertFalse(fieldValueFirst.valueEquals(null));
        assertFalse(fieldValueFirstLast.valueEquals(null));
        assertFalse(fieldValueLast.valueEquals(null));

        assertFalse(fieldNullValue1.valueEquals(""));
        assertFalse(fieldNullValue2.valueEquals(""));
        assertTrue(fieldEmptyValue.valueEquals(""));
        assertFalse(fieldValueFirst.valueEquals(""));
        assertFalse(fieldValueFirstLast.valueEquals(""));
        assertFalse(fieldValueLast.valueEquals(""));

        assertFalse(fieldNullValue1.valueEquals("test"));
        assertFalse(fieldNullValue2.valueEquals("test"));
        assertFalse(fieldEmptyValue.valueEquals("test"));
        assertTrue(fieldValueFirst.valueEquals("test"));
        assertTrue(fieldValueFirstLast.valueEquals("test"));
        assertTrue(fieldValueLast.valueEquals("test"));
    }

    @Test
    public void testValueIsNull() {
        assertTrue(fieldNullValue1.valueIsNull());
        assertTrue(fieldNullValue2.valueIsNull());
        assertFalse(fieldEmptyValue.valueIsNull());
        assertFalse(fieldValueFirst.valueIsNull());
        assertFalse(fieldValueFirstLast.valueIsNull());
        assertFalse(fieldValueLast.valueIsNull());
    }

    @Test
    public void testValueIsEmpty() {
        assertFalse(fieldNullValue1.valueIsEmpty());
        assertFalse(fieldNullValue2.valueIsEmpty());
        assertTrue(fieldEmptyValue.valueIsEmpty());
        assertFalse(fieldValueFirst.valueIsEmpty());
        assertFalse(fieldValueFirstLast.valueIsEmpty());
        assertFalse(fieldValueLast.valueIsEmpty());
    }

    @Test
    public void testValueIsNullOrEmpty() {
        assertTrue(fieldNullValue1.valueIsNullOrEmpty());
        assertTrue(fieldNullValue2.valueIsNullOrEmpty());
        assertTrue(fieldEmptyValue.valueIsNullOrEmpty());
        assertFalse(fieldValueFirst.valueIsNullOrEmpty());
        assertFalse(fieldValueFirstLast.valueIsNullOrEmpty());
        assertFalse(fieldValueLast.valueIsNullOrEmpty());
    }

    @Test
    public void testLength() {
        assertEquals(0L, fieldNullValue1.length());
        assertEquals(0L, fieldNullValue2.length());
        assertEquals(0L, fieldEmptyValue.length());
        assertEquals(4L, fieldValueFirst.length());
        assertEquals(4L, fieldValueFirstLast.length());
        assertEquals(4L, fieldValueLast.length());
    }

    @Test
    public void testStream() {
        assertFalse(fieldNullValue1.stream().findFirst().isPresent());
        assertFalse(fieldNullValue2.stream().findFirst().isPresent());
        assertEquals("", fieldEmptyValue.stream().findFirst().get());
        assertEquals("test", fieldValueFirst.stream().findFirst().get());
        assertEquals("test", fieldValueFirstLast.stream().findFirst().get());
        assertEquals("test", fieldValueLast.stream().findFirst().get());
    }

    @Test
    public void testEquals() {
        assertEquals(fieldNullValue1, fieldNullValue1);
        assertEquals(fieldNullValue1, fieldNullValue2);
        assertEquals(fieldNullValue2, fieldNullValue1);
        assertEquals(fieldNullValue2, fieldNullValue2);
        assertNotEquals(fieldNullValue1, fieldEmptyValue);
        assertNotEquals(fieldNullValue1, fieldValueFirst);
        assertNotEquals(fieldNullValue1, fieldValueFirstLast);
        assertNotEquals(fieldNullValue1, fieldValueLast);

        assertNotEquals(fieldEmptyValue, fieldNullValue1);
        assertEquals(fieldEmptyValue, fieldEmptyValue);
        assertNotEquals(fieldEmptyValue, fieldValueFirst);
        assertNotEquals(fieldEmptyValue, fieldValueFirstLast);
        assertNotEquals(fieldEmptyValue, fieldValueLast);

        assertNotEquals(fieldValueFirst, fieldNullValue1);
        assertNotEquals(fieldValueFirst, fieldEmptyValue);
        assertEquals(fieldValueFirst, fieldValueFirst);
        assertNotEquals(fieldValueFirst, fieldValueFirstLast);
        assertNotEquals(fieldValueFirst, fieldValueLast);

        assertNotEquals(fieldValueFirstLast, fieldNullValue1);
        assertNotEquals(fieldValueFirstLast, fieldEmptyValue);
        assertNotEquals(fieldValueFirstLast, fieldValueFirst);
        assertEquals(fieldValueFirstLast, fieldValueFirstLast);
        assertNotEquals(fieldValueFirstLast, fieldValueLast);

        assertNotEquals(fieldValueLast, fieldNullValue1);
        assertNotEquals(fieldValueLast, fieldEmptyValue);
        assertNotEquals(fieldValueLast, fieldValueFirst);
        assertNotEquals(fieldValueLast, fieldValueFirstLast);
        assertEquals(fieldValueLast, fieldValueLast);

        assertEquals(fieldNullValue1, new Field(0, true, null));
        assertEquals(fieldEmptyValue, new Field(0, true, ""));
        assertEquals(fieldValueFirst, new Field(0, false, "test"));
        assertEquals(fieldValueFirstLast, new Field(0, true, "test"));
        assertEquals(fieldValueLast, new Field(1, true, "test"));

        assertNotEquals(fieldNullValue1, new Field(1, true, null));
        assertNotEquals(fieldEmptyValue, new Field(1, true, ""));
        assertNotEquals(fieldValueFirst, new Field(1, false, "test"));
        assertNotEquals(fieldValueFirstLast, new Field(1, true, "test"));
        assertNotEquals(fieldValueLast, new Field(0, true, "test"));

        assertNotEquals(fieldNullValue1, new Field(0, false, null));
        assertNotEquals(fieldEmptyValue, new Field(0, false, ""));
        assertNotEquals(fieldValueFirst, new Field(0, true, "test"));
        assertNotEquals(fieldValueFirstLast, new Field(0, false, "test"));
        assertNotEquals(fieldValueLast, new Field(1, false, "test"));

        assertEquals(fieldNullValue1, new Field(0, true, null));
        assertNotEquals(fieldEmptyValue, new Field(0, true, null));
        assertNotEquals(fieldValueFirst, new Field(0, false, null));
        assertNotEquals(fieldValueFirstLast, new Field(0, true, null));
        assertNotEquals(fieldValueLast, new Field(1, true, null));

        assertNotEquals(fieldNullValue1, new Field(0, true, ""));
        assertEquals(fieldEmptyValue, new Field(0, true, ""));
        assertNotEquals(fieldValueFirst, new Field(0, false, ""));
        assertNotEquals(fieldValueFirstLast, new Field(0, true, ""));
        assertNotEquals(fieldValueLast, new Field(1, true, ""));

        assertNotEquals(fieldNullValue1, new Field(0, true, "test"));
        assertNotEquals(fieldEmptyValue, new Field(0, true, "test"));
        assertEquals(fieldValueFirst, new Field(0, false, "test"));
        assertEquals(fieldValueFirstLast, new Field(0, true, "test"));
        assertEquals(fieldValueLast, new Field(1, true, "test"));

        assertNotEquals(fieldNullValue1, new Field(0, true, "test2"));
        assertNotEquals(fieldEmptyValue, new Field(0, true, "test2"));
        assertNotEquals(fieldValueFirst, new Field(0, false, "test2"));
        assertNotEquals(fieldValueFirstLast, new Field(0, true, "test2"));
        assertNotEquals(fieldValueLast, new Field(1, true, "test2"));
    }

    @Test
    public void testHashCode() {
        assertEquals(fieldNullValue1.hashCode(), fieldNullValue2.hashCode());

        assertNotEquals(fieldNullValue1.hashCode(), fieldEmptyValue.hashCode());
        assertNotEquals(fieldNullValue1.hashCode(), fieldValueFirst.hashCode());
        assertNotEquals(fieldNullValue1.hashCode(), fieldValueFirstLast.hashCode());
        assertNotEquals(fieldNullValue1.hashCode(), fieldValueLast.hashCode());

        assertNotEquals(fieldNullValue2.hashCode(), fieldEmptyValue.hashCode());
        assertNotEquals(fieldNullValue2.hashCode(), fieldValueFirst.hashCode());
        assertNotEquals(fieldNullValue2.hashCode(), fieldValueFirstLast.hashCode());
        assertNotEquals(fieldNullValue2.hashCode(), fieldValueLast.hashCode());

        assertNotEquals(fieldEmptyValue.hashCode(), fieldValueFirst.hashCode());
        assertNotEquals(fieldEmptyValue.hashCode(), fieldValueFirstLast.hashCode());
        assertNotEquals(fieldEmptyValue.hashCode(), fieldValueLast.hashCode());

        assertNotEquals(fieldValueFirst.hashCode(), fieldValueFirstLast.hashCode());
        assertNotEquals(fieldValueFirst.hashCode(), fieldValueLast.hashCode());

        assertNotEquals(fieldValueFirstLast.hashCode(), fieldValueLast.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("Field{0, true, null}", fieldNullValue1.toString());
        assertEquals("Field{0, true, null}", fieldNullValue2.toString());
        assertEquals("Field{0, true, ''}", fieldEmptyValue.toString());
        assertEquals("Field{0, false, 'test'}", fieldValueFirst.toString());
        assertEquals("Field{0, true, 'test'}", fieldValueFirstLast.toString());
        assertEquals("Field{1, true, 'test'}", fieldValueLast.toString());
    }

}
