package edu.austral.dissis.common

import edu.austral.dissis.common.game_results.EndGameResult
import edu.austral.dissis.common.game_results.InvalidGameResult
import edu.austral.dissis.common.game_results.ValidGameResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.interfaces.EndGameValidator
import edu.austral.dissis.common.interfaces.GameResult
import edu.austral.dissis.common.interfaces.Result
import edu.austral.dissis.common.results.InvalidResult
import edu.austral.dissis.common.results.ValidResult


class Game(
    private val moves: List<Board>,
    private val gameValidators: List<Validator>,
    private val endGameValidators : List<EndGameValidator>,
    private val board: Board,
    private val rules: Map<Piece, Validator>,
    private val currentTeam: Color = Color.WHITE,

)  {

    fun getEndGameValidators():List<EndGameValidator>{
        return endGameValidators
    }

    fun getMoves(): List<Board> {
        return moves
    }
    fun gameValidators(): List<Validator> {
        return gameValidators
    }

    fun getRules(): Map<Piece, Validator> {
        return rules
    }

    fun getBoard(): Board {
        return board
    }

    fun getCurrentTeam(): Color {
        return currentTeam
    }

    fun move(from: Position, to: Position): GameResult {
        val movement = Movement(from, to)
        when (val movementValidation = validateMovement(movement)) {
            is InvalidResult -> {
                return InvalidGameResult(movementValidation.getMessage())
            }
            else -> {
                val boards = moves.toList() + board
                val newGame = Game(
                    boards,
                    gameValidators,
                    endGameValidators,
                    board.move(movement),
                    rules,
                    currentTeam = opposite()
                )
                if (newGame.checkEndGameValidators()){
                    return EndGameResult()
                }
                return ValidGameResult(newGame)
            }
        }
    }


    fun checkEndGameValidators(): Boolean{
        for (validator in endGameValidators){
            if (validator.validateEndGame(this)){
                return true
            }
        }
        return false
    }


    fun opposite(): Color {
        return if (currentTeam == Color.WHITE) Color.BLACK else Color.WHITE
    }

    fun validateMovement(movement: Movement): Result {
        val gameValidatorsResult = validateGameValidators(movement)
        val pieceRuleResult = validatePieceRule(movement)
        return when {
            gameValidatorsResult is InvalidResult -> gameValidatorsResult
            pieceRuleResult is InvalidResult -> pieceRuleResult
            else -> ValidResult("The movement is valid")
        }
    }

    fun validateGameValidators(movement: Movement): Result {
        for (validator in gameValidators) {
            val validation = validator.validateMovement(movement, this)
            if (validation !is ValidResult) {
                return validator.validateMovement(movement, this)
            }
        }
        return ValidResult("The movement is valid")
    }

    fun validatePieceRule(movement: Movement):Result{
        val piece = this.board.getBoardMap()[movement.getFrom()]
        val pieceRule = rules[piece]
        return pieceRule?.validateMovement(movement, this)!!
    }
}
