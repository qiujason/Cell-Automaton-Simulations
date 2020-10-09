package cellsociety.model.cellmodel;

import cellsociety.State;
import cellsociety.configuration.Grid;
import cellsociety.model.Cell;
import cellsociety.model.Neighborhood;

public class GameOfLifeCell implements Cell {

  private State myState;
  private Neighborhood myNeighbors;

  public GameOfLifeCell(State state) {
    myState = state;
  }

  @Override
  public void updateCell(State state) {
    myState = state;
  }

  @Override
  public State getNewState() {
    switch(myNeighbors.getNumberOfNeighborsWithState(State.ALIVE)) {
      case 2 -> {
        if (myState == State.ALIVE) {
          return State.ALIVE;
        }
      }
      case 3 -> {
        return State.ALIVE;
      }
    }
    return State.DEAD;
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
