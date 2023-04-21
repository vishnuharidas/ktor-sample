package com.iamvishnu.ktor.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    routing {
        webSocket("/ws") {


            while (true) {
                try {
                    outgoing.send(Frame.Text("Current time: ${formatter.format(LocalDateTime.now())}"))

                    delay(5000)

                } catch (e: ClosedReceiveChannelException) {
                    // the client has disconnected
                    break
                } finally {

                }
            }

            println("Disconnected!............................................")

        }
    }
}
