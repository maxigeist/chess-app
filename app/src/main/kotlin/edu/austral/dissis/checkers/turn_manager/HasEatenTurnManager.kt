package edu.austral.dissis.checkers.turn_manager

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.TurnManager

class HasEatenTurnManager:TurnManager {
    override fun newGame(): Game {
        TODO("Not yet implemented")
    }

    override fun returnTurnManager(): TurnManager {
        TODO("Not yet implemented")
    }

    override fun validateMovement(gameState: Game, movement: Movement): Boolean {
        TODO("Not yet implemented")
    }

    override fun getHasEaten(): Boolean {
        return true
    }
}