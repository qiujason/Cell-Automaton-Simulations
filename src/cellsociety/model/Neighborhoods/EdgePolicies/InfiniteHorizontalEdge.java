package cellsociety.model.Neighborhoods.EdgePolicies;

import cellsociety.model.Cells.Cell;
import java.util.List;

public class InfiniteHorizontalEdge extends ToroidalEdge {

  public InfiniteHorizontalEdge(List<List<Cell>> cells) {
    super(cells);
  }

  @Override
  protected int getRowBasedOnEdge(int row, int lastRow) {
    return row;
  }

}
