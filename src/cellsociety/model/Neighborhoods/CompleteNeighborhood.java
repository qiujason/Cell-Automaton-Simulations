package cellsociety.model.Neighborhoods;

import cellsociety.configuration.Grid;
import cellsociety.model.Cells.Cell;

/**
 * complete neighborhood class representing a complete neighborhood where all neighbors are counted
 *
 * @author Jason Qiu
 */
public class CompleteNeighborhood extends Neighborhood {

  /**
   * instantiates a new complete neighborhood
   * @param cell Cell to build a neighborhood around
   * @param grid Grid containing all cells
   * @param edgePolicy String describing the type of edge policy
   */
  public CompleteNeighborhood(Cell cell, Grid grid, String edgePolicy) {
    super(cell, grid, edgePolicy, 8);
    setActiveNeighbors(new boolean[][]{
        {true, true, true},
        {true, false, true},
        {true, true, true}
    });
  }
}
