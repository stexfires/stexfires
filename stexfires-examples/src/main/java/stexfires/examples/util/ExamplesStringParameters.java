package stexfires.examples.util;

import stexfires.util.StringParameters;

import java.util.*;

@SuppressWarnings({"UseOfSystemOutOrSystemErr"})
public final class ExamplesStringParameters {

    private ExamplesStringParameters() {
    }

    private static void printParameter(StringParameters stringParameters, String key) {
        System.out.println(key + " = " + stringParameters.value(key).orElse("<null>"));
    }

    private static void showStringParameter() {
        System.out.println("-showStringParameter---");

        StringParameters stringParameters = new StringParameters.Builder()
                .addSystemEnvironments()
                .addSystemProperties()
                .addParameter("key1", "value1")
                .addParameters(Map.of("key2", "value2", "key3", "value3"))
                .addParameter("key3", "value3b")
                .build();

        // size
        System.out.println("---size");
        System.out.println("size = " + stringParameters.size());
        // all
        System.out.println("---all");
        stringParameters.keySet().forEach(key -> System.out.println(key + " = " + stringParameters.value(key).orElse("<null>")));
        // keys which starts with "java"
        System.out.println("---keys which starts with \"java.\"");
        stringParameters.keySet().stream()
                        .filter(key -> key.startsWith("java."))
                        .sorted()
                        .forEach(key -> System.out.println(key + " = " + stringParameters.value(key).orElse("<null>")));
        // system environments
        System.out.println("---system environments");
        printParameter(stringParameters, "APPDATA");
        printParameter(stringParameters, "USERNAME");
        printParameter(stringParameters, "TMP");
        // system properties
        System.out.println("---system properties");
        printParameter(stringParameters, "java.version");
        printParameter(stringParameters, "user.name");
        // own parameters
        System.out.println("---own parameters");
        printParameter(stringParameters, "key1");
        printParameter(stringParameters, "key2");
        printParameter(stringParameters, "key3");
        printParameter(stringParameters, "key4");
    }

    public static void main(String... args) {
        showStringParameter();
    }

}
