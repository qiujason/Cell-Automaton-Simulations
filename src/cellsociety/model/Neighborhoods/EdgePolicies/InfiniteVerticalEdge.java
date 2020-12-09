package cellsociety.model.Neighborhoods.EdgePolicies;

import cellsociety.model.Cells.Cell;
import java.util.List;

/**
 * Class that implements edge policy such that only vertical edges wrap around.
 * Inherits methods from toroidal edge policy and only keeps implementation for row determination
 * to simulate an infinite vertical edge.
 *
 * @author Jason Qiu
 */
public class InfiniteVerticalEdge extends ToroidalEdge {

  /**
   * instantiates a infinite vertical edge policy
   * @param cells List of list of cells that represents grid of cells
   */
  public InfiniteVerticalEdge(List<List<Cell>> cells) {
    super(cells);
  }

  /**
   * returns same column so it is finite horizontally
   * @param col int representing the current column
   * @param lastCol int representing the last column
   * @return int that is the same column as passed in
   */
  @Override
  protected int getColBasedOnEdge(int col, int lastCol) {
    return col;
  }

}
