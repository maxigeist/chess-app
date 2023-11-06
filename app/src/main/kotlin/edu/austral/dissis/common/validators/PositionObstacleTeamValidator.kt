package edu.austral.dissis.common.validators
import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.Result
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.results.InvalidResult
import edu.austral.dissis.common.results.ValidResult

class PositionObstacleTeamValidator: Validator {
    override fun validateMovement(movement: Movement?, gameState: Game?): Result {
        val pieceColor = gameState?.getBoard()?.getBoardMap()?.get(movement!!.getFrom())!!.getColor()
        if (gameState.getBoard().getBoardMap()[movement?.getTo()] != null ){
            if (gameState.getBoard().getBoardMap()[movement?.getTo()]!!.getColor() == pieceColor){
                return InvalidResult("The movement is invalid")
            }
        }
        return ValidResult("The movement is valid")
    }
}