plugins {
    id("stexfires-examples.java-conventions")
}

dependencies {
    implementation(project(":stexfires-data"))
    implementation(project(":stexfires-io"))
    implementation(project(":stexfires-record"))
    implementation(project(":stexfires-util"))
    implementation("org.jetbrains:annotations:24.0.1")
}


