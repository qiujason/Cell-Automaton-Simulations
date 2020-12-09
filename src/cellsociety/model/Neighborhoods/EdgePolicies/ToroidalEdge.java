package cellsociety.model.Neighborhoods.EdgePolicies;

import cellsociety.model.Cells.Cell;
import java.util.List;

/**
 * Class that implements edge policy such that both horizontal and vertical edges wrap around.
 *
 * @author Jason Qiu
 */
public class ToroidalEdge extends Edge {

  /**
   * instantiates a toroidal edge policy
   * @param cells List of list of cells that represents grid of cells
   */
  public ToroidalEdge(List<List<Cell>> cells) {
    super(cells);
  }

  /**
   * returns row that is either the bottom row if the row is at the top, the top row if it is at the
   * bottom, or the same row
   * @param row int representing the current row
   * @param lastRow int representing the last row
   * @return int representing the row depending on the rules
   */
  @Override
  protected int getRowBasedOnEdge(int row, int lastRow) {
    if (row == -1) {
      return lastRow - 1;
    } else if (row == lastRow) {
      return 0;
    }
    return row;
  }

  /**
   * returns column that is either the left column if the column is at the right, the right column if it is at the
   * left, or the same column
   * @param col int representing the current column
   * @param lastCol int representing the last column
   * @return int representing the column depending on the rules
   */
  @Override
  protected int getColBasedOnEdge(int col, int lastCol) {
    if (col == -1) {
      return lastCol - 1;
    } else if (col == lastCol) {
      return 0;
    }
    return col;
  }

}
