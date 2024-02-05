package br.com.amarques

import br.com.amarques.FunctionRequestHandler.Companion.EVENTS_TOPIC
import br.com.amarques.config.IntegrationTestCase
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus.OK
import java.time.Duration.ofSeconds

class FunctionRequestHandlerTest : IntegrationTestCase() {

    init {
        Given("A new event received") {
            val eventMessage = "{\"event\":{\"type\":\"example_type\"}}"

            val handler = FunctionRequestHandler()

            val request = APIGatewayProxyRequestEvent()
            request.httpMethod = "POST"
            request.path = "/"
            request.body = eventMessage

            val response = handler.execute(request)

            When("the request is processed") {
                Then("the event should be published to kafka") {

                    response.statusCode.toInt() shouldBe OK.code

                    kafkaConsumer.subscribe(listOf(EVENTS_TOPIC))
                    val records = kafkaConsumer.poll(ofSeconds(10))
                    val messages = records.iterator().asSequence().toList().map { it.value() }

                    messages shouldContain jacksonObjectMapper().writeValueAsString(eventMessage)

                    kafkaConsumer.close()
                    handler.close()
                }
            }
        }
    }
}
