package edu.austral.dissis.common.validators

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.Validator

class EnemyOnToValidator:Validator {
    override fun validateMovement(movement: Movement?, gameState: Game?): Boolean {
        //to check if there is a teammate there is another validator
        return gameState?.getBoard()?.getBoardMap()!![movement?.getTo()] != null
    }
}