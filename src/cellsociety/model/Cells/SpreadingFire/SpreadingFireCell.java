package cellsociety.model.Cells.SpreadingFire;

import cellsociety.model.Cells.Cell;
import java.util.Map;
import java.util.Random;

public class SpreadingFireCell extends Cell {

  private static final Random random = new Random();

  private final double catchFireProbability;

  public SpreadingFireCell(Enum<?> state, Map optional) {
    super(state);
    catchFireProbability = (double) optional.get("probCatch");
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
