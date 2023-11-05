package edu.austral.dissis.chess.entities

import edu.austral.dissis.common.Name

enum class ChessPieceName:Name {
    PAWN {
        override fun getName():String{
            return "pawn";
    }
    },
    ROOK{
        override fun getName():String{
            return "rook";
        }
    },
    BISHOP{
        override fun getName():String{
            return "bishop";
        }
    },
    KING{
        override fun getName():String{
            return "king";
        }
    },
    QUEEN{
        override fun getName():String{
            return "queen";
        }
    },
    KNIGHT{
        override fun getName():String{
            return "knight";
        }
    },
}