package stexfires.examples.util;

import stexfires.util.CommonCharsetNames;

import java.nio.charset.StandardCharsets;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public final class ExamplesCommonCharsetNames {

    private ExamplesCommonCharsetNames() {
    }

    private static void showCommonCharsetNames() {
        System.out.println("-showCommonCharsetNames---");

        for (var charset : CommonCharsetNames.values()) {
            System.out.println(charset.name() + " (canonicalName())    ? " + charset.canonicalName());
            System.out.println(charset.name() + " (charset())          ? " + charset.charset());
        }

        System.out.println();
        System.out.println("US_ASCII : " + CommonCharsetNames.ofStandardCharset(StandardCharsets.US_ASCII));
        System.out.println("UTF_8    : " + CommonCharsetNames.ofStandardCharset(StandardCharsets.UTF_8));
    }

    public static void main(String... args) {
        showCommonCharsetNames();
    }

}
