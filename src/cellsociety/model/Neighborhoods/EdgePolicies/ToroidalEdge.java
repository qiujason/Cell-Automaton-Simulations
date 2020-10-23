package cellsociety.model.Neighborhoods.EdgePolicies;

import cellsociety.model.Cells.Cell;
import java.util.List;

public class ToroidalEdge extends Edge {

  public ToroidalEdge(List<List<Cell>> cells) {
    super(cells);
  }

  @Override
  protected int getRowBasedOnEdge(int row, int lastRow) {
    if (row == -1) {
      return lastRow - 1;
    } else if (row == lastRow) {
      return 0;
    }
    return row;
  }

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
