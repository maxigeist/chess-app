package edu.austral.dissis.client_server.client_listeners


import edu.austral.dissis.chess.gui.GameEventListener
import edu.austral.dissis.chess.gui.Move
import edu.austral.dissis.client_server.Client

class MoveEventListener (private val client: Client) : GameEventListener {
    override fun handleMove(move: Move) {
        client.sendMove(move)
    }

}