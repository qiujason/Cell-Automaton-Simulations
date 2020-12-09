package cellsociety.model.Neighborhoods;


import cellsociety.configuration.ConfigurationException;
import cellsociety.configuration.Grid;
import cellsociety.model.Cells.Cell;
import cellsociety.model.Neighborhoods.EdgePolicies.Edge;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * This class instantiates a neighborhood class which stores all the neighbors of a specific cell.
 * There are no abstract methods to implement but it can't be directly instantiated. Rather, subclasses
 * store a 2D boolean array containing the active neighbors depending on the neighbor policy implemented.
 * @author Jason Qiu and Hayden Lau
 */
public abstract class Neighborhood {

  private static final int NEIGHBORHOOD_LENGTH = 3; // size of row and col
  private final String EDGE_POLICY_PATH = getClass().getPackageName() + ".EdgePolicies.%sEdge";

  private final Cell[][] myCells;
  private final Cell mainCell;
  private final int neighborhoodSize;
  private boolean[][] activeNeighbors;

  /**
   * instantiates a new neighborhood
   * @param cell Cell representing the cell that the neighbors are surrounding
   * @param grid Grid containing all cells
   * @param edgePolicy String representing the type of edge to be implemented
   * @param neighborhoodSize int representing the number of neighbors based on the neighborhood policy
   */
  public Neighborhood(Cell cell, Grid grid, String edgePolicy,
      int neighborhoodSize) { // Builds a neighborhood for a given cell
    mainCell = cell;
    myCells = getMyCellsWithEdgePolicy(edgePolicy, grid.getMyCells());
    this.neighborhoodSize = neighborhoodSize;
    activeNeighbors = new boolean[NEIGHBORHOOD_LENGTH][NEIGHBORHOOD_LENGTH];
  }

  private Cell[][] getMyCellsWithEdgePolicy(String edgePolicy, List<List<Cell>> cells) {
    Edge edge = null;
    try {
      Class<?> edgeClass = Class.forName(String.format(EDGE_POLICY_PATH, edgePolicy));
      Constructor<?> edgeConstructor = edgeClass.getConstructor(List.class);
      edge = (Edge) edgeConstructor.newInstance(cells);
    } catch (ClassNotFoundException e) {
      throw new ConfigurationException("Class not found");
    } catch (Exception e) {
      throw new ConfigurationException("Error setting up simulation");
    }

    int[] cellLocation = getCellLocation(cells);
    if (edge == null || cellLocation[0] == -1 || cellLocation[1] == -1) {
      throw new ConfigurationException("Error loading cells");
    }

    return edge.determineNeighbors(cellLocation[0], cellLocation[1]);
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

  /**
   * returns the neighboring cells of a certain state
   * @param state enum representing a simulation state
   * @return list of neighboring cells that have the current state
   */
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

  /**
   * gets number of active neighbors (size of neighborhood - null cells)
   * @return int representing number of active neighbors
   */
  public int getNumActiveNeighbors() {
    return neighborhoodSize - getNumberOfNullCells();
  }

  /**
   * testing purposes: returns the 2D array of the neighborhood
   * @return 2D cell array representing the neighborhood
   */
  public Cell[][] getMyCells() {
    return myCells;
  }

  /**
   * initializes active neighbors from subclass
   * @param activeNeighbors boolean 2D array representing what neighbors are to be activated for the
   *                        neighborhood policy
   */
  protected void setActiveNeighbors(boolean[][] activeNeighbors) {
    this.activeNeighbors = activeNeighbors;
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
