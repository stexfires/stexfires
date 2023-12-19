package stexfires.store;

import org.eclipse.store.storage.embedded.types.EmbeddedStorage;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;

import java.io.File;
import java.nio.file.Path;
import java.time.ZonedDateTime;

public class TestEclipseStore {

    public TestEclipseStore() {
    }

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Missing valid output directory parameter!");
        }
        File outputDirectory = new File(args[0]);
        if (!outputDirectory.exists() || !outputDirectory.isDirectory()) {
            throw new IllegalArgumentException("Missing valid output directory parameter! " + outputDirectory);
        }

        // Application-specific root instance
        final DataRoot root = new DataRoot();

        // Initialize a storage manager ("the database") with the given directory and defaults for everything else.
        final EmbeddedStorageManager storageManager = EmbeddedStorage.start(root, Path.of(args[0]));

        // print the root to show its loaded content (stored in the last execution).
        System.out.println(root);

        // Set content data to the root element, including the time to visualize changes on the next execution.
        root.setContent("Hello World! @ " + ZonedDateTime.now());
        root.setDataRecord(new DataRecord(42));

        // Store the modified root and its content.
        storageManager.storeRoot();

        // Shutdown is optional as the storage concept is inherently crash-safe
        storageManager.shutdown();
        // System.exit(0);
    }
}
