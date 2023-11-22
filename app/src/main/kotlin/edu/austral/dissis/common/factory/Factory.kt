package edu.austral.dissis.common.factory

import edu.austral.dissis.chess.entities.ChessPieceName
import edu.austral.dissis.common.Color
import edu.austral.dissis.common.Piece
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.validators.*

fun kingRule():Validator{
    return OrRuleValidator(
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
        )
    )

}