package cellsociety.model.Neighborhoods.EdgePolicies;

import cellsociety.model.Cells.Cell;
import java.util.List;

/**
 * Class that implements edge policy such that edges do not wrap around.
 *
 * @author Jason Qiu
 */
public class FiniteEdge extends Edge {

  /**
   * instantiates a finite edge policy
   * @param cells List of list of cells that represents grid of cells
   */
  public FiniteEdge(List<List<Cell>> cells) {
    super(cells);
  }

  /**
   * returns same row
   * @param row int representing the current row
   * @param lastRow int representing the last row
   * @return int that is the same row as passed in
   */
  @Override
  protected int getRowBasedOnEdge(int row, int lastRow) {
    return row;
  }

  /**
   * returns same column
   * @param col int representing the current column
   * @param lastCol int representing the last column
   * @return int that is the same column as passed in
   */
  @Override
  protected int getColBasedOnEdge(int col, int lastCol) {
    return col;
  }

}
