package edu.austral.dissis.common.game_results

import edu.austral.dissis.common.interfaces.GameResult

class InvalidGameResult(private val message: String): GameResult {

    fun getMessage(): String {
        return this.message
    }

}