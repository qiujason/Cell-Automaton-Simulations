package cellsociety.model.Wator;

import cellsociety.model.Cell;
import java.util.List;
import java.util.Random;

public class WatorCell extends Cell {

  private double thresholdForBirth;
  private int turnsWithoutBirth;

  public WatorCell(Enum<?> state, double thresholdForBirth) {
    super(state);
    this.thresholdForBirth = thresholdForBirth;
    turnsWithoutBirth = 0;
  }

  @Override
  public void setNewState() {
    if (myState == WatorStates.FISH) {
      turnsWithoutBirth++;
      handleFish();
    } else if (myState == WatorStates.SHARK) {
      turnsWithoutBirth++;
      handleShark();
    } else { // empty or dead cell
      nextState = WatorStates.EMPTY;
    }
  }

  public void kill() {
    nextState = WatorStates.DEAD;
  }

  private void handleFish() {
    moveSelf();
    if (turnsWithoutBirth >= thresholdForBirth) {
      handleBirth();
    }
  }

  private void handleShark() {
    if (killFish()) {
      nextState = myState;
    } else {
      moveSelf();
    }
    if (turnsWithoutBirth >= thresholdForBirth) {
      handleBirth();
    }
  }

  private boolean killFish() {
    if (myNeighbors.getNumberOfNeighborsWithState(WatorStates.FISH, true) > 0) {
      List<Cell> fish = myNeighbors.getNeighborsWithState(WatorStates.FISH, true);
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

  private void moveSelf() {
    if (myNeighbors.getNumberOfNeighborsWithState(WatorStates.EMPTY, true) > 0) {
      List<Cell> empty = myNeighbors.getNeighborsWithState(WatorStates.EMPTY, true);
      WatorCell emptyCell = null;
      while (empty.size() > 0) {
        emptyCell = (WatorCell) empty.get(new Random().nextInt(empty.size()));
        if (emptyCell.getNextState() == WatorStates.EMPTY || emptyCell.getNextState() == null) {
          break;
        }
        empty.remove(emptyCell);
        emptyCell = null;
      }
      if (emptyCell != null) {
        emptyCell.setNextState(myState);
        nextState = WatorStates.EMPTY;
        return;
      }
    }
    nextState = myState;
  }

  private void handleBirth() {

  }

}
