package edu.austral.dissis.common.game_results

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.interfaces.GameResult

class ValidGameResult(private val game: Game):GameResult {
    fun getGame(): Game {
        return this.game
    }
}