package cellsociety.model.Neighborhoods.EdgePolicies;

import cellsociety.model.Cells.Cell;
import java.util.List;

/**
 * Class that implements edge policy such that only horizontal edges wrap around.
 * Inherits methods from toroidal edge policy and only keeps implementation for column determination
 * to simulate an infinite horizontal edge.
 *
 * @author Jason Qiu
 */
public class InfiniteHorizontalEdge extends ToroidalEdge {

  /**
   * instantiates a infinite horizontal edge policy
   * @param cells List of list of cells that represents grid of cells
   */
  public InfiniteHorizontalEdge(List<List<Cell>> cells) {
    super(cells);
  }

  /**
   * returns same row so it is finite vertically
   * @param row int representing the current row
   * @param lastRow int representing the last row
   * @return int that is the same row as passed in
   */
  @Override
  protected int getRowBasedOnEdge(int row, int lastRow) {
    return row;
  }

}
