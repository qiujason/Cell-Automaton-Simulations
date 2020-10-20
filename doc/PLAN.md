# Simulation Design Plan
### 7
### Jack Ellwood - jce22, Hayden Lau - hpl5, Jason Qiu - jq39


## Design Overview

Our main classes will include a `Simulation`, `Cell`, `Grid`, and `Neighborhood` classes. `Simulation`
will be used to visualize the actual visualization.  `Cell` serve as an object for each cell in the
visualization. `Grid` will configure the locations of cells by storing their locations in a 2D array and
updating itself on each frame. `Neighborhood` will be used to assess the neighbors of a cell and update
the cell based on its neighbors.

```java
import javafx.application.Application;

public class Simulation extends Application{
  public Grid myGrid;

  public void start(Stage stage);
  Scene setupScene();
  void step(double elapsedTime);
}
```

```java
public class Cell {
  private int myValue; // store some basic info about the cell
  private Neighborhood myNeighbors;
  
  public Cell(int value, Grid grid); // Might need a grid to initialize its neighbors
  public void updateCell(); // Use information about my neighbors to update myself
  public int getMyValue();

}
```

```java
public class Grid {
  private List<List<Cell>> myCells;

  public Grid(Path cellFile); // This constructor should take in a path to the csv file, doesn't necessarily have to be a String
}
```

```java
public class Neighborhood {
  private Cell[][] myCells;

  public Neighborhood(Cell cell, Grid grid); // Builds a neighborhood for a given cell
  public updateCells(Grid grid); // Performs calculations for a given neighborhood and updates a grid
  public numberOfLiveCells();

}
```

## Design Details


 * Live cell with fewer than two live neighbors dies.
 ```java
    if(getMyValue()==1){
      if(myNeighbors.numberOfLiveCells() < 2){
        updateCell();
      }   
    }
 ```

* Dead cell with three live neighbors becomes a live cell.
 ```java
    if(getMyValue()==0){
          if(myNeighbors.numberOfLiveCells() == 3){
            updateCell();
          }   
        }
 ```

* Live cell with more than three live neighbors dies.
 ```java
    if(getMyValue()==1){
          if(myNeighbors.numberOfLiveCells() > 3){
            updateCell();
          }   
        }
 ```

## Design Considerations

   * How the neighborhoods interact with other neighborhoods and how the neighborhoods' cells interact with each other.

   * Probably one of the major challenges we'll face as the project progresses will be dealing with different
    sets of rules for different games and different types of neighborhoods.  While the basic implementation
    of the GUI and Cell objects will remain the same, the actual interactions could be very different
    from visualization to visualization.
 
   * We also needed to decide whether to work with 2D arrays or lists of lists for the implementation of the grid.
   We decided to go with a list of list because it allows the possibility to expand the grid size if that ever
   becomes a deliverable need and the only downside is the time complexity of updating cells.

## User Interface

Here is our amazing UI:

![sketch image](sketch_wireframe.jpg "UI Design")

Users can load in a file by clicking on Choose File button and picking their CSV file from the File
Dialog that pops up. The user can also click the top animation buttons to step through. In order from
left to right, the button's functionalities are as follows: reset to step 1, go back 1 step and pause, play
animation, pause, and go forward 1 step and pause.



## Team Responsibilities

 * Team Member #1 - Hayden Lau
    * CSV file handling (Configuration)
    * Implementation of 2D grid of rectangular cells
    
 * Team Member #2 - Jack Ellwood
    * Display of the current state of the 2D grid of cells
    * Overall Visualization
     
 * Team Member #3 - Jason Qiu
    * Implement Rules for the Game of Life