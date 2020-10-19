package cellsociety.model.Neighborhoods;

import cellsociety.configuration.Grid;
import cellsociety.model.Cells.Cell;

public class OrdinalNeighborhood extends Neighborhood {

  public OrdinalNeighborhood(Cell cell, Grid grid) {
    super(cell, grid, 4);
    setActiveNeighbors(new boolean[][] {
        {true, false, true},
        {false, false, false},
        {true, false, true}
    });
  }
}
