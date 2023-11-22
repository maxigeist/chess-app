package edu.austral.dissis.common.interfaces

import edu.austral.dissis.common.Board
import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement


//It is a complex movement because if the movement is valid, it provides a new board
interface ComplexMovement{


    fun validateMovement(movement: Movement, game: Game): Result



    fun newGameState(movement: Movement, game: Game): Game


}
