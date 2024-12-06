
rootProject.name = "skreeps"

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
    }

    includeBuild("plugin")
}

include("api", "example")
