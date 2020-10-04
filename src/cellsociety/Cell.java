package cellsociety;

public class Cell {
  private int myValue; // store some basic info about the cell
  private Neighborhood myNeighbors;

  public Cell(int value){
    myValue = value;
  }

  public void updateCell(){
    myValue = Math.abs(myValue-1);
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
