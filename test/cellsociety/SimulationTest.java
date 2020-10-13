package cellsociety;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.configuration.Grid;
import cellsociety.configuration.PropertyReader;
import cellsociety.model.GameOfLife.GameOfLifeCell;
import cellsociety.model.GameOfLife.GameOfLifeStates;
import org.junit.jupiter.api.Test;

class SimulationTest {

  @Test
  void getNumberOfLiveNeighborsCorner() {
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/square.properties");
    Grid grid = reader.gridFromPropertyFile();
    int numberOfLiveNeighbors = grid.getMyCells().get(0).get(0).getMyNeighbors()
        .getNumberOfNeighborsWithState(GameOfLifeStates.ALIVE, false);
    assertEquals(3, numberOfLiveNeighbors);
  }

  @Test
  void getNumberOfLiveNeighborsMiddle() {
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/boat.properties");
    Grid grid = reader.gridFromPropertyFile();
    int numberOfLiveNeighbors = grid.getMyCells().get(1).get(1).getMyNeighbors()
        .getNumberOfNeighborsWithState(GameOfLifeStates.ALIVE, false);
    assertEquals(2, numberOfLiveNeighbors);
  }

  @Test
  void getNumberOfLiveNeighborsDead() {
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/boat.properties");
    Grid grid = reader.gridFromPropertyFile();
    int numberOfLiveNeighbors = grid.getMyCells().get(2).get(2).getMyNeighbors()
        .getNumberOfNeighborsWithState(GameOfLifeStates.ALIVE, false);
    assertEquals(5, numberOfLiveNeighbors);
  }

  @Test
  void getNewState2NeighborsAlive() {
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/edges.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(GameOfLifeStates.ALIVE, grid.getMyCells().get(0).get(0).getMyState()); // assert alive
    assertEquals(2, grid.getMyCells().get(0).get(0).getMyNeighbors()
        .getNumberOfNeighborsWithState(GameOfLifeStates.ALIVE, false)); // assert 2 neighbors
    grid.updateNewStates();
    assertEquals(GameOfLifeStates.ALIVE, grid.getMyCells().get(0).get(0).getMyState()); // assert alive
  }

  @Test
  void getNewState3NeighborsAlive() {
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/beacon.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(GameOfLifeStates.ALIVE, grid.getMyCells().get(2).get(1).getMyState()); // assert alive
    assertEquals(3, grid.getMyCells().get(2).get(1).getMyNeighbors()
          .getNumberOfNeighborsWithState(GameOfLifeStates.ALIVE, false)); // assert 3 neighbors
    grid.updateNewStates();
    assertEquals(GameOfLifeStates.ALIVE, grid.getMyCells().get(2).get(1).getMyState());
  }

  @Test
  void getNewStateReproduction() {
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/toad.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(GameOfLifeStates.DEAD, grid.getMyCells().get(2).get(1).getMyState()); // assert dead
    assertEquals(3, grid.getMyCells().get(2).get(1).getMyNeighbors()
        .getNumberOfNeighborsWithState(GameOfLifeStates.ALIVE, false)); // assert 3 neighbors
    grid.updateNewStates();
    assertEquals(GameOfLifeStates.ALIVE, grid.getMyCells().get(2).get(1).getMyState());
  }

  @Test
  void getNewStateOverpopulation() {
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/beacon.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(GameOfLifeStates.ALIVE, grid.getMyCells().get(2).get(2).getMyState()); // assert alive
    assertTrue(grid.getMyCells().get(2).get(2).getMyNeighbors()
        .getNumberOfNeighborsWithState(GameOfLifeStates.ALIVE, false) > 3); // assert overpopulation
    grid.updateNewStates();
    assertEquals(GameOfLifeStates.DEAD, grid.getMyCells().get(2).get(2).getMyState());
  }

  @Test
  void getNewStateUnderpopulation() {
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/corner.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(GameOfLifeStates.ALIVE, grid.getMyCells().get(0).get(0).getMyState()); // assert alive
    assertTrue(grid.getMyCells().get(0).get(0).getMyNeighbors()
        .getNumberOfNeighborsWithState(GameOfLifeStates.ALIVE, false) < 2); // assert underpopulation
    grid.updateNewStates();
    assertEquals(GameOfLifeStates.DEAD, grid.getMyCells().get(0).get(0).getMyState());
  }

  @Test
  void updateCellsSquareTest() {
    GameOfLifeCell[][] expected = {
        {new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE)},
        {new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE)}
    };
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/square.properties");
    Grid grid = reader.gridFromPropertyFile();
    grid.updateNewStates();

    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

  @Test
  void updateCellsBeaconTest() {
    GameOfLifeCell[][] expectedUpdate = {
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)}
    };
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/beacon.properties");
    Grid grid = reader.gridFromPropertyFile();
    grid.updateNewStates();

    for (int i = 0; i < expectedUpdate.length; i++) {
      for (int j = 0; j < expectedUpdate[i].length; j++) {
        assertEquals(expectedUpdate[i][j].getMyState(),
            grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

  @Test
  void updateCellsToadTest() {
    GameOfLifeCell[][] expected = {
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)}
    };
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/toad.properties");
    Grid grid = reader.gridFromPropertyFile();
    grid.updateNewStates();

    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

  @Test
  void updateCellsBlinkerTest() {
    GameOfLifeCell[][] expected = {
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)}
    };
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/blinker.properties");
    Grid grid = reader.gridFromPropertyFile();
    grid.updateNewStates();

    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

  @Test
  void updateCellsTubTest() {
    GameOfLifeCell[][] expected = {
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
    };
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/tub.properties");
    Grid grid = reader.gridFromPropertyFile();
    grid.updateNewStates();

      for (int i = 0; i < expected.length; i++) {
        for (int j = 0; j < expected[i].length; j++) {
          assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
        }
      }
  }

  @Test
  void updateCellsBoatTest() {
    GameOfLifeCell[][] expected = {
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
    };
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/boat.properties");
    Grid grid = reader.gridFromPropertyFile();
    grid.updateNewStates();

    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

  @Test
  void updateCellsCornerTest() {
    GameOfLifeCell[][] expected = {
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
        {new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.DEAD)},
    };
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/corner.properties");
    Grid grid = reader.gridFromPropertyFile();
    grid.updateNewStates();

    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

  @Test
  void updateCellsEdgesTest() {
    GameOfLifeCell[][] expected = {
        {new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE)},
        {new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE)},
        {new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE)},
        {new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.DEAD), new GameOfLifeCell(GameOfLifeStates.ALIVE)},
        {new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE), new GameOfLifeCell(GameOfLifeStates.ALIVE)},
    };
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/edges.properties");
    Grid grid = reader.gridFromPropertyFile();
    grid.updateNewStates();

    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }
}