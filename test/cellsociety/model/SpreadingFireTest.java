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
    PropertyReader reader = new PropertyReader("property_lists/SpreadingFire/centerOnFire.properties");
    SpreadingFireCell[][] expected = {
        {new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire"))},
        {new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire"))},
        {new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.EMPTY, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.BURNING, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire"))},
        {new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire"))},
        {new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire"))}
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
  void updateCenterOnFireWithSpace() {
    PropertyReader reader = new PropertyReader("property_lists/SpreadingFire/centerOnFireWithSpace.properties");
    SpreadingFireCell[][] expected = {
        {new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire"))},
        {new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.EMPTY, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.EMPTY, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.EMPTY, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire"))},
        {new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.EMPTY, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.EMPTY, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.EMPTY, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire"))},
        {new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.EMPTY, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.EMPTY, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.EMPTY, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire"))},
        {new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire"))}
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
  void updateOneOnFire() {
    PropertyReader reader = new PropertyReader("property_lists/SpreadingFire/oneOnFire.properties");
    SpreadingFireCell[][] expected = {
        {new SpreadingFireCell(SpreadingFireStates.EMPTY, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.EMPTY, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire"))},
        {new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.EMPTY, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.EMPTY, reader.optionalKeyMap("SpreadingFire"))},
        {new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire"))},
        {new SpreadingFireCell(SpreadingFireStates.EMPTY, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.EMPTY, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.EMPTY, reader.optionalKeyMap("SpreadingFire"))},
        {new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.EMPTY, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.EMPTY, reader.optionalKeyMap("SpreadingFire")), new SpreadingFireCell(SpreadingFireStates.TREE, reader.optionalKeyMap("SpreadingFire"))}
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