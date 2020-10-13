package cellsociety.model.SpreadingFire;

import cellsociety.configuration.PropertyReader;
import cellsociety.model.Cell;

public class SpreadingFireCell extends Cell {

  private final double catchFireProbability;

  public SpreadingFireCell(Enum<?> state) {
    super(state);
    catchFireProbability = PropertyReader.getOptionalProperty("probCatch");
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
