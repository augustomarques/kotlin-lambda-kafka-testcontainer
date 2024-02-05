package br.com.amarques.config

import io.kotest.core.listeners.AfterProjectListener
import io.kotest.core.listeners.BeforeProjectListener

object TestExtension : BeforeProjectListener, AfterProjectListener {

    override suspend fun beforeProject() {
        TestEnvironment.start()
    }

    override suspend fun afterProject() {
        TestEnvironment.stop()
    }
}
