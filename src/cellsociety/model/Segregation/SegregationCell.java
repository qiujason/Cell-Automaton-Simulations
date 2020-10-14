package cellsociety.model.Segregation;

import cellsociety.configuration.PropertyReader;
import cellsociety.model.Cell;
import cellsociety.model.Neighborhood;
import java.util.Random;

public class SegregationCell extends Cell {

  public static int unsatisfiedA;
  public static int unsatisfiedB;
  public static int numEmptyCanBeMoved;

  private static final Random random = new Random();

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
      double totalNeighbors = Neighborhood.NEIGHBORHOOD_SIZE - myNeighbors.getNumberOfNullCells() - 1;
      double percentSimilar = myNeighbors.getNumberOfNeighborsWithState(myState, false) / totalNeighbors;
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

  public SegregationStates getSatisfiedState() {
    return satisfiedState;
  }

  public static void setRandomSeed(long seed) {
    random.setSeed(seed);
  }

  private void changeStateFromA() {
    int totalOtherState = unsatisfiedB + numEmptyCanBeMoved;
    double probForB = (double)unsatisfiedB/totalOtherState;
    if (random.nextDouble() < probForB) {
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
    if (random.nextDouble() < probForA) {
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
    double probability = random.nextDouble();
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
