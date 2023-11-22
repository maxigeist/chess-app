package edu.austral.dissis.common

import edu.austral.dissis.chess.entities.ChessPieceName
import java.util.Objects

class Piece(private val pieceName: Name, private val color: Color, private var id: Int) {

    fun getId(): Int {
        return id
    }

    fun getName(): Name {
        return pieceName
    }


    fun getColor(): Color {
        return color
    }


    override fun toString(): String {
        return "Piece{" +
                "name='" + pieceName.getName() + '\'' +
                ", color='" + color + '\'' +
                '}'
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Piece) return false

        if (pieceName.getName() != other.pieceName.getName()) return false
        if (color != other.color) return false
        if(id != other.id) return false


        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(pieceName.getName(), color, id)
    }
}
