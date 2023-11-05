package edu.austral.dissis.checkers.validators

import edu.austral.dissis.common.Board
import edu.austral.dissis.common.Game
import edu.austral.dissis.common.Movement
import edu.austral.dissis.common.Position
import edu.austral.dissis.common.interfaces.NewBoardValidator
import edu.austral.dissis.common.validators.DiagonalOrientationValidator
import edu.austral.dissis.common.validators.EmptySquareValidator
import edu.austral.dissis.common.validators.EvenQuantityValidator


//class PawnEatValidator : NewBoardValidator{
//    override fun validateMovement(movement: Movement?, gameState: Game?): Board {
//
//
//
//    }
//
//
//
//    fun recursivePathChecker(movement: Movement?, gameState: Game?, positionsToDelete: List<Position>, initialPosition: Position): List<Position>{
//            if (movement?.getTo()?.equals(initialPosition)!!){
//                return positionsToDelete
//            }
//            else{
//                if (DiagonalOrientationValidator().validateMovement(movement, gameState) && EmptySquareValidator().validateMovement(movement, gameState)) {
//                    if (EvenQuantityValidator().validateMovement(movement, gameState)){
//                        val pos = Position(movement.getTo().getXCoordinate() -1 , movement.getTo().getYCoordinate() -1)
//                        val secPos = Position(movement.getTo().getXCoordinate() +1 , movement.getTo().getYCoordinate() -1)
//                        if (gameState?.getBoard()?.getBoardMap()?.get((pos))?.getColor() != gameState?.getCurrentTeam()){
//                            recursivePathChecker(Movement(movement.getTo(), Position(movement.getFrom().getXCoordinate()-2, movement.getFrom().getYCoordinate()-2)), gameState, )
//
//                        }
//
//                    }
//                }
//                return gameState?.getBoard()!!
//
//            }
//
//
//    }
//
//}