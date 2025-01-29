package stexfires.examples.javatest;

import org.jspecify.annotations.Nullable;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "MethodCanBeVariableArityMethod", "DataFlowIssue", "NullableProblems", "ConfusingArgumentToVarargsMethod", "ConstantValue"})
public final class JSpecifyTest {

    private JSpecifyTest() {
    }

    public static void testStringVarargs1(@Nullable String... stringValues) {
        if (stringValues == null) {
            System.out.println("testStringVarargs1 is NULL");
        } else if (stringValues.length == 0) {
            System.out.println("testStringVarargs1 is EMPTY");
        } else {
            System.out.println("testStringVarargs1 has length=" + stringValues.length);
        }
    }

    public static void testStringVarargs2(String @Nullable ... stringValues) {
        if (stringValues == null) {
            System.out.println("testStringVarargs2 is NULL");
        } else if (stringValues.length == 0) {
            System.out.println("testStringVarargs2 is EMPTY");
        } else {
            System.out.println("testStringVarargs2 has length=" + stringValues.length);
        }
    }

    public static void testStringArray1(@Nullable String[] stringValues) {
        if (stringValues == null) {
            System.out.println("testStringArray1 is NULL");
        } else if (stringValues.length == 0) {
            System.out.println("testStringArray1 is EMPTY");
        } else {
            System.out.println("testStringArray1 has length=" + stringValues.length);
        }
    }

    public static void testStringArray2(String @Nullable [] stringValues) {
        if (stringValues == null) {
            System.out.println("testStringArray2 is NULL");
        } else if (stringValues.length == 0) {
            System.out.println("testStringArray2 is EMPTY");
        } else {
            System.out.println("testStringArray2 has length=" + stringValues.length);
        }
    }

    public static void testIntVarargs1(int... intValues) {
        if (intValues == null) {
            System.out.println("testIntVarargs1 is NULL");
        } else if (intValues.length == 0) {
            System.out.println("testIntVarargs1 is EMPTY");
        } else {
            System.out.println("testIntVarargs1 has length=" + intValues.length);
        }
    }

    public static void testIntVarargs2(int @Nullable ... intValues) {
        if (intValues == null) {
            System.out.println("testIntVarargs2 is NULL");
        } else if (intValues.length == 0) {
            System.out.println("testIntVarargs2 is EMPTY");
        } else {
            System.out.println("testIntVarargs2 has length=" + intValues.length);
        }
    }

    public static void testIntArray1(int[] intValues) {
        if (intValues == null) {
            System.out.println("testIntArray1 is NULL");
        } else if (intValues.length == 0) {
            System.out.println("testIntArray1 is EMPTY");
        } else {
            System.out.println("testIntArray1 has length=" + intValues.length);
        }
    }

    public static void testIntArray2(int @Nullable [] intValues) {
        if (intValues == null) {
            System.out.println("testIntArray2 is NULL");
        } else if (intValues.length == 0) {
            System.out.println("testIntArray2 is EMPTY");
        } else {
            System.out.println("testIntArray2 has length=" + intValues.length);
        }
    }

    private static void testString() {
        System.out.println("---testStringVarargs1");
        testStringVarargs1("A");
        testStringVarargs1("B", "C");
        testStringVarargs1("D", null, "E");
        testStringVarargs1("B", "C");
        testStringVarargs1("D", null, "E");
        testStringVarargs1();
        testStringVarargs1((String[]) null); // Warning
        testStringVarargs1(null); // Warning
        testStringVarargs1((String) null);

        System.out.println("---testStringVarargs2");
        testStringVarargs2("A");
        testStringVarargs2("B", "C");
        testStringVarargs2("D", null, "E"); // Warning
        testStringVarargs2("B", "C");
        testStringVarargs2("D", null, "E"); // Warning
        testStringVarargs2();
        testStringVarargs2((String[]) null);
        testStringVarargs2(null); // Warning
        testStringVarargs2((String) null); // Warning

        System.out.println("---testStringArray1");
        testStringArray1(new String[]{"A"});
        testStringArray1(new String[]{"B", "C"});
        testStringArray1(new @Nullable String[]{"D", null, "E"});
        testStringArray1(new String[]{});
        testStringArray1(null); // Warning
        testStringArray1(null); // Warning

        System.out.println("---testStringArray2");
        testStringArray2(new String[]{"A"});
        testStringArray2(new String[]{"B", "C"});
        testStringArray2(new @Nullable String[]{"D", null, "E"}); // Warning
        testStringArray2(new String[]{});
        testStringArray2(null);
        testStringArray2(null);
    }

    private static void testInt() {
        System.out.println("---testIntVarargs1");
        testIntVarargs1(1);
        testIntVarargs1(2, 3);
        testIntVarargs1(4, 5);
        testIntVarargs1();
        testIntVarargs1((int[]) null); // Warning
        testIntVarargs1(null); // Warning

        System.out.println("---testIntVarargs2");
        testIntVarargs2(1);
        testIntVarargs2(2, 3);
        testIntVarargs2(4, 5);
        testIntVarargs2();
        testIntVarargs2((int[]) null);
        testIntVarargs2(null); // Warning

        System.out.println("---testIntArray1");
        testIntArray1(new int[]{1});
        testIntArray1(new int[]{4, 5});
        testIntArray1(new int[]{});
        testIntArray1(null); // Warning
        testIntArray1(null); // Warning

        System.out.println("---testIntArray2");
        testIntArray2(new int[]{1});
        testIntArray2(new int[]{4, 5});
        testIntArray2(new int[]{});
        testIntArray2(null);
        testIntArray2(null);
    }

    public static void main(String... args) {
        testString();
        testInt();
    }

}
