package edu.austral.dissis.client_server

import com.fasterxml.jackson.core.type.TypeReference
import edu.austral.ingsis.clientserver.ClientBuilder
import edu.austral.ingsis.clientserver.netty.client.NettyClientBuilder
import edu.austral.ingsis.clientserver.Client
import edu.austral.dissis.chess.gui.*
import edu.austral.dissis.client_server.client_listeners.*
import edu.austral.dissis.common.game_results.InvalidGameResult
import edu.austral.ingsis.clientserver.Message
import java.net.InetSocketAddress

class Client(
    private val builder: ClientBuilder = NettyClientBuilder.createDefault()
) {

    private lateinit var view: GameView
    private var client: Client

    init {
        client = buildClient()
    }

    fun start(newView: GameView) {
        view = newView
        client.connect()
        client.send(Message("initial-state", Unit))
        view.addListener(MoveEventListener(this))
    }

    fun sendMove(move: Move) {
        client.send(Message("move", move))
    }

    fun handleInitialState(message: Message<InitialState>) {
        view.handleInitialState(message.payload)
    }

    fun handleNewGameState(message: Message<NewGameState>) {
        view.handleMoveResult(message.payload)
    }

    fun handleInvalidMove(message: Message<InvalidMove>) {
        view.handleMoveResult(message.payload)
    }

    fun disconnect() {
        client.closeConnection()
    }

    fun handleEndGameResult(message: Message<GameOver>) {
        view.handleMoveResult(message.payload)
        this.disconnect()
    }

    private fun buildClient(): Client {
        //Falta agregarle los message listeners y el address
        return builder.withAddress(InetSocketAddress(8080))
            .addMessageListener(
            "initial-connection",
            object : TypeReference<Message<InitialState>>() {},
            InitialListener(this)
        )
            .addMessageListener(
                "new-game-state",
                object : TypeReference<Message<NewGameState>>() {},
                NewGameStateListener(this)
            )
            .addMessageListener(
                "invalid-move",
                object : TypeReference<Message<InvalidMove>>() {},
                InvalidMoveListener(this)
            )
            .addMessageListener(
                "end-game",
                object : TypeReference<Message<GameOver>>() {},
                EndGameResultListener(this)
            )
            .build()

    }


}