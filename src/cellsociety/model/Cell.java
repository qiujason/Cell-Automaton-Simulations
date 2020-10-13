package cellsociety.model;

import cellsociety.configuration.Grid;

public abstract class Cell {

  protected Enum<?> myState;
  protected Enum<?> nextState;
  protected Neighborhood myNeighbors;

  public Cell(Enum<?> state) {
    myState = state;
    nextState = null;
  }
  public void updateCell() {
    if (nextState != null) {
      myState = nextState;
      nextState = null;
    }
  }

  public Enum<?> getMyState() {
    return myState;
  }

  public void setMyState(Enum<?> newState) { myState = newState; }

  public void setMyNeighbors(Grid grid) {
    myNeighbors = new Neighborhood(this, grid);
  }

  public Neighborhood getMyNeighbors() {
    return myNeighbors;
  }

  public abstract void setNewState();

}
