import java.util.*

plugins {
    `java-gradle-plugin`
    alias(libs.plugins.kotlin.jvm)
}

loadProperties("../gradle.properties")

dependencies {
    implementation(libs.kotlin.plugin.multiplatform)
}

gradlePlugin {
    plugins {
        create("skreeps") {
            id = "$group.skreeps"
            implementationClass = "io.github.skreeps.plugin.SkreepsPlugin"
        }
    }
}

fun loadProperties(path: String) {
    Properties().apply {
        file(path)
            .inputStream().use { load(it) }
    }.mapKeys { it.key as String }
        .filter { (key, _) -> project.hasProperty(key) }
        .forEach { (key, value) -> project.setProperty(key, value) }
}
