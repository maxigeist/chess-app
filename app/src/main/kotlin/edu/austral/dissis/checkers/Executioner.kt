package edu.austral.dissis.checkers

import edu.austral.dissis.checkers.turn_manager.ClassicCheckersTurnManager
import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.game_results.EndGameResult
import edu.austral.dissis.common.game_results.ValidGameResult
import edu.austral.dissis.common.interfaces.Executioner
import edu.austral.dissis.common.interfaces.GameResult
import edu.austral.dissis.common.results.ValidResult

class Executioner: Executioner {
    override fun getNewGame(movement: Movement, game: Game): GameResult {
        var gameInstance = game
        for (complexMovement in game.getComplexMovement()){
            if (complexMovement.validateMovement(movement, gameInstance) is ValidResult) {
                gameInstance = complexMovement.newGameState(movement, gameInstance)
            }
        }
        if(gameInstance == game) {
            gameInstance = game.copy(moves = gameInstance.getMoves().toList() + gameInstance.getBoard(),
                board = gameInstance.getBoard().move(movement), currentTeam = gameInstance.opposite(), turnManager = ClassicCheckersTurnManager())
        }
        if (gameInstance.checkEndGameValidators()){
            return EndGameResult()
        }
        return ValidGameResult(gameInstance)
    }
}