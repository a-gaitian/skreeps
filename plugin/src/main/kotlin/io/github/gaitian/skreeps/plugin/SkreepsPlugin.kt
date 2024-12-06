package io.github.gaitian.skreeps.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.cc.base.logger

class SkreepsPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        logger.lifecycle("SkreepsPlugin applied")
    }
}