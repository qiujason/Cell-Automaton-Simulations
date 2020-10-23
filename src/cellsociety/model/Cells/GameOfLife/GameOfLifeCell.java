package cellsociety.model.Cells.GameOfLife;

import cellsociety.model.Cells.Cell;
import java.util.Map;

public class GameOfLifeCell extends Cell {

  public GameOfLifeCell(Enum<?> state, Map optional) {
    super(state);
  }

  @Override
  public void setNewState() {
    switch (myNeighbors.getNeighborsWithState(GameOfLifeStates.ALIVE).size()) {
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
