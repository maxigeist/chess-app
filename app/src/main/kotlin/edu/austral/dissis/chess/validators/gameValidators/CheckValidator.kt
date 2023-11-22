package edu.austral.dissis.chess.validators.gameValidators

import edu.austral.dissis.common.Board
import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.chess.entities.ChessPieceName
import edu.austral.dissis.common.interfaces.Result
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.results.InvalidResult
import edu.austral.dissis.common.results.ValidResult

class CheckValidator: Validator {
    override fun validateMovement(movement: Movement?, gameState: Game?): Result {
        val newBoard = gameState?.getBoard()?.move(movement!!)
        val invertedBoard = newBoard?.getInvertedBoardMap()
        val invertedBoardKeys = newBoard?.getInvertedBoardMap()?.keys
        for (key in invertedBoardKeys!!){
            if (key?.getName() == ChessPieceName.KING && key.getColor() == gameState.getCurrentTeam()){
                val kingPosition = invertedBoard?.get(key)
                val enemyPieces = invertedBoard?.keys?.filter { it?.getColor() != gameState.getCurrentTeam() }
                //No se si esto cuenta como inmutable pero no se me ocurre otra forma de hacerlo
                val auxMoves = ArrayList<Board>()
                auxMoves.addAll(gameState.getMoves())
                auxMoves.add(newBoard)
                val newMoves = ArrayList<Board>()
                newMoves.addAll(auxMoves)
                val newGameState = Game(newMoves, gameState.gameValidators().filter { it !is CheckValidator }, gameState.getEndGameValidators(),newBoard , gameState.getExecutioner(),gameState.getRules(), gameState.getComplexMovement(),gameState.opposite(), gameState.getTurnManager())
                if(kingPosition!=null){
                    if (enemyPieces != null) {
                        for(piece in enemyPieces){
                            if(newGameState.validateMovement(Movement(invertedBoard[piece]!!, kingPosition)) is ValidResult){
                                return InvalidResult("You are in check")
                            }
                        }
                    }
                    return ValidResult("The movement is valid")
                }
                return InvalidResult("The movement is invalid")
            }
        }
        return ValidResult("The movement is valid")
    }
}