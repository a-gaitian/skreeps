package io.github.skreeps.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.cc.base.logger
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalDistributionDsl

class SkreepsPlugin : Plugin<Project> {

    companion object {
        const val KMP_PLUGIN_ID = "org.jetbrains.kotlin.multiplatform"
    }

    override fun apply(project: Project) {
        logger.lifecycle("SkreepsPlugin applied")
        if (project.pluginManager.hasPlugin(KMP_PLUGIN_ID)) {
            project.pluginManager.withPlugin(KMP_PLUGIN_ID) {
                applyWithKmpPlugin(project)
            }
        } else {
            logger.warn("skreeps plugin will work only with kotlin multiplatform plugin")
        }
    }

    @OptIn(ExperimentalDistributionDsl::class)
    private fun applyWithKmpPlugin(project: Project) {
        project.extensions.configure(KotlinMultiplatformExtension::class.java) { kmp ->
            kmp.js { js ->
                js.binaries.executable()
                js.browser { }
            }
        }
    }
}