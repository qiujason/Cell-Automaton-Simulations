package cellsociety.model.RPS;

import cellsociety.model.Cell;
import java.util.HashMap;
import java.util.Map;

public class RPSCell extends Cell {

  private static final Map<RPSStates, RPSStates> RPSWinDynamics = new HashMap<>();

  static {
    // (A,B): B beats A
    RPSWinDynamics.put(RPSStates.SCISSORS, RPSStates.ROCK);
    RPSWinDynamics.put(RPSStates.PAPER, RPSStates.SCISSORS);
    RPSWinDynamics.put(RPSStates.ROCK, RPSStates.PAPER);
  }

  private double winThreshold;

  public RPSCell(Enum<?> state, Map... optional) {
    super(state);
    this.winThreshold = (double) optional[0].get("winThreshold");
  }

  @Override
  public void setNewState() {
    if (myNeighbors.getNumberOfNeighborsWithState(RPSWinDynamics.get(myState), false) > winThreshold) {
      nextState = RPSWinDynamics.get(myState);
    } else {
      nextState = myState;
    }
  }

}

