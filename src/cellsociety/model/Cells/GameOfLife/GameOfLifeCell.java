package cellsociety.model.Cells.GameOfLife;

import cellsociety.model.Cells.Cell;
import java.util.Map;

/**
 * Cell class representing the game of life simulation
 *
 * @author Jason Qiu
 */
public class GameOfLifeCell extends Cell {

  /**
   * instantiates a game of life cell
   *
   * @param state    enum representing the initial state of the cell
   * @param optional map containing the optional parameters of the simulation
   */
  public GameOfLifeCell(Enum<?> state, Map optional) {
    super(state);
  }

  /**
   * describes the rules for getting the next state of the cell
   */
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
