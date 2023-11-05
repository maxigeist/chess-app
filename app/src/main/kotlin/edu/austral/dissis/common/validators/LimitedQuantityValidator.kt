package edu.austral.dissis.common.validators

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.Validator
import kotlin.math.abs

class LimitedQuantityValidator(
    private val quantity: Int,
): Validator {
    //This method checks if the movement quantity is the same as the quantity of the validator.
    override fun validateMovement(movement: Movement?, gameState: Game?): Boolean {
        val xDistance = abs(movement?.getTo()?.getXCoordinate()?.minus(movement.getFrom().getXCoordinate())!!)
        val yDistance = abs(movement.getTo().getYCoordinate().minus(movement.getFrom().getYCoordinate()))
        if(xDistance == yDistance) {
            return xDistance <= quantity
        } else {
            return (xDistance + yDistance) <= quantity
        }
    }
}