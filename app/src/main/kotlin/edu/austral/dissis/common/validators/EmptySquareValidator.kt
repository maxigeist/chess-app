package edu.austral.dissis.common.validators

import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.Result
import edu.austral.dissis.common.results.InvalidResult
import edu.austral.dissis.common.results.ValidResult

class EmptySquareValidator:Validator {
    override fun validateMovement(movement: Movement?, gameState: Game?): Result {
        return if (gameState?.getBoard()?.getBoardMap()?.get(movement?.getTo()) == null){
            ValidResult("The movement is valid")
        } else{
            InvalidResult("The movement is invalid")
        }
    }

}