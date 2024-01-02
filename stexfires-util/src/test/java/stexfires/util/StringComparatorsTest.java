package stexfires.util;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link StringComparators}.
 */
@SuppressWarnings({"EqualsWithItself", "MagicNumber"})
class StringComparatorsTest {

    /**
     * Test method for {@link StringComparators#compareTo()}.
     */
    @SuppressWarnings("DataFlowIssue")
    @Test
    void compareTo() {
        // null
        assertThrows(NullPointerException.class, () -> StringComparators.compareTo().compare(null, "a"));
        // equal strings
        assertEquals(0, StringComparators.compareTo().compare("a", "a"));
        // different strings, both directions
        assertTrue(StringComparators.compareTo().compare("a", "b") < 0);
        assertTrue(StringComparators.compareTo().compare("b", "a") > 0);
        // different case or umlaut
        assertTrue(StringComparators.compareTo().compare("a", "A") > 0);
        assertTrue(StringComparators.compareTo().compare("a", "Ä") < 0);
        assertTrue(StringComparators.compareTo().compare("a", "B") > 0);
        assertTrue(StringComparators.compareTo().compare("b", "A") > 0);
        assertTrue(StringComparators.compareTo().compare("b", "Ä") < 0);
        assertTrue(StringComparators.compareTo().compare("b", "B") > 0);
        assertTrue(StringComparators.compareTo().compare("ä", "A") > 0);
        assertTrue(StringComparators.compareTo().compare("ä", "Ä") > 0);
        assertTrue(StringComparators.compareTo().compare("ä", "B") > 0);
    }

    /**
     * Test method for {@link StringComparators#compareToIgnoreCase()}.
     */
    @SuppressWarnings("DataFlowIssue")
    @Test
    void compareToIgnoreCase() {
        // null
        assertThrows(NullPointerException.class, () -> StringComparators.compareToIgnoreCase().compare(null, "a"));
        // equal strings
        assertEquals(0, StringComparators.compareToIgnoreCase().compare("a", "a"));
        // different strings, both directions
        assertTrue(StringComparators.compareToIgnoreCase().compare("a", "b") < 0);
        assertTrue(StringComparators.compareToIgnoreCase().compare("b", "a") > 0);
        // different case or umlaut
        assertEquals(0, StringComparators.compareToIgnoreCase().compare("a", "A"));
        assertTrue(StringComparators.compareToIgnoreCase().compare("a", "Ä") < 0);
        assertTrue(StringComparators.compareToIgnoreCase().compare("a", "B") < 0);
        assertTrue(StringComparators.compareToIgnoreCase().compare("b", "A") > 0);
        assertTrue(StringComparators.compareToIgnoreCase().compare("b", "Ä") < 0);
        assertEquals(0, StringComparators.compareToIgnoreCase().compare("b", "B"));
        assertTrue(StringComparators.compareToIgnoreCase().compare("ä", "A") > 0);
        assertEquals(0, StringComparators.compareToIgnoreCase().compare("ä", "Ä"));
        assertTrue(StringComparators.compareToIgnoreCase().compare("ä", "B") > 0);
    }

    /**
     * Test method for {@link StringComparators#collatorComparator(java.text.Collator)}.
     */
    @SuppressWarnings("DataFlowIssue")
    @Test
    void collatorComparator_Collator() {
        Collator collator = Collator.getInstance(Locale.GERMAN);
        // null
        assertThrows(NullPointerException.class, () -> StringComparators.collatorComparator(collator).compare(null, "a"));
        // equal strings
        assertEquals(0, StringComparators.collatorComparator(collator).compare("a", "a"));
        // different strings, both directions
        assertTrue(StringComparators.collatorComparator(collator).compare("a", "b") < 0);
        assertTrue(StringComparators.collatorComparator(collator).compare("b", "a") > 0);
        // different case or umlaut
        assertTrue(StringComparators.collatorComparator(collator).compare("a", "A") < 0);
        assertTrue(StringComparators.collatorComparator(collator).compare("a", "Ä") < 0);
        assertTrue(StringComparators.collatorComparator(collator).compare("a", "B") < 0);
        assertTrue(StringComparators.collatorComparator(collator).compare("b", "A") > 0);
        assertTrue(StringComparators.collatorComparator(collator).compare("b", "Ä") > 0);
        assertTrue(StringComparators.collatorComparator(collator).compare("b", "B") < 0);
        assertTrue(StringComparators.collatorComparator(collator).compare("ä", "A") > 0);
        assertTrue(StringComparators.collatorComparator(collator).compare("ä", "Ä") < 0);
        assertTrue(StringComparators.collatorComparator(collator).compare("ä", "B") < 0);
    }

    /**
     * Test method for {@link StringComparators#collatorComparator(java.util.Locale)}.
     */
    @SuppressWarnings("DataFlowIssue")
    @Test
    void collatorComparator_Locale() {
        Locale locale = Locale.GERMAN;
        // null
        assertThrows(NullPointerException.class, () -> StringComparators.collatorComparator(locale).compare(null, "a"));
        // equal strings
        assertEquals(0, StringComparators.collatorComparator(locale).compare("a", "a"));
        // different strings, both directions
        assertTrue(StringComparators.collatorComparator(locale).compare("a", "b") < 0);
        assertTrue(StringComparators.collatorComparator(locale).compare("b", "a") > 0);
        // different case or umlaut
        assertTrue(StringComparators.collatorComparator(locale).compare("a", "A") < 0);
        assertTrue(StringComparators.collatorComparator(locale).compare("a", "Ä") < 0);
        assertTrue(StringComparators.collatorComparator(locale).compare("a", "B") < 0);
        assertTrue(StringComparators.collatorComparator(locale).compare("b", "A") > 0);
        assertTrue(StringComparators.collatorComparator(locale).compare("b", "Ä") > 0);
        assertTrue(StringComparators.collatorComparator(locale).compare("b", "B") < 0);
        assertTrue(StringComparators.collatorComparator(locale).compare("ä", "A") > 0);
        assertTrue(StringComparators.collatorComparator(locale).compare("ä", "Ä") < 0);
        assertTrue(StringComparators.collatorComparator(locale).compare("ä", "B") < 0);
    }

    /**
     * Test method for {@link StringComparators#collatorComparator(java.util.Locale, int)}.
     */
    @SuppressWarnings("DataFlowIssue")
    @Test
    void collatorComparator_Locale_Strength() {
        Locale locale = Locale.GERMAN;
        // PRIMARY
        int strengthPrimary = Collator.PRIMARY;
        // null
        assertThrows(NullPointerException.class, () -> StringComparators.collatorComparator(locale, strengthPrimary).compare(null, "a"));
        // equal strings
        assertEquals(0, StringComparators.collatorComparator(locale, strengthPrimary).compare("a", "a"));
        // different strings, both directions
        assertTrue(StringComparators.collatorComparator(locale, strengthPrimary).compare("a", "b") < 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthPrimary).compare("b", "a") > 0);
        // different case or umlaut
        assertEquals(0, StringComparators.collatorComparator(locale, strengthPrimary).compare("a", "A"));
        assertEquals(0, StringComparators.collatorComparator(locale, strengthPrimary).compare("a", "Ä"));
        assertTrue(StringComparators.collatorComparator(locale, strengthPrimary).compare("a", "B") < 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthPrimary).compare("b", "A") > 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthPrimary).compare("b", "Ä") > 0);
        assertEquals(0, StringComparators.collatorComparator(locale, strengthPrimary).compare("b", "B"));
        assertEquals(0, StringComparators.collatorComparator(locale, strengthPrimary).compare("ä", "A"));
        assertEquals(0, StringComparators.collatorComparator(locale, strengthPrimary).compare("ä", "Ä"));
        assertTrue(StringComparators.collatorComparator(locale, strengthPrimary).compare("ä", "B") < 0);

        // SECONDARY
        int strengthSecondary = Collator.SECONDARY;
        // null
        assertThrows(NullPointerException.class, () -> StringComparators.collatorComparator(locale, strengthSecondary).compare(null, "a"));
        // equal strings
        assertEquals(0, StringComparators.collatorComparator(locale, strengthSecondary).compare("a", "a"));
        // different strings, both directions
        assertTrue(StringComparators.collatorComparator(locale, strengthSecondary).compare("a", "b") < 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthSecondary).compare("b", "a") > 0);
        // different case or umlaut
        assertEquals(0, StringComparators.collatorComparator(locale, strengthSecondary).compare("a", "A"));
        assertTrue(StringComparators.collatorComparator(locale, strengthSecondary).compare("a", "Ä") < 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthSecondary).compare("a", "B") < 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthSecondary).compare("b", "A") > 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthSecondary).compare("b", "Ä") > 0);
        assertEquals(0, StringComparators.collatorComparator(locale, strengthSecondary).compare("b", "B"));
        assertTrue(StringComparators.collatorComparator(locale, strengthSecondary).compare("ä", "A") > 0);
        assertEquals(0, StringComparators.collatorComparator(locale, strengthSecondary).compare("ä", "Ä"));
        assertTrue(StringComparators.collatorComparator(locale, strengthSecondary).compare("ä", "B") < 0);

        // TERTIARY
        int strengthTertiary = Collator.TERTIARY;
        // null
        assertThrows(NullPointerException.class, () -> StringComparators.collatorComparator(locale, strengthTertiary).compare(null, "a"));
        // equal strings
        assertEquals(0, StringComparators.collatorComparator(locale, strengthTertiary).compare("a", "a"));
        // different strings, both directions
        assertTrue(StringComparators.collatorComparator(locale, strengthTertiary).compare("a", "b") < 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthTertiary).compare("b", "a") > 0);
        // different case or umlaut
        assertTrue(StringComparators.collatorComparator(locale, strengthTertiary).compare("a", "A") < 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthTertiary).compare("a", "Ä") < 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthTertiary).compare("a", "B") < 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthTertiary).compare("b", "A") > 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthTertiary).compare("b", "Ä") > 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthTertiary).compare("b", "B") < 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthTertiary).compare("ä", "A") > 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthTertiary).compare("ä", "Ä") < 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthTertiary).compare("ä", "B") < 0);

        // IDENTICAL
        int strengthIdentical = Collator.IDENTICAL;
        // null
        assertThrows(NullPointerException.class, () -> StringComparators.collatorComparator(locale, strengthIdentical).compare(null, "a"));
        // equal strings
        assertEquals(0, StringComparators.collatorComparator(locale, strengthIdentical).compare("a", "a"));
        // different strings, both directions
        assertTrue(StringComparators.collatorComparator(locale, strengthIdentical).compare("a", "b") < 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthIdentical).compare("b", "a") > 0);
        // different case or umlaut
        assertTrue(StringComparators.collatorComparator(locale, strengthIdentical).compare("a", "A") < 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthIdentical).compare("a", "Ä") < 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthIdentical).compare("a", "B") < 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthIdentical).compare("b", "A") > 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthIdentical).compare("b", "Ä") > 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthIdentical).compare("b", "B") < 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthIdentical).compare("ä", "A") > 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthIdentical).compare("ä", "Ä") < 0);
        assertTrue(StringComparators.collatorComparator(locale, strengthIdentical).compare("ä", "B") < 0);
    }

    /**
     * Test method for {@link StringComparators#normalizedComparator(java.util.function.UnaryOperator, java.util.Comparator, SortNulls)}
     */
    @Test
    void normalizedComparator() {
        UnaryOperator<@Nullable String> unaryOperator = s -> s == null ? null : s.toUpperCase(Locale.GERMAN).trim(); // Converts to upper case and removes leading and trailing spaces
        // null
        assertEquals(0, StringComparators.normalizedComparator(unaryOperator, String::compareTo, SortNulls.FIRST).compare(null, null));
        assertEquals(-1, StringComparators.normalizedComparator(unaryOperator, String::compareTo, SortNulls.FIRST).compare(null, "a"));
        assertEquals(-1, StringComparators.normalizedComparator(unaryOperator, String::compareTo, SortNulls.LAST).compare("a", null));
        assertEquals(1, StringComparators.normalizedComparator(unaryOperator, String::compareTo, SortNulls.FIRST).compare("a", null));
        assertEquals(1, StringComparators.normalizedComparator(unaryOperator, String::compareTo, SortNulls.LAST).compare(null, "a"));
        // equal strings
        assertEquals(0, StringComparators.normalizedComparator(unaryOperator, String::compareTo, SortNulls.FIRST).compare("a", "a"));
        // different strings
        assertTrue(StringComparators.normalizedComparator(unaryOperator, String::compareTo, SortNulls.FIRST).compare("a", "b") < 0);
        assertEquals(0, StringComparators.normalizedComparator(unaryOperator, String::compareTo, SortNulls.FIRST).compare("a ", " A"));
        assertTrue(StringComparators.normalizedComparator(unaryOperator, String::compareTo, SortNulls.FIRST).compare("a ", " B") < 0);
    }

    /**
     * Test method for {@link StringComparators#lengthComparator()}.
     */
    @SuppressWarnings("DataFlowIssue")
    @Test
    void lengthComparator() {
        // null
        assertThrows(NullPointerException.class, () -> StringComparators.lengthComparator().compare(null, "a"));
        // equal strings
        assertEquals(0, StringComparators.lengthComparator().compare("a", "a"));
        // different strings
        assertEquals(0, StringComparators.lengthComparator().compare("a", "b"));
        assertTrue(StringComparators.lengthComparator().compare("a", "aa") < 0);
        assertTrue(StringComparators.lengthComparator().compare("aa", "a") > 0);
        assertTrue(StringComparators.lengthComparator().compare("a", "aaa") < 0);
        assertTrue(StringComparators.lengthComparator().compare("aaa", "a") > 0);
        assertTrue(StringComparators.lengthComparator().compare("b", "aa") < 0);
        assertTrue(StringComparators.lengthComparator().compare("aa", "b") > 0);
        assertTrue(StringComparators.lengthComparator().compare("b", "aaa") < 0);
        assertTrue(StringComparators.lengthComparator().compare("aaa", "b") > 0);
    }

    /**
     * Test method for {@link StringComparators#primitiveIntComparator(int, int)}
     */
    @Test
    void primitiveIntComparator() {
        // null
        assertEquals(1, StringComparators.primitiveIntComparator(2, 3).compare(null, "1"));
        assertEquals(-1, StringComparators.primitiveIntComparator(2, 3).compare("1", null));
        assertEquals(0, StringComparators.primitiveIntComparator(2, 3).compare(null, "2"));
        assertEquals(0, StringComparators.primitiveIntComparator(2, 3).compare("2", null));
        // equal strings
        assertEquals(0, StringComparators.primitiveIntComparator(2, 3).compare("1", "1"));
        assertEquals(0, StringComparators.primitiveIntComparator(2, 3).compare("2", "2"));
        assertEquals(0, StringComparators.primitiveIntComparator(2, 3).compare("3", "3"));
        // not parsable
        assertEquals(0, StringComparators.primitiveIntComparator(2, 3).compare("a", "b"));
        assertEquals(0, StringComparators.primitiveIntComparator(2, 3).compare("a", "3"));
        assertEquals(0, StringComparators.primitiveIntComparator(2, 3).compare("3", "a"));
        assertEquals(1, StringComparators.primitiveIntComparator(2, 3).compare("a", "1"));
        assertEquals(-1, StringComparators.primitiveIntComparator(2, 3).compare("1", "a"));
        // different strings
        assertEquals(-1, StringComparators.primitiveIntComparator(2, 3).compare("1", "2"));
        assertEquals(-1, StringComparators.primitiveIntComparator(2, 3).compare("2", "3"));
        assertEquals(-1, StringComparators.primitiveIntComparator(2, 3).compare("1", "3"));
        assertEquals(1, StringComparators.primitiveIntComparator(2, 3).compare("2", "1"));
        assertEquals(1, StringComparators.primitiveIntComparator(2, 3).compare("3", "2"));
        assertEquals(1, StringComparators.primitiveIntComparator(2, 3).compare("3", "1"));
        // min and max
        assertEquals(1, StringComparators.primitiveIntComparator(1, 1).compare("1", String.valueOf(Integer.MIN_VALUE)));
        assertEquals(-1, StringComparators.primitiveIntComparator(1, 1).compare("1", String.valueOf(Integer.MAX_VALUE)));
        assertEquals(-1, StringComparators.primitiveIntComparator(1, 1).compare(String.valueOf(Integer.MIN_VALUE), String.valueOf(Integer.MAX_VALUE)));
    }

    /**
     * Test method for {@link StringComparators#primitiveLongComparator(long, long)}
     */
    @Test
    void primitiveLongComparator() {
        // null
        assertEquals(1, StringComparators.primitiveLongComparator(2L, 3L).compare(null, "1"));
        assertEquals(-1, StringComparators.primitiveLongComparator(2L, 3L).compare("1", null));
        assertEquals(0, StringComparators.primitiveLongComparator(2L, 3L).compare(null, "2"));
        assertEquals(0, StringComparators.primitiveLongComparator(2L, 3L).compare("2", null));
        // equal strings
        assertEquals(0, StringComparators.primitiveLongComparator(2L, 3L).compare("1", "1"));
        assertEquals(0, StringComparators.primitiveLongComparator(2L, 3L).compare("2", "2"));
        assertEquals(0, StringComparators.primitiveLongComparator(2L, 3L).compare("3", "3"));
        // not parsable
        assertEquals(0, StringComparators.primitiveLongComparator(2L, 3L).compare("a", "b"));
        assertEquals(0, StringComparators.primitiveLongComparator(2L, 3L).compare("a", "3"));
        assertEquals(0, StringComparators.primitiveLongComparator(2L, 3L).compare("3", "a"));
        assertEquals(1, StringComparators.primitiveLongComparator(2L, 3L).compare("a", "1"));
        assertEquals(-1, StringComparators.primitiveLongComparator(2L, 3L).compare("1", "a"));
        // different strings
        assertEquals(-1, StringComparators.primitiveLongComparator(2L, 3L).compare("1", "2"));
        assertEquals(-1, StringComparators.primitiveLongComparator(2L, 3L).compare("2", "3"));
        assertEquals(-1, StringComparators.primitiveLongComparator(2L, 3L).compare("1", "3"));
        assertEquals(1, StringComparators.primitiveLongComparator(2L, 3L).compare("2", "1"));
        assertEquals(1, StringComparators.primitiveLongComparator(2L, 3L).compare("3", "2"));
        assertEquals(1, StringComparators.primitiveLongComparator(2L, 3L).compare("3", "1"));
        // min and max
        assertEquals(1, StringComparators.primitiveLongComparator(1L, 1L).compare("1", String.valueOf(Long.MIN_VALUE)));
        assertEquals(-1, StringComparators.primitiveLongComparator(1L, 1L).compare("1", String.valueOf(Long.MAX_VALUE)));
        assertEquals(-1, StringComparators.primitiveLongComparator(1L, 1L).compare(String.valueOf(Long.MIN_VALUE), String.valueOf(Long.MAX_VALUE)));
    }

    /**
     * Test method for {@link StringComparators#primitiveDoubleComparator(double, double)}
     */
    @Test
    void primitiveDoubleComparator() {
        // null
        assertEquals(1, StringComparators.primitiveDoubleComparator(2.0d, 3.0d).compare(null, "1"));
        assertEquals(-1, StringComparators.primitiveDoubleComparator(2.0d, 3.0d).compare("1", null));
        assertEquals(0, StringComparators.primitiveDoubleComparator(2.0d, 3.0d).compare(null, "2"));
        assertEquals(0, StringComparators.primitiveDoubleComparator(2.0d, 3.0d).compare("2", null));
        // equal strings
        assertEquals(0, StringComparators.primitiveDoubleComparator(2.0d, 3.0d).compare("1", "1"));
        assertEquals(0, StringComparators.primitiveDoubleComparator(2.0d, 3.0d).compare("2", "2"));
        assertEquals(0, StringComparators.primitiveDoubleComparator(2.0d, 3.0d).compare("3.45678", "3.45678"));
        // not parsable
        assertEquals(0, StringComparators.primitiveDoubleComparator(2.0d, 3.0d).compare("a", "b"));
        assertEquals(0, StringComparators.primitiveDoubleComparator(2.0d, 3.0d).compare("a", "3"));
        assertEquals(0, StringComparators.primitiveDoubleComparator(2.0d, 3.0d).compare("3", "a"));
        assertEquals(1, StringComparators.primitiveDoubleComparator(2.0d, 3.0d).compare("a", "1"));
        assertEquals(-1, StringComparators.primitiveDoubleComparator(2.0d, 3.0d).compare("1", "a"));
        // different strings
        assertEquals(-1, StringComparators.primitiveDoubleComparator(2.0d, 3.0d).compare("1", "2"));
        assertEquals(-1, StringComparators.primitiveDoubleComparator(2.0d, 3.0d).compare("2", "3.45678"));
        assertEquals(-1, StringComparators.primitiveDoubleComparator(2.0d, 3.0d).compare("1", "3.45678"));
        assertEquals(1, StringComparators.primitiveDoubleComparator(2.0d, 3.0d).compare("2", "1"));
        assertEquals(1, StringComparators.primitiveDoubleComparator(2.0d, 3.0d).compare("3.45678", "2"));
        assertEquals(1, StringComparators.primitiveDoubleComparator(2.0d, 3.0d).compare("3.45678", "1"));
        // min and max
        assertEquals(1, StringComparators.primitiveDoubleComparator(1.0d, 1.0d).compare("3.45678", String.valueOf(Double.MIN_VALUE)));
        assertEquals(-1, StringComparators.primitiveDoubleComparator(1.0d, 1.0d).compare("3.45678", String.valueOf(Double.MAX_VALUE)));
        assertEquals(-1, StringComparators.primitiveDoubleComparator(1.0d, 1.0d).compare(String.valueOf(Double.MIN_VALUE), String.valueOf(Double.MAX_VALUE)));

    }

    /**
     * Test method for {@link StringComparators#integerComparator(Integer, Integer, java.util.Comparator)}
     */
    @SuppressWarnings("DataFlowIssue")
    @Test
    void integerComparator() {
        // null
        assertEquals(1, StringComparators.integerComparator(2, 3, Integer::compareTo).compare(null, "1"));
        assertEquals(-1, StringComparators.integerComparator(2, 3, Integer::compareTo).compare("1", null));
        assertEquals(0, StringComparators.integerComparator(2, 3, Integer::compareTo).compare(null, "2"));
        assertEquals(0, StringComparators.integerComparator(2, 3, Integer::compareTo).compare("2", null));
        // equal strings
        assertEquals(0, StringComparators.integerComparator(2, 3, Integer::compareTo).compare("1", "1"));
        assertEquals(0, StringComparators.integerComparator(2, 3, Integer::compareTo).compare("2", "2"));
        assertEquals(0, StringComparators.integerComparator(2, 3, Integer::compareTo).compare("3", "3"));
        // not parsable
        assertEquals(0, StringComparators.integerComparator(2, 3, Integer::compareTo).compare("a", "b"));
        assertEquals(0, StringComparators.integerComparator(2, 3, Integer::compareTo).compare("a", "3"));
        assertEquals(0, StringComparators.integerComparator(2, 3, Integer::compareTo).compare("3", "a"));
        assertEquals(1, StringComparators.integerComparator(2, 3, Integer::compareTo).compare("a", "1"));
        assertEquals(-1, StringComparators.integerComparator(2, 3, Integer::compareTo).compare("1", "a"));
        // different strings
        assertEquals(-1, StringComparators.integerComparator(2, 3, Integer::compareTo).compare("1", "2"));
        assertEquals(-1, StringComparators.integerComparator(2, 3, Integer::compareTo).compare("2", "3"));
        assertEquals(-1, StringComparators.integerComparator(2, 3, Integer::compareTo).compare("1", "3"));
        assertEquals(1, StringComparators.integerComparator(2, 3, Integer::compareTo).compare("2", "1"));
        assertEquals(1, StringComparators.integerComparator(2, 3, Integer::compareTo).compare("3", "2"));
        assertEquals(1, StringComparators.integerComparator(2, 3, Integer::compareTo).compare("3", "1"));
        // min and max
        assertEquals(1, StringComparators.integerComparator(1, 1, Integer::compareTo).compare("1", String.valueOf(Integer.MIN_VALUE)));
        assertEquals(-1, StringComparators.integerComparator(1, 1, Integer::compareTo).compare("1", String.valueOf(Integer.MAX_VALUE)));
        assertEquals(-1, StringComparators.integerComparator(1, 1, Integer::compareTo).compare(String.valueOf(Integer.MIN_VALUE), String.valueOf(Integer.MAX_VALUE)));
        // natural order
        assertEquals(-1, StringComparators.integerComparator(2, 3, Comparator.naturalOrder()).compare("1", "2"));
        assertEquals(1, StringComparators.integerComparator(2, 3, Comparator.naturalOrder()).compare("2", "1"));
        // reverseOrder
        assertEquals(1, StringComparators.integerComparator(2, 3, Comparator.reverseOrder()).compare("1", "2"));
        assertEquals(-1, StringComparators.integerComparator(2, 3, Comparator.reverseOrder()).compare("2", "1"));
    }

    /**
     * Test method for {@link StringComparators#longComparator(Long, Long, java.util.Comparator)}
     */
    @SuppressWarnings("DataFlowIssue")
    @Test
    void longComparator() {
        // null
        assertEquals(1, StringComparators.longComparator(2L, 3L, Long::compareTo).compare(null, "1"));
        assertEquals(-1, StringComparators.longComparator(2L, 3L, Long::compareTo).compare("1", null));
        assertEquals(0, StringComparators.longComparator(2L, 3L, Long::compareTo).compare(null, "2"));
        assertEquals(0, StringComparators.longComparator(2L, 3L, Long::compareTo).compare("2", null));
        // equal strings
        assertEquals(0, StringComparators.longComparator(2L, 3L, Long::compareTo).compare("1", "1"));
        assertEquals(0, StringComparators.longComparator(2L, 3L, Long::compareTo).compare("2", "2"));
        assertEquals(0, StringComparators.longComparator(2L, 3L, Long::compareTo).compare("3", "3"));
        // not parsable
        assertEquals(0, StringComparators.longComparator(2L, 3L, Long::compareTo).compare("a", "b"));
        assertEquals(0, StringComparators.longComparator(2L, 3L, Long::compareTo).compare("a", "3"));
        assertEquals(0, StringComparators.longComparator(2L, 3L, Long::compareTo).compare("3", "a"));
        assertEquals(1, StringComparators.longComparator(2L, 3L, Long::compareTo).compare("a", "1"));
        assertEquals(-1, StringComparators.longComparator(2L, 3L, Long::compareTo).compare("1", "a"));
        // different strings
        assertEquals(-1, StringComparators.longComparator(2L, 3L, Long::compareTo).compare("1", "2"));
        assertEquals(-1, StringComparators.longComparator(2L, 3L, Long::compareTo).compare("2", "3"));
        assertEquals(-1, StringComparators.longComparator(2L, 3L, Long::compareTo).compare("1", "3"));
        assertEquals(1, StringComparators.longComparator(2L, 3L, Long::compareTo).compare("2", "1"));
        assertEquals(1, StringComparators.longComparator(2L, 3L, Long::compareTo).compare("3", "2"));
        assertEquals(1, StringComparators.longComparator(2L, 3L, Long::compareTo).compare("3", "1"));
        // min and max
        assertEquals(1, StringComparators.longComparator(1L, 1L, Long::compareTo).compare("1", String.valueOf(Long.MIN_VALUE)));
        assertEquals(-1, StringComparators.longComparator(1L, 1L, Long::compareTo).compare("1", String.valueOf(Long.MAX_VALUE)));
        assertEquals(-1, StringComparators.longComparator(1L, 1L, Long::compareTo).compare(String.valueOf(Long.MIN_VALUE), String.valueOf(Long.MAX_VALUE)));
        // natural order
        assertEquals(-1, StringComparators.longComparator(2L, 3L, Comparator.naturalOrder()).compare("1", "2"));
        assertEquals(1, StringComparators.longComparator(2L, 3L, Comparator.naturalOrder()).compare("2", "1"));
        assertEquals(-1, StringComparators.longComparator(2L, 3L, Comparator.naturalOrder()).compare("1000000000000", "2000000000000"));
        // reverseOrder
        assertEquals(1, StringComparators.longComparator(2L, 3L, Comparator.reverseOrder()).compare("1", "2"));
        assertEquals(-1, StringComparators.longComparator(2L, 3L, Comparator.reverseOrder()).compare("2", "1"));
        assertEquals(1, StringComparators.longComparator(2L, 3L, Comparator.reverseOrder()).compare("1000000000000", "2000000000000"));
    }

    /**
     * Test method for {@link StringComparators#doubleComparator(Double, Double, java.util.Comparator)}
     */
    @SuppressWarnings("DataFlowIssue")
    @Test
    void doubleComparator() {
        // null
        assertEquals(1, StringComparators.doubleComparator(2.0d, 3.0d, Double::compareTo).compare(null, "1"));
        assertEquals(-1, StringComparators.doubleComparator(2.0d, 3.0d, Double::compareTo).compare("1", null));
        assertEquals(0, StringComparators.doubleComparator(2.0d, 3.0d, Double::compareTo).compare(null, "2"));
        assertEquals(0, StringComparators.doubleComparator(2.0d, 3.0d, Double::compareTo).compare("2", null));
        // equal strings
        assertEquals(0, StringComparators.doubleComparator(2.0d, 3.0d, Double::compareTo).compare("1", "1"));
        assertEquals(0, StringComparators.doubleComparator(2.0d, 3.0d, Double::compareTo).compare("2", "2"));
        assertEquals(0, StringComparators.doubleComparator(2.0d, 3.0d, Double::compareTo).compare("3.45678", "3.45678"));
        // not parsable
        assertEquals(0, StringComparators.doubleComparator(2.0d, 3.0d, Double::compareTo).compare("a", "b"));
        assertEquals(0, StringComparators.doubleComparator(2.0d, 3.0d, Double::compareTo).compare("a", "3"));
        assertEquals(0, StringComparators.doubleComparator(2.0d, 3.0d, Double::compareTo).compare("3", "a"));
        assertEquals(1, StringComparators.doubleComparator(2.0d, 3.0d, Double::compareTo).compare("a", "1"));
        assertEquals(-1, StringComparators.doubleComparator(2.0d, 3.0d, Double::compareTo).compare("1", "a"));
        // different strings
        assertEquals(-1, StringComparators.doubleComparator(2.0d, 3.0d, Double::compareTo).compare("1", "2"));
        assertEquals(-1, StringComparators.doubleComparator(2.0d, 3.0d, Double::compareTo).compare("2", "3.45678"));
        assertEquals(-1, StringComparators.doubleComparator(2.0d, 3.0d, Double::compareTo).compare("1", "3.45678"));
        assertEquals(1, StringComparators.doubleComparator(2.0d, 3.0d, Double::compareTo).compare("2", "1"));
        assertEquals(1, StringComparators.doubleComparator(2.0d, 3.0d, Double::compareTo).compare("3.45678", "2"));
        assertEquals(1, StringComparators.doubleComparator(2.0d, 3.0d, Double::compareTo).compare("3.45678", "1"));
        // min and max
        assertEquals(1, StringComparators.doubleComparator(1.0d, 1.0d, Double::compareTo).compare("3.45678", String.valueOf(Double.MIN_VALUE)));
        assertEquals(-1, StringComparators.doubleComparator(1.0d, 1.0d, Double::compareTo).compare("3.45678", String.valueOf(Double.MAX_VALUE)));
        assertEquals(-1, StringComparators.doubleComparator(1.0d, 1.0d, Double::compareTo).compare(String.valueOf(Double.MIN_VALUE), String.valueOf(Double.MAX_VALUE)));
        // natural order
        assertEquals(-1, StringComparators.doubleComparator(2.0d, 3.0d, Comparator.naturalOrder()).compare("1", "2"));
        assertEquals(1, StringComparators.doubleComparator(2.0d, 3.0d, Comparator.naturalOrder()).compare("2", "1"));
        assertEquals(-1, StringComparators.doubleComparator(2.0d, 3.0d, Comparator.naturalOrder()).compare("3.45678", "3.456789"));
        // reverseOrder
        assertEquals(1, StringComparators.doubleComparator(2.0d, 3.0d, Comparator.reverseOrder()).compare("1", "2"));
        assertEquals(-1, StringComparators.doubleComparator(2.0d, 3.0d, Comparator.reverseOrder()).compare("2", "1"));
        assertEquals(1, StringComparators.doubleComparator(2.0d, 3.0d, Comparator.reverseOrder()).compare("3.45678", "3.456789"));
    }

    /**
     * Test method for {@link StringComparators#extractorComparator(java.util.function.Function, java.util.Comparator)}
     */
    @SuppressWarnings("DataFlowIssue")
    @Test
    void extractorComparator() {
        // String and Function.identity() with null
        assertThrows(NullPointerException.class, () -> StringComparators.extractorComparator(Function.identity(), String::compareTo).compare(null, "a"));
        // String and Function.identity() with equal strings
        assertEquals(0, StringComparators.extractorComparator(Function.identity(), String::compareTo).compare("a", "a"));
        // String and Function.identity() with different strings
        assertTrue(StringComparators.extractorComparator(Function.identity(), String::compareTo).compare("a", "b") < 0);
        assertTrue(StringComparators.extractorComparator(Function.identity(), String::compareTo).compare("b", "a") > 0);

        // String and last character with equal strings
        assertEquals(0, StringComparators.extractorComparator(s -> s.substring(s.length() - 1), String::compareTo).compare("a", "a"));
        assertEquals(0, StringComparators.extractorComparator(s -> s.substring(s.length() - 1), String::compareTo).compare("abc", "abc"));
        // String and last character with different strings but equal last character
        assertEquals(0, StringComparators.extractorComparator(s -> s.substring(s.length() - 1), String::compareTo).compare("abc", "ABc"));
        assertEquals(0, StringComparators.extractorComparator(s -> s.substring(s.length() - 1), String::compareTo).compare("abc", "c"));
        // String and last character with different strings
        assertTrue(StringComparators.extractorComparator(s -> s.substring(s.length() - 1), String::compareTo).compare("abc", "abd") < 0);
        assertTrue(StringComparators.extractorComparator(s -> s.substring(s.length() - 1), String::compareTo).compare("abd", "abc") > 0);
    }

}