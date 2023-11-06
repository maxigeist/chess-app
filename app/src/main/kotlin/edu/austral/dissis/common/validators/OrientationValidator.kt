package edu.austral.dissis.common.validators

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.Result
import edu.austral.dissis.common.results.InvalidResult
import edu.austral.dissis.common.results.ValidResult

class OrientationValidator(private val north: Boolean):Validator {
    override fun validateMovement(movement: Movement?, gameState: Game?): Result {
        return if (north){
            if(movement?.getTo()?.getYCoordinate()!! > movement.getFrom().getYCoordinate()){
                ValidResult("The movement is valid")
            } else{
                InvalidResult("The movement is invalid")
            }

        } else{
            if(movement?.getTo()?.getYCoordinate()!! < movement.getFrom().getYCoordinate()){
                ValidResult("The movement is valid")
            } else{
                InvalidResult("The movement is invalid")
            }
        }
    }
}