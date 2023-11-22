package edu.austral.dissis.common.validators

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.Result
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.results.InvalidResult
import edu.austral.dissis.common.results.ValidResult


class InsideBoardValidator: Validator {

    override fun validateMovement(movement: Movement?, gameState: Game?): Result {
        return if(movement?.getTo()?.getXCoordinate()!! <= gameState?.getBoard()?.getXDimension()!! && movement.getTo().getXCoordinate() >= 1
            && 1<= movement.getTo().getYCoordinate() && movement.getTo().getYCoordinate() <= gameState.getBoard().getYDimension()){
            ValidResult("The movement is valid")
        } else{
            InvalidResult("The movement is invalid")
        }
    }

}