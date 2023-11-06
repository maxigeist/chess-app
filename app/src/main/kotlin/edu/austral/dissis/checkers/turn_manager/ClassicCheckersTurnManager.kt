package edu.austral.dissis.checkers.turn_manager

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.TurnManager


class ClassicCheckersTurnManager: TurnManager {

    override fun newGame(): Game {
        TODO("Not yet implemented")
    }


    override fun returnTurnManager() : ClassicCheckersTurnManager{
        return this;

    }
    override fun validateMovement(gameState: Game, movement: Movement): Boolean {
        return true;
    }


}