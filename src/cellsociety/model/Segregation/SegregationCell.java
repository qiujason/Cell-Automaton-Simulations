package cellsociety.model.Segregation;

import cellsociety.model.Cell;
import cellsociety.model.Neighborhood;

public class SegregationCell extends Cell {

  private final int satisfiedThreshold;
  private SegregationStates satisfiedState;

  public SegregationCell(Enum<?> state, int satisfiedThreshold) {
    super(state);
    this.satisfiedThreshold = satisfiedThreshold;
    satisfiedState = SegregationStates.UNSATISFIED;
  }

  @Override
  public void updateCell() {

  }

  @Override
  public void setNewState() {
    int percentSimilar = myNeighbors.getNumberOfNeighborsWithState(myState, false) /
        Neighborhood.NEIGHBORHOOD_SIZE;
    if (percentSimilar < satisfiedThreshold) {
      satisfiedState = SegregationStates.UNSATISFIED;
    } else {
      satisfiedState = SegregationStates.SATISFIED;
    }
  }
}
