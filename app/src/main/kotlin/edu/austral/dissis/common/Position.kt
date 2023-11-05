package edu.austral.dissis.common

import java.util.*

class  Position(private val xCoordinate: Int, private val yCoordinate: Int) {

    fun getXCoordinate(): Int {
        return xCoordinate
    }

    fun getYCoordinate(): Int {
        return yCoordinate
    }

    //I don't know if this two methods are correct but if not the hashmap does not compare
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val position = o as Position
        return xCoordinate == position.xCoordinate && yCoordinate == position.yCoordinate
    }

    override fun hashCode(): Int {
        return Objects.hash(xCoordinate, yCoordinate)
    }

    override fun toString(): String {
        return "Position{" +
                "x_coordinate=" + xCoordinate +
                ", y_coordinate=" + yCoordinate +
                '}'
    }
}