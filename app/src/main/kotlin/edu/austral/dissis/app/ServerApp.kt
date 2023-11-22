package edu.austral.dissis.app

import edu.austral.dissis.client_server.Server
import edu.austral.dissis.common.Board
import edu.austral.dissis.common.Game
import edu.austral.dissis.utils.*


fun main(){
    val game = classicChess()
    Server(game)
}