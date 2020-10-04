package cellsociety;


import java.util.Arrays;
import java.util.List;

public class Neighborhood {
  private Cell[][] myCells;

  public Neighborhood(Cell cell, Grid grid){ // Builds a neighborhood for a given cell
    myCells = determineNeighbors(cell, grid);
  }

  private Cell[][] determineNeighbors(Cell cell, Grid grid) {
    Cell[][] ret = new Cell[3][3];
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

  public void updateCells(Grid grid){} // Performs calculations for a given neighborhood and updates a grid
  public void numberOfLiveCells(){}
}
