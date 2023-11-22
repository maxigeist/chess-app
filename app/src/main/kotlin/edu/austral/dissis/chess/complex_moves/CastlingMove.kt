package edu.austral.dissis.chess.complex_moves

import edu.austral.dissis.chess.entities.ChessPieceName
import edu.austral.dissis.common.*
import edu.austral.dissis.common.interfaces.ComplexMovement
import edu.austral.dissis.common.interfaces.Result
import edu.austral.dissis.common.results.InvalidResult
import edu.austral.dissis.common.results.ValidResult

import kotlin.math.abs

class CastlingMove: ComplexMovement {
    override fun validateMovement(movement: Movement, game: Game): Result {
        if (game.getBoard().getBoardMap()[movement.getFrom()]?.getName() == ChessPieceName.KING && abs(movement.getTo().getXCoordinate() - movement.getFrom().getXCoordinate()) == 2) {
            return ValidResult("The movement is valid")
        }
        return InvalidResult("The movement is invalid")
    }

    override fun newGameState(movement: Movement, game: Game): Game {
        val newBoard = Board( game.getBoard().getXDimension(), game.getBoard().getYDimension(),
            validateRookPosition(movement, game)
        )
        return game.copy(
            board = newBoard,
            currentTeam = game.opposite(),
        )
    }

    fun validateRookPosition(movement: Movement, game: Game): Map<Position?, Piece?>{
        val auxBoard: MutableMap<Position?, Piece?> = HashMap(game.getBoard().getBoardMap())
        if (game.getCurrentTeam() == Color.WHITE){
            if (movement.getTo().getXCoordinate() > movement.getFrom().getXCoordinate()) {
                if (game.getBoard().getBoardMap()[Position(8, 8)]?.getName() == ChessPieceName.ROOK) {
                    val rook = auxBoard[Position(8,8)]
                    val king = auxBoard[movement.getFrom()]
                    auxBoard.remove(Position(8,8))
                    auxBoard.remove(movement.getFrom())
                    auxBoard[movement.getTo()] = king
                    auxBoard[Position(movement.getTo().getXCoordinate()-1, movement.getTo().getYCoordinate())] = rook
                }
            }
            else{
                if (game.getBoard().getBoardMap()[Position(1, 8)]?.getName() == ChessPieceName.ROOK) {
                    val rook = auxBoard[Position(1,8)]
                    val king = auxBoard[movement.getFrom()]
                    auxBoard.remove(Position(1,8))
                    auxBoard.remove(movement.getFrom())
                    auxBoard[movement.getTo()] = king
                    auxBoard[Position(movement.getTo().getXCoordinate()+1, movement.getTo().getYCoordinate())] = rook
                }
            }
        }
        else {
            if (movement.getTo().getXCoordinate() > movement.getFrom().getXCoordinate()) {
                if (game.getBoard().getBoardMap()[Position(8, 1)]?.getName() == ChessPieceName.ROOK) {
                    val rook = auxBoard[Position(8,1)]
                    val king = auxBoard[movement.getFrom()]
                    auxBoard.remove(Position(8,1))
                    auxBoard.remove(movement.getFrom())
                    auxBoard[movement.getTo()] = king
                    auxBoard[Position(movement.getTo().getXCoordinate()-1, movement.getTo().getYCoordinate())] = rook
                }
            }
            else {
                if (game.getBoard().getBoardMap()[Position(1, 1)]?.getName() == ChessPieceName.ROOK) {
                    val rook = auxBoard[Position(1,1)]
                    val king = auxBoard[movement.getFrom()]
                    auxBoard.remove(Position(1,1))
                    auxBoard.remove(movement.getFrom())
                    auxBoard[movement.getTo()] = king
                    auxBoard[Position(movement.getTo().getXCoordinate()+1, movement.getTo().getYCoordinate())] = rook
                }
            }

        }
        return auxBoard
    }
}