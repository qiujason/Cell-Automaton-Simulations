package cellsociety.model.Neighborhoods;

import cellsociety.configuration.Grid;
import cellsociety.model.Cells.Cell;

/**
 * cardinal neighborhood class representing a carindal neighborhood where only neighbors with
 * connecting edges ard counted
 *
 * @author Jason Qiu
 */
public class CardinalNeighborhood extends Neighborhood {

  /**
   * instantiates a new cardinal neighborhood
   * @param cell Cell to build a neighborhood around
   * @param grid Grid containing all cells
   * @param edgePolicy String describing the type of edge policy
   */
  public CardinalNeighborhood(Cell cell, Grid grid, String edgePolicy) {
    super(cell, grid, edgePolicy, 4);
    setActiveNeighbors(new boolean[][]{
        {false, true, false},
        {true, false, true},
        {false, true, false}
    });
  }

}
