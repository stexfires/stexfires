plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

group = "stexfires"
version = "0.1.0"

repositories {
    mavenCentral()
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
        vendor.set(JvmVendorSpec.ADOPTIUM)
    }
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use JUnit Jupiter test framework
            useJUnitJupiter("5.11.4")
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    // options.compilerArgs.add("--enable-preview")
    // options.compilerArgs.add("-Xlint:preview")
    // options.compilerArgs.add("-Xlint:unchecked")
    // options.compilerArgs.add("-Xlint:deprecation")
}

tasks.withType<Test>().configureEach {
    // jvmArgs("--enable-preview")
}

tasks.withType<JavaExec>().configureEach {
    // jvmArgs("--enable-preview")
}

tasks.withType<Javadoc> {
    val javadocOptions = options as CoreJavadocOptions

    javadocOptions.addStringOption("source", "21")
    // javadocOptions.addBooleanOption("-enable-preview", true)
}

tasks.withType<Jar> {
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version
            )
        )
    }
}
