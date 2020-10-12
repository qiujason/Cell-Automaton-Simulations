package cellsociety;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cellsociety.configuration.ConfigurationException;
import cellsociety.configuration.Grid;
import cellsociety.configuration.PropertyReader;
import cellsociety.model.Cell;
import cellsociety.model.GameOfLife.GameOfLifeCell;
import cellsociety.model.GameOfLife.GameOfLifeStates;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

class ConfigurationTest {

  @Test
  void squareGridTest() {
    InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream("property_lists/square.properties");
    PropertyReader reader = new PropertyReader(fileInputStream);
    Cell[][] expected = {{new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE)}, {new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE)}};
    Grid grid = reader.gridFromPropertyFile();
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

  @Test
  void beaconGridTest() {
    InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream("property_lists/beacon.properties");
    PropertyReader reader = new PropertyReader(fileInputStream);
    Cell[][] expected = {
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)}
    };
    Grid grid = reader.gridFromPropertyFile();
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

  @Test
  void ToadGridTest() {
    InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream("property_lists/toad.properties");
    PropertyReader reader = new PropertyReader(fileInputStream);
    Cell[][] expected = {
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)}
    };
    Grid grid = reader.gridFromPropertyFile();
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

  @Test
  void BlinkerGridTest() {
    InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream("property_lists/blinker.properties");
    PropertyReader reader = new PropertyReader(fileInputStream);
    Cell[][] expected = {
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
    };
    Grid grid = reader.gridFromPropertyFile();
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

  @Test
  void TubGridTest() {
    InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream("property_lists/tub.properties");
    PropertyReader reader = new PropertyReader(fileInputStream);
    Cell[][] expected = {
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
    };
    Grid grid = reader.gridFromPropertyFile();
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

  @Test
  void BoatGridTest() {
    InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream("property_lists/boat.properties");
    PropertyReader reader = new PropertyReader(fileInputStream);
    Cell[][] expected = {
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
    };
    Grid grid = reader.gridFromPropertyFile();
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

  @Test
  void CornerGridTest() {
    InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream("property_lists/corner.properties");
    PropertyReader reader = new PropertyReader(fileInputStream);
    Cell[][] expected = {
        {new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE)},
    };
    Grid grid = reader.gridFromPropertyFile();
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

  @Test
  void EdgesGridTest() {
    InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream("property_lists/edges.properties");
    PropertyReader reader = new PropertyReader(fileInputStream);
    Cell[][] expected = {
        {new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE)},
        {new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE)},
        {new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE)},
        {new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE)},
        {new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE)},
    };
    Grid grid = reader.gridFromPropertyFile();
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

  @Test
  void setMySquareNeighbors() {
    InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream("property_lists/square.properties");
    PropertyReader reader = new PropertyReader(fileInputStream);
    Grid grid = reader.gridFromPropertyFile();
    Cell[][] expected = {
        {null, null, null},
        {null, grid.getMyCells().get(0).get(0), grid.getMyCells().get(0).get(1)},
        {null, grid.getMyCells().get(1).get(0), grid.getMyCells().get(1).get(1)}
    };
    Cell[][] actual = grid.getMyCells().get(0).get(0).getMyNeighbors().getMyCells();
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        if (expected[i][j] == null) {
          assertEquals(expected[i][j], actual[i][j]);
        } else {
          assertEquals(expected[i][j].getMyState(), actual[i][j].getMyState());
        }
      }
    }
  }

  @Test
  void saveCSVFile() throws IOException {
    InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream("property_lists/boat.properties");
    PropertyReader reader = new PropertyReader(fileInputStream);
    Grid grid = reader.gridFromPropertyFile();

    grid.saveCurrentGrid("test_data/BoatTest.csv");

    fileInputStream = getClass().getClassLoader().getResourceAsStream("property_lists/boat.properties");
    reader = new PropertyReader(fileInputStream);
    Grid gridTest = reader.gridFromPropertyFile();

    Cell[][] expected = {
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
    };
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), gridTest.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

  @Test
  void saveEmptyCSVFile() throws IOException {
    InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream("property_lists/boat.properties");
    PropertyReader reader = new PropertyReader(fileInputStream);

    Grid grid = new Grid(Path.of("test_data/EmptyTest.csv"));
    grid.saveCurrentGrid("test_data/EmptyData.csv");
    Grid gridTest = new Grid(Path.of("test_data/EmptyData.csv"));
    assertTrue(gridTest.getMyCells().isEmpty());
  }

  @Test
  void badPropertyFile(){
    InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream("property_list/bad_property_file.properties");
    assertThrows(ConfigurationException.class, () -> new PropertyReader(fileInputStream));
  }
}