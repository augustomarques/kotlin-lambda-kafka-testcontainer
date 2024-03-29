package br.com.amarques.shared.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger

inline fun <reified T> T.logger(): Logger = getLogger(T::class.java)
