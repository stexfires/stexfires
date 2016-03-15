package org.textfiledatatools.core.record;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FieldTest {

    private Field fieldNullValue;
    private Field fieldEmptyValue;
    private Field fieldValueFirst;
    private Field fieldValueFirstLast;
    private Field fieldValueLast;

    @Before
    public void setUp() throws Exception {
        fieldNullValue = new Field(0, true, null);
        fieldEmptyValue = new Field(0, true, "");
        fieldValueFirst = new Field(0, false, "test");
        fieldValueFirstLast = new Field(0, true, "test");
        fieldValueLast = new Field(1, true, "test");
    }

    @Test
    public void testGetIndex() throws Exception {
        assertEquals(0, fieldNullValue.getIndex());
        assertEquals(0, fieldEmptyValue.getIndex());
        assertEquals(0, fieldValueFirst.getIndex());
        assertEquals(0, fieldValueFirstLast.getIndex());
        assertEquals(1, fieldValueLast.getIndex());
    }

    @Test
    public void testIsFirst() throws Exception {
        assertTrue(fieldNullValue.isFirst());
        assertTrue(fieldEmptyValue.isFirst());
        assertTrue(fieldValueFirst.isFirst());
        assertTrue(fieldValueFirstLast.isFirst());
        assertFalse(fieldValueLast.isFirst());
    }

    @Test
    public void testIsLast() throws Exception {
        assertTrue(fieldNullValue.isLast());
        assertTrue(fieldEmptyValue.isLast());
        assertFalse(fieldValueFirst.isLast());
        assertTrue(fieldValueFirstLast.isLast());
        assertTrue(fieldValueLast.isLast());
    }

    @Test
    public void testGetValue() throws Exception {
        assertNull(fieldNullValue.getValue());
        assertEquals("", fieldEmptyValue.getValue());
        assertEquals("test", fieldValueFirst.getValue());
        assertEquals("test", fieldValueFirstLast.getValue());
        assertEquals("test", fieldValueLast.getValue());
    }

    @Test
    public void testGetValueOrElse() throws Exception {
        assertEquals("else", fieldNullValue.getValueOrElse("else"));
        assertEquals("", fieldEmptyValue.getValueOrElse("else"));
        assertEquals("test", fieldValueFirst.getValueOrElse("else"));
        assertEquals("test", fieldValueFirstLast.getValueOrElse("else"));
        assertEquals("test", fieldValueLast.getValueOrElse("else"));
    }

    @Test
    public void testIsValueEquals() throws Exception {
        assertTrue(fieldNullValue.isValueEquals(null));
        assertFalse(fieldEmptyValue.isValueEquals(null));
        assertFalse(fieldValueFirst.isValueEquals(null));
        assertFalse(fieldValueFirstLast.isValueEquals(null));
        assertFalse(fieldValueLast.isValueEquals(null));

        assertFalse(fieldNullValue.isValueEquals(""));
        assertTrue(fieldEmptyValue.isValueEquals(""));
        assertFalse(fieldValueFirst.isValueEquals(""));
        assertFalse(fieldValueFirstLast.isValueEquals(""));
        assertFalse(fieldValueLast.isValueEquals(""));

        assertFalse(fieldNullValue.isValueEquals("test"));
        assertFalse(fieldEmptyValue.isValueEquals("test"));
        assertTrue(fieldValueFirst.isValueEquals("test"));
        assertTrue(fieldValueFirstLast.isValueEquals("test"));
        assertTrue(fieldValueLast.isValueEquals("test"));
    }

    @Test
    public void testIsNullValue() throws Exception {
        assertTrue(fieldNullValue.isNullValue());
        assertFalse(fieldEmptyValue.isNullValue());
        assertFalse(fieldValueFirst.isNullValue());
        assertFalse(fieldValueFirstLast.isNullValue());
        assertFalse(fieldValueLast.isNullValue());
    }

    @Test
    public void testIsEmptyValue() throws Exception {
        assertFalse(fieldNullValue.isEmptyValue());
        assertTrue(fieldEmptyValue.isEmptyValue());
        assertFalse(fieldValueFirst.isEmptyValue());
        assertFalse(fieldValueFirstLast.isEmptyValue());
        assertFalse(fieldValueLast.isEmptyValue());
    }

    @Test
    public void testIsNullOrEmptyValue() throws Exception {
        assertTrue(fieldNullValue.isNullOrEmptyValue());
        assertTrue(fieldEmptyValue.isNullOrEmptyValue());
        assertFalse(fieldValueFirst.isNullOrEmptyValue());
        assertFalse(fieldValueFirstLast.isNullOrEmptyValue());
        assertFalse(fieldValueLast.isNullOrEmptyValue());
    }

    @Test
    public void testGetLength() throws Exception {
        assertEquals(0, fieldNullValue.getLength());
        assertEquals(0, fieldEmptyValue.getLength());
        assertEquals(4, fieldValueFirst.getLength());
        assertEquals(4, fieldValueFirstLast.getLength());
        assertEquals(4, fieldValueLast.getLength());
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(fieldNullValue.equals(fieldNullValue));
        assertFalse(fieldNullValue.equals(fieldEmptyValue));
        assertFalse(fieldNullValue.equals(fieldValueFirst));
        assertFalse(fieldNullValue.equals(fieldValueFirstLast));
        assertFalse(fieldNullValue.equals(fieldValueLast));

        assertFalse(fieldEmptyValue.equals(fieldNullValue));
        assertTrue(fieldEmptyValue.equals(fieldEmptyValue));
        assertFalse(fieldEmptyValue.equals(fieldValueFirst));
        assertFalse(fieldEmptyValue.equals(fieldValueFirstLast));
        assertFalse(fieldEmptyValue.equals(fieldValueLast));

        assertFalse(fieldValueFirst.equals(fieldNullValue));
        assertFalse(fieldValueFirst.equals(fieldEmptyValue));
        assertTrue(fieldValueFirst.equals(fieldValueFirst));
        assertFalse(fieldValueFirst.equals(fieldValueFirstLast));
        assertFalse(fieldValueFirst.equals(fieldValueLast));

        assertFalse(fieldValueFirstLast.equals(fieldNullValue));
        assertFalse(fieldValueFirstLast.equals(fieldEmptyValue));
        assertFalse(fieldValueFirstLast.equals(fieldValueFirst));
        assertTrue(fieldValueFirstLast.equals(fieldValueFirstLast));
        assertFalse(fieldValueFirstLast.equals(fieldValueLast));

        assertFalse(fieldValueLast.equals(fieldNullValue));
        assertFalse(fieldValueLast.equals(fieldEmptyValue));
        assertFalse(fieldValueLast.equals(fieldValueFirst));
        assertFalse(fieldValueLast.equals(fieldValueFirstLast));
        assertTrue(fieldValueLast.equals(fieldValueLast));

        assertTrue(fieldNullValue.equals(new Field(0, true, null)));
        assertTrue(fieldEmptyValue.equals(new Field(0, true, "")));
        assertTrue(fieldValueFirst.equals(new Field(0, false, "test")));
        assertTrue(fieldValueFirstLast.equals(new Field(0, true, "test")));
        assertTrue(fieldValueLast.equals(new Field(1, true, "test")));

        assertFalse(fieldNullValue.equals(new Field(1, true, null)));
        assertFalse(fieldEmptyValue.equals(new Field(1, true, "")));
        assertFalse(fieldValueFirst.equals(new Field(1, false, "test")));
        assertFalse(fieldValueFirstLast.equals(new Field(1, true, "test")));
        assertFalse(fieldValueLast.equals(new Field(0, true, "test")));

        assertFalse(fieldNullValue.equals(new Field(0, false, null)));
        assertFalse(fieldEmptyValue.equals(new Field(0, false, "")));
        assertFalse(fieldValueFirst.equals(new Field(0, true, "test")));
        assertFalse(fieldValueFirstLast.equals(new Field(0, false, "test")));
        assertFalse(fieldValueLast.equals(new Field(1, false, "test")));

        assertTrue(fieldNullValue.equals(new Field(0, true, null)));
        assertFalse(fieldEmptyValue.equals(new Field(0, true, null)));
        assertFalse(fieldValueFirst.equals(new Field(0, false, null)));
        assertFalse(fieldValueFirstLast.equals(new Field(0, true, null)));
        assertFalse(fieldValueLast.equals(new Field(1, true, null)));

        assertFalse(fieldNullValue.equals(new Field(0, true, "")));
        assertTrue(fieldEmptyValue.equals(new Field(0, true, "")));
        assertFalse(fieldValueFirst.equals(new Field(0, false, "")));
        assertFalse(fieldValueFirstLast.equals(new Field(0, true, "")));
        assertFalse(fieldValueLast.equals(new Field(1, true, "")));

        assertFalse(fieldNullValue.equals(new Field(0, true, "test")));
        assertFalse(fieldEmptyValue.equals(new Field(0, true, "test")));
        assertTrue(fieldValueFirst.equals(new Field(0, false, "test")));
        assertTrue(fieldValueFirstLast.equals(new Field(0, true, "test")));
        assertTrue(fieldValueLast.equals(new Field(1, true, "test")));

        assertFalse(fieldNullValue.equals(new Field(0, true, "test2")));
        assertFalse(fieldEmptyValue.equals(new Field(0, true, "test2")));
        assertFalse(fieldValueFirst.equals(new Field(0, false, "test2")));
        assertFalse(fieldValueFirstLast.equals(new Field(0, true, "test2")));
        assertFalse(fieldValueLast.equals(new Field(1, true, "test2")));
    }

    @Test
    public void testHashCode() throws Exception {
        assertNotEquals(fieldNullValue.hashCode(), fieldEmptyValue.hashCode());
        assertNotEquals(fieldNullValue.hashCode(), fieldValueFirst.hashCode());
        assertNotEquals(fieldNullValue.hashCode(), fieldValueFirstLast.hashCode());
        assertNotEquals(fieldNullValue.hashCode(), fieldValueLast.hashCode());

        assertNotEquals(fieldEmptyValue.hashCode(), fieldValueFirst.hashCode());
        assertNotEquals(fieldEmptyValue.hashCode(), fieldValueFirstLast.hashCode());
        assertNotEquals(fieldEmptyValue.hashCode(), fieldValueLast.hashCode());

        assertNotEquals(fieldValueFirst.hashCode(), fieldValueFirstLast.hashCode());
        assertNotEquals(fieldValueFirst.hashCode(), fieldValueLast.hashCode());

        assertNotEquals(fieldValueFirstLast.hashCode(), fieldValueLast.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("Field{0, true, null}", fieldNullValue.toString());
        assertEquals("Field{0, true, ''}", fieldEmptyValue.toString());
        assertEquals("Field{0, false, 'test'}", fieldValueFirst.toString());
        assertEquals("Field{0, true, 'test'}", fieldValueFirstLast.toString());
        assertEquals("Field{1, true, 'test'}", fieldValueLast.toString());
    }

}