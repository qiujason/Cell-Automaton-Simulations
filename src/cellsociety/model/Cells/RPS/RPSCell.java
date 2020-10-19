package cellsociety.model.Cells.RPS;

import cellsociety.model.Cells.Cell;
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

  private final double winThreshold;

  public RPSCell(Enum<?> state, Map optional) {
    super(state);
    this.winThreshold = (double) optional.get("winThreshold");
  }

  @Override
  public void setNewState() {
    if (myNeighbors.getNeighborsWithState(RPSWinDynamics.get(myState)).size() > winThreshold) {
      nextState = RPSWinDynamics.get(myState);
    } else {
      nextState = myState;
    }
  }

}

