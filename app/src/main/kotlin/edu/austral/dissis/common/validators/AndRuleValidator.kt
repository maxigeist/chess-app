package edu.austral.dissis.common.validators

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.Result
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.results.InvalidResult
import edu.austral.dissis.common.results.ValidResult

class AndRuleValidator(private val andRuleValidator:  List<Validator>):Validator {
    override fun validateMovement(movement: Movement?, gameState: Game?): Result {
        for (rule in andRuleValidator){
            if (rule.validateMovement(movement, gameState) is InvalidResult){
                return InvalidResult("The movement is invalid")
            }
        }
        return ValidResult("The movement is valid")
    }
}