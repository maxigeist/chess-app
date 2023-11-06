package edu.austral.dissis.common.game_results


import edu.austral.dissis.common.interfaces.GameResult

class EndGameResult : GameResult {

    fun getMessage(): String {
        return "Game has ended"
    }
}