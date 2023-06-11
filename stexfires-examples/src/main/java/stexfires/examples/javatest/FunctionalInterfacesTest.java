package stexfires.examples.javatest;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

@SuppressWarnings({"SpellCheckingInspection", "EmptyMethod", "ResultOfMethodCallIgnored", "Convert2MethodRef"})
public final class FunctionalInterfacesTest {

    public FunctionalInterfacesTest() {
    }

    public void testFunctionaInterfaces() {
        functionTest(MyTestObject::function0);
        functionTest(m0 -> m0.function0());
        functionTest(MyTestObject::staticFunction1);
        functionTest(m0 -> MyTestObject.staticFunction1(m0));

        unaryOperatorTest(MyTestObject::function0);
        unaryOperatorTest(m0 -> m0.function0());
        unaryOperatorTest(MyTestObject::staticFunction1);
        unaryOperatorTest(m0 -> MyTestObject.staticFunction1(m0));

        biFunctionTest(MyTestObject::function1);
        biFunctionTest((m0, m1) -> m0.function1(m1));
        biFunctionTest(MyTestObject::staticFunction2);
        biFunctionTest((m0, m1) -> MyTestObject.staticFunction2(m0, m1));

        predicateTest(MyTestObject::predicate0);
        predicateTest(m0 -> m0.predicate0());
        predicateTest(MyTestObject::staticPredicate1);
        predicateTest(m0 -> MyTestObject.staticPredicate1(m0));

        biPredicateTest(MyTestObject::predicate1);
        biPredicateTest((m0, m1) -> m0.predicate1(m1));
        biPredicateTest(MyTestObject::staticPredicate2);
        biPredicateTest((m0, m1) -> MyTestObject.staticPredicate2(m0, m1));

        consumerTest(MyTestObject::voidMethod0);
        consumerTest(m0 -> m0.voidMethod0());
        consumerTest(MyTestObject::staticVoidMethod1);
        consumerTest(m0 -> MyTestObject.staticVoidMethod1(m0));

        consumerTest(MyTestObject::function0);
        consumerTest(m0 -> m0.function0());
        consumerTest(MyTestObject::staticFunction1);
        consumerTest(m0 -> MyTestObject.staticFunction1(m0));

        consumerTest(MyTestObject::predicate0);
        consumerTest(m0 -> m0.predicate0());
        consumerTest(MyTestObject::staticPredicate1);
        consumerTest(m0 -> MyTestObject.staticPredicate1(m0));
    }

    private void functionTest(Function<MyTestObject, MyTestObject> function) {
    }

    private void unaryOperatorTest(UnaryOperator<MyTestObject> unaryOperator) {
    }

    private void biFunctionTest(BiFunction<MyTestObject, MyTestObject, MyTestObject> bifunction) {
    }

    private void predicateTest(Predicate<MyTestObject> predicate) {
    }

    private void biPredicateTest(BiPredicate<MyTestObject, MyTestObject> predicate) {
    }

    private void consumerTest(Consumer<MyTestObject> consumer) {
    }

    @SuppressWarnings({"SameReturnValue", "UnusedReturnValue", "MethodMayBeStatic"})
    public static final class MyTestObject {

        public MyTestObject() {
        }

        public static boolean staticPredicate0() {
            return true;
        }

        public static boolean staticPredicate1(MyTestObject myTestObject) {
            return true;
        }

        public static boolean staticPredicate2(MyTestObject myTestObject0, MyTestObject myTestObject1) {
            return true;
        }

        public static MyTestObject staticFunction0() {
            return new MyTestObject();
        }

        public static MyTestObject staticFunction1(MyTestObject myTestObject) {
            return new MyTestObject();
        }

        public static MyTestObject staticFunction2(MyTestObject myTestObject0, MyTestObject myTestObject1) {
            return new MyTestObject();
        }

        public static void staticVoidMethod0() {
        }

        public static void staticVoidMethod1(MyTestObject myTestObject) {
        }

        public static void staticVoidMethod2(MyTestObject myTestObject0, MyTestObject myTestObject1) {
        }

        public boolean predicate0() {
            return true;
        }

        public boolean predicate1(MyTestObject myTestObject) {
            return true;
        }

        public boolean predicate2(MyTestObject myTestObject0, MyTestObject myTestObject1) {
            return true;
        }

        public MyTestObject function0() {
            return this;
        }

        public MyTestObject function1(MyTestObject myTestObject) {
            return this;
        }

        public MyTestObject function2(MyTestObject myTestObject0, MyTestObject myTestObject1) {
            return this;
        }

        public void voidMethod0() {
        }

        public void voidMethod1(MyTestObject myTestObject) {
        }

        public void voidMethod2(MyTestObject myTestObject0, MyTestObject myTestObject1) {
        }

    }

}
