package stexfires.util;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.text.Collator;
import java.util.*;
import java.util.function.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link StringComparators}.
 */
@SuppressWarnings({"EqualsWithItself", "MagicNumber"})
final class StringComparatorsTest {

    /**
     * Test method for {@link StringComparators#compareTo()}.
     */
    @Test
    void compareTo() {
        // null
        // noinspection DataFlowIssue
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
    @Test
    void compareToIgnoreCase() {
        // null
        // noinspection DataFlowIssue
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
    @Test
    void collatorComparator_Collator() {
        Collator collator = Collator.getInstance(Locale.GERMAN);
        // null
        // noinspection DataFlowIssue
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
    @Test
    void collatorComparator_Locale() {
        Locale locale = Locale.GERMAN;
        // null
        // noinspection DataFlowIssue
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
    @Test
    void collatorComparator_Locale_Strength() {
        Locale locale = Locale.GERMAN;
        // PRIMARY
        int strengthPrimary = Collator.PRIMARY;
        // null
        // noinspection DataFlowIssue
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
        // noinspection DataFlowIssue
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
        // noinspection DataFlowIssue
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
        // noinspection DataFlowIssue
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
        UnaryOperator<@Nullable String> unaryOperator = s -> (s == null) ? null : s.toUpperCase(Locale.GERMAN).trim(); // Converts to upper case and removes leading and trailing spaces
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
    @Test
    void lengthComparator() {
        // null
        // noinspection DataFlowIssue
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
    @Test
    void integerComparator() {
        var c = SortNulls.FIRST.wrap(Integer::compare);

        // null
        assertEquals(1, StringComparators.integerComparator(2, 3, c).compare(null, "1"));
        assertEquals(-1, StringComparators.integerComparator(2, 3, c).compare("1", null));
        assertEquals(0, StringComparators.integerComparator(2, 3, c).compare(null, "2"));
        assertEquals(0, StringComparators.integerComparator(2, 3, c).compare("2", null));
        // equal strings
        assertEquals(0, StringComparators.integerComparator(2, 3, c).compare("1", "1"));
        assertEquals(0, StringComparators.integerComparator(2, 3, c).compare("2", "2"));
        assertEquals(0, StringComparators.integerComparator(2, 3, c).compare("3", "3"));
        // not parsable
        assertEquals(0, StringComparators.integerComparator(2, 3, c).compare("a", "b"));
        assertEquals(0, StringComparators.integerComparator(2, 3, c).compare("a", "3"));
        assertEquals(0, StringComparators.integerComparator(2, 3, c).compare("3", "a"));
        assertEquals(1, StringComparators.integerComparator(2, 3, c).compare("a", "1"));
        assertEquals(-1, StringComparators.integerComparator(2, 3, c).compare("1", "a"));
        // different strings
        assertEquals(-1, StringComparators.integerComparator(2, 3, c).compare("1", "2"));
        assertEquals(-1, StringComparators.integerComparator(2, 3, c).compare("2", "3"));
        assertEquals(-1, StringComparators.integerComparator(2, 3, c).compare("1", "3"));
        assertEquals(1, StringComparators.integerComparator(2, 3, c).compare("2", "1"));
        assertEquals(1, StringComparators.integerComparator(2, 3, c).compare("3", "2"));
        assertEquals(1, StringComparators.integerComparator(2, 3, c).compare("3", "1"));
        // min and max
        assertEquals(1, StringComparators.integerComparator(1, 1, c).compare("1", String.valueOf(Integer.MIN_VALUE)));
        assertEquals(-1, StringComparators.integerComparator(1, 1, c).compare("1", String.valueOf(Integer.MAX_VALUE)));
        assertEquals(-1, StringComparators.integerComparator(1, 1, c).compare(String.valueOf(Integer.MIN_VALUE), String.valueOf(Integer.MAX_VALUE)));
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
    @Test
    void longComparator() {
        var c = SortNulls.FIRST.wrap(Long::compare);

        // null
        assertEquals(1, StringComparators.longComparator(2L, 3L, c).compare(null, "1"));
        assertEquals(-1, StringComparators.longComparator(2L, 3L, c).compare("1", null));
        assertEquals(0, StringComparators.longComparator(2L, 3L, c).compare(null, "2"));
        assertEquals(0, StringComparators.longComparator(2L, 3L, c).compare("2", null));
        // equal strings
        assertEquals(0, StringComparators.longComparator(2L, 3L, c).compare("1", "1"));
        assertEquals(0, StringComparators.longComparator(2L, 3L, c).compare("2", "2"));
        assertEquals(0, StringComparators.longComparator(2L, 3L, c).compare("3", "3"));
        // not parsable
        assertEquals(0, StringComparators.longComparator(2L, 3L, c).compare("a", "b"));
        assertEquals(0, StringComparators.longComparator(2L, 3L, c).compare("a", "3"));
        assertEquals(0, StringComparators.longComparator(2L, 3L, c).compare("3", "a"));
        assertEquals(1, StringComparators.longComparator(2L, 3L, c).compare("a", "1"));
        assertEquals(-1, StringComparators.longComparator(2L, 3L, c).compare("1", "a"));
        // different strings
        assertEquals(-1, StringComparators.longComparator(2L, 3L, c).compare("1", "2"));
        assertEquals(-1, StringComparators.longComparator(2L, 3L, c).compare("2", "3"));
        assertEquals(-1, StringComparators.longComparator(2L, 3L, c).compare("1", "3"));
        assertEquals(1, StringComparators.longComparator(2L, 3L, c).compare("2", "1"));
        assertEquals(1, StringComparators.longComparator(2L, 3L, c).compare("3", "2"));
        assertEquals(1, StringComparators.longComparator(2L, 3L, c).compare("3", "1"));
        // min and max
        assertEquals(1, StringComparators.longComparator(1L, 1L, c).compare("1", String.valueOf(Long.MIN_VALUE)));
        assertEquals(-1, StringComparators.longComparator(1L, 1L, c).compare("1", String.valueOf(Long.MAX_VALUE)));
        assertEquals(-1, StringComparators.longComparator(1L, 1L, c).compare(String.valueOf(Long.MIN_VALUE), String.valueOf(Long.MAX_VALUE)));
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
    @Test
    void doubleComparator() {
        var c = SortNulls.FIRST.wrap(Double::compare);

        // null
        assertEquals(1, StringComparators.doubleComparator(2.0d, 3.0d, c).compare(null, "1"));
        assertEquals(-1, StringComparators.doubleComparator(2.0d, 3.0d, c).compare("1", null));
        assertEquals(0, StringComparators.doubleComparator(2.0d, 3.0d, c).compare(null, "2"));
        assertEquals(0, StringComparators.doubleComparator(2.0d, 3.0d, c).compare("2", null));
        // equal strings
        assertEquals(0, StringComparators.doubleComparator(2.0d, 3.0d, c).compare("1", "1"));
        assertEquals(0, StringComparators.doubleComparator(2.0d, 3.0d, c).compare("2", "2"));
        assertEquals(0, StringComparators.doubleComparator(2.0d, 3.0d, c).compare("3.45678", "3.45678"));
        // not parsable
        assertEquals(0, StringComparators.doubleComparator(2.0d, 3.0d, c).compare("a", "b"));
        assertEquals(0, StringComparators.doubleComparator(2.0d, 3.0d, c).compare("a", "3"));
        assertEquals(0, StringComparators.doubleComparator(2.0d, 3.0d, c).compare("3", "a"));
        assertEquals(1, StringComparators.doubleComparator(2.0d, 3.0d, c).compare("a", "1"));
        assertEquals(-1, StringComparators.doubleComparator(2.0d, 3.0d, c).compare("1", "a"));
        // different strings
        assertEquals(-1, StringComparators.doubleComparator(2.0d, 3.0d, c).compare("1", "2"));
        assertEquals(-1, StringComparators.doubleComparator(2.0d, 3.0d, c).compare("2", "3.45678"));
        assertEquals(-1, StringComparators.doubleComparator(2.0d, 3.0d, c).compare("1", "3.45678"));
        assertEquals(1, StringComparators.doubleComparator(2.0d, 3.0d, c).compare("2", "1"));
        assertEquals(1, StringComparators.doubleComparator(2.0d, 3.0d, c).compare("3.45678", "2"));
        assertEquals(1, StringComparators.doubleComparator(2.0d, 3.0d, c).compare("3.45678", "1"));
        // min and max
        assertEquals(1, StringComparators.doubleComparator(1.0d, 1.0d, c).compare("3.45678", String.valueOf(Double.MIN_VALUE)));
        assertEquals(-1, StringComparators.doubleComparator(1.0d, 1.0d, c).compare("3.45678", String.valueOf(Double.MAX_VALUE)));
        assertEquals(-1, StringComparators.doubleComparator(1.0d, 1.0d, c).compare(String.valueOf(Double.MIN_VALUE), String.valueOf(Double.MAX_VALUE)));
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
    @Test
    void extractorComparator() {
        var c = SortNulls.FIRST.wrap(String::compareTo);
        Function<@Nullable String, @Nullable String> extractor = s -> (s != null) ? s.substring(s.length() - 1) : null;

        // String and Function.identity() with null
        assertEquals(-1, StringComparators.extractorComparator(Function.identity(), c).compare(null, "a"));
        // String and Function.identity() with equal strings
        assertEquals(0, StringComparators.extractorComparator(Function.identity(), c).compare("a", "a"));
        // String and Function.identity() with different strings
        assertTrue(StringComparators.extractorComparator(Function.identity(), c).compare("a", "b") < 0);
        assertTrue(StringComparators.extractorComparator(Function.identity(), c).compare("b", "a") > 0);

        // String and last character with equal strings
        assertEquals(0, StringComparators.extractorComparator(extractor, c).compare("a", "a"));
        assertEquals(0, StringComparators.extractorComparator(extractor, c).compare("abc", "abc"));
        // String and last character with different strings but equal last character
        assertEquals(0, StringComparators.extractorComparator(extractor, c).compare("abc", "ABc"));
        assertEquals(0, StringComparators.extractorComparator(extractor, c).compare("abc", "c"));
        // String and last character with different strings
        assertTrue(StringComparators.extractorComparator(extractor, c).compare("abc", "abd") < 0);
        assertTrue(StringComparators.extractorComparator(extractor, c).compare("abd", "abc") > 0);
    }

}