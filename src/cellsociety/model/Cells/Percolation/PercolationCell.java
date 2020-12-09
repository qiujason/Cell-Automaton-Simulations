package cellsociety.model.Cells.Percolation;

import cellsociety.model.Cells.Cell;
import java.util.Map;

/**
 * Cell class representing the percolation simulation
 *
 * @author Jason Qiu
 */
public class PercolationCell extends Cell {

  /**
   * instantiates a percolation cell
   *
   * @param state    enum representing the initial state of the cell
   * @param optional map containing the optional parameters of the simulation
   */
  public PercolationCell(Enum<?> state, Map optional) {
    super(state);
  }

  /**
   * describes the rules for getting the next state of the cell
   */
  @Override
  public void setNewState() {
    if (myState == PercolationStates.UNFILLED) {
      if (myNeighbors.getNeighborsWithState(PercolationStates.FILLED).size() > 0) {
        nextState = PercolationStates.FILLED;
      } else {
        nextState = PercolationStates.UNFILLED;
      }
    }
  }

}
