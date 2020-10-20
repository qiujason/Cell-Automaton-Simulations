package cellsociety.model.Neighborhoods.EdgePolicies;

import cellsociety.model.Cells.Cell;
import java.util.List;

public class InfiniteVerticalEdge extends ToroidalEdge {

  public InfiniteVerticalEdge(List<List<Cell>> cells) {
    super(cells);
  }

  @Override
  protected int getColBasedOnEdge(int col, int lastCol) {
    return col;
  }

}
