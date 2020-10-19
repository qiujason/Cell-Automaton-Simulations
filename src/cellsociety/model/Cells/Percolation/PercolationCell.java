package cellsociety.model.Cells.Percolation;

import cellsociety.model.Cells.Cell;
import java.util.Map;

public class PercolationCell extends Cell {

  public PercolationCell(Enum<?> state, Map optional) {
    super(state);
  }

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
