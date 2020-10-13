package cellsociety.configuration;

import cellsociety.model.Cell;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Grid {

  private static final String MODEL_PATH = "cellsociety.model.";

  private final List<List<Cell>> myCells;
  private final ResourceBundle resourceBundle;
  private final String simulationName;

  public Grid(Path cellFile, String simulationName, Map<String, Double> optional) throws ConfigurationException {
    this.simulationName = simulationName;
    resourceBundle = ResourceBundle.getBundle(getClass().getPackageName()+".resources.ConfigurationErrors");
    myCells = build2DArray(cellFile, optional);
    if(myCells!=null){
      establishNeighbors();
    }
  }

  private List<List<Cell>> build2DArray(Path cellFile, Map<String, Double> optional) throws ConfigurationException {
    List<String[]> csvData = null;
    List<List<Cell>> ret = new ArrayList<>();
    int rows = 0;
    int cols = 0;
    try {
      FileReader inputFile = new FileReader(String.valueOf(cellFile));
      CSVReader csvReader = new CSVReader(inputFile);
      csvData = csvReader.readAll();
    } catch (CsvException | IOException e) {
      throw new ConfigurationException(String.format(resourceBundle.getString("otherSimulationCreationErrors"), e.getMessage()));
    }
    Iterator<String[]> iterator = csvData.iterator();
    if(iterator.hasNext()){
      String[] headerRow = iterator.next();
      rows = removeHiddenChars(headerRow[0]);
      cols = removeHiddenChars(headerRow[1]);
    } else {
      throw new ConfigurationException(String.format(resourceBundle.getString("otherSimulationCreationErrors"), "no header row"));
    }
    for (int i = 0; i < rows; i++) {
      if(!iterator.hasNext()){
        throw new ConfigurationException(String.format(resourceBundle.getString("mismatchedCSVData")));
      }
      String[] nextRow = iterator.next();
      if(nextRow.length!=cols){
        throw new ConfigurationException(String.format(resourceBundle.getString("mismatchedCSVData")));
      }
      ret.add(i, new ArrayList<>());
      for (int j = 0; j < cols; j++) {
        ret.get(i).add(convertStringToCell(nextRow[j], optional));
      }
    }
    return ret;
  }

  private Cell convertStringToCell(String stringValueForCell, Map<String, Double>... optional) {
    int cellValue = removeHiddenChars(stringValueForCell);
    Cell ret;
    try {
      String modelPackagePath = MODEL_PATH + simulationName + ".";

      // get state from cell value and simulation name
      Class<?> modelStates = Class.forName(modelPackagePath + simulationName + "States");
      Method method = modelStates.getMethod("getStateFromValue", int.class);
      Enum<?> state = (Enum<?>) method.invoke(null, cellValue);

      // create a new cell from simulation name with defined state
      Class<?> simulation = Class.forName(modelPackagePath + simulationName + "Cell");
      Constructor<?> simConstructor = simulation.getConstructor(Enum.class, Map[].class);
      ret = (Cell) simConstructor.newInstance(state, optional);
    } catch (ClassNotFoundException e) {
      throw new ConfigurationException(String.format(resourceBundle.getString("simulationNotSupported"), simulationName));
    } catch (Exception e) {
      e.printStackTrace();
      throw new ConfigurationException(String.format(resourceBundle.getString("otherSimulationCreationErrors"), e.getMessage()));
    }
    return ret;
  }

  public void saveCurrentGrid(String filePath) throws ConfigurationException {
    File file = new File(filePath);
    try {
      FileWriter outputFile = new FileWriter(file,false);
      int row = myCells.size();
      if(row == 0){
        return;
      }
      int col = myCells.get(row-1).size();
      CSVWriter csvWriter = new CSVWriter(outputFile);

      List<String[]> data = new ArrayList<>();
      String[] header = new String[col];
      header[0] = String.valueOf(row);
      header[1] = String.valueOf(col);
      data.add(header);
      for (List<Cell> myCell : myCells) {
        String[] newRow = new String[col];
        for (int j = 0; j < col; j++) {
          newRow[j] = String.valueOf(myCell.get(j).getMyState());
        }
        data.add(newRow);
      }
      csvWriter.writeAll(data);
      csvWriter.close();
    } catch (IOException e) {
      throw new ConfigurationException(String.format(resourceBundle.getString("errorWritingToFile"), file));
    }
  }

  private void establishNeighbors() {
    for (List<Cell> cells : myCells) {
      for (Cell cell : cells) {
        cell.setMyNeighbors(this);
      }
    }
  }

  private int removeHiddenChars(String fileString) {
    final String UTF8_BOM = "\uFEFF";
    final String CARRIAGE_RETURN = "\r";
    fileString = fileString.replace(UTF8_BOM, ""); // accounts for the BOM Character
    fileString = fileString.replace(CARRIAGE_RETURN, ""); // accounts for the carriage return character
    fileString = fileString.replace("\"","");
    return Integer.parseInt(fileString);
  }

  public List<List<Cell>> getMyCells() {
    return myCells;
  }

  public void updateNewStates() {
    myCells.forEach(cells -> cells.forEach(Cell::setNewState));
    myCells.forEach(cells -> cells.forEach(Cell::updateCell));
  }

}

