package edu.austral.dissis.common.validators

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.Validator


class InsideBoardValidator: Validator {

    override fun validateMovement(movement: Movement?, gameState: Game?): Boolean {
        return movement?.getTo()?.getXCoordinate()!! <= gameState?.getBoard()?.getXDimension()!! && movement.getTo().getYCoordinate() <= gameState.getBoard().getYDimension()
    }

}