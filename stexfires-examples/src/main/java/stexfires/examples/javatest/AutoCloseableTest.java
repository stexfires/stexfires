package stexfires.examples.javatest;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public final class AutoCloseableTest {

    private AutoCloseableTest() {
    }

    private static void testTryWithResource(ThrowRuntimeExceptionType throwRuntimeExceptionType) {
        System.out.println("-testTryWithResource---");
        try {
            try (TestCloseable t = new TestCloseable(throwRuntimeExceptionType)) {
                t.method();
            } catch (RuntimeException e) {
                System.out.println("inner catch: " + e.getMessage() + (e.getSuppressed().length >= 1 ? " AND " + e.getSuppressed()[0].getMessage() : ""));
            } finally {
                System.out.println("inner finally");
            }
        } catch (RuntimeException e) {
            System.out.println("outer catch: " + e.getMessage() + (e.getSuppressed().length >= 1 ? " AND " + e.getSuppressed()[0].getMessage() : ""));
        }
        System.out.println();
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    private static void testTryWithResourceConstructorBefore(ThrowRuntimeExceptionType throwRuntimeExceptionType) {
        System.out.println("-testTryWithResourceConstructorBefore---");
        try {
            // constructor before try-with
            TestCloseable testCloseable = new TestCloseable(throwRuntimeExceptionType);
            try (TestCloseable t = testCloseable) {
                t.method();
            } catch (RuntimeException e) {
                System.out.println("inner catch: " + e.getMessage() + (e.getSuppressed().length >= 1 ? " AND " + e.getSuppressed()[0].getMessage() : ""));
            } finally {
                System.out.println("inner finally");
            }
        } catch (RuntimeException e) {
            System.out.println("outer catch: " + e.getMessage() + (e.getSuppressed().length >= 1 ? " AND " + e.getSuppressed()[0].getMessage() : ""));
        }
        System.out.println();
    }

    private static void testWithoutTryWithResource(ThrowRuntimeExceptionType throwRuntimeExceptionType) {
        System.out.println("-testWithoutTryWithResource---");
        try {
            TestCloseable t = new TestCloseable(throwRuntimeExceptionType);
            try {
                t.method();
            } catch (RuntimeException e) {
                System.out.println("inner catch: " + e.getMessage() + (e.getSuppressed().length >= 1 ? " AND " + e.getSuppressed()[0].getMessage() : ""));
            } finally {
                System.out.println("inner finally");
            }
            t.close();
        } catch (RuntimeException e) {
            System.out.println("outer catch: " + e.getMessage() + (e.getSuppressed().length >= 1 ? " AND " + e.getSuppressed()[0].getMessage() : ""));
        }
        System.out.println();
    }

    public static void main(String... args) {
        testTryWithResource(ThrowRuntimeExceptionType.NONE);
        testTryWithResource(ThrowRuntimeExceptionType.CONSTRUCTOR);
        testTryWithResource(ThrowRuntimeExceptionType.METHOD);
        testTryWithResource(ThrowRuntimeExceptionType.CLOSE);
        testTryWithResource(ThrowRuntimeExceptionType.METHOD_AND_CLOSE);

        testTryWithResourceConstructorBefore(ThrowRuntimeExceptionType.CONSTRUCTOR);
        testTryWithResourceConstructorBefore(ThrowRuntimeExceptionType.METHOD);

        testWithoutTryWithResource(ThrowRuntimeExceptionType.CONSTRUCTOR);
        testWithoutTryWithResource(ThrowRuntimeExceptionType.METHOD);
        testWithoutTryWithResource(ThrowRuntimeExceptionType.CLOSE);
        testWithoutTryWithResource(ThrowRuntimeExceptionType.METHOD_AND_CLOSE);
    }

    private enum ThrowRuntimeExceptionType {
        NONE, CONSTRUCTOR, METHOD, CLOSE, METHOD_AND_CLOSE
    }

    @SuppressWarnings("ClassCanBeRecord")
    private static final class TestCloseable implements AutoCloseable {

        private final ThrowRuntimeExceptionType throwRuntimeExceptionType;

        private TestCloseable(ThrowRuntimeExceptionType throwRuntimeExceptionType) {
            this.throwRuntimeExceptionType = throwRuntimeExceptionType;

            System.out.println("constructor " + throwRuntimeExceptionType);
            if (throwRuntimeExceptionType == ThrowRuntimeExceptionType.CONSTRUCTOR) {
                throw new RuntimeException("Ex: 'constructor'");
            }
        }

        private void method() {
            System.out.println("method " + throwRuntimeExceptionType);
            if ((throwRuntimeExceptionType == ThrowRuntimeExceptionType.METHOD) || (throwRuntimeExceptionType == ThrowRuntimeExceptionType.METHOD_AND_CLOSE)) {
                throw new RuntimeException("Ex: 'method'");
            }
        }

        @Override
        public void close() {
            System.out.println("close " + throwRuntimeExceptionType);
            if ((throwRuntimeExceptionType == ThrowRuntimeExceptionType.CLOSE) || (throwRuntimeExceptionType == ThrowRuntimeExceptionType.METHOD_AND_CLOSE)) {
                throw new RuntimeException("Ex: 'close'");
            }
        }

    }

}
