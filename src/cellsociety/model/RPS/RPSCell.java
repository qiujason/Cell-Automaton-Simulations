package cellsociety.model.RPS;

import cellsociety.configuration.PropertyReader;
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

  private final double winThreshold;

  public RPSCell(Enum<?> state, Map optional) {
    super(state);
    this.winThreshold = (double) optional.get("winThreshold");
  }

  @Override
  public void setNewState() {
    if (myNeighbors.getNumberOfNeighborsWithState(RPSWinDynamics.get(myState), false) > winThreshold) {
      System.out.println("hi");
      nextState = RPSWinDynamics.get(myState);
      System.out.println("FJDKLSFJD" + myState);

      System.out.println("AAAAAAAAA" + nextState);
    } else {
      nextState = myState;
    }
  }

}

