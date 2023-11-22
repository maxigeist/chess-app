package edu.austral.dissis.client_server.client_listeners

import edu.austral.dissis.chess.gui.GameEventListener
import edu.austral.dissis.chess.gui.InvalidMove
import edu.austral.dissis.chess.gui.Move
import edu.austral.dissis.client_server.Client
import edu.austral.dissis.common.game_results.InvalidGameResult
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener


class InvalidMoveListener(private val client: Client): MessageListener<InvalidMove> {
    override fun handleMessage(message: Message<InvalidMove>) {
        client.handleInvalidMove(message)
    }
}