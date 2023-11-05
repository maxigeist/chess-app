package edu.austral.dissis.common.validators

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.Validator
import kotlin.math.abs

class LOrientationValidator: Validator {
    override fun validateMovement(movement: Movement?, gameState: Game?): Boolean {
        val xDistance = abs(movement?.getTo()?.getXCoordinate()?.minus(movement.getFrom().getXCoordinate())!!)
        val yDistance = abs(movement.getTo().getYCoordinate().minus(movement.getFrom().getYCoordinate()))
        return (xDistance == 1 && yDistance == 2) || (xDistance == 2 && yDistance == 1)
    }
}