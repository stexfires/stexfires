@org.jspecify.annotations.NullMarked
module stexfires.io.json {
    requires org.jspecify;
    requires transitive stexfires.data;
    requires transitive stexfires.io;
    requires transitive stexfires.record;
    requires transitive stexfires.util;
    requires com.google.gson;
    exports stexfires.io.json;
}
