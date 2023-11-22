package edu.austral.dissis.chess.turn_manager

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.TurnManager

class ClassicChessTurnManager : TurnManager {

    override fun newGame(): Game {
        TODO("Not yet implemented")
    }

    override fun returnTurnManager() : ClassicChessTurnManager{
        return this;
    }

    override fun validateMovement(gameState: Game, movement: Movement):Boolean {
        return true;
    }

    override fun getHasEaten(): Boolean {
        return false
    }


}