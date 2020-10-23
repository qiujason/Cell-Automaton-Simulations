package cellsociety.model.Neighborhoods;

import cellsociety.configuration.Grid;
import cellsociety.model.Cells.Cell;

/**
 * ordinal neighborhood class representing a ordinal neighborhood where only neighbors without
 * connecting edges ard counted
 *
 * @author Jason Qiu
 */
public class OrdinalNeighborhood extends Neighborhood {

  /**
   * instantiates a new ordinal neighborhood
   * @param cell Cell to build a neighborhood around
   * @param grid Grid containing all cells
   * @param edgePolicy String describing the type of edge policy
   */
  public OrdinalNeighborhood(Cell cell, Grid grid, String edgePolicy) {
    super(cell, grid, edgePolicy, 4);
    setActiveNeighbors(new boolean[][]{
        {true, false, true},
        {false, false, false},
        {true, false, true}
    });
  }
}
