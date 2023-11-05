package edu.austral.dissis.common.validators

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.Movement

class OrientationValidator(private val north: Boolean):Validator {
    override fun validateMovement(movement: Movement?, gameState: Game?): Boolean {
        return if (north){
            movement?.getTo()?.getYCoordinate()!! > movement.getFrom().getYCoordinate()
        } else{
            return movement?.getTo()?.getYCoordinate()!! < movement.getFrom().getYCoordinate()
        }
    }
}