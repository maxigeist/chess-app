package edu.austral.dissis.common

data class Movement(private val from: Position, private val to: Position){

    fun getFrom(): Position {
        return from
    }
    fun getTo(): Position {
        return to
    }
}


