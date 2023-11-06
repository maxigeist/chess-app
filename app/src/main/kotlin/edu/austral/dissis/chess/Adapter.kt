package edu.austral.dissis.chess

import edu.austral.dissis.chess.gui.*
import edu.austral.dissis.chess.gui.Position
import edu.austral.dissis.chess.validators.endGameValidators.CheckMateValidator
import edu.austral.dissis.chess.validators.gameValidators.CheckValidator
import edu.austral.dissis.common.interfaces.EndGameValidator
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.validators.DiagonalObstacleValidator
import edu.austral.dissis.common.validators.HorizontalObstacleValidator
import edu.austral.dissis.common.validators.VerticalObstacleValidator
import edu.austral.dissis.common.validators.LimitedQuantityValidator
import edu.austral.dissis.chess.entities.ChessPieceName
import edu.austral.dissis.common.*
import edu.austral.dissis.common.game_results.EndGameResult
import edu.austral.dissis.common.game_results.InvalidGameResult
import edu.austral.dissis.common.game_results.ValidGameResult
import edu.austral.dissis.common.validators.*


class Adapter(): GameEngine{

    var gameState = classicChess()


    override fun applyMove(move: Move): MoveResult {
        when (val movementResult = gameState.move(edu.austral.dissis.common.Position(move.from.column, move.from.row),
            edu.austral.dissis.common.Position(move.to.column, move.to.row))){
            is InvalidGameResult -> {
                return InvalidMove(movementResult.getMessage())
            }
            is ValidGameResult ->{
                gameState = movementResult.getGame()
                return NewGameState(convertUiPieces(movementResult.getGame().getBoard().getBoardMap()), getColor(movementResult.getGame().getCurrentTeam()))
            }
            is EndGameResult ->{
                return GameOver(getColor(gameState.getCurrentTeam()))
            }
            else -> {
                return InvalidMove("Invalid move")
            }
        }
    }

    override fun init(): InitialState {
        val boardSize = BoardSize(8,8)
        val currentPlayer = PlayerColor.WHITE
        return InitialState(boardSize, convertUiPieces(gameState.getBoard().getBoardMap()), currentPlayer)
    }

    fun getColor(color: Color):PlayerColor{
        return if (color == Color.WHITE){
            PlayerColor.WHITE
        } else{
            PlayerColor.BLACK
        }
    }

    fun convertUiPieces(map:Map<edu.austral.dissis.common.Position?, Piece?>):List<ChessPiece>{
        val pieces = ArrayList<ChessPiece>()
        val positions = map.keys
        for(position in positions){
            val piece = map[position]
            val id = piece?.getId()
            val stringId = id.toString()
            val color = when (piece?.getColor()) {
                Color.WHITE -> PlayerColor.WHITE
                Color.BLACK -> PlayerColor.BLACK
                null -> TODO()
            }
            val type = piece.getName().getName()
            pieces.add(ChessPiece(stringId, color, Position(position?.getYCoordinate()!!, position.getXCoordinate()), type))
        }
        return pieces
    }

    fun classicChess(): Game {
        val gameBoard: MutableMap<edu.austral.dissis.common.Position?, Piece?> = HashMap()
        val board = Board(8, 8, gameBoard)
        val gameValidators: MutableList<Validator> = ArrayList()
        val endGameValidators : MutableList<EndGameValidator> = ArrayList()
        val ruleValidators : MutableMap<Piece, Validator> = HashMap()
        val insideBoardValidator = InsideBoardValidator()
        val makeAMoveValidator = MakeAMoveValidator()
        val currentTurnValidator = CurrentTurnValidator()
        val checkValidator = CheckValidator()
        val checkMateValidator = CheckMateValidator()
        val positionObstacleTeamValidator = PositionObstacleTeamValidator()
        gameValidators.add(insideBoardValidator)
        gameValidators.add(makeAMoveValidator)
        gameValidators.add(currentTurnValidator)
        gameValidators.add(checkValidator)
        gameValidators.add(positionObstacleTeamValidator)
        endGameValidators.add(checkMateValidator)
        uploadKingRule(ruleValidators)
        uploadQueenRule(ruleValidators)
        uploadPawnRule(ruleValidators)
        bishopRule(ruleValidators)
        uploadRookRule(ruleValidators)
        knightRule(ruleValidators)
        uploadClassicPieces(gameBoard)
        return Game(ArrayList<Board>(),gameValidators, endGameValidators, board, ruleValidators)
    }

    fun pawnsKingsAndQueens(): Game {
        val gameBoard: MutableMap<edu.austral.dissis.common.Position?, Piece?> = HashMap()
        val board = Board(16, 16, gameBoard)
        val gameValidators: MutableList<Validator> = ArrayList()
        val endGameValidators : MutableList<EndGameValidator> = ArrayList()
        val ruleValidators : MutableMap<Piece, Validator> = HashMap()
        val insideBoardValidator = InsideBoardValidator()
        val makeAMoveValidator = MakeAMoveValidator()
        val currentTurnValidator = CurrentTurnValidator()
        val checkValidator = CheckValidator()
        val checkMateValidator = CheckMateValidator()
        val positionObstacleTeamValidator = PositionObstacleTeamValidator()
        gameValidators.add(insideBoardValidator)
        gameValidators.add(makeAMoveValidator)
        gameValidators.add(currentTurnValidator)
        gameValidators.add(checkValidator)
        gameValidators.add(positionObstacleTeamValidator)
        endGameValidators.add(checkMateValidator)
        uploadKingRule(ruleValidators)
        uploadQueenRule(ruleValidators)
        uploadPawnsKingsAndQueens(gameBoard)
        return Game(ArrayList<Board>(),gameValidators, endGameValidators, board, ruleValidators)
    }

    fun uploadKingRule(ruleValidators: MutableMap<Piece, Validator>){
        val orRuleValidator = OrRuleValidator(
            listOf(
                AndRuleValidator(
                listOf(
                    DiagonalOrientationValidator(),
                    LimitedQuantityValidator(1),
                )
            )
            ,AndRuleValidator(
                listOf(
                    HorizontalOrientationValidator(),
                    LimitedQuantityValidator(1),
                )
            ),AndRuleValidator(
                    listOf(
                        VerticalOrientationValidator(),
                        LimitedQuantityValidator(1),
                    )
                )
            )
        )
        ruleValidators[Piece(ChessPieceName.KING, Color.WHITE, 13)] = orRuleValidator
        ruleValidators[Piece(ChessPieceName.KING, Color.BLACK, 29)] = orRuleValidator
    }


    fun uploadQueenRule(ruleValidators: MutableMap<Piece, Validator>){
        val orRuleValidator = OrRuleValidator(
            listOf(
                AndRuleValidator(
                    listOf(
                        DiagonalOrientationValidator(),
                        DiagonalObstacleValidator(),
                    )
                )
                ,AndRuleValidator(
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
        ruleValidators[Piece(ChessPieceName.QUEEN, Color.WHITE,12)] = orRuleValidator
        ruleValidators[Piece(ChessPieceName.QUEEN, Color.BLACK, 28)] = orRuleValidator
    }

    fun savePawnRule(color: Color):Validator{
        val orRuleValidator = OrRuleValidator(
            listOf(
                AndRuleValidator(
                    listOf(
                        DiagonalOrientationValidator(),
                        LimitedQuantityValidator(1),
                        EnemyOnToValidator(),
                        if (color == Color.WHITE) OrientationValidator(false) else OrientationValidator(true),
                    )
                )
                ,AndRuleValidator(
                    listOf(
                        VerticalOrientationValidator(),
                        LimitedQuantityValidator(1),
                        EmptySquareValidator(),
                        if (color == Color.WHITE) OrientationValidator(false) else OrientationValidator(true),
                    )
                ),AndRuleValidator(
                    listOf(
                        VerticalOrientationValidator(),
                        VerticalObstacleValidator(),
                        FirstMoveValidator(),
                        LimitedQuantityValidator(2),
                        EmptySquareValidator(),
                        if (color == Color.WHITE) OrientationValidator(false) else OrientationValidator(true),
                    )
                )
            )
        )

        return orRuleValidator
    }


    fun uploadPawnRule(ruleValidators: MutableMap<Piece, Validator>){
        for (i in 1..8) {
            ruleValidators[Piece(ChessPieceName.PAWN, Color.WHITE,i)] = savePawnRule(Color.WHITE)
            ruleValidators[Piece(ChessPieceName.PAWN, Color.BLACK,i+16)] = savePawnRule(Color.BLACK)
        }
    }

    fun bishopRule(ruleValidators: MutableMap<Piece, Validator>){
        val orRuleValidator = OrRuleValidator(
            listOf(
                AndRuleValidator(
                    listOf(
                        DiagonalOrientationValidator(),
                        DiagonalObstacleValidator(),
                    )
                )
            )
        )

        ruleValidators[Piece(ChessPieceName.BISHOP, Color.WHITE, 11)] = orRuleValidator
        ruleValidators[Piece(ChessPieceName.BISHOP, Color.WHITE, 14)] = orRuleValidator

        ruleValidators[Piece(ChessPieceName.BISHOP, Color.BLACK, 27)] = orRuleValidator
        ruleValidators[Piece(ChessPieceName.BISHOP, Color.BLACK, 30)] = orRuleValidator
    }

    fun uploadRookRule(ruleValidators: MutableMap<Piece, Validator>){
        val orRuleValidator = OrRuleValidator(
            listOf(
                AndRuleValidator(
                    listOf(
                        HorizontalOrientationValidator(),
                        HorizontalObstacleValidator(),
                    )
                )
                ,AndRuleValidator(
                    listOf(
                        VerticalOrientationValidator(),
                        VerticalObstacleValidator(),
                    )
                )
            )
        )

        ruleValidators[Piece(ChessPieceName.ROOK, Color.WHITE, 9)] = orRuleValidator
        ruleValidators[Piece(ChessPieceName.ROOK, Color.WHITE, 16)] = orRuleValidator
        ruleValidators[Piece(ChessPieceName.ROOK, Color.BLACK, 25)] = orRuleValidator
        ruleValidators[Piece(ChessPieceName.ROOK, Color.BLACK, 32)] = orRuleValidator

    }

    fun knightRule(ruleValidators: MutableMap<Piece, Validator>){
        val orRuleValidator = OrRuleValidator(
            listOf(
                AndRuleValidator(
                    listOf(
                        LOrientationValidator(),
                    )
                )
            )
        )

        ruleValidators[Piece(ChessPieceName.KNIGHT, Color.WHITE, 10)] = orRuleValidator
        ruleValidators[Piece(ChessPieceName.KNIGHT, Color.WHITE, 15)] = orRuleValidator
        ruleValidators[Piece(ChessPieceName.KNIGHT, Color.BLACK, 26)] = orRuleValidator
        ruleValidators[Piece(ChessPieceName.KNIGHT, Color.BLACK, 31)] = orRuleValidator
    }


    fun uploadPawnsKingsAndQueens(gameBoard:MutableMap<edu.austral.dissis.common.Position?, Piece?>){
        for (i in 1..8) {
            gameBoard[edu.austral.dissis.common.Position(i, 7)] = Piece(ChessPieceName.PAWN, Color.WHITE, i)
            gameBoard[edu.austral.dissis.common.Position(i, 2)] = Piece(ChessPieceName.PAWN, Color.BLACK, i+16)
        }
        gameBoard[edu.austral.dissis.common.Position(4, 8)] = Piece(ChessPieceName.QUEEN, Color.WHITE, 12)
        gameBoard[edu.austral.dissis.common.Position(5, 8)] = Piece(ChessPieceName.KING, Color.WHITE, 13)
        gameBoard[edu.austral.dissis.common.Position(4, 1)] = Piece(ChessPieceName.QUEEN, Color.BLACK, 28)
        gameBoard[edu.austral.dissis.common.Position(5, 1)] = Piece(ChessPieceName.KING, Color.BLACK, 29)

    }

    fun uploadClassicPieces(gameBoard:MutableMap<edu.austral.dissis.common.Position?, Piece?>){
        //The white and black pawns
        for (i in 1..8) {
            gameBoard[edu.austral.dissis.common.Position(i, 7)] = Piece(ChessPieceName.PAWN, Color.WHITE, i)
            gameBoard[edu.austral.dissis.common.Position(i, 2)] = Piece(ChessPieceName.PAWN, Color.BLACK, i+16)
        }
        gameBoard[edu.austral.dissis.common.Position(1, 8)] = Piece(ChessPieceName.ROOK, Color.WHITE, 9)
        gameBoard[edu.austral.dissis.common.Position(2, 8)] = Piece(ChessPieceName.KNIGHT, Color.WHITE, 10)
        gameBoard[edu.austral.dissis.common.Position(3, 8)] = Piece(ChessPieceName.BISHOP, Color.WHITE, 11)
        gameBoard[edu.austral.dissis.common.Position(4, 8)] = Piece(ChessPieceName.QUEEN, Color.WHITE, 12)
        gameBoard[edu.austral.dissis.common.Position(5, 8)] = Piece(ChessPieceName.KING, Color.WHITE, 13)
        gameBoard[edu.austral.dissis.common.Position(6, 8)] = Piece(ChessPieceName.BISHOP, Color.WHITE, 14)
        gameBoard[edu.austral.dissis.common.Position(7, 8)] = Piece(ChessPieceName.KNIGHT, Color.WHITE, 15)
        gameBoard[edu.austral.dissis.common.Position(8, 8)] = Piece(ChessPieceName.ROOK, Color.WHITE, 16)
        gameBoard[edu.austral.dissis.common.Position(1, 1)] = Piece(ChessPieceName.ROOK, Color.BLACK, 25)
        gameBoard[edu.austral.dissis.common.Position(2, 1)] = Piece(ChessPieceName.KNIGHT, Color.BLACK, 26)
        gameBoard[edu.austral.dissis.common.Position(3, 1)] = Piece(ChessPieceName.BISHOP, Color.BLACK, 27)
        gameBoard[edu.austral.dissis.common.Position(4, 1)] = Piece(ChessPieceName.QUEEN, Color.BLACK, 28)
        gameBoard[edu.austral.dissis.common.Position(5, 1)] = Piece(ChessPieceName.KING, Color.BLACK, 29)
        gameBoard[edu.austral.dissis.common.Position(6, 1)] = Piece(ChessPieceName.BISHOP, Color.BLACK, 30)
        gameBoard[edu.austral.dissis.common.Position(7, 1)] = Piece(ChessPieceName.KNIGHT, Color.BLACK, 31)
        gameBoard[edu.austral.dissis.common.Position(8, 1)] = Piece(ChessPieceName.ROOK, Color.BLACK, 32)
    }

}