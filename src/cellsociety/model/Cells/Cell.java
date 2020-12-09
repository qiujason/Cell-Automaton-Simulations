package cellsociety.model.Cells;

import cellsociety.configuration.ConfigurationException;
import cellsociety.configuration.Grid;
import cellsociety.model.Neighborhoods.Neighborhood;
import java.lang.reflect.Constructor;
import java.util.ResourceBundle;

/**
 * Purpose of this class is to provide a super class containing properties, rules for interactions,
 * and state changing methods for simulation classes to inherit.
 *
 * @author Jason Qiu
 */
public abstract class Cell {

  private static final String NEIGHBORHOODS_PATH = "cellsociety.model.Neighborhoods.";
  private static final ResourceBundle modelErrorsResourceBundle = ResourceBundle
      .getBundle(Cell.class.getPackageName() + ".resources.ModelErrors");

  protected Enum<?> myState;
  protected Enum<?> nextState;
  protected Neighborhood myNeighbors;

  /**
   * initializes a superclass cell with state
   *
   * @param state enum that represents the current state of the cell
   */
  public Cell(Enum<?> state) {
    myState = state;
    nextState = null;
  }

  /**
   * updates the cell's state with its next state
   */
  public void updateCell() {
    if (nextState != null) {
      myState = nextState;
      nextState = null;
    }
  }

  /**
   * sets the next state of the cell
   *
   * @param nextState enum that represents the next state of the cell
   */
  public void setNextState(Enum<?> nextState) {
    this.nextState = nextState;
  }

  /**
   * sets state to a new state
   *
   * @param newState enum that represents a state
   */
  public void setMyState(Enum<?> newState) {
    myState = newState;
  }

  /**
   * sets the neighbors of the cell
   *
   * @param grid               Grid of all cells
   * @param neighborhoodPolicy type of neighborhood policy to implement
   * @param edgePolicy         type of edge policy to implement
   */
  public void setMyNeighbors(Grid grid, String neighborhoodPolicy, String edgePolicy) {
    try {
      Class<?> neighborhood = Class
          .forName(NEIGHBORHOODS_PATH + neighborhoodPolicy + "Neighborhood");
      Constructor<?> neighborConstructor = neighborhood
          .getConstructor(Cell.class, Grid.class, String.class);
      myNeighbors = (Neighborhood) neighborConstructor.newInstance(this, grid, edgePolicy);
    } catch (ClassNotFoundException e) {
      throw new ConfigurationException(String
          .format(modelErrorsResourceBundle.getString("unrecognizedNeighborhoodPolicy"),
              neighborhoodPolicy));
    } catch (Exception e) {
      throw new ConfigurationException(String
          .format(modelErrorsResourceBundle.getString("otherNeighborhoodErrors"), e.getMessage()));
    }
  }

  /**
   * testing purposes: returns all neighbors of the cell
   *
   * @return Neighborhood containing the neighbors of the cell
   */
  public Neighborhood getMyNeighbors() {
    return myNeighbors;
  }

  /**
   * returns the current state of the cell
   *
   * @return enum representing the current state
   */
  public Enum<?> getMyState() {
    return myState;
  }

  /**
   * returns the next state of the cell
   *
   * @return enum representing the next state
   */
  public Enum<?> getNextState() {
    return nextState;
  }

  /**
   * abstract method to be implemented where the next state is determined based on the rules of the
   * simulation
   */
  public abstract void setNewState();

}
