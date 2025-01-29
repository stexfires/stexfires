package stexfires.examples.javatest;

import java.text.CompactNumberFormat;
import java.text.NumberFormat;
import java.util.*;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public final class CompactNumberFormatTest {

    private static final int[] INT_VALUES = new int[]{-1_000_000, 1_000, 1_499, 1_500, 5_000, 10_000, 100_000, 999_999, 1_000_000};

    private CompactNumberFormatTest() {
    }

    private static void showCompactNumberFormatTest() {
        System.out.println("-showCompactNumberFormatTest---");

        CompactNumberFormat formatShortUS = (CompactNumberFormat) NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);
        CompactNumberFormat formatLongUS = (CompactNumberFormat) NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.LONG);
        CompactNumberFormat formatShortDE = (CompactNumberFormat) NumberFormat.getCompactNumberInstance(Locale.GERMANY, NumberFormat.Style.SHORT);
        CompactNumberFormat formatLongDE = (CompactNumberFormat) NumberFormat.getCompactNumberInstance(Locale.GERMANY, NumberFormat.Style.LONG);
        CompactNumberFormat formatShortJP = (CompactNumberFormat) NumberFormat.getCompactNumberInstance(Locale.JAPAN, NumberFormat.Style.SHORT);
        CompactNumberFormat formatLongJP = (CompactNumberFormat) NumberFormat.getCompactNumberInstance(Locale.JAPAN, NumberFormat.Style.LONG);

        for (int i : INT_VALUES) {
            System.out.println("Standard : " + i);
            System.out.println("US Short : " + formatShortUS.format(i));
            System.out.println("US Long  : " + formatLongUS.format(i));
            System.out.println("DE Short : " + formatShortDE.format(i));
            System.out.println("DE Long  : " + formatLongDE.format(i));
            System.out.println("JP Short : " + formatShortJP.format(i));
            System.out.println("JP Long  : " + formatLongJP.format(i));
        }
    }

    public static void main(String... args) {
        showCompactNumberFormatTest();
    }

}
