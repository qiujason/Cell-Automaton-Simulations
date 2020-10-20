package cellsociety.configuration;

import cellsociety.model.Cells.Cell;
import cellsociety.model.Cells.GameOfLife.GameOfLifeCell;
import cellsociety.model.Cells.GameOfLife.GameOfLifeStates;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyReaderTest {

  PropertyReader reader;

  @Test
  void getPropertyRequiredValid() {
    reader = new PropertyReader("property_lists/GameOfLife/square.properties");
    assertEquals("GameOfLife", reader.getProperty("simulationType"));
  }

  @Test
  void getPropertyNonExistent(){
    reader = new PropertyReader("property_lists/GameOfLife/square.properties");
    assertThrows(ConfigurationException.class, ()->reader.getProperty("test"));
  }

  @Test
  void getPropertyOptionalInitialized() {
    reader = new PropertyReader("property_lists/Segregation/checkerboard.properties");
    assertEquals(".5", reader.getProperty("satisfiedThreshold"));
  }

  @Test
  void getPropertyOptionalUnInitialized() {
    reader = new PropertyReader("property_lists/Segregation/checkerboard.properties");
    assertEquals("Csv", reader.getProperty("initialStatePolicy"));
  }

  @Test
  void missingRequiredProperty() {
    assertThrows(ConfigurationException.class, () -> new PropertyReader(
        "property_lists/TestProperties/bad_property_file.properties"));
  }

  @Test
  void spaceInSimulationTypeProperty() {
    assertThrows(ConfigurationException.class, () -> new PropertyReader(
        "property_lists/TestProperties/bad_simulation_type.properties"));
  }

  @Test
  void setProperty(){
    reader = new PropertyReader("property_lists/TestProperties/writing.properties");
    reader.setProperty("configAuthor", "Hayden Lau");
    assertEquals("Hayden Lau", reader.getProperty("configAuthor"));
    reader.setProperty("configAuthor", "John Conway");
  }

  @Test
  void optionalKeyMapGameOfLife() {
    reader = new PropertyReader("property_lists/GameOfLife/square.properties");
    HashMap<String, Object> optionalKeyMap = reader.optionalKeyMap("GameOfLife");
    HashMap<String, Object> actual = new HashMap<>();
    actual.put("edgePolicy", "finite");
    actual.put("neighborPolicy", "Complete");
    actual.put("initialStatePolicy", "Csv");
    actual.forEach((key, value)-> assertEquals(value, optionalKeyMap.get(key)));
  }

  @Test
  void optionalKeyMapPercolation() {
    reader = new PropertyReader("property_lists/Percolation/allBlocked.properties");
    HashMap<String, Object> optionalKeyMap = reader.optionalKeyMap("Percolation");
    HashMap<String, Object> actual = new HashMap<>();
    actual.put("edgePolicy", "finite");
    actual.put("neighborPolicy", "Complete");
    actual.put("initialStatePolicy", "Csv");
    actual.forEach((key, value)-> assertEquals(value, optionalKeyMap.get(key)));
  }

  @Test
  void optionalKeyMapRPS() {
    reader = new PropertyReader("property_lists/RPS/rockDominant.properties");
    HashMap<String, Object> optionalKeyMap = reader.optionalKeyMap("RPS");
    HashMap<String, Object> actual = new HashMap<>();
    actual.put("edgePolicy", "finite");
    actual.put("neighborPolicy", "Complete");
    actual.put("initialStatePolicy", "Csv");
    actual.put("winThreshold", "3.0");
    actual.forEach((key, value)-> assertEquals(value, optionalKeyMap.get(key)));
  }

  @Test
  void optionalKeyMapSegregation() {
    reader = new PropertyReader("property_lists/Segregation/checkerboard.properties");
    HashMap<String, Object> optionalKeyMap = reader.optionalKeyMap("Segregation");
    HashMap<String, Object> actual = new HashMap<>();
    actual.put("edgePolicy", "finite");
    actual.put("neighborPolicy", "Complete");
    actual.put("initialStatePolicy", "Csv");
    actual.put("satisfiedThreshold", ".5");
    actual.forEach((key, value)-> assertEquals(value, optionalKeyMap.get(key)));
  }

  @Test
  void optionalKeyMapSpreadingFire() {
    reader = new PropertyReader("property_lists/SpreadingFire/centerOnFire.properties");
    HashMap<String, Object> optionalKeyMap = reader.optionalKeyMap("SpreadingFire");
    HashMap<String, Object> actual = new HashMap<>();
    actual.put("edgePolicy", "finite");
    actual.put("neighborPolicy", "Complete");
    actual.put("initialStatePolicy", "Csv");
    actual.put("probCatch", ".1");
    actual.forEach((key, value)-> assertEquals(value, optionalKeyMap.get(key)));
  }

  @Test
  void optionalKeyMapWator() {
    reader = new PropertyReader("property_lists/Wator/AllFish.properties");
    HashMap<String, Object> optionalKeyMap = reader.optionalKeyMap("Wator");
    HashMap<String, Object> actual = new HashMap<>();
    actual.put("edgePolicy", "finite");
    actual.put("neighborPolicy", "Complete");
    actual.put("initialStatePolicy", "Csv");
    actual.put("thresholdForBirth", "4");
    actual.forEach((key, value)-> assertEquals(value, optionalKeyMap.get(key)));
  }

  @Test
  void gridFromPropertyFileValid() {
    reader = new PropertyReader("property_lists/GameOfLife/square.properties");
    Cell[][] expected = {{new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife"))}, {new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife"))}};
    Grid grid = reader.gridFromPropertyFile();
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

  @Test
  void gridFromPropertyFileBadCsvFile(){
    reader = new PropertyReader("property_lists/TestProperties/bad_csv.properties");
    assertThrows(ConfigurationException.class, () -> reader.gridFromPropertyFile());
  }

  @Test
  void gridFromPropertyFileUnSupportedSimulation(){
    reader = new PropertyReader("property_lists/TestProperties/unsupported.properties");
    assertThrows(ConfigurationException.class, () -> reader.gridFromPropertyFile());
  }
}