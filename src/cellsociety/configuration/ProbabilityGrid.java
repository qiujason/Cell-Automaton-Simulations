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
import java.util.Random;

public class ProbabilityGrid extends Grid{
  private Random random;
  private double probability;
  public ProbabilityGrid(Path cellFile, String simulationName, Map optional) throws ConfigurationException {
    super(cellFile, simulationName, optional);
    probability = .5;
    random = new Random(1);
  }

  @Override
  List<List<Cell>> build2DArray(Path cellFile) throws ConfigurationException {
    List<List<Cell>> ret = new ArrayList<>();
    List<String[]> csvData;
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
      probability = removeHiddenChars(headerRow[2]);
    } else {
      throw new ConfigurationException(String.format(resourceBundle.getString("otherSimulationCreationErrors"), "no header row"));
    }
    for (int i = 0; i < rows; i++) {
      ret.add(i, new ArrayList<>());
      for (int j = 0; j < cols; j++) {
        ret.get(i).add(createNewCell());
      }
    }
    return ret;
  }

  private Cell createNewCell() throws ConfigurationException{
    Cell ret;
    try {
      String modelPackagePath = MODEL_PATH + simulationName + ".";

      // get state from cell value and simulation name
      Class<?> modelStates = Class.forName(modelPackagePath + simulationName + "States");
      Method method = modelStates.getMethod("values");
      Enum<?>[] states = ((Enum<?>[]) method.invoke(null));
      Enum<?> state = states[0];

      // randomization of state and defaults to the 0th state
      for(int i = states.length-1; i > 0; i++){
        if(random.nextDouble()<=probability){
          state = states[i];
          break;
        }
      }

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
