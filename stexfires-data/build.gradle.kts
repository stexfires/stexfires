plugins {
    id("stexfires.java-conventions")
}

dependencies {
    api(project(":stexfires-record"))
    api(project(":stexfires-util"))
    implementation("org.jspecify:jspecify:1.0.0")
}

