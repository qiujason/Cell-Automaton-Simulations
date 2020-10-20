package cellsociety.model;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.configuration.Grid;
import cellsociety.configuration.PropertyReader;
import org.junit.jupiter.api.Test;

public class EdgeTests {

  @Test
  void getFiniteEdge() {
    PropertyReader reader = new PropertyReader("property_lists/Percolation/AllBlocked.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertNull(grid.getMyCells().get(1).get(0).getMyNeighbors().getMyCells()[0][0]);
    assertNull(grid.getMyCells().get(1).get(0).getMyNeighbors().getMyCells()[1][0]);
    assertNull(grid.getMyCells().get(1).get(0).getMyNeighbors().getMyCells()[2][0]);
  }

  @Test
  void getFiniteCorner() {
    PropertyReader reader = new PropertyReader("property_lists/Percolation/AllBlocked.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertNull(grid.getMyCells().get(0).get(0).getMyNeighbors().getMyCells()[0][0]);
    assertNull(grid.getMyCells().get(0).get(0).getMyNeighbors().getMyCells()[1][0]);
    assertNull(grid.getMyCells().get(0).get(0).getMyNeighbors().getMyCells()[2][0]);
    assertNull(grid.getMyCells().get(0).get(0).getMyNeighbors().getMyCells()[0][1]);
  }

  @Test
  void getToroidalHorizontalEdge() {
    PropertyReader reader = new PropertyReader("property_lists/Percolation/Diagonal.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(grid.getMyCells().get(1).get(4), grid.getMyCells().get(2).get(0).getMyNeighbors().getMyCells()[0][0]);
    assertEquals(grid.getMyCells().get(2).get(4), grid.getMyCells().get(2).get(0).getMyNeighbors().getMyCells()[1][0]);
    assertEquals(grid.getMyCells().get(3).get(4), grid.getMyCells().get(2).get(0).getMyNeighbors().getMyCells()[2][0]);
  }

  @Test
  void getToroidalVerticalEdge() {
    PropertyReader reader = new PropertyReader("property_lists/Percolation/Diagonal.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(grid.getMyCells().get(4).get(0), grid.getMyCells().get(0).get(1).getMyNeighbors().getMyCells()[0][0]);
    assertEquals(grid.getMyCells().get(4).get(1), grid.getMyCells().get(0).get(1).getMyNeighbors().getMyCells()[0][1]);
    assertEquals(grid.getMyCells().get(4).get(2), grid.getMyCells().get(0).get(1).getMyNeighbors().getMyCells()[0][2]);
  }

  @Test
  void getToroidalCorner() {
    PropertyReader reader = new PropertyReader("property_lists/Percolation/Diagonal.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(grid.getMyCells().get(1).get(4), grid.getMyCells().get(2).get(0).getMyNeighbors().getMyCells()[0][0]);
    assertEquals(grid.getMyCells().get(2).get(4), grid.getMyCells().get(2).get(0).getMyNeighbors().getMyCells()[1][0]);
    assertEquals(grid.getMyCells().get(3).get(4), grid.getMyCells().get(2).get(0).getMyNeighbors().getMyCells()[2][0]);
  }

  @Test
  void getInfiniteVerticalEdge() {
    PropertyReader reader = new PropertyReader("property_lists/Percolation/Diagonal.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(grid.getMyCells().get(4).get(0), grid.getMyCells().get(0).get(1).getMyNeighbors().getMyCells()[0][0]);
    assertEquals(grid.getMyCells().get(4).get(1), grid.getMyCells().get(0).get(1).getMyNeighbors().getMyCells()[0][1]);
    assertEquals(grid.getMyCells().get(4).get(2), grid.getMyCells().get(0).get(1).getMyNeighbors().getMyCells()[0][2]);
  }

  @Test
  void getInfiniteVerticalCorner() {
    PropertyReader reader = new PropertyReader("property_lists/Percolation/Diagonal.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(grid.getMyCells().get(1).get(4), grid.getMyCells().get(2).get(0).getMyNeighbors().getMyCells()[0][0]);
    assertEquals(grid.getMyCells().get(2).get(4), grid.getMyCells().get(2).get(0).getMyNeighbors().getMyCells()[1][0]);
    assertEquals(grid.getMyCells().get(3).get(4), grid.getMyCells().get(2).get(0).getMyNeighbors().getMyCells()[2][0]);
  }

  @Test
  void getInfiniteHorizontalEdge() {
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/tub.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(grid.getMyCells().get(0).get(4), grid.getMyCells().get(1).get(0).getMyNeighbors().getMyCells()[0][0]);
    assertEquals(grid.getMyCells().get(1).get(4), grid.getMyCells().get(1).get(0).getMyNeighbors().getMyCells()[0][1]);
    assertEquals(grid.getMyCells().get(2).get(4), grid.getMyCells().get(1).get(0).getMyNeighbors().getMyCells()[0][2]);
  }

  @Test
  void getInfiniteHorizontalCorner() {
    PropertyReader reader = new PropertyReader("property_lists/GameOfLife/tub.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(grid.getMyCells().get(1).get(4), grid.getMyCells().get(2).get(0).getMyNeighbors().getMyCells()[0][0]);
    assertEquals(grid.getMyCells().get(2).get(4), grid.getMyCells().get(2).get(0).getMyNeighbors().getMyCells()[1][0]);
    assertEquals(grid.getMyCells().get(3).get(4), grid.getMyCells().get(2).get(0).getMyNeighbors().getMyCells()[2][0]);
  }

}
