package edu.austral.dissis.common.validators

import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement

class EmptySquareValidator:Validator {
    override fun validateMovement(movement: Movement?, gameState: Game?): Boolean {
        return gameState?.getBoard()?.getBoardMap()?.get(movement?.getTo()) == null
    }

}