package cellsociety.model.Segregation;

import cellsociety.configuration.PropertyReader;
import cellsociety.model.Cell;
import cellsociety.model.Neighborhood;
import java.util.Map;

public class SegregationCell extends Cell {

  public static int unsatisfiedA;
  public static int unsatisfiedB;
  public static int numEmptyCanBeMoved;

  private final double satisfiedThreshold;
  private SegregationStates satisfiedState;

  public SegregationCell(Enum<?> state) {
    super(state);
    this.satisfiedThreshold = PropertyReader.getOptionalProperty("satisfiedThreshold");
    satisfiedState = SegregationStates.UNSATISFIED;
  }

  @Override
  public void setNewState() {
    if (myState != SegregationStates.EMPTY) {
      int percentSimilar = myNeighbors.getNumberOfNeighborsWithState(myState, false) /
          Neighborhood.NEIGHBORHOOD_SIZE;
      if (percentSimilar < satisfiedThreshold) {
        satisfiedState = SegregationStates.UNSATISFIED;
        if (myState == SegregationStates.A) {
          unsatisfiedA++;
        } else if (myState == SegregationStates.B) {
          unsatisfiedB++;
        }
      } else {
        satisfiedState = SegregationStates.SATISFIED;
      }
    } else {
      numEmptyCanBeMoved++;
    }
  }

  @Override
  public void updateCell() {
    if (satisfiedState == SegregationStates.UNSATISFIED) {
      if (myState == SegregationStates.A) {
        changeStateFromA();
      } else if (myState == SegregationStates.B) {
        changeStateFromB();
      } else {
        changeStateFromEmpty();
      }
    }
  }

  private void changeStateFromA() {
    int totalOtherState = unsatisfiedB + numEmptyCanBeMoved;
    double probForB = (double)unsatisfiedB/totalOtherState;
    if (Math.random() < probForB) {
      myState = SegregationStates.B;
      unsatisfiedB--;
    } else {
      myState = SegregationStates.EMPTY;
      numEmptyCanBeMoved--;
    }
  }

  private void changeStateFromB() {
    int totalOtherState = unsatisfiedA + numEmptyCanBeMoved;
    double probForA = (double)unsatisfiedA/totalOtherState;
    if (Math.random() < probForA) {
      myState = SegregationStates.A;
      unsatisfiedA--;
    } else {
      myState = SegregationStates.EMPTY;
      numEmptyCanBeMoved--;
    }
  }

  private void changeStateFromEmpty() {
    // can remain empty
    int totalStatesToMove = unsatisfiedA + unsatisfiedB + numEmptyCanBeMoved;
    double probForA = (double)unsatisfiedA/totalStatesToMove;
    double probForB = (double)unsatisfiedB/totalStatesToMove;
    double probability = Math.random();
    if (probability < probForA) {
      myState = SegregationStates.A;
      unsatisfiedA--;
    } else if (probability < probForA + probForB){
      myState = SegregationStates.B;
      unsatisfiedB--;
    } else {
      // remains empty
      numEmptyCanBeMoved--;
    }
  }

}
