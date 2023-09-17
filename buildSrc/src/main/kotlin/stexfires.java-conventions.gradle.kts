plugins {
    `java-library`
    `java-library-distribution`
    `maven-publish`
}

group = "stexfires"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:24.0.1")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(20))
        vendor.set(JvmVendorSpec.ADOPTIUM)
    }
    withJavadocJar()
    withSourcesJar()
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use JUnit Jupiter test framework
            useJUnitJupiter("5.9.3")
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.javaModuleVersion.set(provider { version as String })
    options.compilerArgs.add("--enable-preview")
    // options.compilerArgs.add("-Xlint:preview")
    //options.compilerArgs.add("-Xlint:unchecked")
    //options.compilerArgs.add("-Xlint:deprecation")
}

tasks.withType<Test>().configureEach {
    jvmArgs("--enable-preview")
}

tasks.withType<Javadoc> {
    val javadocOptions = options as CoreJavadocOptions

    javadocOptions.addStringOption("source", "19")
    javadocOptions.addBooleanOption("-enable-preview", true)
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

distributions {
    main {
        distributionBaseName.set(project.name)
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri(layout.buildDirectory.dir("repo"))
        }
    }
}
