package cellsociety.model;

import cellsociety.State;
import cellsociety.configuration.Grid;

public interface Cell {

  void updateCell(State state);

  State getNewState();

  State getMyState();

  void setMyNeighbors(Grid grid);

  Neighborhood getMyNeighbors();

}
