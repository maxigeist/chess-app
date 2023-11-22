package edu.austral.dissis.common.end_game_validators

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.interfaces.EndGameValidator

class NoPiecesValidator: EndGameValidator {
    override fun validateEndGame(gameState: Game?): Boolean {
        val list = gameState?.getBoard()?.getInvertedBoardMap()?.keys?.filter { it?.getColor() == gameState.getCurrentTeam() }
        return list?.isEmpty()!!
    }

}