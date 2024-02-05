package br.com.amarques.producer

import br.com.amarques.shared.config.Correlation.CORRELATION_ID_HEADER
import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.messaging.annotation.MessageBody
import io.micronaut.messaging.annotation.MessageHeader

@KafkaClient
interface KafkaClientEvent {

    fun sendMessage(
        @Topic topic: String,
        @MessageHeader(CORRELATION_ID_HEADER) correlationId: String,
        @MessageBody message: Any
    )
}
