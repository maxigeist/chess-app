package edu.austral.dissis.utils

import edu.austral.dissis.checkers.Executioner
import edu.austral.dissis.checkers.complex_moves.PawnEating
import edu.austral.dissis.checkers.entities.CheckersPieceName
import edu.austral.dissis.chess.complex_moves.CastlingMove
import edu.austral.dissis.chess.complex_moves.PawnPromotion
import edu.austral.dissis.chess.entities.ChessPieceName
import edu.austral.dissis.chess.gui.ChessPiece
import edu.austral.dissis.chess.gui.PlayerColor
import edu.austral.dissis.chess.gui.Position
import edu.austral.dissis.chess.turn_manager.ClassicChessTurnManager
import edu.austral.dissis.chess.validators.endGameValidators.CheckMateValidator
import edu.austral.dissis.chess.validators.gameValidators.CheckValidator
import edu.austral.dissis.common.Board
import edu.austral.dissis.common.Color
import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Piece
import edu.austral.dissis.common.end_game_validators.NoPiecesValidator
import edu.austral.dissis.common.interfaces.ComplexMovement
import edu.austral.dissis.common.interfaces.EndGameValidator
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.validators.*


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


fun getColor(color: Color):PlayerColor{
    return if (color == Color.WHITE){
        PlayerColor.WHITE
    } else{
        PlayerColor.BLACK
    }
}

fun classicChess(): Game {
    val gameBoard: MutableMap<edu.austral.dissis.common.Position?, Piece?> = HashMap()
    val board = Board(8, 8, gameBoard)
    val gameValidators: MutableList<Validator> = ArrayList()
    val complexMoves: MutableList<ComplexMovement> = ArrayList()
    val normalTurnManager = ClassicChessTurnManager()
    val endGameValidators : MutableList<EndGameValidator> = ArrayList()
    val ruleValidators : MutableMap<Piece, Validator> = HashMap()
    val insideBoardValidator = InsideBoardValidator()
    val makeAMoveValidator = MakeAMoveValidator()
    val currentTurnValidator = CurrentTurnValidator()
    val checkValidator = CheckValidator()
    val checkMateValidator = CheckMateValidator()
    val pawnPromotion = PawnPromotion()
    val executioner = Executioner()
    val castling = CastlingMove()
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
    complexMoves.add(pawnPromotion)
    complexMoves.add(castling)
    return Game(ArrayList<Board>(),gameValidators, endGameValidators, board,executioner, ruleValidators, complexMoves,Color.WHITE, normalTurnManager )
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
            , AndRuleValidator(
                listOf(
                    HorizontalOrientationValidator(),
                    LimitedQuantityValidator(1),
                )
            ), AndRuleValidator(
                listOf(
                    VerticalOrientationValidator(),
                    LimitedQuantityValidator(1),
                )
            )
            , AndRuleValidator(
                listOf(
                    HorizontalOrientationValidator(),
                    FirstMoveValidator(),
                    LimitedQuantityValidator(2)
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
                    DiagonalObstacleValidator(false),
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
                    DiagonalObstacleValidator(false),
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
    val pawnPromotion = edu.austral.dissis.checkers.complex_moves.PawnPromotion()
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
    uploadClassicCheckersPieces(gameBoard)
    uploadCheckersPawnRule(ruleValidators)
    return Game(
        ArrayList<Board>(), gameValidators, endGameValidators, board, executioner,ruleValidators, complexMoves,
        Color.WHITE, normalTurnManager
    )
}


fun saveCheckersPawnRule(color: Color) :Validator{
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

fun uploadCheckersPawnRule(ruleValidators: MutableMap<Piece, Validator>){
    for (i in 1..12) {
        ruleValidators[Piece(ChessPieceName.PAWN, Color.WHITE, i)] = saveCheckersPawnRule(Color.WHITE)
        ruleValidators[Piece(ChessPieceName.PAWN, Color.BLACK, i +12)] = saveCheckersPawnRule(Color.BLACK)
    }
}

fun uploadClassicCheckersPieces(gameBoard: MutableMap<edu.austral.dissis.common.Position?, Piece?>) {
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
