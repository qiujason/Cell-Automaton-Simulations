package cellsociety.model.cellmodel;

import cellsociety.State;
import cellsociety.configuration.Grid;
import cellsociety.model.Cell;
import cellsociety.model.Neighborhood;
import java.util.ResourceBundle;

public class SpreadingFireCell implements Cell {

  private static final ResourceBundle MODEL_VALUES = ResourceBundle.getBundle("ModelValues");

  private State myState;
  private Neighborhood myNeighbors;

  public SpreadingFireCell(State state) {
    myState = state;
  }

  @Override
  public void updateCell(State state) {
    myState = state;
  }

  @Override
  public State getNewState() {
    if (myState == State.TREE) {
      if (myNeighbors.getNumberOfNeighborsWithState(State.BURNING, true) > 0 &&
          Math.random() < Integer.parseInt(MODEL_VALUES.getString("CatchFireProbability"))) {
        return State.BURNING;
      }
      return State.TREE;
    }
    return State.EMPTY;
  }

  @Override
  public State getMyState() {
    return myState;
  }

  @Override
  public void setMyNeighbors(Grid grid) {
    myNeighbors = new Neighborhood(this, grid);
  }

  @Override
  public Neighborhood getMyNeighbors() {
    return myNeighbors;
  }

}
