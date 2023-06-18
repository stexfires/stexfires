plugins {
    id("stexfires-app.java-conventions")
}

dependencies {
    implementation(project(":stexfires-io"))
    implementation(project(":stexfires-record"))
    implementation(project(":stexfires-util"))
    implementation("org.jetbrains:annotations:24.0.1")
}

application {
    mainModule.set("stexfires.app.character")
    mainClass.set("stexfires.app.character.CharacterInformationFiles")
    applicationDefaultJvmArgs = listOf("--enable-preview")
}