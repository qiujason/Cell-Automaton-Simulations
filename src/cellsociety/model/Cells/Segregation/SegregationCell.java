package cellsociety.model.Cells.Segregation;

import cellsociety.model.Cells.Cell;
import java.util.Map;
import java.util.Random;

/**
 * Cell class representing the Segregation simulation
 *
 * @author Jason Qiu
 */
public class SegregationCell extends Cell {

  private static final Random random = new Random();

  private static int unsatisfiedA;
  private static int unsatisfiedB;
  private static int numEmptyCanBeMoved;

  private final double satisfiedThreshold;
  private SegregationStates satisfiedState;

  /**
   * instantiates a segregation cell
   *
   * @param state    enum representing the initial state of the cell
   * @param optional map containing the optional parameters of the simulation
   */
  public SegregationCell(Enum<?> state, Map optional) {
    super(state);
    this.satisfiedThreshold = Double.parseDouble((String) optional.get("satisfiedThreshold"));
    satisfiedState = SegregationStates.UNSATISFIED;
  }

  /**
   * describes the rules for getting the next state of the cell
   */
  @Override
  public void setNewState() {
    if (myState != SegregationStates.EMPTY) {
      double percentSimilar =
          (double) myNeighbors.getNeighborsWithState(myState).size() / myNeighbors
              .getNumActiveNeighbors();
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

  /**
   * updates cell's next state depending on current state and amount of states available to satisfy
   */
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

  /**
   * returns its satisfied state
   * @return SegregationState representing its satisfaction with its current position
   */
  public SegregationStates getSatisfiedState() {
    return satisfiedState;
  }

  /**
   * testing purposes: sets a random seed for the Random class
   *
   * @param seed long representing the seed
   */
  public static void setRandomSeed(long seed) {
    random.setSeed(seed);
  }

  /**
   * testing purposes: resets static variable unsatisfied A
   */
  public static void resetUnsatisfiedA() {
    unsatisfiedA = 0;
  }

  /**
   * testing purposes: resets static variable unsatisfied B
   */
  public static void resetUnsatisfiedB() {
    unsatisfiedB = 0;
  }

  /**
   * testing purposes: resets static variable number of empty that can be moved
   */
  public static void resetNumEmptyCanBeMoved() {
    numEmptyCanBeMoved = 0;
  }

  private void changeStateFromA() {
    int totalOtherState = unsatisfiedB + numEmptyCanBeMoved;
    double probForB = (double) unsatisfiedB / totalOtherState;
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
    double probForA = (double) unsatisfiedA / totalOtherState;
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
    double probForA = (double) unsatisfiedA / totalStatesToMove;
    double probForB = (double) unsatisfiedB / totalStatesToMove;
    double probability = random.nextDouble();
    if (probability < probForA) {
      myState = SegregationStates.A;
      unsatisfiedA--;
    } else if (probability < probForA + probForB) {
      myState = SegregationStates.B;
      unsatisfiedB--;
    } else {
      // remains empty
      numEmptyCanBeMoved--;
    }
  }

}
