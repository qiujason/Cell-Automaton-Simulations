package cellsociety;


import java.util.List;

public class Neighborhood {
  private final int NEIGHBORHOOD_SIZE = 3; // size of row and col
  private Cell[][] myCells;
  private Cell mainCell;

  public Neighborhood(Cell cell, Grid grid){ // Builds a neighborhood for a given cell
    mainCell = cell;
    myCells = determineNeighbors(cell, grid);
  }

  private Cell[][] determineNeighbors(Cell cell, Grid grid) {
    Cell[][] ret = new Cell[NEIGHBORHOOD_SIZE][NEIGHBORHOOD_SIZE];
    List<List<Cell>> gridCells = grid.getMyCells();
    int cellRow = -1;
    int cellCol = -1;
    for(int i = 0; i< gridCells.size(); i++){
      for (int j = 0; j < gridCells.get(i).size(); j++){
        if(gridCells.get(i).get(j).equals(cell)){
          cellRow = i;
          cellCol = j;
        }
      }
    }
    if(cellRow==-1 || cellCol == -1){
      return null;
    }
    for(int i = cellRow-1; i<=cellRow+1; i++){
      if(i >= 0 && i < gridCells.size()){
        for(int j = cellCol-1; j<=cellCol+1; j++){
          if(j >= 0 && j < gridCells.get(i).size()){
            ret[i-(cellRow-1)][j-(cellCol-1)] = gridCells.get(i).get(j);
          }
        }
      }
    }
    return ret;
  }

  public Cell[][] getMyCells() {
    return myCells;
  }

  public int getNumberOfLiveNeighbors() {
    int numberLive = 0;
    for (int r = 0; r < NEIGHBORHOOD_SIZE; r++) {
      for (int c = 0; c < NEIGHBORHOOD_SIZE; c++) {
        Cell neighbor = myCells[r][c];
        if (neighbor != null && neighbor != mainCell) { // skip cell if it is the main cell and not a neighbor
          numberLive += neighbor.getMyValue();
        }
      }
    }
    return numberLive;
  }
}
