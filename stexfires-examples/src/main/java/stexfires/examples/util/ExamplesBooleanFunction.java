package stexfires.examples.util;

import stexfires.util.function.BooleanBinaryOperator;
import stexfires.util.function.BooleanUnaryOperator;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "SpellCheckingInspection"})
public final class ExamplesBooleanFunction {

    private ExamplesBooleanFunction() {
    }

    private static void printBooleanUnaryOperator(String title, BooleanUnaryOperator operator) {
        System.out.println(title + " : false -> " + operator.applyAsBoolean(false));
        System.out.println(title + " : true  -> " + operator.applyAsBoolean(true));
    }

    private static void printBooleanBinaryOperator(String title, BooleanBinaryOperator operator) {
        System.out.println(title + " : false false -> " + operator.applyAsBoolean(false, false));
        System.out.println(title + " : false true  -> " + operator.applyAsBoolean(false, true));
        System.out.println(title + " : true  false -> " + operator.applyAsBoolean(true, false));
        System.out.println(title + " : true  true  -> " + operator.applyAsBoolean(true, true));
    }

    private static void showBooleanUnaryOperator() {
        System.out.println("-showBooleanUnaryOperator---");

        printBooleanUnaryOperator("identity", BooleanUnaryOperator.identity());
        printBooleanUnaryOperator("constant true", BooleanUnaryOperator.constant(true));
        printBooleanUnaryOperator("supplier false", BooleanUnaryOperator.supplier(() -> false));

        printBooleanUnaryOperator("NOT", BooleanUnaryOperator.NOT());

        printBooleanUnaryOperator("AND identity, NOT", BooleanUnaryOperator.AND(BooleanUnaryOperator.identity(), BooleanUnaryOperator.NOT()));
        printBooleanUnaryOperator("AND NOT, NOT", BooleanUnaryOperator.AND(BooleanUnaryOperator.NOT(), BooleanUnaryOperator.NOT()));

        printBooleanUnaryOperator("OR identity, NOT", BooleanUnaryOperator.OR(BooleanUnaryOperator.identity(), BooleanUnaryOperator.NOT()));
        printBooleanUnaryOperator("OR NOT, NOT", BooleanUnaryOperator.OR(BooleanUnaryOperator.NOT(), BooleanUnaryOperator.NOT()));

        printBooleanUnaryOperator("compose identity, NOT", BooleanUnaryOperator.identity().compose(BooleanUnaryOperator.NOT()));
        printBooleanUnaryOperator("andThen identity, NOT", BooleanUnaryOperator.identity().andThen(BooleanUnaryOperator.NOT()));
        printBooleanUnaryOperator("and identity, NOT", BooleanUnaryOperator.identity().and(BooleanUnaryOperator.NOT()));
        printBooleanUnaryOperator("or identity, NOT", BooleanUnaryOperator.identity().or(BooleanUnaryOperator.NOT()));
        printBooleanUnaryOperator("negate identity", BooleanUnaryOperator.identity().negate());
        printBooleanUnaryOperator("negate NOT", BooleanUnaryOperator.NOT().negate());

        System.out.println("asPredicate NOT : false -> " + BooleanUnaryOperator.NOT().asPredicate().test(Boolean.FALSE));
        System.out.println("asPredicate NOT : true -> " + BooleanUnaryOperator.NOT().asPredicate().test(Boolean.TRUE));
        System.out.println("asUnaryOperator NOT : false -> " + BooleanUnaryOperator.NOT().asUnaryOperator().apply(Boolean.FALSE));
        System.out.println("asUnaryOperator NOT : true -> " + BooleanUnaryOperator.NOT().asUnaryOperator().apply(Boolean.TRUE));
    }

    private static void showBooleanBinaryOperator() {
        System.out.println("-showBooleanBinaryOperator---");

        printBooleanBinaryOperator("constant ", BooleanBinaryOperator.constant(true));
        printBooleanBinaryOperator("supplier ", BooleanBinaryOperator.supplier(() -> false));
        printBooleanBinaryOperator("first    ", BooleanBinaryOperator.first());
        printBooleanBinaryOperator("second   ", BooleanBinaryOperator.second());
        printBooleanBinaryOperator("equals   ", BooleanBinaryOperator.equals());

        printBooleanBinaryOperator("AND  ", BooleanBinaryOperator.AND());
        printBooleanBinaryOperator("NAND ", BooleanBinaryOperator.AND());

        printBooleanBinaryOperator("OR   ", BooleanBinaryOperator.OR());
        printBooleanBinaryOperator("NOR  ", BooleanBinaryOperator.NOR());

        printBooleanBinaryOperator("XOR  ", BooleanBinaryOperator.XOR());
        printBooleanBinaryOperator("XNOR ", BooleanBinaryOperator.XNOR());

        printBooleanBinaryOperator("andThen AND NOT", BooleanBinaryOperator.AND().andThen(BooleanUnaryOperator.NOT()));

        System.out.println("asBinaryOperator AND : true  false -> " + BooleanBinaryOperator.AND().asBinaryOperator().apply(Boolean.TRUE, Boolean.FALSE));
        System.out.println("asBooleanUnaryOperator AND : true  false -> " + BooleanBinaryOperator.AND().asBooleanUnaryOperator(false).applyAsBoolean(true));
        System.out.println("asPredicate AND : true  false -> " + BooleanBinaryOperator.AND().asPredicate(false).test(Boolean.TRUE));
        System.out.println("asUnaryOperator AND : true  false -> " + BooleanBinaryOperator.AND().asUnaryOperator(false).apply(Boolean.TRUE));
    }

    public static void main(String... args) {
        showBooleanUnaryOperator();
        showBooleanBinaryOperator();
    }

}
