package stexfires.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("MagicNumber")
public class FieldTest {

    private Field fieldNullValue;
    private Field fieldEmptyValue;
    private Field fieldValueFirst;
    private Field fieldValueFirstLast;
    private Field fieldValueLast;

    public FieldTest() {
    }

    @Before
    public void setUp() {
        fieldNullValue = new Field(0, true, null);
        fieldEmptyValue = new Field(0, true, "");
        fieldValueFirst = new Field(0, false, "test");
        fieldValueFirstLast = new Field(0, true, "test");
        fieldValueLast = new Field(1, true, "test");
    }

    @Test
    public void testGetIndex() {
        assertEquals(0L, (long) fieldNullValue.getIndex());
        assertEquals(0L, (long) fieldEmptyValue.getIndex());
        assertEquals(0L, (long) fieldValueFirst.getIndex());
        assertEquals(0L, (long) fieldValueFirstLast.getIndex());
        assertEquals(1L, (long) fieldValueLast.getIndex());
    }

    @Test
    public void testIsFirst() {
        assertTrue(fieldNullValue.isFirst());
        assertTrue(fieldEmptyValue.isFirst());
        assertTrue(fieldValueFirst.isFirst());
        assertTrue(fieldValueFirstLast.isFirst());
        assertFalse(fieldValueLast.isFirst());
    }

    @Test
    public void testIsLast() {
        assertTrue(fieldNullValue.isLast());
        assertTrue(fieldEmptyValue.isLast());
        assertFalse(fieldValueFirst.isLast());
        assertTrue(fieldValueFirstLast.isLast());
        assertTrue(fieldValueLast.isLast());
    }

    @Test
    public void testGetValue() {
        assertNull(fieldNullValue.getValue());
        assertEquals("", fieldEmptyValue.getValue());
        assertEquals("test", fieldValueFirst.getValue());
        assertEquals("test", fieldValueFirstLast.getValue());
        assertEquals("test", fieldValueLast.getValue());
    }

    @Test
    public void testGetValueOrElse() {
        assertEquals("else", fieldNullValue.getValueOrElse("else"));
        assertEquals("", fieldEmptyValue.getValueOrElse("else"));
        assertEquals("test", fieldValueFirst.getValueOrElse("else"));
        assertEquals("test", fieldValueFirstLast.getValueOrElse("else"));
        assertEquals("test", fieldValueLast.getValueOrElse("else"));
    }

    @Test
    public void testValueEquals() {
        assertTrue(fieldNullValue.valueEquals(null));
        assertFalse(fieldEmptyValue.valueEquals(null));
        assertFalse(fieldValueFirst.valueEquals(null));
        assertFalse(fieldValueFirstLast.valueEquals(null));
        assertFalse(fieldValueLast.valueEquals(null));

        assertFalse(fieldNullValue.valueEquals(""));
        assertTrue(fieldEmptyValue.valueEquals(""));
        assertFalse(fieldValueFirst.valueEquals(""));
        assertFalse(fieldValueFirstLast.valueEquals(""));
        assertFalse(fieldValueLast.valueEquals(""));

        assertFalse(fieldNullValue.valueEquals("test"));
        assertFalse(fieldEmptyValue.valueEquals("test"));
        assertTrue(fieldValueFirst.valueEquals("test"));
        assertTrue(fieldValueFirstLast.valueEquals("test"));
        assertTrue(fieldValueLast.valueEquals("test"));
    }

    @Test
    public void testValueIsNull() {
        assertTrue(fieldNullValue.valueIsNull());
        assertFalse(fieldEmptyValue.valueIsNull());
        assertFalse(fieldValueFirst.valueIsNull());
        assertFalse(fieldValueFirstLast.valueIsNull());
        assertFalse(fieldValueLast.valueIsNull());
    }

    @Test
    public void testValueIsEmpty() {
        assertFalse(fieldNullValue.valueIsEmpty());
        assertTrue(fieldEmptyValue.valueIsEmpty());
        assertFalse(fieldValueFirst.valueIsEmpty());
        assertFalse(fieldValueFirstLast.valueIsEmpty());
        assertFalse(fieldValueLast.valueIsEmpty());
    }

    @Test
    public void testValueIsNullOrEmpty() {
        assertTrue(fieldNullValue.valueIsNullOrEmpty());
        assertTrue(fieldEmptyValue.valueIsNullOrEmpty());
        assertFalse(fieldValueFirst.valueIsNullOrEmpty());
        assertFalse(fieldValueFirstLast.valueIsNullOrEmpty());
        assertFalse(fieldValueLast.valueIsNullOrEmpty());
    }

    @Test
    public void testLength() {
        assertEquals(0L, (long) fieldNullValue.length());
        assertEquals(0L, (long) fieldEmptyValue.length());
        assertEquals(4L, (long) fieldValueFirst.length());
        assertEquals(4L, (long) fieldValueFirstLast.length());
        assertEquals(4L, (long) fieldValueLast.length());
    }

    @Test
    public void testEquals() {
        assertEquals(fieldNullValue, fieldNullValue);
        assertNotEquals(fieldNullValue, fieldEmptyValue);
        assertNotEquals(fieldNullValue, fieldValueFirst);
        assertNotEquals(fieldNullValue, fieldValueFirstLast);
        assertNotEquals(fieldNullValue, fieldValueLast);

        assertNotEquals(fieldEmptyValue, fieldNullValue);
        assertEquals(fieldEmptyValue, fieldEmptyValue);
        assertNotEquals(fieldEmptyValue, fieldValueFirst);
        assertNotEquals(fieldEmptyValue, fieldValueFirstLast);
        assertNotEquals(fieldEmptyValue, fieldValueLast);

        assertNotEquals(fieldValueFirst, fieldNullValue);
        assertNotEquals(fieldValueFirst, fieldEmptyValue);
        assertEquals(fieldValueFirst, fieldValueFirst);
        assertNotEquals(fieldValueFirst, fieldValueFirstLast);
        assertNotEquals(fieldValueFirst, fieldValueLast);

        assertNotEquals(fieldValueFirstLast, fieldNullValue);
        assertNotEquals(fieldValueFirstLast, fieldEmptyValue);
        assertNotEquals(fieldValueFirstLast, fieldValueFirst);
        assertEquals(fieldValueFirstLast, fieldValueFirstLast);
        assertNotEquals(fieldValueFirstLast, fieldValueLast);

        assertNotEquals(fieldValueLast, fieldNullValue);
        assertNotEquals(fieldValueLast, fieldEmptyValue);
        assertNotEquals(fieldValueLast, fieldValueFirst);
        assertNotEquals(fieldValueLast, fieldValueFirstLast);
        assertEquals(fieldValueLast, fieldValueLast);

        assertEquals(fieldNullValue, new Field(0, true, null));
        assertEquals(fieldEmptyValue, new Field(0, true, ""));
        assertEquals(fieldValueFirst, new Field(0, false, "test"));
        assertEquals(fieldValueFirstLast, new Field(0, true, "test"));
        assertEquals(fieldValueLast, new Field(1, true, "test"));

        assertNotEquals(fieldNullValue, new Field(1, true, null));
        assertNotEquals(fieldEmptyValue, new Field(1, true, ""));
        assertNotEquals(fieldValueFirst, new Field(1, false, "test"));
        assertNotEquals(fieldValueFirstLast, new Field(1, true, "test"));
        assertNotEquals(fieldValueLast, new Field(0, true, "test"));

        assertNotEquals(fieldNullValue, new Field(0, false, null));
        assertNotEquals(fieldEmptyValue, new Field(0, false, ""));
        assertNotEquals(fieldValueFirst, new Field(0, true, "test"));
        assertNotEquals(fieldValueFirstLast, new Field(0, false, "test"));
        assertNotEquals(fieldValueLast, new Field(1, false, "test"));

        assertEquals(fieldNullValue, new Field(0, true, null));
        assertNotEquals(fieldEmptyValue, new Field(0, true, null));
        assertNotEquals(fieldValueFirst, new Field(0, false, null));
        assertNotEquals(fieldValueFirstLast, new Field(0, true, null));
        assertNotEquals(fieldValueLast, new Field(1, true, null));

        assertNotEquals(fieldNullValue, new Field(0, true, ""));
        assertEquals(fieldEmptyValue, new Field(0, true, ""));
        assertNotEquals(fieldValueFirst, new Field(0, false, ""));
        assertNotEquals(fieldValueFirstLast, new Field(0, true, ""));
        assertNotEquals(fieldValueLast, new Field(1, true, ""));

        assertNotEquals(fieldNullValue, new Field(0, true, "test"));
        assertNotEquals(fieldEmptyValue, new Field(0, true, "test"));
        assertEquals(fieldValueFirst, new Field(0, false, "test"));
        assertEquals(fieldValueFirstLast, new Field(0, true, "test"));
        assertEquals(fieldValueLast, new Field(1, true, "test"));

        assertNotEquals(fieldNullValue, new Field(0, true, "test2"));
        assertNotEquals(fieldEmptyValue, new Field(0, true, "test2"));
        assertNotEquals(fieldValueFirst, new Field(0, false, "test2"));
        assertNotEquals(fieldValueFirstLast, new Field(0, true, "test2"));
        assertNotEquals(fieldValueLast, new Field(1, true, "test2"));
    }

    @Test
    public void testHashCode() {
        assertNotEquals((long) fieldNullValue.hashCode(), (long) fieldEmptyValue.hashCode());
        assertNotEquals((long) fieldNullValue.hashCode(), (long) fieldValueFirst.hashCode());
        assertNotEquals((long) fieldNullValue.hashCode(), (long) fieldValueFirstLast.hashCode());
        assertNotEquals((long) fieldNullValue.hashCode(), (long) fieldValueLast.hashCode());

        assertNotEquals((long) fieldEmptyValue.hashCode(), (long) fieldValueFirst.hashCode());
        assertNotEquals((long) fieldEmptyValue.hashCode(), (long) fieldValueFirstLast.hashCode());
        assertNotEquals((long) fieldEmptyValue.hashCode(), (long) fieldValueLast.hashCode());

        assertNotEquals((long) fieldValueFirst.hashCode(), (long) fieldValueFirstLast.hashCode());
        assertNotEquals((long) fieldValueFirst.hashCode(), (long) fieldValueLast.hashCode());

        assertNotEquals((long) fieldValueFirstLast.hashCode(), (long) fieldValueLast.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("Field{0, true, null}", fieldNullValue.toString());
        assertEquals("Field{0, true, ''}", fieldEmptyValue.toString());
        assertEquals("Field{0, false, 'test'}", fieldValueFirst.toString());
        assertEquals("Field{0, true, 'test'}", fieldValueFirstLast.toString());
        assertEquals("Field{1, true, 'test'}", fieldValueLast.toString());
    }

}
