package cellsociety.model;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.configuration.Grid;
import cellsociety.configuration.PropertyReader;
import cellsociety.model.SpreadingFire.SpreadingFireCell;
import cellsociety.model.SpreadingFire.SpreadingFireStates;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpreadingFireTest {

  @BeforeEach
  void setRandomSeed() {
    SpreadingFireCell.setRandomSeed(10);
  }

  @Test
  void getNewStateAdjacentFilledRandom() {
    PropertyReader reader = new PropertyReader("property_lists/SpreadingFire/centerOnFire.properties");
    Grid grid = reader.gridFromPropertyFile();

    assertEquals(SpreadingFireStates.TREE, grid.getMyCells().get(1).get(2).getMyState());
    assertEquals(SpreadingFireStates.TREE, grid.getMyCells().get(2).get(1).getMyState());
    assertEquals(SpreadingFireStates.TREE, grid.getMyCells().get(2).get(3).getMyState());
    assertEquals(SpreadingFireStates.TREE, grid.getMyCells().get(3).get(2).getMyState());
    grid.updateNewStates();

    assertEquals(SpreadingFireStates.TREE, grid.getMyCells().get(1).get(2).getMyState());
    assertEquals(SpreadingFireStates.TREE, grid.getMyCells().get(2).get(1).getMyState());
    assertEquals(SpreadingFireStates.BURNING, grid.getMyCells().get(2).get(3).getMyState());
    assertEquals(SpreadingFireStates.TREE, grid.getMyCells().get(3).get(2).getMyState());
  }

  @Test
  void testEmptyIsUnaffected() {
    PropertyReader reader = new PropertyReader("property_lists/SpreadingFire/centerOnFireWithSpace.properties");
    Grid grid = reader.gridFromPropertyFile();
    List<List<Cell>> initialState = grid.getMyCells();
    for (int i = 0; i < 100; i++) {
      grid.updateNewStates();
    }
    assertEquals(initialState, grid.getMyCells());
  }

  @Test
  void testBurningBecomesEmpty() {
    PropertyReader reader = new PropertyReader("property_lists/SpreadingFire/centerOnFire.properties");
    Grid grid = reader.gridFromPropertyFile();

    assertEquals(SpreadingFireStates.TREE, grid.getMyCells().get(2).get(3).getMyState());
    grid.updateNewStates();

    assertEquals(SpreadingFireStates.BURNING, grid.getMyCells().get(2).get(3).getMyState());
    grid.updateNewStates();

    assertEquals(SpreadingFireStates.EMPTY, grid.getMyCells().get(2).get(3).getMyState());
  }

  @Test
  void updateCenterOnFire() {
    SpreadingFireCell[][] expected = {
        {new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE)},
        {new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE)},
        {new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.EMPTY), new SpreadingFireCell(SpreadingFireStates.BURNING), new SpreadingFireCell(SpreadingFireStates.TREE)},
        {new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE)},
        {new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE)}
    };
    PropertyReader reader = new PropertyReader("property_lists/SpreadingFire/centerOnFire.properties");
    Grid grid = reader.gridFromPropertyFile();
    grid.updateNewStates();

    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

  @Test
  void updateCenterOnFireWithSpace() {
    SpreadingFireCell[][] expected = {
        {new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE)},
        {new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.EMPTY), new SpreadingFireCell(SpreadingFireStates.EMPTY), new SpreadingFireCell(SpreadingFireStates.EMPTY), new SpreadingFireCell(SpreadingFireStates.TREE)},
        {new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.EMPTY), new SpreadingFireCell(SpreadingFireStates.EMPTY), new SpreadingFireCell(SpreadingFireStates.EMPTY), new SpreadingFireCell(SpreadingFireStates.TREE)},
        {new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.EMPTY), new SpreadingFireCell(SpreadingFireStates.EMPTY), new SpreadingFireCell(SpreadingFireStates.EMPTY), new SpreadingFireCell(SpreadingFireStates.TREE)},
        {new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE)}
    };
    PropertyReader reader = new PropertyReader("property_lists/SpreadingFire/centerOnFireWithSpace.properties");
    Grid grid = reader.gridFromPropertyFile();
    grid.updateNewStates();

    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

  @Test
  void updateOneOnFire() {
    SpreadingFireCell[][] expected = {
        {new SpreadingFireCell(SpreadingFireStates.EMPTY), new SpreadingFireCell(SpreadingFireStates.EMPTY), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE)},
        {new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.EMPTY), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.EMPTY)},
        {new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.TREE)},
        {new SpreadingFireCell(SpreadingFireStates.EMPTY), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.EMPTY), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.EMPTY)},
        {new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.EMPTY), new SpreadingFireCell(SpreadingFireStates.TREE), new SpreadingFireCell(SpreadingFireStates.EMPTY), new SpreadingFireCell(SpreadingFireStates.TREE)}
    };
    PropertyReader reader = new PropertyReader("property_lists/SpreadingFire/oneOnFire.properties");
    Grid grid = reader.gridFromPropertyFile();
    grid.updateNewStates();

    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

}