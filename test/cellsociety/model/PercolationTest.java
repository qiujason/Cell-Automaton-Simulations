package cellsociety.model;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.configuration.Grid;
import cellsociety.configuration.PropertyReader;
import cellsociety.model.Percolation.PercolationCell;
import cellsociety.model.Percolation.PercolationStates;
import org.junit.jupiter.api.Test;

class PercolationTest {

  @Test
  void getNewStateAdjacentFilled() {
    PropertyReader reader = new PropertyReader("property_lists/Percolation/diagonal.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(PercolationStates.UNFILLED, grid.getMyCells().get(1).get(0).getMyState());
    grid.updateNewStates();
    assertEquals(PercolationStates.FILLED, grid.getMyCells().get(1).get(0).getMyState());
  }

  @Test
  void getNewStateOfBlockedWithAdjacentFilled() {
    PropertyReader reader = new PropertyReader("property_lists/Percolation/diagonal.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(PercolationStates.BLOCKED, grid.getMyCells().get(0).get(1).getMyState());
    grid.updateNewStates();
    assertEquals(PercolationStates.BLOCKED, grid.getMyCells().get(0).get(1).getMyState());
  }

  @Test
  void getNewStateWithAdjacentFilledDiagonal() {
    PropertyReader reader = new PropertyReader("property_lists/Percolation/diagonal.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(PercolationStates.UNFILLED, grid.getMyCells().get(1).get(1).getMyState());
    grid.updateNewStates();
    assertEquals(PercolationStates.FILLED, grid.getMyCells().get(1).get(1).getMyState());
  }

  @Test
  void getNewStateOfBlockedWithNoAdjacentFilled() {
    PropertyReader reader = new PropertyReader("property_lists/Percolation/diagonal.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(PercolationStates.UNFILLED, grid.getMyCells().get(2).get(1).getMyState());
    grid.updateNewStates();
    assertEquals(PercolationStates.UNFILLED, grid.getMyCells().get(2).get(1).getMyState());
  }

  @Test
  void updateAllBlocked() {
    PercolationCell[][] expected = {
        {new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED)},
        {new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED)},
        {new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED)},
        {new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED)},
        {new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED)}
    };
    PropertyReader reader = new PropertyReader("property_lists/Percolation/allBlocked.properties");
    Grid grid = reader.gridFromPropertyFile();
    grid.updateNewStates();

    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

  @Test
  void updateDiagonal() {
    PercolationCell[][] expected = {
        {new PercolationCell(PercolationStates.FILLED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED)},
        {new PercolationCell(PercolationStates.FILLED), new PercolationCell(PercolationStates.FILLED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED)},
        {new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.UNFILLED), new PercolationCell(PercolationStates.UNFILLED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED)},
        {new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.UNFILLED), new PercolationCell(PercolationStates.UNFILLED), new PercolationCell(PercolationStates.BLOCKED)},
        {new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.UNFILLED), new PercolationCell(PercolationStates.UNFILLED)}
    };
    PropertyReader reader = new PropertyReader("property_lists/Percolation/diagonal.properties");
    Grid grid = reader.gridFromPropertyFile();
    grid.updateNewStates();

    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

  @Test
  void updateStraightLine() {
    PercolationCell[][] expected = {
        {new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.FILLED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED)},
        {new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.FILLED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED)},
        {new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.UNFILLED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED)},
        {new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.UNFILLED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED)},
        {new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.UNFILLED), new PercolationCell(PercolationStates.BLOCKED), new PercolationCell(PercolationStates.BLOCKED)}
    };
    PropertyReader reader = new PropertyReader("property_lists/Percolation/straightLine.properties");
    Grid grid = reader.gridFromPropertyFile();
    grid.updateNewStates();

    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

}