package edu.austral.dissis.common.validators

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.Validator

class OrRuleValidator(private val rules: List<Validator>) : Validator {
    override fun validateMovement(movement: Movement?, gameState: Game?): Boolean {
        for (rule in rules){
            if (rule.validateMovement(movement, gameState)){
                return true
            }
        }
        return false;
    }


}