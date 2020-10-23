package cellsociety.model.Cells.SpreadingFire;

import cellsociety.model.Cells.Cell;
import java.util.Map;
import java.util.Random;

/**
 * Cell class representing the Wa-tor predator and prey simulation
 * @author Jason Qiu
 */
public class SpreadingFireCell extends Cell {

  private static final Random random = new Random();

  private final double catchFireProbability;

  /**
   *
   * @param state
   * @param optional
   */
  public SpreadingFireCell(Enum<?> state, Map optional) {
    super(state);
    catchFireProbability = Double.parseDouble((String) optional.get("probCatch"));
  }

  @Override
  public void setNewState() {
    if (myState == SpreadingFireStates.TREE) {
      if (myNeighbors.getNeighborsWithState(SpreadingFireStates.BURNING).size() > 0 &&
          random.nextDouble() < catchFireProbability) {
        nextState = SpreadingFireStates.BURNING;
      } else {
        nextState = SpreadingFireStates.TREE;
      }
    } else {
      nextState = SpreadingFireStates.EMPTY;
    }
  }

  public static void setRandomSeed(long seed) {
    random.setSeed(seed);
  }

}
