package edu.austral.dissis.chess.validators.endGameValidators

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.chess.entities.ChessPieceName
import edu.austral.dissis.common.Position
import edu.austral.dissis.common.interfaces.EndGameValidator
import edu.austral.dissis.common.results.ValidResult


class CheckMateValidator: EndGameValidator {

    override fun validateEndGame(gameState: Game?): Boolean {
        val invertedBoard = gameState?.getBoard()?.getInvertedBoardMap()
        val invertedBoardKeys = invertedBoard?.keys
        for (key in invertedBoardKeys!!){
            if (key?.getName() == ChessPieceName.KING && key.getColor() == gameState.getCurrentTeam()) {
                val teamPieces = invertedBoard.keys.filter { it?.getColor() == gameState.getCurrentTeam() }
                for (piece in teamPieces) {
                    for (i in 1..gameState.getBoard().getXDimension()) {
                        for (j in 1..gameState.getBoard().getYDimension()) {
                            val move = Movement(invertedBoard[piece]!!, Position(i,j))
                            if(gameState.validateMovement(move) is ValidResult){
                                return false
                            }
                        }
                    }
                }
                return true
            }
        }
        return false
    }
}