package cellsociety.model;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.configuration.Grid;
import cellsociety.configuration.PropertyReader;
import cellsociety.model.Cells.GameOfLife.GameOfLifeCell;
import cellsociety.model.Cells.GameOfLife.GameOfLifeStates;
import org.junit.jupiter.api.Test;

class GameOfLifeTest {

  @Test
  void getNumberOfLiveNeighborsCorner() {
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/square.properties");
    Grid grid = reader.gridFromPropertyFile();
    int numberOfLiveNeighbors = grid.getMyCells().get(0).get(0).getMyNeighbors()
        .getNeighborsWithState(GameOfLifeStates.ALIVE).size();
    assertEquals(3, numberOfLiveNeighbors);
  }

  @Test
  void getNumberOfLiveNeighborsMiddle() {
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/boat.properties");
    Grid grid = reader.gridFromPropertyFile();
    int numberOfLiveNeighbors = grid.getMyCells().get(1).get(1).getMyNeighbors()
        .getNeighborsWithState(GameOfLifeStates.ALIVE).size();
    assertEquals(2, numberOfLiveNeighbors);
  }

  @Test
  void getNumberOfLiveNeighborsDead() {
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/boat.properties");
    Grid grid = reader.gridFromPropertyFile();
    int numberOfLiveNeighbors = grid.getMyCells().get(2).get(2).getMyNeighbors()
        .getNeighborsWithState(GameOfLifeStates.ALIVE).size();
    assertEquals(5, numberOfLiveNeighbors);
  }

  @Test
  void getNewState2NeighborsAlive() {
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/edges.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(GameOfLifeStates.ALIVE, grid.getMyCells().get(0).get(0).getMyState()); // assert alive
    assertEquals(2, grid.getMyCells().get(0).get(0).getMyNeighbors()
        .getNeighborsWithState(GameOfLifeStates.ALIVE).size()); // assert 2 neighbors
    grid.updateNewStates();
    assertEquals(GameOfLifeStates.ALIVE, grid.getMyCells().get(0).get(0).getMyState()); // assert alive
  }

  @Test
  void getNewState3NeighborsAlive() {
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/beacon.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(GameOfLifeStates.ALIVE, grid.getMyCells().get(2).get(1).getMyState()); // assert alive
    assertEquals(3, grid.getMyCells().get(2).get(1).getMyNeighbors()
          .getNeighborsWithState(GameOfLifeStates.ALIVE).size()); // assert 3 neighbors
    grid.updateNewStates();
    assertEquals(GameOfLifeStates.ALIVE, grid.getMyCells().get(2).get(1).getMyState());
  }

  @Test
  void getNewStateReproduction() {
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/toad.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(GameOfLifeStates.DEAD, grid.getMyCells().get(2).get(1).getMyState()); // assert dead
    assertEquals(3, grid.getMyCells().get(2).get(1).getMyNeighbors()
        .getNeighborsWithState(GameOfLifeStates.ALIVE).size()); // assert 3 neighbors
    grid.updateNewStates();
    assertEquals(GameOfLifeStates.ALIVE, grid.getMyCells().get(2).get(1).getMyState());
  }

  @Test
  void getNewStateOverpopulation() {
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/beacon.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(GameOfLifeStates.ALIVE, grid.getMyCells().get(2).get(2).getMyState()); // assert alive
    assertTrue(grid.getMyCells().get(2).get(2).getMyNeighbors()
        .getNeighborsWithState(GameOfLifeStates.ALIVE).size() > 3); // assert overpopulation
    grid.updateNewStates();
    assertEquals(GameOfLifeStates.DEAD, grid.getMyCells().get(2).get(2).getMyState());
  }

  @Test
  void getNewStateUnderpopulation() {
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/corner.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(GameOfLifeStates.ALIVE, grid.getMyCells().get(0).get(0).getMyState()); // assert alive
    assertTrue(grid.getMyCells().get(0).get(0).getMyNeighbors()
        .getNeighborsWithState(GameOfLifeStates.ALIVE).size() < 2); // assert underpopulation
    grid.updateNewStates();
    assertEquals(GameOfLifeStates.DEAD, grid.getMyCells().get(0).get(0).getMyState());
  }

  @Test
  void updateCellsSquareTest() {
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/square.properties");
    GameOfLifeCell[][] expected = {
        {new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife"))}
    };
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
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/beacon.properties");

    GameOfLifeCell[][] expectedUpdate = {
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))}
    };

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
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/toad.properties");

    GameOfLifeCell[][] expected = {
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))}
    };
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
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/blinker.properties");

    GameOfLifeCell[][] expected = {
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))}
    };
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
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/tub.properties");

    GameOfLifeCell[][] expected = {
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
    };
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
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/boat.properties");

    GameOfLifeCell[][] expected = {
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
    };
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
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/corner.properties");

    GameOfLifeCell[][] expected = {
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
        {new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife"))},
    };
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
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/edges.properties");

    GameOfLifeCell[][] expected = {
      {new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife"))},
      {new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife"))},
      {new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife"))},
      {new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.DEAD, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife"))},
      {new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife")), new GameOfLifeCell(GameOfLifeStates.ALIVE, reader.optionalKeyMap("GameOfLife"))},
    };
    Grid grid = reader.gridFromPropertyFile();
    grid.updateNewStates();

    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }
}