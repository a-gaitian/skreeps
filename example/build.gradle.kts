plugins {
    alias(libs.plugins.kotlin.multiplatform)
    id("io.github.a-gaitian.skreeps")
}

dependencies {
    jsMainImplementation(project(":api"))
}
