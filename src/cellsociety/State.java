package cellsociety;

import java.util.HashMap;
import java.util.Map;

public enum State {

  DEAD, ALIVE,                  // Game of life
  UNFILLED, FILLED, OBSTACLE,   // Percolation
  ROCK, PAPER, SCISSORS,        // Rock, paper, scissors
  EMPTY, TREE, BURNING;         // Spreading of fire

  private static final Map<String, State[]> simulationStates = new HashMap<>();

  static {
    simulationStates.put("GameOfLife", new State[]{State.DEAD, State.ALIVE});
    simulationStates.put("Percolation", new State[]{State.UNFILLED, State.FILLED, State.OBSTACLE});
    simulationStates.put("RPS", new State[]{State.ROCK, State.PAPER, State.SCISSORS});
    simulationStates.put("SpreadFire", new State[]{State.EMPTY, State.TREE, State.BURNING});
//    simulationStates.put("PredatorPrey", new State[]{});
  }

  public static State getState(String simulation, int stateValue) {
    return simulationStates.get(simulation)[stateValue];
  }

}
