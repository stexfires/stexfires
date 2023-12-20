plugins {
    id("stexfires.java-conventions")
}

dependencies {
    api(project(":stexfires-data"))
    api(project(":stexfires-record"))
    api(project(":stexfires-util"))
    implementation("org.jetbrains:annotations:24.0.1")
}
