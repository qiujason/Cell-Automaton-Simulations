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
    PropertyReader reader = new PropertyReader("property_lists/Percolation/Diagonal.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(PercolationStates.UNFILLED, grid.getMyCells().get(1).get(0).getMyState());
    grid.updateNewStates();
    assertEquals(PercolationStates.FILLED, grid.getMyCells().get(1).get(0).getMyState());
  }

  @Test
  void getNewStateOfBlockedWithAdjacentFilled() {
    PropertyReader reader = new PropertyReader("property_lists/Percolation/Diagonal.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(PercolationStates.BLOCKED, grid.getMyCells().get(0).get(1).getMyState());
    grid.updateNewStates();
    assertEquals(PercolationStates.BLOCKED, grid.getMyCells().get(0).get(1).getMyState());
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
    PropertyReader reader = new PropertyReader("property_lists/Percolation/AllBlocked.properties");
    Grid grid = reader.gridFromPropertyFile();
    grid.updateNewStates();

    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

}