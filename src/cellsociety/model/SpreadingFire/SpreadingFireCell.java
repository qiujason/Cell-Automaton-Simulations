package cellsociety.model.SpreadingFire;

import cellsociety.model.Cell;

public class SpreadingFireCell extends Cell {

  private final double catchFireProbability;

  public SpreadingFireCell(SpreadingFireStates state, double probability) {
    super(state);
    catchFireProbability = probability;
  }

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
