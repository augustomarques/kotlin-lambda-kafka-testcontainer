package br.com.amarques

import br.com.amarques.producer.KafkaClientEvent
import br.com.amarques.shared.config.Correlation.CORRELATION_ID
import br.com.amarques.shared.config.correlationId
import br.com.amarques.shared.utils.logger
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import io.micronaut.function.aws.MicronautRequestHandler
import io.micronaut.http.HttpStatus.INTERNAL_SERVER_ERROR
import io.micronaut.http.HttpStatus.OK
import jakarta.inject.Inject
import org.slf4j.MDC
import java.io.IOException
import java.util.UUID.randomUUID

class FunctionRequestHandler : MicronautRequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>() {

    @Inject
    private lateinit var kafkaProducer: KafkaClientEvent

    override fun execute(input: APIGatewayProxyRequestEvent): APIGatewayProxyResponseEvent {
        val response = APIGatewayProxyResponseEvent()
        MDC.put(CORRELATION_ID, randomUUID().toString())

        try {
            kafkaProducer.sendMessage(
                EVENTS_TOPIC,
                correlationId(),
                input.body
            )

            response.statusCode = OK.code
        } catch (e: IOException) {
            logger.error("Error to consumer event ${input.body}")
            response.statusCode = INTERNAL_SERVER_ERROR.code
        }
        return response
    }

    companion object {
        const val EVENTS_TOPIC = "tp_events"
        private val logger = logger()
    }
}
