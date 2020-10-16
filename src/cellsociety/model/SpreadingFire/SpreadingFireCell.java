package cellsociety.model.SpreadingFire;

import cellsociety.configuration.PropertyReader;
import cellsociety.model.Cell;
import java.util.Random;

public class SpreadingFireCell extends Cell {

  private final double catchFireProbability;
  private static final Random random = new Random();

  public SpreadingFireCell(Enum<?> state) {
    super(state);
    catchFireProbability = PropertyReader.getOptionalProperty("probCatch");
  }

  @Override
  public void setNewState() {
    if (myState == SpreadingFireStates.TREE) {
      if (myNeighbors.getNumberOfNeighborsWithState(SpreadingFireStates.BURNING, true) > 0 &&
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
