package cellsociety.model.Neighborhoods.EdgePolicies;

import cellsociety.model.Cells.Cell;
import java.util.List;

public class FiniteEdge extends Edge {

  public FiniteEdge(List<List<Cell>> cells) {
    super(cells);
  }

  @Override
  protected int getRowBasedOnEdge(int row, int lastRow) {
    return row;
  }

  @Override
  protected int getColBasedOnEdge(int col, int lastCol) {
    return col;
  }

}
