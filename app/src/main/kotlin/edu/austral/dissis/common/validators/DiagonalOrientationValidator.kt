package edu.austral.dissis.common.validators

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.Result
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.results.InvalidResult
import edu.austral.dissis.common.results.ValidResult
import kotlin.math.abs

class DiagonalOrientationValidator: Validator {
    override fun validateMovement(movement: Movement?, gameState: Game?): Result {
        return if((abs((movement?.getTo()?.getXCoordinate()?.minus(movement.getFrom().getXCoordinate()))!!)  == abs((movement.getTo().getYCoordinate().minus(movement.getFrom().getYCoordinate()))))){
            ValidResult("The movement is valid")
        } else{
            InvalidResult("The movement is invalid")
        }

    }
}