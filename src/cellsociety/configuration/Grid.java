package cellsociety.configuration;

import cellsociety.model.Cells.Cell;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * this abstract class serves as the basis for the specific types of grid models and houses common
 * methods and protected variables such as the list of cells
 * <p>
 * relies on its sub classes and the PropertyReader class to pass the necessary values in
 *
 * @author Hayden Lau and Jason Qiu
 */
public abstract class Grid {

  protected static final String MODEL_PATH = "cellsociety.model.Cells.";

  protected final List<List<Cell>> myCells;
  protected final ResourceBundle resourceBundle;
  protected final String simulationName;
  protected final Map optional;

  /**
   * serves as the general constructor that gets utilized by the subclasses
   * <p>
   * creates the list of list of cells and stores the necessary information for the simulation
   *
   * @param cellFile       path of csv file pertaining to the grid being created
   * @param simulationName simulation type to specify for cell types
   * @param optional       map of optional keys and their values
   * @throws ConfigurationException if there is an error in building the array of cells
   */
  public Grid(Path cellFile, String simulationName, Map optional) throws ConfigurationException {
    this.simulationName = simulationName;
    this.optional = optional;
    resourceBundle = ResourceBundle
        .getBundle(getClass().getPackageName() + ".resources.ConfigurationErrors");
    myCells = build2DArray(cellFile);
    if (myCells != null) {
      establishNeighbors();
    }
  }

  /**
   * builds out the grid of cells based on the type of grid implemented by its sub classes
   *
   * @param cellFile the path of csv file to create the simulation from
   * @return list of list of cells that represent the grid
   * @throws ConfigurationException if there is an error while building the grid
   */
  abstract List<List<Cell>> build2DArray(Path cellFile) throws ConfigurationException;

  /**
   * saves the current grid format into the filePath parameter
   *
   * @param filePath csv file to save the current grid configuration to
   * @throws ConfigurationException throws an exception if there is an error writing to the file
   */
  public void saveCurrentGrid(String filePath) throws ConfigurationException {
    File file = new File(filePath);
    try {
      String modelPackagePath = MODEL_PATH + simulationName + ".";
      Class<?> modelStates = Class.forName(modelPackagePath + simulationName + "States");
      Method method = modelStates.getMethod("values");
      Enum<?>[] states = ((Enum<?>[]) method.invoke(null));
      FileWriter outputFile = new FileWriter(file, false);
      int row = myCells.size();
      if (row == 0) {
        return;
      }
      int col = myCells.get(row - 1).size();
      CSVWriter csvWriter = new CSVWriter(outputFile);
      List<String[]> data = new ArrayList<>();
      String[] header = new String[col];
      header[0] = String.valueOf(row);
      header[1] = String.valueOf(col);
      data.add(header);
      for (List<Cell> myCell : myCells) {
        String[] newRow = new String[col];
        for (int j = 0; j < col; j++) {
          int valueToStore = 0;
          for (int i = 0; i < states.length; i++) {
            if (states[i].equals(myCell.get(j).getMyState())) {
              valueToStore = i;
            }
          }
          newRow[j] = String.valueOf(valueToStore);
        }
        data.add(newRow);
      }
      csvWriter.writeAll(data);
      csvWriter.close();
    } catch (Exception e) {
      throw new ConfigurationException(
          String.format(resourceBundle.getString("errorWritingToFile"), file));
    }
  }

  private void establishNeighbors() {
    for (List<Cell> cells : myCells) {
      for (Cell cell : cells) {
        cell.setMyNeighbors(this, (String) optional.get("neighborPolicy"),
            (String) optional.get("edgePolicy"));
      }
    }
  }

  /**
   * this method is designed to remove hidden characters that can be generated by Excel when saving
   * to a csv file
   *
   * @param fileString string to remove any hidden characters from
   * @return a double representing the value stored in the string
   * @throws ConfigurationException if the value stored isn't a numerical value
   */
  protected double removeHiddenChars(String fileString) throws ConfigurationException {
    final String UTF8_BOM = "\uFEFF";
    final String CARRIAGE_RETURN = "\r";
    fileString = fileString.replace(UTF8_BOM, ""); // accounts for the BOM Character
    fileString = fileString
        .replace(CARRIAGE_RETURN, ""); // accounts for the carriage return character
    fileString = fileString.replace("\"", "");
    try {
      double ret = Double.parseDouble(fileString);
      return ret;
    } catch (Exception e) {
      throw new ConfigurationException(
          String.format(resourceBundle.getString("otherSimulationCreationErrors"), e.getMessage()));
    }
  }

  /**
   * getter method for the cells
   *
   * @return the list of list of cells
   */
  public List<List<Cell>> getMyCells() {
    return myCells;
  }

  /**
   * for each cell determines the next state and then updates the state
   */
  public void updateNewStates() {
    myCells.forEach(cells -> cells.forEach(Cell::setNewState));
    myCells.forEach(cells -> cells.forEach(Cell::updateCell));
  }

}

