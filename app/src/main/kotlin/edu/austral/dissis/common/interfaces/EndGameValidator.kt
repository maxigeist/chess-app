package edu.austral.dissis.common.interfaces

import edu.austral.dissis.common.Game


interface EndGameValidator {
    fun validateEndGame(gameState: Game?): Boolean
}