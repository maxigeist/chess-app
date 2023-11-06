package edu.austral.dissis.common.validators

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.interfaces.Result
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.results.InvalidResult
import edu.austral.dissis.common.results.ValidResult

class OrRuleValidator(private val rules: List<Validator>) : Validator {
    override fun validateMovement(movement: Movement?, gameState: Game?): Result {
        for (rule in rules){
            if (rule.validateMovement(movement, gameState) is ValidResult){
                return ValidResult("The movement is valid")
            }

        }
        return InvalidResult("The movement is invalid");
    }
}