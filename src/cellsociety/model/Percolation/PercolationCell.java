package cellsociety.model.Percolation;

import cellsociety.model.Cell;
import java.util.Map;

public class PercolationCell extends Cell {

  public PercolationCell(Enum<?> state, Map... optional) {
    super(state);
  }

  @Override
  public void setNewState() {
    if (myState == PercolationStates.UNFILLED) {
      if (myNeighbors.getNumberOfNeighborsWithState(PercolationStates.FILLED, false) > 0) {
        nextState = PercolationStates.FILLED;
      } else {
        nextState = PercolationStates.UNFILLED;
      }
    }
  }

}
