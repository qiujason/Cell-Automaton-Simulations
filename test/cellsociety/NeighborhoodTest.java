package cellsociety;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class NeighborhoodTest {

  @Test
  void getNumberOfLiveNeighborsCorner() throws URISyntaxException {
    Path path = Paths.get(getClass().getClassLoader().getResource("initial_states/Square.csv").toURI());
    try {
      Grid grid = new Grid(path);
      int numberOfLiveNeighbors = grid.getMyCells().get(0).get(0).getMyNeighbors()
          .getNumberOfLiveNeighbors();
      assertEquals(3, numberOfLiveNeighbors);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getNumberOfLiveNeighborsMiddle() throws URISyntaxException {
    Path path = Paths.get(getClass().getClassLoader().getResource("initial_states/Boat.csv").toURI());
    try {
      Grid grid = new Grid(path);
      int numberOfLiveNeighbors = grid.getMyCells().get(1).get(1).getMyNeighbors()
          .getNumberOfLiveNeighbors();
      assertEquals(2, numberOfLiveNeighbors);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getNumberOfLiveNeighborsDead() throws URISyntaxException {
    Path path = Paths.get(getClass().getClassLoader().getResource("initial_states/Boat.csv").toURI());
    try {
      Grid grid = new Grid(path);
      int numberOfLiveNeighbors = grid.getMyCells().get(2).get(2).getMyNeighbors()
          .getNumberOfLiveNeighbors();
      assertEquals(5, numberOfLiveNeighbors);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}