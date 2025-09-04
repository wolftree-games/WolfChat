import java.util.Properties

plugins {
    id("java")
    id("maven-publish")
}

fun loadVersion(): String {
    val propsFile = file("version.properties")
    val props = Properties()

    // Datei automatisch anlegen, falls sie nicht existiert
    if (propsFile.exists()) {
        props.load(propsFile.inputStream())
    } else {
        props["major"] = "1"
        props["minor"] = "0"
        props["patch"] = "0"
    }

    val major = props["major"].toString().toInt()
    val minor = props["minor"].toString().toInt()
    var patch = props["patch"].toString().toInt()

    // Patch erhöhen
    patch++

    // Datei zurückschreiben
    props["patch"] = patch.toString()
    props.store(propsFile.outputStream(), "Version file for de.wolftree.WolfChat")

    return "$major.$minor.$patch"
}

group = "de.wolftree"
version = loadVersion()

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("org.tinylog:slf4j-tinylog:2.7.0")
    implementation("org.tinylog:tinylog-impl:2.7.0")

    //Apache Commons
    implementation("commons-io:commons-io:2.20.0")

    //jackson
    implementation("com.fasterxml.jackson.core:jackson-databind:2.20.0")

    //Annotations
    compileOnly("org.jetbrains:annotations:24.0.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(listOf("-parameters", "-Xlint:unchecked", "-Xlint:deprecation"))
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = project.group.toString()
            artifactId = "wolfchat"
            version = project.version.toString()
        }
    }
}