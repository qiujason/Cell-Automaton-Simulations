package cellsociety.model.Cells.SpreadingFire;

import cellsociety.model.Cells.Cell;
import java.util.Map;
import java.util.Random;

/**
 * Cell class representing the Wa-tor predator and prey simulation
 *
 * @author Jason Qiu
 */
public class SpreadingFireCell extends Cell {

  private static final Random random = new Random();

  private final double catchFireProbability;

  /**
   * instantiates a spreading fire cell
   *
   * @param state    enum representing the initial state of the cell
   * @param optional map containing the optional parameters of the simulation
   */
  public SpreadingFireCell(Enum<?> state, Map optional) {
    super(state);
    catchFireProbability = Double.parseDouble((String) optional.get("probCatch"));
  }

  /**
   * describes the rules for getting the next state of the cell
   */
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

  /**
   * testing purposes: sets a random seed for the Random class
   *
   * @param seed long representing the seed
   */
  public static void setRandomSeed(long seed) {
    random.setSeed(seed);
  }

}
