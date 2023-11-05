package edu.austral.dissis.common.validators

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.Validator

class HorizontalOrientationValidator: Validator {
    override fun validateMovement(movement: Movement?, gameState: Game?): Boolean {
        return movement?.getTo()?.getYCoordinate() == movement?.getFrom()?.getYCoordinate()
    }


}