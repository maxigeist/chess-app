package edu.austral.dissis.checkers.complex_moves

import edu.austral.dissis.checkers.entities.CheckersPieceName
import edu.austral.dissis.checkers.turn_manager.ClassicCheckersTurnManager
import edu.austral.dissis.checkers.turn_manager.EatAgainTurnManager
import edu.austral.dissis.checkers.turn_manager.HasEatenTurnManager
import edu.austral.dissis.chess.gui.Move
import edu.austral.dissis.common.*
import edu.austral.dissis.common.game_results.ValidGameResult
import edu.austral.dissis.common.interfaces.ComplexMovement
import edu.austral.dissis.common.interfaces.Result
import edu.austral.dissis.common.interfaces.TurnManager
import edu.austral.dissis.common.results.InvalidResult
import edu.austral.dissis.common.results.ValidResult


class PawnEating : ComplexMovement {
    override fun validateMovement(movement: Movement, game: Game): Result {
            //Antes ya chequeo que sea en diagonal y que sea de dos lugares.
            return validateEnemyPieceInMiddle(movement, game)
    }
    override fun newGameState(movement: Movement, game: Game): Game {
    val newBoard = Board(
        game.getBoard().getXDimension(),
        game.getBoard().getYDimension(),
        boardImmutableTransform(game, movement)
    )
        val canEatAgain = canEatAgain(movement, game, newBoard)
        val newCurrentTeam = if (canEatAgain) game.getCurrentTeam() else game.opposite()
        val turnManager = if (canEatAgain) EatAgainTurnManager(game.getBoard().getBoardMap()[movement.getFrom()]?.getId()!!) else HasEatenTurnManager()

        return Game(
        game.getMoves().toList() + newBoard,
        game.gameValidators(),
        game.getEndGameValidators(),
        newBoard,
            game.getExecutioner(),
        game.getRules(),
        game.getComplexMovement(),
        newCurrentTeam,
        turnManager,
    )
}


    private fun calculateMiddlePosition(movement: Movement): Position {
        return Position((movement.getTo().getXCoordinate() + movement.getFrom().getXCoordinate())/2,
            (movement.getTo().getYCoordinate() + movement.getFrom().getYCoordinate())/2)
        }


    private fun validateEnemyPieceInMiddle(
        movement: Movement,
        game: Game
    ): Result {
        val middlePiece = getMiddleEnemyPiece(movement, game)
        return if (middlePiece != null && middlePiece.getColor() != game.getCurrentTeam() ) {
            ValidResult("The movement is allowed")
        } else {
            InvalidResult("You have a teammate piece in the middle")
        }
    }

    private fun getMiddleEnemyPiece(movement: Movement, game: Game): Piece? {
        return game.getBoard().getBoardMap()[calculateMiddlePosition(movement)]
    }

    fun boardImmutableTransform(game: Game, movement: Movement): HashMap<Position?, Piece?> {
        val auxBoard: MutableMap<Position?, Piece?> = HashMap(game.getBoard().getBoardMap())
        auxBoard.remove(calculateMiddlePosition(movement))
        val piece = auxBoard[movement.getFrom()]
        auxBoard[movement.getTo()] = piece
        auxBoard.remove(movement.getFrom())
        return HashMap(auxBoard)
    }

    fun canEatAgain(movement: Movement, game: Game, newBoard:Board): Boolean {
        val newGame = Game(
            game.getMoves().toList() + newBoard,
            game.gameValidators(),
            game.getEndGameValidators(),
            newBoard,
            game.getExecutioner(),
            game.getRules(),
            game.getComplexMovement(),
            game.getCurrentTeam(),
            game.getTurnManager(),
        )
        val piece = newGame.getBoard().getBoardMap()[movement.getFrom()]
        val targetPosition = movement.getTo()

        if (piece?.getName() == CheckersPieceName.PAWN) {
            val directions = if (piece.getColor() == Color.WHITE) {
                listOf(
                    Position(targetPosition.getXCoordinate() + 2, targetPosition.getYCoordinate() - 2),
                    Position(targetPosition.getXCoordinate() - 2, targetPosition.getYCoordinate() - 2)
                )
            } else {
                listOf(
                    Position(targetPosition.getXCoordinate() + 2, targetPosition.getYCoordinate() + 2),
                    Position(targetPosition.getXCoordinate() - 2, targetPosition.getYCoordinate() + 2)
                )
            }

            directions.forEach { direction ->
                    if (newGame.validateMovement(Movement(movement.getTo(), direction)) is ValidResult){
                        return true
                }
            }
            return false
        } else {
            val directions = listOf(
                Position(targetPosition.getXCoordinate() + 2, targetPosition.getYCoordinate() - 2),
                Position(targetPosition.getXCoordinate() + 2, targetPosition.getYCoordinate() + 2),
                Position(targetPosition.getXCoordinate() - 2, targetPosition.getYCoordinate() + 2),
                Position(targetPosition.getXCoordinate() - 2, targetPosition.getYCoordinate() - 2)
            )

            directions.forEach { direction ->
                if (newGame.validateMovement(Movement(movement.getTo(), direction)) is ValidResult){
                    return true
                }
            }
            return false
        }
    }
    }