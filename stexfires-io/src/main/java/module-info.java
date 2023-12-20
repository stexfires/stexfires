module stexfires.io {
    requires org.jetbrains.annotations;
    requires transitive stexfires.data;
    requires transitive stexfires.record;
    requires transitive stexfires.util;
    exports stexfires.io;
    exports stexfires.io.combined;
    exports stexfires.io.config;
    exports stexfires.io.consumer;
    exports stexfires.io.container;
    exports stexfires.io.delimited.simple;
    exports stexfires.io.fixedwidth;
    exports stexfires.io.html.table;
    exports stexfires.io.markdown.list;
    exports stexfires.io.markdown.table;
    exports stexfires.io.message;
    exports stexfires.io.path;
    exports stexfires.io.producer;
    exports stexfires.io.properties;
    exports stexfires.io.singlevalue;
}
