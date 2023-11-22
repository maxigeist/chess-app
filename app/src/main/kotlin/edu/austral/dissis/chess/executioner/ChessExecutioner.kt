package edu.austral.dissis.chess.executioner

import edu.austral.dissis.common.Board
import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.game_results.EndGameResult
import edu.austral.dissis.common.game_results.ValidGameResult
import edu.austral.dissis.common.interfaces.ComplexMovement
import edu.austral.dissis.common.interfaces.Executioner
import edu.austral.dissis.common.interfaces.GameResult
import edu.austral.dissis.common.results.ValidResult


//It has a list for when it first starts.
class ChessExecutioner(private val complexMovements:List<ComplexMovement>) : Executioner {

    fun getComplexMovements(): List<ComplexMovement> {
        return this.complexMovements
    }

    fun checkComplexMovement(movement: Movement, game: Game): Game {
        for (complexMovement in getComplexMovements()) {
            if (complexMovement.validateMovement(movement, game) is ValidResult) {
                return complexMovement.newGameState(movement, game)
            }
        }
        return game
    }
    override fun getNewGame(movement: Movement, game: Game): GameResult {
        var gameInstance = game
        for (complexMovement in game.getComplexMovement()){
            if (complexMovement.validateMovement(movement, game) is ValidResult) {
                gameInstance = complexMovement.newGameState(movement, game)
            }
        }
        if (gameInstance.checkEndGameValidators()){
            return EndGameResult()
        }
        return ValidGameResult(gameInstance)
    }

}