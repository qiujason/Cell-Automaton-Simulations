package cellsociety.model.SpreadingFire;

import cellsociety.model.Cell;
import java.util.Map;

public class SpreadingFireCell extends Cell {

  private final double catchFireProbability;

  public SpreadingFireCell(Enum<?> state, Map... optional) {
    super(state);
    catchFireProbability = (double) optional[0].get("probCatch");
  }

  @Override
  public void setNewState() {
    if (myState == SpreadingFireStates.TREE) {
      if (myNeighbors.getNumberOfNeighborsWithState(SpreadingFireStates.BURNING, true) > 0 &&
          Math.random() < catchFireProbability) {
        nextState = SpreadingFireStates.BURNING;
      } else {
        nextState = SpreadingFireStates.TREE;
      }
    } else {
      nextState = SpreadingFireStates.EMPTY;
    }
  }

}
