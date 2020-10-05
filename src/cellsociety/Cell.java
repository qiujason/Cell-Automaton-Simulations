package cellsociety;

public class Cell {
  private int myValue; // store some basic info about the cell
  private Neighborhood myNeighbors;

  public Cell(int value){
    myValue = value;
  }

  public void updateCell(int state){
    myValue = state;
  }

  public int getNewState() {
    switch(myNeighbors.getNumberOfLiveNeighbors()) {
      case 2 -> {
        if (myValue == 1) {
          return 1;
        }
      }
      case 3 -> {
        return 1;
      }
    }
    return 0;
  }

  public int getMyValue(){
    return myValue;
  }

  public void setMyNeighbors(Grid grid) {
    myNeighbors = new Neighborhood(this, grid);
  }

  public Neighborhood getMyNeighbors() {
    return myNeighbors;
  }
}
