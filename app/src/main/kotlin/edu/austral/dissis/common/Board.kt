package edu.austral.dissis.common


class Board(private val x_dimension: Int, private val y_dimension: Int, private val boardMap: Map<Position?, Piece?>) {

    fun getXDimension(): Int {
        return x_dimension
    }

    fun getYDimension(): Int {
        return y_dimension
    }

    fun display() {
        val positions = boardMap.keys
        for (pos in positions) {
            println(pos.toString() + " " + boardMap[pos].toString())
        }
    }

    fun getBoardMap(): Map<Position?, Piece?> {
        return boardMap
    }

    fun getInvertedBoardMap(): Map<Piece?, Position?>{
        val invertedBoardMap: MutableMap<Piece?, Position?> = HashMap()
        for (pos in boardMap.keys) {
            invertedBoardMap[boardMap[pos]] = pos
        }
        return invertedBoardMap
    }



    fun move(movement: Movement): Board {
        val auxBoard: MutableMap<Position?, Piece?> = HashMap(boardMap)
        val piece = auxBoard[movement.getFrom()]
        auxBoard.remove(movement.getFrom())
        auxBoard[movement.getTo()] = piece
        val newBoard: HashMap<Position?, Piece?> = HashMap(auxBoard)
        return Board(x_dimension, y_dimension, newBoard)
    }
}

