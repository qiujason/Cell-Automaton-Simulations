package cellsociety;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CellTest {

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
  void getNewState2NeighborsAlive() throws URISyntaxException {
    Path path = Paths
        .get(getClass().getClassLoader().getResource("initial_states/Edges.csv").toURI());
    try {
      Grid grid = new Grid(path);
      assertEquals(1, grid.getMyCells().get(0).get(0).getMyValue()); // assert alive
      assertEquals(2, grid.getMyCells().get(0).get(0).getMyNeighbors()
          .getNumberOfLiveNeighbors()); // assert 2 neighbors
      int actualNewState = grid.getMyCells().get(0).get(0).getNewState();
      assertEquals(1, actualNewState); // assert alive
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getNewState3NeighborsAlive() throws URISyntaxException {
    Path path = Paths
        .get(getClass().getClassLoader().getResource("initial_states/Beacon.csv").toURI());
    try {
      Grid grid = new Grid(path);
      assertEquals(1, grid.getMyCells().get(2).get(1).getMyValue()); // assert alive
      assertEquals(3, grid.getMyCells().get(2).get(1).getMyNeighbors()
          .getNumberOfLiveNeighbors()); // assert 3 neighbors
      int actualNewState = grid.getMyCells().get(2).get(1).getNewState();
      assertEquals(1, actualNewState); // assert alive
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getNewStateReproduction() throws URISyntaxException {
    Path path = Paths
        .get(getClass().getClassLoader().getResource("initial_states/Toad.csv").toURI());
    try {
      Grid grid = new Grid(path);
      assertEquals(0, grid.getMyCells().get(2).get(1).getMyValue()); // assert dead
      assertEquals(3, grid.getMyCells().get(2).get(1).getMyNeighbors()
          .getNumberOfLiveNeighbors()); // assert 3 neighbors
      int actualNewState = grid.getMyCells().get(2).get(1).getNewState();
      assertEquals(1, actualNewState); // assert alive
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getNewStateOverpopulation() throws URISyntaxException {
    Path path = Paths
        .get(getClass().getClassLoader().getResource("initial_states/Beacon.csv").toURI());
    try {
      Grid grid = new Grid(path);
      assertEquals(1, grid.getMyCells().get(2).get(2).getMyValue()); // assert alive
      assertTrue(grid.getMyCells().get(2).get(2).getMyNeighbors().getNumberOfLiveNeighbors()
          > 3); // assert overpopulation
      int actualNewState = grid.getMyCells().get(2).get(2).getNewState();
      assertEquals(0, actualNewState); // assert alive
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getNewStateUnderpopulation() throws URISyntaxException {
    Path path = Paths
        .get(getClass().getClassLoader().getResource("initial_states/Corner.csv").toURI());
    try {
      Grid grid = new Grid(path);
      assertEquals(1, grid.getMyCells().get(0).get(0).getMyValue()); // assert alive
      assertTrue(grid.getMyCells().get(0).get(0).getMyNeighbors().getNumberOfLiveNeighbors()
          < 2); // assert underpopulation
      int actualNewState = grid.getMyCells().get(0).get(0).getNewState();
      assertEquals(0, actualNewState); // assert dead
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}