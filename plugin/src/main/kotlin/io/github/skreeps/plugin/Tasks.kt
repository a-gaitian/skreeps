package io.github.skreeps.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.*
import org.gradle.api.tasks.Optional
import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*

abstract class DeployTask : DefaultTask() {

    @get:InputDirectory
    lateinit var buildFilesDir: File

    @get:Input
    lateinit var branch: String

    @get:Input
    lateinit var host: String

    @get:Input
    lateinit var port: String

    @Internal
    fun getJsFile(): File =
        buildFilesDir
            .listFiles()!!
            .single { it.name.endsWith(".js") }
}

open class DeployLocallyTask : DeployTask() {

    @get:Input
    lateinit var scriptsRoot: String

    @TaskAction
    fun execute() {
        val serverDirName =
            if(host == SkreepsPlugin.OFFICIAL_SERVER_HOST) host
            else (host.replace(".", "_") + "___" + port)

        val serverDir = File(scriptsRoot, serverDirName)
        val branchDir = File(serverDir, branch)
        branchDir.mkdirs()
        getJsFile().copyTo(File(branchDir, "main.js"), true)
    }
}

abstract class AbstractRemoteDeployTask : DeployTask() {

    @get:Input
    lateinit var scheme: String

    fun sendRequest(
        path: String,
        body: Any?,
        customize: (HttpRequest.Builder) -> Unit = {}
    ): Map<String, String> {
        val bodyJson = groovy.json.JsonOutput.toJson(body)
        val requestBuilder = HttpRequest.newBuilder()
            .uri(URI("$scheme://$host:$port/${path.removePrefix("/")}"))
            .setHeader("Content-Type", "application/json; charset=utf-8")
            .POST(HttpRequest.BodyPublishers.ofString(bodyJson))

        customize(requestBuilder)

        val response = HttpClient.newHttpClient()
            .send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString())

        @Suppress("UNCHECKED_CAST")
        val errorMessage = if (response.statusCode() in 200..299) {
            val responseJson = groovy.json.JsonSlurper().parseText(response.body()) as Map<String, String>
            if (responseJson["error"] == null) {
                return responseJson
            } else {
                "Request $path failed"
            }
        } else {
            "Request $path failed - ${response.statusCode()}"
        }

        logger.error(errorMessage)
        logger.error(response.body())
        throw RuntimeException(errorMessage)
    }
}

abstract class ObtainTokenDeployTask : AbstractRemoteDeployTask() {

    @get:Input
    abstract var username: String

    @get:Input
    abstract var password: String

    @get:OutputFile
    abstract val tokenFile: RegularFileProperty

    @TaskAction
    fun execute() {
        val body = mapOf("username" to username, "password" to password)

        val response = sendRequest("/api/auth/signin", body) {
            val encodedCredentials = Base64.getEncoder().encodeToString(
                "$username:$password".toByteArray()
            )
            it.setHeader("Authorization", "Basic $encodedCredentials")
        }
        tokenFile.get().asFile.writeText(response["token"]!!)
        logger.lifecycle("Token for $host saved to build directory")
    }
}

abstract class RemoteDeployTask : AbstractRemoteDeployTask() {

    @get:Optional
    @get:Input
    abstract var token: String?

    @get:Optional
    @get:InputFile
    abstract val tokenFile: RegularFileProperty

    @TaskAction
    fun execute() {
        val modules = mapOf("main" to getJsFile().readText())
        val body = mapOf("branch" to branch, "modules" to modules)
        fun doSend() = sendRequest("/api/user/code", body, ::tokenAuthCustomizer)

        val response = try {
            doSend()
        } catch (e: RuntimeException) {
            sendRequest(
                "/api/user/clone-branch",
                mapOf("branch" to "default", "newName" to branch),
                ::tokenAuthCustomizer
            )
            logger.lifecycle("[default] cloned to [$branch]")
            doSend()
        }

        logger.lifecycle("Code uploaded to $host [$branch]")
        logger.lifecycle(response.toString())
    }

    private fun tokenAuthCustomizer(requestBuilder: HttpRequest.Builder) {
        val actualToken = token ?: tokenFile.get().asFile.readText()
        requestBuilder.setHeader("X-Token", actualToken)
        requestBuilder.setHeader("X-Username", actualToken)
    }
}
