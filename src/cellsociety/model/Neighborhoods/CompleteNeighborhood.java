package cellsociety.model.Neighborhoods;

import cellsociety.configuration.Grid;
import cellsociety.model.Cells.Cell;

public class CompleteNeighborhood extends Neighborhood {

  public CompleteNeighborhood(Cell cell, Grid grid) {
    super(cell, grid, 8);
    setActiveNeighbors(new boolean[][] {
        {true, true, true},
        {true, false, true},
        {true, true, true}
    });
  }
}
