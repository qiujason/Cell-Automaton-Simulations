package cellsociety.model.cellmodel;

import cellsociety.State;
import cellsociety.configuration.Grid;
import cellsociety.model.Cell;
import cellsociety.model.Neighborhood;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class RPSCell implements Cell {

  private static final ResourceBundle MODEL_VALUES = ResourceBundle.getBundle("ModelValues");
  private static final Map<State, State> RPSWinDynamics = new HashMap<>();

  static {
    // (A,B): B beats A
    RPSWinDynamics.put(State.SCISSORS, State.ROCK);
    RPSWinDynamics.put(State.PAPER, State.SCISSORS);
    RPSWinDynamics.put(State.ROCK, State.PAPER);
  }

  private State myState;
  private Neighborhood myNeighbors;

  public RPSCell(State state) {
    myState = state;
  }

  @Override
  public void updateCell(State state) {
    myState = state;
  }

  @Override
  public State getNewState() {
    int threshold = Integer.parseInt(MODEL_VALUES.getString("RPSWinThreshold"));
    if (myNeighbors.getNumberOfNeighborsWithState(RPSWinDynamics.get(myState), false) > threshold) {
      return RPSWinDynamics.get(myState);
    }
    return myState;
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

