package cellsociety.model.GameOfLife;

import cellsociety.model.Cell;

public class GameOfLifeCell extends Cell {

  public GameOfLifeCell(Enum<?> state) {
    super(state);
  }

  @Override
  public void setNewState() {
    switch(myNeighbors.getNumberOfNeighborsWithState(GameOfLifeStates.ALIVE, false)) {
      case 2 -> {
        if (myState == GameOfLifeStates.ALIVE) {
          nextState = GameOfLifeStates.ALIVE;
        }
      }
      case 3 -> nextState = GameOfLifeStates.ALIVE;
      default -> nextState = GameOfLifeStates.DEAD;
    }
  }

}
