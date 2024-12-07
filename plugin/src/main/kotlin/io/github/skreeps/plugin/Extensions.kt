package io.github.skreeps.plugin

import org.gradle.api.Action
import org.gradle.api.Named
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import java.io.File
import javax.inject.Inject

open class ScreepsExtension @Inject constructor(
    objects: ObjectFactory
) {
    val deploy: DeployDsl = objects.newInstance(DeployDsl::class.java)
    fun deploy(action: Action<DeployDsl>) = action.execute(deploy)
}

open class DeployDsl @Inject constructor(
    objects: ObjectFactory
) {

    val branch: Property<String> = objects.property(String::class.java)
    val scriptsRoot: Property<String?> = objects.property(String::class.java)
        .convention(
            File(System.getProperty("user.home"), ".config/Screeps/scripts").absolutePath
        )

    val servers: NamedDomainObjectContainer<ServerDsl> = objects.domainObjectContainer(ServerDsl::class.java)
    fun servers(action: Action<NamedDomainObjectContainer<ServerDsl>>) {
        action.execute(servers)
    }

    fun NamedDomainObjectContainer<ServerDsl>.official(action: Action<ServerDsl>) {
        action.execute(servers.named("official").get())
    }
}

open class ServerDsl @Inject constructor(
    private val name: String,
    objects: ObjectFactory
) : Named {

    override fun getName() = name

    val host: Property<String> = objects.property(String::class.java)

    val port: Property<Int> = objects.property(Int::class.java)
        .convention(443)

    val token: Property<String> = objects.property(String::class.java)

    val tls: Property<Boolean> = objects.property(Boolean::class.java)
        .convention(true)

    val username: Property<String> = objects.property(String::class.java)

    val password: Property<String> = objects.property(String::class.java)
}
