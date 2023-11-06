package edu.austral.dissis.common.validators

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.Position
import edu.austral.dissis.common.interfaces.Result
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.results.InvalidResult
import edu.austral.dissis.common.results.ValidResult
import kotlin.math.abs

class DiagonalObstacleValidator : Validator {
    override fun validateMovement(movement: Movement?, gameState: Game?): Result {
        val (start, end) = if (movement?.getFrom()?.getXCoordinate()!! > movement.getTo().getXCoordinate()) {
            movement.getTo().getXCoordinate()+ 1 to movement.getFrom().getXCoordinate()
        } else {
            movement.getFrom().getXCoordinate() + 1 to movement.getTo().getXCoordinate()
        }
        val (startY, endY) = if (movement.getFrom().getYCoordinate() > movement.getTo().getYCoordinate()) {
            movement.getTo().getYCoordinate()+ 1 to movement.getFrom().getYCoordinate()
        } else {
            movement.getFrom().getYCoordinate()+ 1 to movement.getTo().getYCoordinate()
        }
        for (i in start until end) {
            for (j in startY until endY) {
                if ((gameState?.getBoard()?.getBoardMap()?.get(Position(i,j)) != null) && abs(i - movement.getTo().getXCoordinate()) == abs(j - movement.getTo().getYCoordinate()) ) {
                    return InvalidResult("The movement is invalid")
                }
            }
        }
        return ValidResult("The movement is valid")
    }
}