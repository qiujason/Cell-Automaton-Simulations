package cellsociety.model.cellmodel;

import cellsociety.State;
import cellsociety.configuration.Grid;
import cellsociety.model.Cell;
import cellsociety.model.Neighborhood;

public class PercolationCell implements Cell {

  private State myState;
  private Neighborhood myNeighbors;

  public PercolationCell(State state) {
    myState = state;
  }

  @Override
  public void updateCell(State state) {
    myState = state;
  }

  @Override
  public State getNewState() {
    if (myNeighbors.getNumberOfNeighborsWithState(State.FILLED) > 0) {
      return State.FILLED;
    } else {
      return State.UNFILLED;
    }
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
