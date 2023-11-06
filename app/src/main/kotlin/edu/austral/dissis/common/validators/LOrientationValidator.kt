package edu.austral.dissis.common.validators

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.Result
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.results.InvalidResult
import edu.austral.dissis.common.results.ValidResult
import kotlin.math.abs

class LOrientationValidator: Validator {
    override fun validateMovement(movement: Movement?, gameState: Game?): Result {
        val xDistance = abs(movement?.getTo()?.getXCoordinate()?.minus(movement.getFrom().getXCoordinate())!!)
        val yDistance = abs(movement.getTo().getYCoordinate().minus(movement.getFrom().getYCoordinate()))
        return if((xDistance == 1 && yDistance == 2) || (xDistance == 2 && yDistance == 1)){
            ValidResult("The movement is valid")
        } else{
            InvalidResult("The movement is invalid")
        }
    }
}