package edu.austral.dissis.common.interfaces

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement

interface TurnManager {

    fun newGame(): Game

    fun returnTurnManager() : TurnManager

    fun validateMovement(gameState: Game, movement: Movement):Boolean

    fun getHasEaten(): Boolean



}