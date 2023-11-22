package edu.austral.dissis.checkers.complex_moves

import edu.austral.dissis.checkers.entities.CheckersPieceName
import edu.austral.dissis.checkers.turn_manager.ClassicCheckersTurnManager
import edu.austral.dissis.checkers.turn_manager.HasEatenTurnManager
import edu.austral.dissis.common.*
import edu.austral.dissis.common.interfaces.ComplexMovement
import edu.austral.dissis.common.interfaces.Result
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.results.InvalidResult
import edu.austral.dissis.common.results.ValidResult
import edu.austral.dissis.common.validators.*

class PawnPromotion: ComplexMovement {
//    override fun validateMovement(movement: Movement, game: Game): Result {
//        when (game.getTurnManager()) {
//            is HasEatenTurnManager -> {
//                if (game.getBoard().getBoardMap()[movement.getTo()]?.getName() == CheckersPieceName.PAWN) {
//                    when (game.getBoard().getBoardMap()[movement.getTo()]?.getColor()) {
//                        Color.WHITE -> {
//                            return if (movement.getTo().getYCoordinate() == 1) {
//                                ValidResult("The movement is valid")
//                            } else {
//                                InvalidResult("The movement is invalid")
//                            }
//                        }
//
//                        Color.BLACK -> {
//                            return if (movement.getTo().getYCoordinate() == game.getBoard().getYDimension()) {
//                                ValidResult("The movement is valid")
//                            } else {
//                                InvalidResult("The movement is invalid")
//                            }
//                        }
//
//                        null -> TODO()
//                    }
//                }
//                return InvalidResult("The movement is invalid")
//            }
//
//            else -> {
//                if (game.getBoard().getBoardMap()[movement.getFrom()]?.getName() == CheckersPieceName.PAWN) {
//                    when (game.getBoard().getBoardMap()[movement.getFrom()]?.getColor()) {
//                        Color.WHITE -> {
//                            return if (movement.getTo().getYCoordinate() == 1) {
//                                ValidResult("The movement is valid")
//                            } else {
//                                InvalidResult("The movement is invalid")
//                            }
//                        }
//                        Color.BLACK -> {
//                            return if (movement.getTo().getYCoordinate() == game.getBoard().getYDimension()) {
//                                ValidResult("The movement is valid")
//                            } else {
//                                InvalidResult("The movement is invalid")
//                            }
//                        }
//
//                        null -> TODO()
//                    }
//
//                }
//                return InvalidResult("The movement is not valid")
//            }
//        }
//    }

    override fun validateMovement(movement: Movement, game: Game): Result {
        val pieceFrom = game.getBoard().getBoardMap()[movement.getFrom()]
        val pieceTo = game.getBoard().getBoardMap()[movement.getTo()]

        return when (game.getTurnManager()) {
            is HasEatenTurnManager -> validatePawnMovement(pieceTo, movement.getTo(), game.getBoard().getYDimension())
            else -> validatePawnMovement(pieceFrom, movement.getTo(), game.getBoard().getYDimension())
        }
    }

    private fun validatePawnMovement(piece: Piece?, destination: Position, maxY: Int): Result {
        return when {
            piece?.getName() == CheckersPieceName.PAWN -> {
                when (piece.getColor()) {
                    Color.WHITE, Color.BLACK -> {
                        if (destination.getYCoordinate() == if (piece.getColor() == Color.WHITE) 1 else maxY) {
                            ValidResult("The movement is valid")
                        } else {
                            InvalidResult("The movement is invalid")
                        }
                    }
                    else -> InvalidResult("Invalid color")
                }
            }
            else -> InvalidResult("The movement is not valid")
        }
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
            game.getCurrentTeam(),
            ClassicCheckersTurnManager()
        )
    }

    fun buildKing(): Validator {
        return OrRuleValidator(
            listOf(
                AndRuleValidator(
                    listOf(
                        LimitedQuantityValidator(1),
                        DiagonalOrientationValidator(),
                    )
                ),
                AndRuleValidator(
                    listOf(
                        DiagonalOrientationValidator(),
                        LimitedQuantityValidator(2),
                        DiagonalObstacleValidator(true),
                    )
                )
            )
        )
    }


    //No funciona
    private fun boardImmutableTransform(game: Game, movement: Movement): HashMap<Position?, Piece?> {
        val auxBoard: MutableMap<Position?, Piece?> = HashMap(game.getBoard().getBoardMap())
        if (game.getTurnManager() is HasEatenTurnManager){
            val piece = auxBoard[movement.getTo()]
            auxBoard[movement.getTo()] = Piece(CheckersPieceName.KING, piece!!.getColor(), piece.getId())
            return HashMap(auxBoard)
        }
        else{
            val piece = auxBoard[movement.getFrom()]
            auxBoard.remove(movement.getFrom())
            auxBoard[movement.getTo()] = Piece(CheckersPieceName.KING, piece!!.getColor(), piece.getId())
            return HashMap(auxBoard)
        }
    }

    private fun rulesImmutableTransform(game: Game, movement: Movement, newBoard:Board): Map<Piece, Validator> {
        val auxBoard: MutableMap<Piece, Validator> = HashMap(game.getRules())
        val piece = newBoard.getBoardMap()[movement.getTo()]
        auxBoard[piece!!] = buildKing()
        return HashMap(auxBoard)
    }


}