package io.github.skreeps.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.cc.base.logger
import org.gradle.internal.extensions.stdlib.capitalized
import org.gradle.process.ExecOperations
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

class SkreepsPlugin @Inject constructor(
    private val execOperations: ExecOperations
) : Plugin<Project> {

    companion object {
        const val KMP_PLUGIN_ID = "org.jetbrains.kotlin.multiplatform"
        const val BUILD_TASK_NAME = "jsBrowserDistribution"
        const val OFFICIAL_SERVER_NAME = "official"
        const val OFFICIAL_SERVER_HOST = "screeps.com"
        const val PTR_SERVER_NAME = "ptr"
        const val PTR_SERVER_HOST = "screeps.com/ptr"
    }

    override fun apply(project: Project) {
        if (project.pluginManager.hasPlugin(KMP_PLUGIN_ID)) {
            project.pluginManager.withPlugin(KMP_PLUGIN_ID) {
                applyWithKmpPlugin(project)
            }
        } else {
            logger.warn("Skreeps plugin will work only with kotlin multiplatform plugin")
        }
    }

    private fun applyWithKmpPlugin(project: Project) {

        configureKmpPlugin(project)

        val extension =
            project.extensions.create("screeps", ScreepsExtension::class.java)

        setupConventions(extension)

        project.afterEvaluate {

            val servers = extension.deploy.servers
            val serversMap = servers.asMap

            if (servers.size == 2 && !servers.any { server ->
                    server.token.isPresent || server.username.isPresent
            }) {
                logger.warn("No servers to deploy")
                return@afterEvaluate
            }

            val isLocal = extension.deploy.scriptsRoot.let {
                it.isPresent && File(it.get()).let { root ->
                    root.exists()
                            && root.isDirectory
                            && root.listFiles()
                                ?.any { file -> file.name == OFFICIAL_SERVER_HOST } == true
                }
            }

            val buildTask = project.tasks.getByName(BUILD_TASK_NAME)

            val prepareJsTask = project.tasks.create("prepareJs", PrepareJsTask::class.java) {
                it.group = "screeps"
                it.dependsOn(buildTask)
                it.buildFilesDir = buildTask.outputs.files.singleFile
                it.jsExtensions.set(extension.jsExtensions)
                it.jsFile.set(
                    project.layout.buildDirectory.dir("screeps").get().file("main.js")
                )
            }

            fun DeployTask.commonConfig(server: ServerDsl) {
                group = "screeps"
                dependsOn(prepareJsTask)

                preparedJsFile = prepareJsTask.jsFile

                branch = extension.deploy.branch.get()
                host = server.host.get()
                port = server.port.orNull
            }

            fun AbstractRemoteDeployTask.commonRemoteConfig(server: ServerDsl) {
                scheme = if(server.tls.get()) "https" else "http"
            }

            serversMap[PTR_SERVER_NAME]!!.token.set(
                serversMap[OFFICIAL_SERVER_NAME]!!.token
            )

            servers.forEach { server ->
                val toServerName = if(server.name == OFFICIAL_SERVER_NAME) ""
                    else "To${server.name.capitalized()}"

                // Cannot deploy to PTR server locally
                if (isLocal && server.name != PTR_SERVER_NAME) {
                    val taskName = "deploy${toServerName}Locally"
                    project.tasks.create(taskName, DeployLocallyTask::class.java) {
                        it.commonConfig(server)
                        it.scriptsRoot = extension.deploy.scriptsRoot.get()
                            ?: throw IllegalStateException()
                    }
                }
                var obtainTokenTask: ObtainTokenDeployTask? = null
                if (!server.token.isPresent) {
                    obtainTokenTask = project.tasks.create(
                        "obtainToken$toServerName", ObtainTokenDeployTask::class.java
                    ) {
                        it.commonConfig(server)
                        it.commonRemoteConfig(server)
                        it.username = server.username.get()
                        it.password = server.password.get()
                        it.tokenFile.set(
                            project.layout.buildDirectory
                            .file("${server.name}.token")
                        )
                    }
                }
                project.tasks.create("deploy$toServerName", RemoteDeployTask::class.java) {
                    it.commonConfig(server)
                    it.commonRemoteConfig(server)
                    if (server.token.isPresent) {
                        it.token = server.token.orNull
                    } else {
                        it.dependsOn(obtainTokenTask)
                        it.tokenFile.set(obtainTokenTask!!.tokenFile)
                    }
                }
            }
        }
    }

    private fun configureKmpPlugin(project: Project) {
        project.extensions.configure(KotlinMultiplatformExtension::class.java) { kmp ->
            kmp.js { js ->
                js.binaries.executable()
                js.browser { }
            }
        }
    }

    private fun setupConventions(extension: ScreepsExtension) {
        extension.deploy.branch.convention(gitBranch())
        extension.deploy.servers.create(OFFICIAL_SERVER_NAME) {
            it.host.set(OFFICIAL_SERVER_HOST)
        }
        extension.deploy.servers.create(PTR_SERVER_NAME) {
            it.host.set(PTR_SERVER_HOST)
        }
        extension.jsExtensions.add("if (!Game.rooms['sim']) globalThis = this;")
    }

    /**
     * Utility function to retrieve the name of the current git branch.
     * Will not work if build tool detaches head after checkout, which some do!
     */
    private fun gitBranch(): String {
        return try {
            val byteOut = ByteArrayOutputStream()
            execOperations.exec {
                it.commandLine = "git rev-parse --abbrev-ref HEAD".split(" ")
                it.standardOutput = byteOut
            }
            String(byteOut.toByteArray()).trim().also {
                if (it == "HEAD")
                    logger.warn("Unable to determine current branch: Project is checked out with detached head!")
            }
        } catch (e: Exception) {
            logger.warn("Unable to determine current branch: ${e.message}")
            "Unknown Branch"
        }
    }
}