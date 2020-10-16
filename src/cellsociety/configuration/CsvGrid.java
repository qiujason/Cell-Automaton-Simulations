package cellsociety.configuration;

import cellsociety.model.Cell;
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

public class CsvGrid extends Grid {

  public CsvGrid(Path cellFile, String simulationName, Map<String, Object> optional) throws ConfigurationException {
    super(cellFile, simulationName, optional);
  }

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
      throw new ConfigurationException(String.format(resourceBundle.getString("otherSimulationCreationErrors"), e.getMessage()));
    }
    Iterator<String[]> iterator = csvData.iterator();
    if(iterator.hasNext()){
      String[] headerRow = iterator.next();
      rows = (int) removeHiddenChars(headerRow[0]);
      cols = (int) removeHiddenChars(headerRow[1]);
    } else {
      throw new ConfigurationException(String.format(resourceBundle.getString("otherSimulationCreationErrors"), "no header row"));
    }
    buildArrayFromCSV(ret, rows, cols, iterator);
    return ret;
  }

  private void buildArrayFromCSV(List<List<Cell>> ret, int rows, int cols,
      Iterator<String[]> iterator) {
    for (int i = 0; i < rows; i++) {
      if(!iterator.hasNext()){
        throw new ConfigurationException(resourceBundle.getString("mismatchedCSVData"));
      }
      String[] nextRow = iterator.next();
      if(nextRow.length!= cols){
        throw new ConfigurationException(resourceBundle.getString("mismatchedCSVData"));
      }
      ret.add(i, new ArrayList<>());
      for (int j = 0; j < cols; j++) {
        ret.get(i).add(convertStringToCell(nextRow[j]));
      }
    }
  }

  private Cell convertStringToCell(String stringValueForCell) throws ConfigurationException{
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
      throw new ConfigurationException(String.format(resourceBundle.getString("simulationNotSupported"), simulationName));
    } catch (Exception e) {
      e.printStackTrace();
      throw new ConfigurationException(String.format(resourceBundle.getString("otherSimulationCreationErrors"), e.getMessage()));
    }
    return ret;
  }


}
