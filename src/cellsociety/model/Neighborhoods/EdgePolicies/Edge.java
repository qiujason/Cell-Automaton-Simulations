package cellsociety.model.Neighborhoods.EdgePolicies;

import cellsociety.model.Cells.Cell;
import java.util.List;

/**
 * This class contains implementation for edge policies. It determines the neighbors of a neighborhood
 * and subclasses account for rules that determine what cells to put in the neighborhood on its edges.
 *
 * @author Jason Qiu and Hayden Lau
 */
public abstract class Edge {

  private static final int NEIGHBORHOOD_LENGTH = 3; // size of row and col
  private final List<List<Cell>> cells;

  /**
   * instantiates an edge policy that determines neighbors
   * @param cells List of list of cells that represent the grid of cells
   */
  public Edge(List<List<Cell>> cells) {
    this.cells = cells;
  }

  /**
   * creates a 2D array of neighboring cells depending on the edge policy
   * @param cellRow int describing the row of the cell
   * @param cellCol int describing the column of the cell
   * @return 2D array of Cells that contains the cell's neighbors
   */
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

  /**
   * method to be implemented by subclasses that determines what row location to get for its neighbor above/below
   * @param row int representing the current row
   * @param lastRow int representing the last row
   * @return int describing the row to grab the neighbor from (an edge)
   */
  abstract int getRowBasedOnEdge(int row, int lastRow);

  /**
   * method to be implemented by subclasses that determines what column location to get for its neighbor above/below
   * @param col int representing the current column
   * @param lastCol int representing the last column
   * @return int describing the column to grab the neighbor from (an edge)
   */
  abstract int getColBasedOnEdge(int col, int lastCol);


}
