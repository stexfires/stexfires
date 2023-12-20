module stexfires.record {
    requires org.jetbrains.annotations;
    requires transitive stexfires.util;
    exports stexfires.record;
    exports stexfires.record.comparator;
    exports stexfires.record.consumer;
    exports stexfires.record.filter;
    exports stexfires.record.generator;
    exports stexfires.record.impl;
    exports stexfires.record.logger;
    exports stexfires.record.mapper;
    exports stexfires.record.mapper.field;
    exports stexfires.record.mapper.impl;
    exports stexfires.record.message;
    exports stexfires.record.modifier;
    exports stexfires.record.producer;
}
