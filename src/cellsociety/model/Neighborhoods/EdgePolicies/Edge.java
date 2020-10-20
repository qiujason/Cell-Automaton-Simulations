package cellsociety.model.Neighborhoods.EdgePolicies;

import cellsociety.model.Cells.Cell;
import java.util.List;

public abstract class Edge {

  private static final int NEIGHBORHOOD_LENGTH = 3; // size of row and col
  private final List<List<Cell>> cells;

  public Edge(List<List<Cell>> cells) {
    this.cells = cells;
  }

  public Cell[][] determineNeighbors(int cellRow, int cellCol) {
    Cell[][] neighborhood = new Cell[NEIGHBORHOOD_LENGTH][NEIGHBORHOOD_LENGTH];
    for (int i = cellRow - 1; i <= cellRow + 1; i++) {
      if (i >= -1 && i <= cells.size()) {
        int gridRow = getRowBasedOnEdge(i, cells.size());
        if (gridRow < 0 || gridRow >= cells.size()) {
          continue;
        }
        for (int j = cellCol - 1; j <= cellCol + 1; j++) {
          if (j >= -1 && j <= cells.get(0).size()) {
            int gridCol = getColBasedOnEdge(j, cells.get(0).size());
            if (gridCol < 0 || gridCol >= cells.get(0).size()) {
              continue;
            }
            neighborhood[i - (cellRow - 1)][j - (cellCol - 1)] = cells.get(gridRow).get(gridCol);
          }
        }
      }
    }
    return neighborhood;
  }

  abstract int getRowBasedOnEdge(int row, int lastRow);

  abstract int getColBasedOnEdge(int col, int lastCol);


}
