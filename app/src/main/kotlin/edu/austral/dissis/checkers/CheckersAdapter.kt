package edu.austral.dissis.checkers

import edu.austral.dissis.checkers.complex_moves.PawnEating
import edu.austral.dissis.checkers.complex_moves.PawnPromotion
import edu.austral.dissis.checkers.entities.CheckersPieceName
import edu.austral.dissis.chess.entities.ChessPieceName
import edu.austral.dissis.chess.gui.*
import edu.austral.dissis.chess.turn_manager.ClassicChessTurnManager
import edu.austral.dissis.common.Board
import edu.austral.dissis.common.Color
import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Piece
import edu.austral.dissis.common.end_game_validators.NoPiecesValidator
import edu.austral.dissis.common.game_results.EndGameResult
import edu.austral.dissis.common.game_results.InvalidGameResult
import edu.austral.dissis.common.game_results.ValidGameResult
import edu.austral.dissis.common.interfaces.ComplexMovement
import edu.austral.dissis.common.interfaces.EndGameValidator
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.validators.*

class CheckersAdapter : GameEngine {

    var gameState = classicCheckers()


    override fun applyMove(move: Move): MoveResult {
        when (val movementResult = gameState.move(
            edu.austral.dissis.common.Position(move.from.column, move.from.row),
            edu.austral.dissis.common.Position(move.to.column, move.to.row)
        )) {
            is InvalidGameResult -> {
                return InvalidMove(movementResult.getMessage())
            }

            is ValidGameResult -> {
                gameState = movementResult.getGame()
                return NewGameState(
                    convertUiPieces(movementResult.getGame().getBoard().getBoardMap()),
                    getColor(movementResult.getGame().getCurrentTeam())
                )
            }

            is EndGameResult -> {
                return GameOver(getColor(gameState.getCurrentTeam()))
            }

            else -> {
                return InvalidMove("Invalid move")
            }
        }
    }

    override fun init(): InitialState {
        val boardSize = BoardSize(8, 8)
        val currentPlayer = PlayerColor.WHITE
        return InitialState(boardSize, convertUiPieces(gameState.getBoard().getBoardMap()), currentPlayer)
    }


    fun classicCheckers(): Game {
        val gameBoard: MutableMap<edu.austral.dissis.common.Position?, Piece?> = HashMap()
        val board = Board(8, 8, gameBoard)
        val gameValidators: MutableList<Validator> = ArrayList()
        val complexMoves: MutableList<ComplexMovement> = ArrayList()
        val normalTurnManager = ClassicChessTurnManager()
        val endGameValidators: MutableList<EndGameValidator> = ArrayList()
        val ruleValidators: MutableMap<Piece, Validator> = HashMap()
        val executioner = Executioner()
        val insideBoardValidator = InsideBoardValidator()
        val makeAMoveValidator = MakeAMoveValidator()
        val currentTurnValidator = CurrentTurnValidator()
        val pawnEatingValidator = PawnEating()
        val positionObstacleTeamValidator = PositionObstacleTeamValidator()
        val hasEatenTurnManagerValidator = HasEatenTurnManagerValidator()
        val pawnPromotion = PawnPromotion()
        val emptySquareValidator = EmptySquareValidator()
        val NoPiecesValidator = NoPiecesValidator()
        gameValidators.add(insideBoardValidator)
        gameValidators.add(makeAMoveValidator)
        gameValidators.add(currentTurnValidator)
        gameValidators.add(hasEatenTurnManagerValidator)
        gameValidators.add(positionObstacleTeamValidator)
        gameValidators.add(emptySquareValidator)
        endGameValidators.add(NoPiecesValidator)
        complexMoves.add(pawnEatingValidator)
        complexMoves.add(pawnPromotion)
        uploadClassicPieces(gameBoard)
        uploadPawnRule(ruleValidators)
        return Game(
            ArrayList<Board>(), gameValidators, endGameValidators, board, executioner,ruleValidators, complexMoves,
            Color.WHITE, normalTurnManager
        )
    }

    fun getColor(color: Color): PlayerColor {
        return if (color == Color.WHITE) {
            PlayerColor.WHITE
        } else {
            PlayerColor.BLACK
        }
    }

    fun convertUiPieces(map: Map<edu.austral.dissis.common.Position?, Piece?>): List<ChessPiece> {
        val pieces = ArrayList<ChessPiece>()
        val positions = map.keys
        for (position in positions) {
            val piece = map[position]
            val id = piece?.getId()
            val stringId = id.toString()
            val color = when (piece?.getColor()) {
                Color.WHITE -> PlayerColor.WHITE
                Color.BLACK -> PlayerColor.BLACK
                null -> TODO()
            }
            val type = piece.getName().getName()
            pieces.add(
                ChessPiece(
                    stringId,
                    color,
                    Position(position?.getYCoordinate()!!, position.getXCoordinate()),
                    type
                )
            )
        }
        return pieces
    }

    fun savePawnRule(color: Color) :Validator{
        return OrRuleValidator(
            listOf(
                AndRuleValidator(
                    listOf(
                        LimitedQuantityValidator(1),
                        DiagonalOrientationValidator(),
                        if (color == Color.WHITE) OrientationValidator(false) else OrientationValidator(true),
                    )
                ),
                AndRuleValidator(
                    listOf(
                        DiagonalOrientationValidator(),
                        LimitedQuantityValidator(2),
                        DiagonalObstacleValidator(true),
                        if (color == Color.WHITE) OrientationValidator(false) else OrientationValidator(true),
                    )
                )
            )
        )
    }

    fun uploadPawnRule(ruleValidators: MutableMap<Piece, Validator>){
        for (i in 1..12) {
            ruleValidators[Piece(ChessPieceName.PAWN, Color.WHITE, i)] = savePawnRule(Color.WHITE)
            ruleValidators[Piece(ChessPieceName.PAWN, Color.BLACK, i +12)] = savePawnRule(Color.BLACK)
        }
    }

    fun uploadClassicPieces(gameBoard: MutableMap<edu.austral.dissis.common.Position?, Piece?>) {
        gameBoard[edu.austral.dissis.common.Position(1, 8)] = Piece(CheckersPieceName.PAWN, Color.WHITE, 1)
        gameBoard[edu.austral.dissis.common.Position(3, 8)] = Piece(CheckersPieceName.PAWN, Color.WHITE, 2)
        gameBoard[edu.austral.dissis.common.Position(5, 8)] = Piece(CheckersPieceName.PAWN, Color.WHITE, 3)
        gameBoard[edu.austral.dissis.common.Position(7, 8)] = Piece(CheckersPieceName.PAWN, Color.WHITE, 4)

        gameBoard[edu.austral.dissis.common.Position(2, 7)] = Piece(CheckersPieceName.PAWN, Color.WHITE, 5)
        gameBoard[edu.austral.dissis.common.Position(4, 7)] = Piece(CheckersPieceName.PAWN, Color.WHITE, 6)
        gameBoard[edu.austral.dissis.common.Position(6, 7)] = Piece(CheckersPieceName.PAWN, Color.WHITE, 7)
        gameBoard[edu.austral.dissis.common.Position(8, 7)] = Piece(CheckersPieceName.PAWN, Color.WHITE, 8)

        gameBoard[edu.austral.dissis.common.Position(1, 6)] = Piece(CheckersPieceName.PAWN, Color.WHITE, 9)
        gameBoard[edu.austral.dissis.common.Position(3, 6)] = Piece(CheckersPieceName.PAWN, Color.WHITE, 10)
        gameBoard[edu.austral.dissis.common.Position(5, 6)] = Piece(CheckersPieceName.PAWN, Color.WHITE, 11)
        gameBoard[edu.austral.dissis.common.Position(7, 6)] = Piece(CheckersPieceName.PAWN, Color.WHITE, 12)



        gameBoard[edu.austral.dissis.common.Position(2, 1)] = Piece(CheckersPieceName.PAWN, Color.BLACK, 13)
        gameBoard[edu.austral.dissis.common.Position(4, 1)] = Piece(CheckersPieceName.PAWN, Color.BLACK, 14)
        gameBoard[edu.austral.dissis.common.Position(6, 1)] = Piece(CheckersPieceName.PAWN, Color.BLACK, 15)
        gameBoard[edu.austral.dissis.common.Position(8, 1)] = Piece(CheckersPieceName.PAWN, Color.BLACK, 16)

        gameBoard[edu.austral.dissis.common.Position(1, 2)] = Piece(CheckersPieceName.PAWN, Color.BLACK, 17)
        gameBoard[edu.austral.dissis.common.Position(3, 2)] = Piece(CheckersPieceName.PAWN, Color.BLACK, 18)
        gameBoard[edu.austral.dissis.common.Position(5, 2)] = Piece(CheckersPieceName.PAWN, Color.BLACK, 19)
        gameBoard[edu.austral.dissis.common.Position(7, 2)] = Piece(CheckersPieceName.PAWN, Color.BLACK, 20)

        gameBoard[edu.austral.dissis.common.Position(2, 3)] = Piece(CheckersPieceName.PAWN, Color.BLACK, 21)
        gameBoard[edu.austral.dissis.common.Position(4, 3)] = Piece(CheckersPieceName.PAWN, Color.BLACK, 22)
        gameBoard[edu.austral.dissis.common.Position(6, 3)] = Piece(CheckersPieceName.PAWN, Color.BLACK, 23)
        gameBoard[edu.austral.dissis.common.Position(8, 3)] = Piece(CheckersPieceName.PAWN, Color.BLACK, 24)

    }


}