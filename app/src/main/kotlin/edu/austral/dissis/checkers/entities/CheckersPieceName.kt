package edu.austral.dissis.checkers.entities

import edu.austral.dissis.common.Name

enum class CheckersPieceName:Name {
    PAWN{
        override fun getName():String{
            return "pawn";
        }
    },
    KING{
        override fun getName():String{
            return "king";
        }
    }
}