package edu.austral.dissis.client_server
import com.fasterxml.jackson.core.type.TypeReference
import edu.austral.dissis.chess.gui.*
import edu.austral.dissis.client_server.listeners.GameMoveListener
import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Position
import edu.austral.dissis.common.game_results.EndGameResult
import edu.austral.dissis.common.game_results.InvalidGameResult
import edu.austral.dissis.common.game_results.ValidGameResult
import edu.austral.dissis.utils.convertUiPieces
import edu.austral.dissis.utils.getColor
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.Server
import edu.austral.ingsis.clientserver.ServerBuilder
import edu.austral.ingsis.clientserver.netty.server.NettyServerBuilder

class Server(
    private var game: Game,
    private val builder: ServerBuilder = NettyServerBuilder.createDefault()
) {
    private val server: Server


    init {
        server = buildServer()
        server.start()
    }

    private fun buildServer():Server{
        return builder
            .withConnectionListener(ServerConnectionListener(this))
            .addMessageListener("move",
            object : TypeReference<Message<Move>>() {},
            GameMoveListener(this))
            .withPort(8080)
            .build()
    }

    fun getServer():Server{
        return server
    }
    fun getGame():Game{
        return game
    }

    fun stop(){
        server.stop()
    }

    fun sendMessageInitialConnection(clientId: String){
        val boardSize = BoardSize(game.getBoard().getYDimension(), game.getBoard().getXDimension())
        val currentPlayer = getColor(game.getCurrentTeam())
        val pieces = convertUiPieces(game.getBoard().getBoardMap())
        this.getServer().sendMessage(clientId, Message("initial-connection", InitialState(boardSize, pieces, currentPlayer)))
    }

    fun handleMovement(move: Move){
        when (val result = game.move(Position(move.from.column, move.from.row), Position(move.to.column, move.to.row))){
            is ValidGameResult -> {
                server.broadcast(Message("new-game-state", NewGameState(convertUiPieces(result.getGame().getBoard().getBoardMap()), getColor(result.getGame().getCurrentTeam()))))
                game = result.getGame()
            }
            is InvalidGameResult -> {
                server.broadcast(Message("invalid-move", InvalidMove(result.getMessage())))
            }
            is EndGameResult -> {
                print("hello")
                server.broadcast(Message("end-game", GameOver(getColor(game.getCurrentTeam()))))
                server.stop()
            }
        }

    }


}