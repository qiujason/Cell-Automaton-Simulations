package cellsociety.model.Cells.Segregation;

import cellsociety.model.Cells.Cell;
import java.util.Map;
import java.util.Random;

public class SegregationCell extends Cell {

  private static final Random random = new Random();

  private static int unsatisfiedA;
  private static int unsatisfiedB;
  private static int numEmptyCanBeMoved;

  private final double satisfiedThreshold;
  private SegregationStates satisfiedState;

  public SegregationCell(Enum<?> state, Map optional) {
    super(state);
    this.satisfiedThreshold = (double) optional.get("satisfiedThreshold");
    satisfiedState = SegregationStates.UNSATISFIED;
  }

  @Override
  public void setNewState() {
    if (myState != SegregationStates.EMPTY) {
      double percentSimilar = (double)myNeighbors.getNeighborsWithState(myState).size() / myNeighbors.getNumActiveNeighbors();
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

  public static void resetUnsatisfiedA() {
    unsatisfiedA = 0;
  }

  public static void resetUnsatisfiedB() {
    unsatisfiedB = 0;
  }

  public static void resetNumEmptyCanBeMoved() {
    numEmptyCanBeMoved = 0;
  }

  private void changeStateFromA() {
    int totalOtherState = unsatisfiedB + numEmptyCanBeMoved;
    double probForB = (double)unsatisfiedB/totalOtherState;
    if (random.nextDouble() < probForB) {
      myState = SegregationStates.B;
      unsatisfiedB--;
    } else {
      if (numEmptyCanBeMoved > 0) {
        myState = SegregationStates.EMPTY;
        numEmptyCanBeMoved--;
      } else {
        unsatisfiedA--;
      }
    }
  }

  private void changeStateFromB() {
    int totalOtherState = unsatisfiedA + numEmptyCanBeMoved;
    double probForA = (double)unsatisfiedA/totalOtherState;
    if (random.nextDouble() < probForA) {
      myState = SegregationStates.A;
      unsatisfiedA--;
    } else {
      if (numEmptyCanBeMoved > 0) {
        myState = SegregationStates.EMPTY;
        numEmptyCanBeMoved--;
      } else {
        unsatisfiedB--;
      }
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
