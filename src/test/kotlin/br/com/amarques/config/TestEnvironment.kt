package br.com.amarques.config

import org.testcontainers.containers.KafkaContainer
import org.testcontainers.utility.DockerImageName

object TestEnvironment {

    private const val KAFKA_IMAGE = "confluentinc/cp-kafka:latest"

    private val kafka =
        KafkaContainer(DockerImageName.parse(KAFKA_IMAGE)).apply {
            withEmbeddedZookeeper()
            withEnv("KAFKA_AUTO_CREATE_TOPICS_ENABLE", "true")
        }

    val kafkaBootstrapServers: String
        get() = kafka.bootstrapServers

    fun start() {
        kafka.start()
    }

    fun stop() {
        if (kafka.isRunning) {
            kafka.stop()
        }
    }
}
