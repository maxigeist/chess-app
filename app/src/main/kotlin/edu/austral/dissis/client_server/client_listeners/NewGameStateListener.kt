package edu.austral.dissis.client_server.client_listeners

import edu.austral.dissis.chess.gui.NewGameState
import edu.austral.dissis.client_server.Client
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener


class NewGameStateListener(val client: Client) : MessageListener<NewGameState> {
    override fun handleMessage(message: Message<NewGameState>) {
        client.handleNewGameState(message)
    }
}