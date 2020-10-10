package cellsociety.configuration;

import cellsociety.State;
import cellsociety.model.Cell;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Grid {

  private static final String MODEL_PATH = "cellsociety.model.cellmodel.";

  private Map<Cell, State> newState;
  private List<List<Cell>> myCells;
  private ResourceBundle resourceBundle;
  private String simulationName;

  public Grid(Path cellFile, String simulationName) throws ConfigurationException {
    newState = new HashMap<>();
    this.simulationName = simulationName;
    resourceBundle = ResourceBundle.getBundle("ConfigurationErrors");
    myCells = build2DArray(cellFile);
    if(myCells!=null){
      establishNeighbors();
    }
  }

  private List<List<Cell>> build2DArray(Path cellFile) throws ConfigurationException {
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
      if(iterator.hasNext()==false){
        throw new ConfigurationException(String.format(resourceBundle.getString("mismatchedCSVData")));
      }
      String[] nextRow = iterator.next();
      if(nextRow.length!=cols){
        throw new ConfigurationException(String.format(resourceBundle.getString("mismatchedCSVData")));
      }
      ret.add(i, new ArrayList<>());
      for (int j = 0; j < cols; j++) {
        ret.get(i).add(convertStringToCell(nextRow[j]));
      }
    }
    return ret;
  }

  private Cell convertStringToCell(String stringValueForCell) {
    int cellValue = removeHiddenChars(stringValueForCell);
    Cell ret;
    try {
      State state = State.getState(simulationName, cellValue);
      Class<?> simulation = Class.forName(MODEL_PATH + simulationName + "Cell");
      Constructor<?> simConstructor = simulation.getConstructor(State.class);
      ret = (Cell) simConstructor.newInstance(state);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new ConfigurationException(String.format(resourceBundle.getString("otherSimulationCreationErrors"), e.getMessage()));
    } catch (ClassNotFoundException e) {
      throw new ConfigurationException(String.format(resourceBundle.getString("simulationNotSupported"), simulationName));
    }
    return ret;
  }

  public Path saveCurrentGrid(String filePath) throws IOException {
    File file = new File(filePath);
    FileWriter outputFile = new FileWriter(file,false);
    int row = myCells.size();
    if(row == 0){
      return null;
    }
    int col = myCells.get(row-1).size();
    CSVWriter csvWriter = new CSVWriter(outputFile);

    List<String[]> data = new ArrayList<>();
    String[] header = new String[col];
    header[0] = String.valueOf(row);
    header[1] = String.valueOf(col);
    data.add(header);
    for(int i = 0; i<row; i++){
      String[] newRow = new String[col];
      for(int j = 0; j<col; j++){
        newRow[j] = String.valueOf(myCells.get(i).get(j).getMyState());
      }
      data.add(newRow);
    }
    csvWriter.writeAll(data);
    csvWriter.close();
    return Path.of(filePath);
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

  public Map<Cell, State> getNewState() {
    return newState;
  }

  public void updateNewStates() {
    getNewStates();
    updateStates();
  }

  private void getNewStates() {
    for (List<Cell> cells : myCells) {
      for (Cell cell : cells) {
        newState.put(cell, cell.getNewState());
      }
    }
  }

  private void updateStates() {
    for (Entry<Cell, State> cellState : newState.entrySet()) {
      cellState.getKey().updateCell(cellState.getValue());
    }
  }
}

