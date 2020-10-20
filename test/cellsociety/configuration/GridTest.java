package cellsociety.configuration;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.model.Cells.Cell;
import cellsociety.model.Cells.GameOfLife.GameOfLifeCell;
import cellsociety.model.Cells.GameOfLife.GameOfLifeStates;
import java.nio.file.Path;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

class GridTest {

  @Test
  void csvGridFromBeacon(){
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/beacon.properties");
    Cell[][] expected = {
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))}
    };
    Grid grid = reader.gridFromPropertyFile();
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

  @Test
  void probabilityGridFromSquare(){
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/squareProb.properties");
    Grid grid = reader.gridFromPropertyFile();
    Cell[][] expected = {{new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife"))}, {new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife"))}};
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

  @Test
  void totalNumberGridFromSquare(){
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/squareTotal.properties");
    Grid grid = reader.gridFromPropertyFile();
    Cell[][] expected = {{new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))}, {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))}};
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

  @Test
  void emptyData(){
    HashMap<String, Object> optionalKeyMap = new HashMap<>();
    optionalKeyMap.put("edgePolicy", "finite");
    optionalKeyMap.put("neighborPolicy", "Complete");
    optionalKeyMap.put("initialStatePolicy", "Csv");
    assertThrows(ConfigurationException.class, () -> new CsvGrid(Path.of("test/test_data/EmptyTest.csv"), "GameOfLife", optionalKeyMap));
    optionalKeyMap.put("probability", ".5");
    optionalKeyMap.put("initialStatePolicy", "Probability");
    assertThrows(ConfigurationException.class, () -> new ProbabilityGrid(Path.of("test/test_data/EmptyTest.csv"), "NA", optionalKeyMap));
    optionalKeyMap.put("initialStatePolicy", "TotalLocation");
    optionalKeyMap.put("totalNumber", "1");
    assertThrows(ConfigurationException.class, () -> new TotalLocationGrid(Path.of("test/test_data/EmptyTest.csv"), "NA", optionalKeyMap));

  }

  @Test
  void tooManyRows(){
    HashMap<String, Object> optionalKeyMap = new HashMap<>();
    optionalKeyMap.put("edgePolicy", "finite");
    optionalKeyMap.put("neighborPolicy", "Complete");
    optionalKeyMap.put("initialStatePolicy", "Csv");
    assertThrows(ConfigurationException.class, () -> new CsvGrid(Path.of("test/test_data/TooManyRows.csv"), "GameOfLife", optionalKeyMap));
  }

  @Test
  void tooManyColumns(){
    HashMap<String, Object> optionalKeyMap = new HashMap<>();
    optionalKeyMap.put("edgePolicy", "finite");
    optionalKeyMap.put("neighborPolicy", "Complete");
    optionalKeyMap.put("initialStatePolicy", "Csv");
    assertThrows(ConfigurationException.class, () -> new CsvGrid(Path.of("test/test_data/TooManyColumns.csv"), "GameOfLife", optionalKeyMap));
  }

  @Test
  void tooFewRows(){
    HashMap<String, Object> optionalKeyMap = new HashMap<>();
    optionalKeyMap.put("edgePolicy", "finite");
    optionalKeyMap.put("neighborPolicy", "Complete");
    optionalKeyMap.put("initialStatePolicy", "Csv");
    assertThrows(ConfigurationException.class, () -> new CsvGrid(Path.of("test/test_data/TooFewRows.csv"), "GameOfLife", optionalKeyMap));
  }

  @Test
  void badSimulation(){
    HashMap<String, Object> optionalKeyMap = new HashMap<>();
    optionalKeyMap.put("edgePolicy", "finite");
    optionalKeyMap.put("neighborPolicy", "Complete");
    optionalKeyMap.put("initialStatePolicy", "Csv");
    assertThrows(ConfigurationException.class, () -> new CsvGrid(Path.of("test/test_data/GoodData.csv"), "NA", optionalKeyMap));
    optionalKeyMap.put("probability", ".5");
    optionalKeyMap.put("initialStatePolicy", "Probability");
    assertThrows(ConfigurationException.class, () -> new ProbabilityGrid(Path.of("test/test_data/GoodData.csv"), "NA", optionalKeyMap));
    optionalKeyMap.put("initialStatePolicy", "TotalLocation");
    optionalKeyMap.put("totalNumber", "1");
    assertThrows(ConfigurationException.class, () -> new TotalLocationGrid(Path.of("test/test_data/GoodData.csv"), "NA", optionalKeyMap));
  }

  @Test
  void noFile(){
    HashMap<String, Object> optionalKeyMap = new HashMap<>();
    optionalKeyMap.put("edgePolicy", "finite");
    optionalKeyMap.put("neighborPolicy", "Complete");
    optionalKeyMap.put("initialStatePolicy", "Csv");
    assertThrows(ConfigurationException.class, () -> new CsvGrid(Path.of("test/test_data/na.csv"), "NA", optionalKeyMap));
    optionalKeyMap.put("probability", ".5");
    optionalKeyMap.put("initialStatePolicy", "Probability");
    assertThrows(ConfigurationException.class, () -> new ProbabilityGrid(Path.of("test/test_data/na.csv"), "NA", optionalKeyMap));
    optionalKeyMap.put("initialStatePolicy", "TotalLocation");
    optionalKeyMap.put("totalNumber", "1");
    assertThrows(ConfigurationException.class, () -> new TotalLocationGrid(Path.of("test/test_data/na.csv"), "NA", optionalKeyMap));
  }

  @Test
  void saveCSVFile() {
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/boat.properties");
    Grid grid = reader.gridFromPropertyFile();

    grid.saveCurrentGrid("test/test_data/BoatTest.csv");

    reader = new PropertyReader("property_lists/GameOfLife/boat.properties");
    Grid gridTest = reader.gridFromPropertyFile();

    Cell[][] expected = {
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
    };
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), gridTest.getMyCells().get(i).get(j).getMyState());
      }
    }
  }
}