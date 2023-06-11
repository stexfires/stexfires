plugins {
    id("stexfires.java-conventions")
    `java-library`
}

dependencies {
    implementation(project(":stexfires-util"))
    implementation("org.jetbrains:annotations:24.0.1")
}
