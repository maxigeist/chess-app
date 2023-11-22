package edu.austral.dissis.app


import edu.austral.dissis.chess.gui.CachedImageResolver
import edu.austral.dissis.chess.gui.DefaultImageResolver
import edu.austral.dissis.chess.gui.GameView
import edu.austral.dissis.client_server.Client
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

fun main(){
    Application.launch(GameUI::class.java)
}

class GameUI(): Application() {
    private val imageResolver = CachedImageResolver(DefaultImageResolver())

    companion object {
        const val title = "Chess"
        val gameClient = Client()
    }

    override fun start(primaryStage: Stage) {
        primaryStage.title = title

        val root = GameView(imageResolver)

        primaryStage.scene = Scene(root)

        gameClient.start(root)

        primaryStage.show()
    }
}