package edu.austral.dissis.common.validators
import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.Validator

class PositionObstacleTeamValidator: Validator {
    override fun validateMovement(movement: Movement?, gameState: Game?): Boolean {
        val pieceColor = gameState?.getBoard()?.getBoardMap()?.get(movement!!.getFrom())!!.getColor()
        if (gameState.getBoard().getBoardMap()[movement?.getTo()] != null ){
            if (gameState.getBoard().getBoardMap()[movement?.getTo()]!!.getColor() == pieceColor){
                return false
            }
        }
        return true
    }
}