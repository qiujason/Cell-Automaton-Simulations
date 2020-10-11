package cellsociety;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cellsociety.configuration.ConfigurationException;
import cellsociety.configuration.Grid;
import cellsociety.configuration.PropertyReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

class ConfigurationTest {

  @Test
  void squareGridTest() throws URISyntaxException {
    Path path = Paths
        .get(getClass().getClassLoader().getResource("initial_states/Square.csv").toURI());
    Cell[][] expected = {{new Cell(1), new Cell(1)}, {new Cell(1), new Cell(1)}};
    try {
      Grid grid = new Grid(path);
      for (int i = 0; i < expected.length; i++) {
        for (int j = 0; j < expected[i].length; j++) {
          assertEquals(expected[i][j].getMyValue(), grid.getMyCells().get(i).get(j).getMyValue());
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void beaconGridTest() throws URISyntaxException {
    Path path = Paths
        .get(getClass().getClassLoader().getResource("initial_states/Beacon.csv").toURI());
    Cell[][] expected = {
        {new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(1), new Cell(1), new Cell(0), new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(1), new Cell(1), new Cell(0), new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(0), new Cell(0), new Cell(1), new Cell(1), new Cell(0)},
        {new Cell(0), new Cell(0), new Cell(0), new Cell(1), new Cell(1), new Cell(0)},
        {new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0)}
    };
    try {
      Grid grid = new Grid(path);
      for (int i = 0; i < expected.length; i++) {
        for (int j = 0; j < expected[i].length; j++) {
          assertEquals(expected[i][j].getMyValue(), grid.getMyCells().get(i).get(j).getMyValue());
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void ToadGridTest() throws URISyntaxException {
    Path path = Paths
        .get(getClass().getClassLoader().getResource("initial_states/Toad.csv").toURI());
    Cell[][] expected = {
        {new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(0), new Cell(1), new Cell(1), new Cell(1), new Cell(0)},
        {new Cell(0), new Cell(1), new Cell(1), new Cell(1), new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0)}
    };
    try {
      Grid grid = new Grid(path);
      for (int i = 0; i < expected.length; i++) {
        for (int j = 0; j < expected[i].length; j++) {
          assertEquals(expected[i][j].getMyValue(), grid.getMyCells().get(i).get(j).getMyValue());
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void BlinkerGridTest() throws URISyntaxException {
    Path path = Paths
        .get(getClass().getClassLoader().getResource("initial_states/Blinker.csv").toURI());
    Cell[][] expected = {
        {new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(0), new Cell(1), new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(0), new Cell(1), new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(0), new Cell(1), new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0)},
    };
    try {
      Grid grid = new Grid(path);
      for (int i = 0; i < expected.length; i++) {
        for (int j = 0; j < expected[i].length; j++) {
          assertEquals(expected[i][j].getMyValue(), grid.getMyCells().get(i).get(j).getMyValue());
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void TubGridTest() throws URISyntaxException {
    Path path = Paths
        .get(getClass().getClassLoader().getResource("initial_states/Tub.csv").toURI());
    Cell[][] expected = {
        {new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(0), new Cell(1), new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(1), new Cell(0), new Cell(1), new Cell(0)},
        {new Cell(0), new Cell(0), new Cell(1), new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0)},
    };
    try {
      Grid grid = new Grid(path);
      for (int i = 0; i < expected.length; i++) {
        for (int j = 0; j < expected[i].length; j++) {
          assertEquals(expected[i][j].getMyValue(), grid.getMyCells().get(i).get(j).getMyValue());
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void BoatGridTest() throws URISyntaxException {
    Path path = Paths
        .get(getClass().getClassLoader().getResource("initial_states/Boat.csv").toURI());
    Cell[][] expected = {
        {new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(1), new Cell(1), new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(1), new Cell(0), new Cell(1), new Cell(0)},
        {new Cell(0), new Cell(0), new Cell(1), new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0)},
    };
    try {
      Grid grid = new Grid(path);
      for (int i = 0; i < expected.length; i++) {
        for (int j = 0; j < expected[i].length; j++) {
          assertEquals(expected[i][j].getMyValue(), grid.getMyCells().get(i).get(j).getMyValue());
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void CornerGridTest() throws URISyntaxException {
    Path path = Paths
        .get(getClass().getClassLoader().getResource("initial_states/Corner.csv").toURI());
    Cell[][] expected = {
        {new Cell(1), new Cell(0), new Cell(0), new Cell(0), new Cell(1)},
        {new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0)},
        {new Cell(1), new Cell(0), new Cell(0), new Cell(0), new Cell(1)},
    };
    try {
      Grid grid = new Grid(path);
      for (int i = 0; i < expected.length; i++) {
        for (int j = 0; j < expected[i].length; j++) {
          assertEquals(expected[i][j].getMyValue(), grid.getMyCells().get(i).get(j).getMyValue());
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void EdgesGridTest() throws URISyntaxException {
    Path path = Paths
        .get(getClass().getClassLoader().getResource("initial_states/Edges.csv").toURI());
    Cell[][] expected = {
        {new Cell(1), new Cell(1), new Cell(1), new Cell(1), new Cell(1)},
        {new Cell(1), new Cell(0), new Cell(0), new Cell(0), new Cell(1)},
        {new Cell(1), new Cell(0), new Cell(0), new Cell(0), new Cell(1)},
        {new Cell(1), new Cell(0), new Cell(0), new Cell(0), new Cell(1)},
        {new Cell(1), new Cell(1), new Cell(1), new Cell(1), new Cell(1)},
    };
    try {
      Grid grid = new Grid(path);
      for (int i = 0; i < expected.length; i++) {
        for (int j = 0; j < expected[i].length; j++) {
          assertEquals(expected[i][j].getMyValue(), grid.getMyCells().get(i).get(j).getMyValue());
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void setMySquareNeighbors() throws URISyntaxException {
    Path path = Paths
        .get(getClass().getClassLoader().getResource("initial_states/Square.csv").toURI());
    try {
      Grid grid = new Grid(path);
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
            assertEquals(expected[i][j].getMyValue(), actual[i][j].getMyValue());
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void saveCSVFile() throws URISyntaxException, IOException {
    Path path = Paths
        .get(getClass().getClassLoader().getResource("initial_states/Boat.csv").toURI());
    Grid grid = new Grid(path);
    grid.saveCurrentGrid("test_data/BoatTest.csv");
    Grid gridTest = new Grid(Path.of("test_data/BoatTest.csv"));
    Cell[][] expected = {
        {new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(1), new Cell(1), new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(1), new Cell(0), new Cell(1), new Cell(0)},
        {new Cell(0), new Cell(0), new Cell(1), new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0)},
    };
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyValue(), gridTest.getMyCells().get(i).get(j).getMyValue());
      }
    }
  }

  @Test
  void saveEmptyCSVFile() throws IOException {
    Grid grid = new Grid(Path.of("test_data/EmptyTest.csv"));
    grid.saveCurrentGrid("test_data/EmptyData.csv");
    Grid gridTest = new Grid(Path.of("test_data/EmptyData.csv"));
    assertTrue(gridTest.getMyCells().isEmpty());
  }

  @Test
  void badPropertyFile(){
    InputStream fileInputStream = null;
    fileInputStream = getClass().getClassLoader().getResourceAsStream("property_list/bad_property_file.properties");
    InputStream finalFileInputStream = fileInputStream;
    assertThrows(ConfigurationException.class, () -> new PropertyReader(finalFileInputStream));
  }
}