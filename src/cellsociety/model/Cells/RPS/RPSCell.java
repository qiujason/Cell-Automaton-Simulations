package cellsociety.model.Cells.RPS;

import cellsociety.model.Cells.Cell;
import java.util.HashMap;
import java.util.Map;

/**
 * Cell class representing the rock, paper, scissor simulation
 *
 * @author Jason Qiu
 */
public class RPSCell extends Cell {

  private static final Map<RPSStates, RPSStates> RPSWinDynamics = new HashMap<>();

  static {
    // (A,B): B beats A
    RPSWinDynamics.put(RPSStates.SCISSORS, RPSStates.ROCK);
    RPSWinDynamics.put(RPSStates.PAPER, RPSStates.SCISSORS);
    RPSWinDynamics.put(RPSStates.ROCK, RPSStates.PAPER);
  }

  private final double winThreshold;

  /**
   * instantiates a RPS cell
   *
   * @param state    enum representing the initial state of the cell
   * @param optional map containing the optional parameters of the simulation
   */
  public RPSCell(Enum<?> state, Map optional) {
    super(state);
    this.winThreshold = Double.parseDouble((String) optional.get("winThreshold"));
  }

  /**
   * describes the rules for getting the next state of the cell
   */
  @Override
  public void setNewState() {
    if (myNeighbors.getNeighborsWithState(RPSWinDynamics.get(myState)).size() > winThreshold) {
      nextState = RPSWinDynamics.get(myState);
    } else {
      nextState = myState;
    }
  }

}

