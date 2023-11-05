package edu.austral.dissis.common.validators

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.Validator

class CurrentTurnValidator: Validator {

    override fun validateMovement(movement: Movement?, gameState: Game?): Boolean {
        return gameState?.getBoard()?.getBoardMap()?.get(movement?.getFrom())?.getColor() == gameState?.getCurrentTeam()
    }
}