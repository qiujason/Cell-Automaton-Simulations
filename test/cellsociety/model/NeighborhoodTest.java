package cellsociety.model;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.configuration.Grid;
import cellsociety.configuration.PropertyReader;
import org.junit.jupiter.api.Test;

public class NeighborhoodTest {

  @Test
  void getCompleteNeighborhoodCount() {
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/Beacon.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(8, grid.getMyCells().get(1).get(1).getMyNeighbors().getNumActiveNeighbors());
  }

  @Test
  void getCompleteNeighborhoodCountCorner() {
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/Beacon.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertNotNull(grid.getMyCells().get(1).get(1).getMyNeighbors().getMyCells()[0][0]);
    assertNotNull(grid.getMyCells().get(1).get(1).getMyNeighbors().getMyCells()[0][1]);
    assertNotNull(grid.getMyCells().get(1).get(1).getMyNeighbors().getMyCells()[0][2]);
    assertNotNull(grid.getMyCells().get(1).get(1).getMyNeighbors().getMyCells()[1][0]);
    assertNotNull(grid.getMyCells().get(1).get(1).getMyNeighbors().getMyCells()[1][2]);
    assertNotNull(grid.getMyCells().get(1).get(1).getMyNeighbors().getMyCells()[2][0]);
    assertNotNull(grid.getMyCells().get(1).get(1).getMyNeighbors().getMyCells()[2][1]);
    assertNotNull(grid.getMyCells().get(1).get(1).getMyNeighbors().getMyCells()[2][2]);
  }

  @Test
  void getCardinalNeighborhoodCount() {
    PropertyReader reader = new PropertyReader("property_lists/SpreadingFire/centerOnFireWithSpace.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(4, grid.getMyCells().get(2).get(2).getMyNeighbors().getNumActiveNeighbors());
  }

  @Test
  void getOrdinalNeighborhoodCount() {
    PropertyReader reader = new PropertyReader("property_lists/SpreadingFire/oneOnFire.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(4, grid.getMyCells().get(2).get(2).getMyNeighbors().getNumActiveNeighbors());
  }

}
