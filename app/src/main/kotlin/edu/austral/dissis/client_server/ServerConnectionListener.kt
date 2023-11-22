package edu.austral.dissis.client_server

import edu.austral.ingsis.clientserver.Message
import edu.austral.dissis.client_server.Server
import edu.austral.ingsis.clientserver.ServerConnectionListener

class ServerConnectionListener(
    private val server: Server
) : ServerConnectionListener {


    override fun handleClientConnection(clientId: String) {
        server.sendMessageInitialConnection(clientId)
    }

    override fun handleClientConnectionClosed(clientId: String) {
        server.getServer().sendMessage(clientId, Message("disconnection", "disconnection"))
    }

}