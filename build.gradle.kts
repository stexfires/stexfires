plugins {
    `java-library`
    `project-report`
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:23.0.0")
    testImplementation("junit:junit:4.12")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(19))
    }
}

group = "stexfires"
version = "0.1.0"

val sharedManifest = the<JavaPluginConvention>().manifest {
    attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version
    )
}

tasks {
    jar {
        manifest = project.the<JavaPluginConvention>().manifest {
            from(sharedManifest)
        }
    }
    javadoc {
        options.encoding = "UTF-8"
    }
    compileJava {
        options.encoding = "UTF-8"
    }
    compileTestJava {
        options.encoding = "UTF-8"
    }
}

tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    dependsOn("compileJava")
    from(sourceSets.main.get().allJava)
    manifest = project.the<JavaPluginConvention>().manifest {
        from(sharedManifest)
    }
}

tasks.register<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    dependsOn("javadoc")
    from(tasks.javadoc.get().destinationDir)
    manifest = project.the<JavaPluginConvention>().manifest {
        from(sharedManifest)
    }
}

tasks.register<Zip>("distributionZip") {
    dependsOn("javadocJar", "sourcesJar", "build")
    from("LICENSE")
    from("LICENSE") {
        rename("LICENSE", "LICENSE.txt")
    }
    from("README.md")
    from("README.md") {
        rename("README.md", "README.txt")
    }
    from(sourceSets.main.get().allJava) {
        into("src")
    }
    from(tasks.jar)
    from(tasks.named<Jar>("sourcesJar"))
    from(tasks.named<Jar>("javadocJar"))
}
