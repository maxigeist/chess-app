package edu.austral.dissis.common.validators

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.Validator

class AndRuleValidator(private val andRuleValidator:  List<Validator>):Validator {
    override fun validateMovement(movement: Movement?, gameState: Game?): Boolean {
        for (rule in andRuleValidator){
            if (!rule.validateMovement(movement, gameState)){
                return false
            }
        }
        return true;
    }
}