package edu.austral.dissis.common.interfaces

import edu.austral.dissis.common.Board
import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement

interface Executioner {

    fun getNewGame(movement: Movement, game: Game): GameResult

}