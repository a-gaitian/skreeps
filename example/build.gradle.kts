plugins {
    alias(libs.plugins.kotlin.multiplatform)
    id("io.github.a-gaitian.skreeps")
}

dependencies {
    jsMainImplementation(project(":api"))
}

// ~/.gradle/gradle.properties
val screepsToken: String by project
val newbieLandUsername: String by project
val newbieLandPassword: String by project

screeps {
    deploy {
        servers {
            official {
                token = screepsToken
            }
            create("NewbieLand") {
                host = "screeps.newbieland.net"
                username = newbieLandUsername
                password = newbieLandPassword
            }
        }
    }
    jsExtensions.add("""
        Object.prototype.toString = function() {
            return JSON.stringify(this)
        };
    """.trimIndent())
}
