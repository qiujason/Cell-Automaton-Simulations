package cellsociety;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

class GridTest {

  @Test
  void squareGridTest() throws URISyntaxException {
    Path path = Paths.get(getClass().getClassLoader().getResource("initial_states/Square.csv").toURI());
    Cell[][] expected = {{new Cell(1), new Cell(1)}, {new Cell(1), new Cell(1)}};
    try {
      Grid grid = new Grid(path);
      for(int i = 0; i < expected.length; i++){
        for(int j = 0; j < expected[i].length; j++){
          assertEquals(expected[i][j].getMyValue(), grid.getMyCells().get(i).get(j).getMyValue());
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void beaconGridTest() throws URISyntaxException {
    Path path = Paths.get(getClass().getClassLoader().getResource("initial_states/Beacon.csv").toURI());
    Cell[][] expected = {
        {new Cell(0), new Cell(0),new Cell(0), new Cell(0),new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(1),new Cell(1), new Cell(0),new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(1),new Cell(1), new Cell(0),new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(0),new Cell(0), new Cell(1),new Cell(1), new Cell(0)},
        {new Cell(0), new Cell(0),new Cell(0), new Cell(1),new Cell(1), new Cell(0)},
        {new Cell(0), new Cell(0),new Cell(0), new Cell(0),new Cell(0), new Cell(0)}
    };
    try {
      Grid grid = new Grid(path);
      for(int i = 0; i < expected.length; i++){
        for(int j = 0; j < expected[i].length; j++){
          assertEquals(expected[i][j].getMyValue(), grid.getMyCells().get(i).get(j).getMyValue());        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void ToadGridTest() throws URISyntaxException {
    Path path = Paths.get(getClass().getClassLoader().getResource("initial_states/Toad.csv").toURI());
    Cell[][] expected = {
        {new Cell(0), new Cell(0),new Cell(0), new Cell(0),new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(0),new Cell(0), new Cell(0),new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(0),new Cell(1), new Cell(1),new Cell(1), new Cell(0)},
        {new Cell(0), new Cell(1),new Cell(1), new Cell(1),new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(0),new Cell(0), new Cell(0),new Cell(0), new Cell(0)},
        {new Cell(0), new Cell(0),new Cell(0), new Cell(0),new Cell(0), new Cell(0)}
    };
    try {
      Grid grid = new Grid(path);
      for(int i = 0; i < expected.length; i++){
        for(int j = 0; j < expected[i].length; j++){
          assertEquals(expected[i][j].getMyValue(), grid.getMyCells().get(i).get(j).getMyValue());        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void BlinkerGridTest() throws URISyntaxException {
    Path path = Paths.get(getClass().getClassLoader().getResource("initial_states/Blinker.csv").toURI());
    Cell[][] expected = {
        {new Cell(0), new Cell(0),new Cell(0), new Cell(0),new Cell(0)},
        {new Cell(0), new Cell(0),new Cell(1), new Cell(0),new Cell(0)},
        {new Cell(0), new Cell(0),new Cell(1), new Cell(0),new Cell(0)},
        {new Cell(0), new Cell(0),new Cell(1), new Cell(0),new Cell(0)},
        {new Cell(0), new Cell(0),new Cell(0), new Cell(0),new Cell(0)},
    };
    try {
      Grid grid = new Grid(path);
      for(int i = 0; i < expected.length; i++){
        for(int j = 0; j < expected[i].length; j++){
          assertEquals(expected[i][j].getMyValue(), grid.getMyCells().get(i).get(j).getMyValue());        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void TubGridTest() throws URISyntaxException {
    Path path = Paths.get(getClass().getClassLoader().getResource("initial_states/Tub.csv").toURI());
    Cell[][] expected = {
        {new Cell(0), new Cell(0),new Cell(0), new Cell(0),new Cell(0)},
        {new Cell(0), new Cell(0),new Cell(1), new Cell(0),new Cell(0)},
        {new Cell(0), new Cell(1),new Cell(0), new Cell(1),new Cell(0)},
        {new Cell(0), new Cell(0),new Cell(1), new Cell(0),new Cell(0)},
        {new Cell(0), new Cell(0),new Cell(0), new Cell(0),new Cell(0)},
    };
    try {
      Grid grid = new Grid(path);
      for(int i = 0; i < expected.length; i++){
        for(int j = 0; j < expected[i].length; j++){
          assertEquals(expected[i][j].getMyValue(), grid.getMyCells().get(i).get(j).getMyValue());        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void BoatGridTest() throws URISyntaxException {
    Path path = Paths.get(getClass().getClassLoader().getResource("initial_states/Boat.csv").toURI());
    Cell[][] expected = {
        {new Cell(0), new Cell(0),new Cell(0), new Cell(0),new Cell(0)},
        {new Cell(0), new Cell(1),new Cell(1), new Cell(0),new Cell(0)},
        {new Cell(0), new Cell(1),new Cell(0), new Cell(1),new Cell(0)},
        {new Cell(0), new Cell(0),new Cell(1), new Cell(0),new Cell(0)},
        {new Cell(0), new Cell(0),new Cell(0), new Cell(0),new Cell(0)},
    };
    try {
      Grid grid = new Grid(path);
      for(int i = 0; i < expected.length; i++){
        for(int j = 0; j < expected[i].length; j++){
          assertEquals(expected[i][j].getMyValue(), grid.getMyCells().get(i).get(j).getMyValue());        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void CornerGridTest() throws URISyntaxException {
    Path path = Paths.get(getClass().getClassLoader().getResource("initial_states/Corner.csv").toURI());
    Cell[][] expected = {
        {new Cell(1), new Cell(0),new Cell(0), new Cell(0),new Cell(1)},
        {new Cell(0), new Cell(0),new Cell(0), new Cell(0),new Cell(0)},
        {new Cell(0), new Cell(0),new Cell(0), new Cell(0),new Cell(0)},
        {new Cell(0), new Cell(0),new Cell(0), new Cell(0),new Cell(0)},
        {new Cell(1), new Cell(0),new Cell(0), new Cell(0),new Cell(1)},
    };
    try {
      Grid grid = new Grid(path);
      for(int i = 0; i < expected.length; i++){
        for(int j = 0; j < expected[i].length; j++){
          assertEquals(expected[i][j].getMyValue(), grid.getMyCells().get(i).get(j).getMyValue());        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void EdgesGridTest() throws URISyntaxException {
    Path path = Paths.get(getClass().getClassLoader().getResource("initial_states/Edges.csv").toURI());
    Cell[][] expected = {
        {new Cell(1), new Cell(1),new Cell(1), new Cell(1),new Cell(1)},
        {new Cell(1), new Cell(0),new Cell(0), new Cell(0),new Cell(1)},
        {new Cell(1), new Cell(0),new Cell(0), new Cell(0),new Cell(1)},
        {new Cell(1), new Cell(0),new Cell(0), new Cell(0),new Cell(1)},
        {new Cell(1), new Cell(1),new Cell(1), new Cell(1),new Cell(1)},
    };
    try {
      Grid grid = new Grid(path);
      for(int i = 0; i < expected.length; i++){
        for(int j = 0; j < expected[i].length; j++){
          assertEquals(expected[i][j].getMyValue(), grid.getMyCells().get(i).get(j).getMyValue());        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}