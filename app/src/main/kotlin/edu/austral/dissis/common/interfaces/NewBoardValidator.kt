package edu.austral.dissis.common.interfaces

import edu.austral.dissis.common.Board
import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement

interface NewBoardValidator{
    fun validateMovement(movement: Movement?, gameState: Game?): Board


}