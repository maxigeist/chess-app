package edu.austral.dissis.common.validators

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.Position
import edu.austral.dissis.common.interfaces.Validator

class VerticalObstacleValidator : Validator {
    override fun validateMovement(movement: Movement?, gameState: Game?): Boolean {
        if(movement?.getTo()?.getXCoordinate() == movement?.getFrom()?.getXCoordinate()){
            val min = movement?.getTo()?.getYCoordinate()?.compareTo(movement.getFrom().getYCoordinate())
            if (min != null) {
                if(min < 0){
                    for(i in movement.getTo().getYCoordinate()+1 until  movement.getFrom().getYCoordinate()){
                        if (gameState?.getBoard()?.getBoardMap()
                                ?.get(Position(movement.getTo().getXCoordinate(), i)) != null
                        ) {
                            return false
                        }
                    }
                    return true
                } else{
                    //I have to add one because I don't want the board to check if there is a piece in the same position as the piece that is moving
                    for(i in movement.getFrom().getYCoordinate()+1 until movement.getTo().getYCoordinate()){
                            if (gameState?.getBoard()?.getBoardMap()
                                    ?.get(Position(movement.getTo().getXCoordinate(), i)) != null
                            ) {
                                return false
                            }
                    }
                    return true
                }
            }
        }
        return true
    }

}