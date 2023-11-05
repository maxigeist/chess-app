package edu.austral.dissis.common.interfaces

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement

interface TurnManager {

    fun manageTurn(gameState: Game, movement: Movement){}
}