package edu.austral.dissis.client_server.client_listeners

import edu.austral.dissis.chess.gui.InitialState
import edu.austral.dissis.client_server.Client
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener


class InitialListener(private val client: Client) : MessageListener<InitialState> {
    override fun handleMessage(message: Message<InitialState>) {
        client.handleInitialState(message)
    }
}