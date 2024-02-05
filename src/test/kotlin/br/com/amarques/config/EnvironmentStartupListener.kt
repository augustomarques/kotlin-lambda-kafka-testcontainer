package br.com.amarques.config

import io.micronaut.context.env.ActiveEnvironment
import io.micronaut.context.env.PropertySource
import io.micronaut.context.env.PropertySourceLoader
import io.micronaut.core.io.ResourceLoader
import java.io.InputStream
import java.util.Optional

class EnvironmentStartupListener : PropertySourceLoader {

    override fun load(
        resourceName: String?,
        resourceLoader: ResourceLoader?
    ): Optional<PropertySource> {
        return if (resourceName == "application") {
            Optional.of(
                PropertySource.of(
                    mapOf(
                        KAFKA_BOOTSTRAP_SERVERS_CONFIG_PATH to TestEnvironment.kafkaBootstrapServers
                    )
                )
            )
        } else {
            Optional.empty<PropertySource>()
        }
    }

    override fun read(
        name: String?,
        input: InputStream?
    ): MutableMap<String, Any> = mutableMapOf()

    override fun loadEnv(
        resourceName: String?,
        resourceLoader: ResourceLoader?,
        activeEnvironment: ActiveEnvironment?
    ): Optional<PropertySource> = Optional.empty()

    companion object {
        const val KAFKA_BOOTSTRAP_SERVERS_CONFIG_PATH = "kafka.bootstrap.servers"
    }
}
