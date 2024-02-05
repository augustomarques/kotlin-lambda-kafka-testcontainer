package br.com.amarques.shared.config

import br.com.amarques.shared.config.Correlation.CORRELATION_ID
import org.slf4j.MDC
import java.util.UUID.randomUUID

object Correlation {
    const val CORRELATION_ID_HEADER = "X-Correlation-Id"
    const val CORRELATION_ID = "correlationId"
}

fun correlationId() = MDC.get(CORRELATION_ID) ?: randomUUID().toString()
