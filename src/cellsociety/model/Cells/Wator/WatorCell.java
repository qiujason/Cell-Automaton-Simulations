package cellsociety.model.Cells.Wator;

import cellsociety.model.Cells.Cell;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WatorCell extends Cell {

  private static final Random random = new Random();

  private final double thresholdForBirth;
  private int turnsWithoutBirth;

  public WatorCell(Enum<?> state, Map optional) {
    super(state);
    this.thresholdForBirth = Double.parseDouble((String) optional.get("thresholdForBirth"));
    turnsWithoutBirth = 0;
  }

  @Override
  public void setNewState() {
    WatorCell nextCell = this;
    if (myState == WatorStates.DEAD || myState == WatorStates.EMPTY) {
      nextState = WatorStates.EMPTY;
      return;
    } else if (myState == WatorStates.FISH) {
      nextCell = moveSelf();
    } else if (myState == WatorStates.SHARK) {
      if (killFish()) {
        nextState = myState;
      } else {
        nextCell = moveSelf();
      }
    }
    turnsWithoutBirth++;
    if (turnsWithoutBirth >= thresholdForBirth) {
      handleBirth(nextCell);
    }
  }

  protected void kill() {
    nextState = WatorStates.DEAD;
  }

  protected WatorCell getRandomAdjacentEmptyCell() {
    List<Cell> empty = myNeighbors.getNeighborsWithState(WatorStates.EMPTY);
    if (empty.size() > 0) {
      WatorCell emptyCell = null;
      while (empty.size() > 0) {
        emptyCell = (WatorCell) empty.get(new Random().nextInt(empty.size()));
        if (emptyCell.getNextState() == WatorStates.EMPTY || emptyCell.getNextState() == null) {
          break;
        }
        empty.remove(emptyCell);
        emptyCell = null;
      }
      return emptyCell;
    }
    return null;
  }

  private boolean killFish() {
    List<Cell> fish = myNeighbors.getNeighborsWithState(WatorStates.FISH);
    if (myNeighbors.getNeighborsWithState(WatorStates.FISH).size() > 0) {
      WatorCell deadFish = null;
      while (fish.size() > 0) {
        deadFish = (WatorCell) fish.get(new Random().nextInt(fish.size()));
        if (deadFish.getNextState() != WatorStates.DEAD) {
          break;
        }
        fish.remove(deadFish);
        deadFish = null;
      }
      if (deadFish != null) {
        deadFish.kill();
        return true;
      }
    }
    return false;
  }

  private WatorCell moveSelf() {
    WatorCell emptyCell = getRandomAdjacentEmptyCell();
    if (emptyCell != null) {
      emptyCell.setNextState(myState);
      nextState = WatorStates.EMPTY;
      return emptyCell;
    }
    nextState = myState;
    return this;
  }

  private void handleBirth(WatorCell nextCell) {
    Cell emptyCell = nextCell.getRandomAdjacentEmptyCell();
    if (emptyCell != null) {
      emptyCell.setNextState(myState);
    } else {
      nextState = myState;
    }
  }

  public static void setRandomSeed(long seed) {
    random.setSeed(seed);
  }

}
