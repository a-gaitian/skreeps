plugins {
    alias(libs.plugins.kotlin.multiplatform)
    id("io.github.a-gaitian.skreeps")
}

dependencies {
    jsMainImplementation(project(":api"))
}

// ~/.gradle/gradle.properties
val screepsToken: String by project

screeps {
    deploy {
        servers {
            official {
                token = screepsToken
            }
        }
    }
    jsExtensions.add("""
        Object.prototype.toString = function() {
            return JSON.stringify(this)
        };
    """.trimIndent())
}
