package stexfires.examples.util;

import stexfires.util.LineSeparator;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public final class ExamplesLineSeparator {

    private ExamplesLineSeparator() {
    }

    private static void showLineSeparator() {
        System.out.println("-showLineSeparator---");

        System.out.println("systemLineSeparator: " + LineSeparator.systemLineSeparator().name());
        System.out.println("repeat count 3: " + LineSeparator.systemLineSeparator().repeat(3));

        for (var separator : LineSeparator.values()) {
            System.out.println(separator.name() + " (string())             ? " + separator.string());
            System.out.println(separator.name() + " (length())             ? " + separator.length());
            System.out.println(separator.name() + " (supplier() get)       ? " + separator.supplier().get());
            System.out.println(separator.name() + " (stream() toList)      ? " + separator.stream().toList());
            System.out.println(separator.name() + " (chars() toList)       ? " + separator.chars().boxed().toList());
            System.out.println(separator.name() + " (codePoints() toList)  ? " + separator.codePoints().boxed().toList());
            System.out.println(separator.name() + " (name())               ? " + separator.name());
            System.out.println(separator.name() + " (toString())           ? " + separator);
        }
    }

    public static void main(String... args) {
        showLineSeparator();
    }

}
