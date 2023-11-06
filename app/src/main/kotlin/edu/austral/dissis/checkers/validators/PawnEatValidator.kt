package edu.austral.dissis.checkers.validators


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