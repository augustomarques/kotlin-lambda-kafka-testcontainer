package br.com.amarques.config

import io.kotest.core.spec.IsolationMode.SingleInstance
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.micronaut.function.aws.test.annotation.MicronautLambdaTest
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.util.*

@MicronautLambdaTest
abstract class IntegrationTestCase : BehaviorSpec() {

    override fun isolationMode() = SingleInstance

    override suspend fun afterEach(
        testCase: TestCase,
        result: TestResult
    ) {
        super.afterEach(testCase, result)
    }

    private val kafkaConsumerProps = Properties().apply {
        put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, TestEnvironment.kafkaBootstrapServers)
        put(ConsumerConfig.GROUP_ID_CONFIG, "cg_events_test")
        put(ConsumerConfig.CLIENT_ID_CONFIG, "cl_events_test")
        put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
        put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
        put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    }

    val kafkaConsumer = KafkaConsumer<String?, Any>(kafkaConsumerProps)
}
