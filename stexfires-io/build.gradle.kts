plugins {
    id("stexfires.java-conventions")
}

dependencies {
    api(project(":stexfires-data"))
    api(project(":stexfires-record"))
    api(project(":stexfires-util"))
    implementation("org.jspecify:jspecify:0.3.0")
}
