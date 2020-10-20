package cellsociety.model.Neighborhoods;

import cellsociety.configuration.Grid;
import cellsociety.model.Cells.Cell;

public class CardinalNeighborhood extends Neighborhood {

  public CardinalNeighborhood(Cell cell, Grid grid, String edgePolicy) {
    super(cell, grid, edgePolicy,4);
    setActiveNeighbors(new boolean[][] {
        {false, true, false},
        {true, false, true},
        {false, true, false}
    });
  }

}
