package edu.austral.dissis.common.interfaces

import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement

interface Validator{
    //Hay que ver si tiene sentido pasarle la lista de los moves a esto
    fun validateMovement(movement: Movement?, gameState: Game?): Boolean



}

