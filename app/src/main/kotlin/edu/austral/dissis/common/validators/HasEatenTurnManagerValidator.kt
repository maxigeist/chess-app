package edu.austral.dissis.common.validators

import edu.austral.dissis.checkers.turn_manager.EatAgainTurnManager
import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.Result
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.results.InvalidResult
import edu.austral.dissis.common.results.ValidResult

class HasEatenTurnManagerValidator: Validator {
    override fun validateMovement(movement: Movement?, gameState: Game?): Result {
        return when (val turnManager = gameState?.getTurnManager()){
            is EatAgainTurnManager -> {
                if (gameState.getBoard().getBoardMap()[movement?.getFrom()]?.getId() != turnManager.getPieceId() || LimitedQuantityValidator(1).validateMovement(movement, gameState) is ValidResult){
                    InvalidResult("The movement is invalid because you have to eat again")
                } else{
                    ValidResult("The movement is valid")
                }
            }
            else ->{
                ValidResult("The movement is valid")
            }
        }
    }
}