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
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class ConfigurationTest {

  @Test
  void squareGridTest() {
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/square.properties");
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
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/beacon.properties");
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
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/toad.properties");
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
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/blinker.properties");
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
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/tub.properties");
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
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/boat.properties");
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
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/corner.properties");
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
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/edges.properties");
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
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/square.properties");
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
  void saveCSVFile() {
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/boat.properties");
    Grid grid = reader.gridFromPropertyFile();

    grid.saveCurrentGrid("test_data/BoatTest.csv");

    reader = new PropertyReader("property_lists/GameOfLife/boat.properties");
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
  void saveEmptyCSVFile() {
    Grid grid = new Grid(Path.of("test_data/EmptyTest.csv"), "GameOfLife");
    grid.saveCurrentGrid("test_data/EmptyData.csv");
    Grid gridTest = new Grid(Path.of("test_data/EmptyData.csv"), "GameOfLife");
    assertTrue(gridTest.getMyCells().isEmpty());
  }

  @Test
  void badPropertyFile(){
    assertThrows(ConfigurationException.class, () -> new PropertyReader("property_lists/bad_property_file.properties"));
  }
}