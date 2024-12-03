package org.example

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import java.util.*

private val logger = KotlinLogging.logger {}

class WebSocketClient(val host: String, val port: Int) {

    private val client = HttpClient(CIO) {
        install(Logging)
        install(WebSockets) {
            pingIntervalMillis = 20_000
        }
    }

    fun getUpdate() {
        runBlocking {
            client.webSocket(method = HttpMethod.Get, host = host, port = port) {
                while(true) {
                    val othersMessage = incoming.receive() as? Frame.Text
                    logger.info { othersMessage?.readText() }
                }
            }
        }
    }
}