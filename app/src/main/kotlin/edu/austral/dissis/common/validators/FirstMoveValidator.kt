package edu.austral.dissis.common.validators

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.Validator

class FirstMoveValidator: Validator {
    override fun validateMovement(movement: Movement?, gameState: Game?): Boolean {
        val moves = gameState?.getMoves()
        if (moves != null) {
            for(board in moves){
                if(board.getBoardMap()[movement?.getFrom()] != gameState.getBoard().getBoardMap()[movement?.getFrom()]){
                    return false
                }
            }
            return true
        }
        return true
    }

}