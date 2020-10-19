package cellsociety.model.Neighborhoods;


import cellsociety.configuration.Grid;
import cellsociety.model.Cells.Cell;
import java.util.ArrayList;
import java.util.List;

public abstract class Neighborhood {

  private static final int NEIGHBORHOOD_LENGTH = 3; // size of row and col
  private final Cell[][] myCells;
  private final Cell mainCell;
  private final int neighborhoodSize;
  private boolean[][] activeNeighbors;

  public Neighborhood(Cell cell, Grid grid, int neighborhoodSize) { // Builds a neighborhood for a given cell
    mainCell = cell;
    myCells = determineNeighbors(grid);
    this.neighborhoodSize = neighborhoodSize;
    activeNeighbors = new boolean[NEIGHBORHOOD_LENGTH][NEIGHBORHOOD_LENGTH];
  }

  private Cell[][] determineNeighbors(Grid grid) {
    Cell[][] ret = new Cell[NEIGHBORHOOD_LENGTH][NEIGHBORHOOD_LENGTH];
    List<List<Cell>> gridCells = grid.getMyCells();
    int[] rowCol = getCellLocation(gridCells);
    int cellRow = rowCol[0];
    int cellCol = rowCol[1];
    if (cellRow == -1 || cellCol == -1) {
      return null;
    }
    for (int i = cellRow - 1; i <= cellRow + 1; i++) {
      if (i >= 0 && i < gridCells.size()) {
        for (int j = cellCol - 1; j <= cellCol + 1; j++) {
          if (j >= 0 && j < gridCells.get(i).size()) {
            ret[i - (cellRow - 1)][j - (cellCol - 1)] = gridCells.get(i).get(j);
          }
        }
      }
    }
    return ret;
  }

  public List<Cell> getNeighborsWithState(Enum<?> state) {
    List<Cell> cells = new ArrayList<>();
    for (int r = 0; r < NEIGHBORHOOD_LENGTH; r++) {
      for (int c = 0; c < NEIGHBORHOOD_LENGTH; c++) {
        Cell neighbor = myCells[r][c];
        if (neighbor != null && activeNeighbors[r][c]) {
          // skip cell if it is this cell and not a neighbor
          if (neighbor.getMyState() == state) {
            cells.add(neighbor);
          }
        }
      }
    }
    return cells;
  }

  public int getNumActiveNeighbors() {
    return neighborhoodSize - getNumberOfNullCells();
  }

  protected void setActiveNeighbors(boolean[][] activeNeighbors) {
    this.activeNeighbors = activeNeighbors;
  }

  private int[] getCellLocation(List<List<Cell>> gridCells) {
    for (int i = 0; i < gridCells.size(); i++) {
      for (int j = 0; j < gridCells.get(i).size(); j++) {
        if (gridCells.get(i).get(j).equals(mainCell)) {
          return new int[]{i, j};
        }
      }
    }
    return new int[]{-1, -1};
  }

  private int getNumberOfNullCells() {
    int numberNull = 0;
    for (Cell[] myCell : myCells) {
      for (Cell cell : myCell) {
        if (cell == null) {
          numberNull++;
        }
      }
    }
    return numberNull;
  }

}
