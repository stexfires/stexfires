module stexfires.record {
    requires org.jetbrains.annotations;
    requires transitive org.eclipse.store.storage.embedded;
    requires jdk.unsupported;
    requires stexfires.util;
    exports stexfires.store;
}
