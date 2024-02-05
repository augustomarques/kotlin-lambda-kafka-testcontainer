package br.com.amarques.config

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.spec.IsolationMode.InstancePerTest
import io.micronaut.test.extensions.kotest5.MicronautKotest5Extension

@Suppress("unused")
object ProjectConfig : AbstractProjectConfig() {

    override val isolationMode = InstancePerTest

    override fun extensions() =
        listOf(
            MicronautKotest5Extension,
            TestExtension
        )
}
