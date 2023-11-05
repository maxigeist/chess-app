package edu.austral.dissis.common

import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.interfaces.EndGameValidator


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

    fun move(from: Position, to: Position): Game {
        val movement = Movement(from, to)
        if(!validateMovement(movement)){
            throw Exception("Invalid movement")
        }

        val boards = moves.toList() + board
        return Game(boards, gameValidators, endGameValidators, board.move(movement), rules, currentTeam = opposite())
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

    fun validateMovement(movement: Movement): Boolean{
        return (validatePieceRule(movement)
                && validateGameValidators(movement))
    }

    fun validateGameValidators(movement: Movement): Boolean {
        for (validator in gameValidators) {
            if (!validator.validateMovement(movement, this)) {
                return false
            }
        }
        return true
    }

    fun validatePieceRule(movement: Movement):Boolean{
        val piece = this.board.getBoardMap()[movement.getFrom()]
        val pieceRule = rules[piece]
        return pieceRule?.validateMovement(movement, this)!!
    }

}
