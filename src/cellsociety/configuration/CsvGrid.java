package cellsociety.configuration;

import cellsociety.model.Cells.Cell;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class is designed to develop a Grid of Cells based on the data stored in a CSV file.
 * <p>
 * Exceptions can be thrown if the file passed into the constructor has faulty information, the data
 * in the csv file doesn't match the proper format, or if the simulation requested isn't supported.
 * <p>
 * This class relies on its parent class, Grid, as well as the PropertyReader class to pass in
 * proper data.
 * <p>
 * Grid grid = new CsvGrid(Path.of("test/test_data/na.csv"), "NA", optionalKeyMap);
 *
 * @author Hayden Lau
 */
public class CsvGrid extends Grid {

  /**
   * this constructor serves to call the Grid constructor and generates a CsvGrid of the simulation
   * requested with the data passed in through the parameters
   *
   * @param cellFile       path of the csv file containing the data
   * @param simulationName name of the type of the simulation to be created
   * @param optional       map containing the optional keys and values necessary to generate the
   *                       simulation
   * @throws ConfigurationException
   */
  public CsvGrid(Path cellFile, String simulationName, Map<String, Object> optional)
      throws ConfigurationException {
    super(cellFile, simulationName, optional);
  }

  /**
   * builds out a list of list of cells that represent the graph based on the csv file's data
   *
   * @param cellFile the path of csv file to create the simulation from
   * @return list of list of cells that represent the grid
   * @throws ConfigurationException if there is an error while building the grid
   */
  @Override
  protected List<List<Cell>> build2DArray(Path cellFile) throws ConfigurationException {
    List<String[]> csvData;
    List<List<Cell>> ret = new ArrayList<>();
    int rows;
    int cols;
    try {
      FileReader inputFile = new FileReader(String.valueOf(cellFile));
      CSVReader csvReader = new CSVReader(inputFile);
      csvData = csvReader.readAll();
    } catch (CsvException | IOException e) {
      throw new ConfigurationException(
          String.format(resourceBundle.getString("otherSimulationCreationErrors"), e.getMessage()));
    }
    Iterator<String[]> iterator = csvData.iterator();
    if (iterator.hasNext()) {
      String[] headerRow = iterator.next();
      rows = (int) removeHiddenChars(headerRow[0]);
      cols = (int) removeHiddenChars(headerRow[1]);
    } else {
      throw new ConfigurationException(String
          .format(resourceBundle.getString("otherSimulationCreationErrors"), "no header row"));
    }
    buildArrayFromCSV(ret, rows, cols, iterator);
    return ret;
  }

  private void buildArrayFromCSV(List<List<Cell>> ret, int rows, int cols,
      Iterator<String[]> iterator) {
    for (int i = 0; i < rows; i++) {
      if (!iterator.hasNext()) {
        throw new ConfigurationException(resourceBundle.getString("mismatchedCSVData"));
      }
      String[] nextRow = iterator.next();
      if (nextRow.length != cols) {
        throw new ConfigurationException(resourceBundle.getString("mismatchedCSVData"));
      }
      ret.add(i, new ArrayList<>());
      for (int j = 0; j < cols; j++) {
        ret.get(i).add(convertStringToCell(nextRow[j]));
      }
    }
    if (iterator.hasNext()) {
      throw new ConfigurationException(resourceBundle.getString("mismatchedCSVData"));
    }
  }

  private Cell convertStringToCell(String stringValueForCell) throws ConfigurationException {
    int cellValue = (int) removeHiddenChars(stringValueForCell);
    Cell ret;
    try {
      String modelPackagePath = MODEL_PATH + simulationName + ".";

      // get state from cell value and simulation name
      Class<?> modelStates = Class.forName(modelPackagePath + simulationName + "States");
      Method method = modelStates.getMethod("values");
      Enum<?> state = ((Enum<?>[]) method.invoke(null))[cellValue];

      // create a new cell from simulation name with defined state
      Class<?> simulation = Class.forName(modelPackagePath + simulationName + "Cell");
      Constructor<?> simConstructor = simulation.getConstructor(Enum.class, Map.class);
      ret = (Cell) simConstructor.newInstance(state, optional);
    } catch (ClassNotFoundException e) {
      throw new ConfigurationException(
          String.format(resourceBundle.getString("simulationNotSupported"), simulationName));
    } catch (Exception e) {
      throw new ConfigurationException(
          String.format(resourceBundle.getString("otherSimulationCreationErrors"), e.getMessage()));
    }
    return ret;
  }


}
