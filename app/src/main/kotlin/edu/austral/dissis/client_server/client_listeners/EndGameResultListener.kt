package edu.austral.dissis.client_server.client_listeners

import edu.austral.dissis.chess.gui.GameOver

import edu.austral.dissis.client_server.Client
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener

class EndGameResultListener(private val client: Client): MessageListener<GameOver> {
    override fun handleMessage(message: Message<GameOver>) {
        client.handleEndGameResult(message)
    }
}