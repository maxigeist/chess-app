package edu.austral.dissis.common

import edu.austral.dissis.chess.entities.ChessPieceName
import java.util.Objects

class Piece(private val chessPieceName: ChessPieceName, private val color: Color, private var id: Int) {

    fun getId(): Int {
        return id
    }

    fun getName(): ChessPieceName {
        return chessPieceName
    }


    fun getColor(): Color {
        return color
    }


    override fun toString(): String {
        return "Piece{" +
                "name='" + chessPieceName + '\'' +
                ", color='" + color + '\'' +
                '}'
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Piece) return false

        if (chessPieceName != other.chessPieceName) return false
        if (color != other.color) return false
        if(id != other.id) return false


        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(chessPieceName, color, id)
    }
}
