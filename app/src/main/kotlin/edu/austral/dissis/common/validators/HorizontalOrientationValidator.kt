package edu.austral.dissis.common.validators

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.Result
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.results.InvalidResult
import edu.austral.dissis.common.results.ValidResult

class HorizontalOrientationValidator: Validator {
    override fun validateMovement(movement: Movement?, gameState: Game?): Result {
        if (movement?.getTo()?.getYCoordinate() == movement?.getFrom()?.getYCoordinate()){
            return ValidResult("The movement is valid");
        }
        else{
            return InvalidResult("The movement is invalid");
        }
    }
}