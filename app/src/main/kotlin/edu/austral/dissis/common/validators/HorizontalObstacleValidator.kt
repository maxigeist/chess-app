package edu.austral.dissis.common.validators

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.Position
import edu.austral.dissis.common.interfaces.Result
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.results.InvalidResult
import edu.austral.dissis.common.results.ValidResult


class HorizontalObstacleValidator: Validator {
    override fun validateMovement(movement: Movement?, gameState: Game?): Result {
        if(movement?.getTo()?.getYCoordinate() == movement?.getFrom()?.getYCoordinate()){
            val min = movement?.getTo()?.getXCoordinate()?.compareTo(movement.getFrom().getXCoordinate())
            if (min != null) {
                if(min < 0){
                    for(i in movement.getTo().getXCoordinate()+1 until movement.getFrom().getXCoordinate()){
                        if (gameState?.getBoard()?.getBoardMap()
                                ?.get(Position(i, movement.getTo().getYCoordinate())) != null
                        ) {
                            return InvalidResult("The movement is invalid")
                        }
                    }
                    return ValidResult("The movement is valid")
                } else{
                    //I have to add one because I don't want the board to check if there is a piece in the same position as the piece that is moving
                    for(i in movement.getFrom().getXCoordinate()+1 until movement.getTo().getXCoordinate()){
                        if (gameState?.getBoard()?.getBoardMap()
                                ?.get(Position(i, movement.getTo().getYCoordinate())) != null
                        ) {
                            return InvalidResult("The movement is invalid")
                        }

                    }
                    return ValidResult("The movement is valid")
                }
            }
        }
        return ValidResult("The movement is valid")

    }
}