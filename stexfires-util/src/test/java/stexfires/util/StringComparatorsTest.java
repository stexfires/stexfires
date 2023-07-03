package stexfires.util;

import org.junit.jupiter.api.Test;

import java.text.Collator;
import java.util.Locale;
import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link StringComparators}.
 */
@SuppressWarnings("EqualsWithItself")
class StringComparatorsTest {

    /**
     * Test method for {@link StringComparators#compareTo()}.
     */
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
     * Test method for {@link StringComparators#normalizedComparator(java.util.function.UnaryOperator, java.util.Comparator)}.
     */
    @Test
    void normalizedComparator() {
        UnaryOperator<String> unaryOperator = s -> s.toUpperCase(Locale.GERMAN).trim(); // Converts to upper case and removes leading and trailing spaces
        assertThrows(NullPointerException.class, () -> StringComparators.normalizedComparator(unaryOperator, String::compareTo).compare(null, "a"));
        // equal strings
        assertEquals(0, StringComparators.normalizedComparator(unaryOperator, String::compareTo).compare("a", "a"));
        // different strings
        assertTrue(StringComparators.normalizedComparator(unaryOperator, String::compareTo).compare("a", "b") < 0);
        assertEquals(0, StringComparators.normalizedComparator(unaryOperator, String::compareTo).compare("a ", " A"));
        assertTrue(StringComparators.normalizedComparator(unaryOperator, String::compareTo).compare("a ", " B") < 0);
    }

    /**
     * Test method for {@link StringComparators#lengthComparator()}.
     */
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
    }

    /**
     * Test method for {@link StringComparators#primitiveLongComparator(long, long)}
     */
    @Test
    void primitiveLongComparator() {
    }

    /**
     * Test method for {@link StringComparators#primitiveDoubleComparator(double, double)}
     */
    @Test
    void primitiveDoubleComparator() {
    }

    /**
     * Test method for {@link StringComparators#integerComparator(Integer, Integer, java.util.Comparator)}
     */
    @Test
    void integerComparator() {
    }

    /**
     * Test method for {@link StringComparators#longComparator(Long, Long, java.util.Comparator)}
     */
    @Test
    void longComparator() {
    }

    /**
     * Test method for {@link StringComparators#doubleComparator(Double, Double, java.util.Comparator)}
     */
    @Test
    void doubleComparator() {
    }

    /**
     * Test method for {@link StringComparators#extractorComparator(java.util.function.Function, java.util.Comparator)}
     */
    @Test
    void extractorComparator() {
    }

}