plugins {
    id("stexfires.java-conventions")
}

dependencies {
    api(project(":stexfires-data"))
    api(project(":stexfires-record"))
    implementation(project(":stexfires-util"))
}
