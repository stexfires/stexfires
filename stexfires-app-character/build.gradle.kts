plugins {
    id("stexfires-app.java-conventions")
}

dependencies {
    implementation(project(":stexfires-io"))
    implementation(project(":stexfires-record"))
    implementation(project(":stexfires-util"))
    implementation("org.jspecify:jspecify:0.3.0")
}

application {
    mainModule.set("stexfires.app.character")
    mainClass.set("stexfires.app.character.CharacterInformationFiles")
    applicationDefaultJvmArgs = listOf("--enable-preview")
}