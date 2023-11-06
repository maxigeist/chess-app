package edu.austral.dissis.common.validators

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.Result
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.results.InvalidResult
import edu.austral.dissis.common.results.ValidResult

class FirstMoveValidator: Validator {
    override fun validateMovement(movement: Movement?, gameState: Game?): Result {
        val moves = gameState?.getMoves()
        if (moves != null) {
            for(board in moves){
                if(board.getBoardMap()[movement?.getFrom()] != gameState.getBoard().getBoardMap()[movement?.getFrom()]){
                    return InvalidResult("The movement is invalid")
                }
            }
            return ValidResult("The movement is valid")
        }
        return ValidResult("The movement is valid")
    }

}