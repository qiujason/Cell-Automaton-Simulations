package cellsociety.model.Cells;

import cellsociety.configuration.ConfigurationException;
import cellsociety.configuration.Grid;
import cellsociety.model.Neighborhoods.Neighborhood;
import java.lang.reflect.Constructor;
import java.util.ResourceBundle;

public abstract class Cell {

  private static final String NEIGHBORHOODS_PATH = "cellsociety.model.Neighborhoods.";
  private static final ResourceBundle modelErrorsResourceBundle = ResourceBundle
      .getBundle(Cell.class.getPackageName() + ".ModelErrors");

  protected Enum<?> myState;
  protected Enum<?> nextState;
  protected Neighborhood myNeighbors;

  public Cell(Enum<?> state) {
    myState = state;
    nextState = null;
  }

  public void updateCell() {
    if (nextState != null) {
      myState = nextState;
      nextState = null;
    }
  }

  public void setNextState(Enum<?> nextState) {
    this.nextState = nextState;
  }

  public void setMyState(Enum<?> newState) { myState = newState; }

  public void setMyNeighbors(Grid grid, String neighborhoodPolicy) {
    try {
      Class<?> neighborhood = Class
          .forName(NEIGHBORHOODS_PATH + neighborhoodPolicy + "Neighborhood");
      Constructor<?> neighborConstructor = neighborhood.getConstructor(Cell.class, Grid.class);
      myNeighbors = (Neighborhood) neighborConstructor.newInstance(this, grid);
    } catch (ClassNotFoundException e) {
      throw new ConfigurationException(String.format(modelErrorsResourceBundle.getString("unrecognizedNeighborhoodPolicy"), neighborhoodPolicy));
    } catch (Exception e) {
      throw new ConfigurationException(String.format(modelErrorsResourceBundle.getString("otherNeighborhoodErrors"), e.getMessage()));
    }
  }

  public Neighborhood getMyNeighbors() {
    return myNeighbors;
  }

  public Enum<?> getMyState() {
    return myState;
  }

  public Enum<?> getNextState() {
    return nextState;
  }

  public abstract void setNewState();

}
