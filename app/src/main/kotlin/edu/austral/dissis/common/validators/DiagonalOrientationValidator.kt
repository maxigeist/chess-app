package edu.austral.dissis.common.validators

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.Validator
import kotlin.math.abs

class DiagonalOrientationValidator: Validator {
    override fun validateMovement(movement: Movement?, gameState: Game?): Boolean {
        return (abs((movement?.getTo()?.getXCoordinate()?.minus(movement.getFrom().getXCoordinate()))!!)  == abs((movement.getTo().getYCoordinate().minus(movement.getFrom().getYCoordinate()))))
    }
}