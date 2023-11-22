package edu.austral.dissis.chess.complex_moves

import edu.austral.dissis.chess.entities.ChessPieceName
import edu.austral.dissis.chess.gui.NewGameState
import edu.austral.dissis.common.*
import edu.austral.dissis.common.interfaces.ComplexMovement
import edu.austral.dissis.common.interfaces.Result
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.results.InvalidResult
import edu.austral.dissis.common.results.ValidResult
import edu.austral.dissis.common.validators.*

class PawnPromotion : ComplexMovement {
    override fun validateMovement(movement: Movement, game: Game): Result {
        if (game.getBoard().getBoardMap()[movement.getFrom()]?.getName() == ChessPieceName.PAWN) {
            when (game.getBoard().getBoardMap()[movement.getFrom()]?.getColor()) {
                Color.WHITE -> {
                    return if (movement.getTo().getYCoordinate() == 1) {
                        ValidResult("The movement is valid")
                    } else {
                        InvalidResult("The movement is invalid")
                    }
                }

                Color.BLACK -> {
                    return if (movement.getTo().getYCoordinate() == game.getBoard().getYDimension()) {
                        ValidResult("The movement is valid")
                    } else {
                        InvalidResult("The movement is invalid")
                    }
                }

                null -> TODO()
            }
        }
        return InvalidResult("The movement is invalid")
    }


    override fun newGameState(movement: Movement, game: Game): Game {
        val newBoard = Board(game.getBoard().getXDimension(), game.getBoard().getYDimension(),boardImmutableTransform(game, movement))
        return Game(
            game.getMoves().toList() + newBoard,
            game.gameValidators(),
            game.getEndGameValidators(),
            newBoard,
            game.getExecutioner(),
            rulesImmutableTransform(game, movement, newBoard),
            game.getComplexMovement(),
            game.opposite(),
            game.getTurnManager()
        )
    }

    fun buildQueen(): Validator {
        return OrRuleValidator(
            listOf(
                AndRuleValidator(
                    listOf(
                        DiagonalOrientationValidator(),
                        DiagonalObstacleValidator(false),
                    )
                ), AndRuleValidator(
                    listOf(
                        HorizontalOrientationValidator(),
                        HorizontalObstacleValidator(),
                    )
                ), AndRuleValidator(
                    listOf(
                        VerticalOrientationValidator(),
                        VerticalObstacleValidator(),
                    )
                )
            )
        )
    }

    fun boardImmutableTransform(game: Game, movement: Movement): HashMap<Position?, Piece?> {
//        val nextBoard = game.getBoard().put(movement.getTo(), queen)
//        game.copy(board =  nextBoard)
//        game.getBoard().replacePiece(pawn, queen)
//        return game
        val auxBoard: MutableMap<Position?, Piece?> = HashMap(game.getBoard().getBoardMap())
        val piece = auxBoard[movement.getFrom()]
        auxBoard.remove(movement.getFrom())
        auxBoard[movement.getTo()] = Piece(ChessPieceName.QUEEN, piece!!.getColor(), piece.getId())
        return HashMap(auxBoard)
    }

    fun rulesImmutableTransform(game: Game, movement: Movement, newBoard:Board): Map<Piece, Validator> {
        val auxBoard: MutableMap<Piece, Validator> = HashMap(game.getRules())
        val piece = newBoard.getBoardMap()[movement.getTo()]
        auxBoard[piece!!] = buildQueen()
        return HashMap(auxBoard)
    }


}
