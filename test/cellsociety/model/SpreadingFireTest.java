//package cellsociety.model;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import cellsociety.configuration.Grid;
//import cellsociety.configuration.PropertyReader;
//import cellsociety.model.SpreadingFire.SpreadingFireCell;
//import cellsociety.model.SpreadingFire.SpreadingFireStates;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//class SpreadingFireTest {
//
//  @BeforeAll
//  void setOptionalKey() {
//
//  }
//
//  @Test
//  void getNewStateAdjacentFilled() {
//    PropertyReader reader = new PropertyReader("property_lists/SpreadingFire/diagonal.properties");
//    Grid grid = reader.gridFromPropertyFile();
//    assertEquals(SpreadingFireStates.UNFILLED, grid.getMyCells().get(1).get(0).getMyState());
//    grid.updateNewStates();
//    assertEquals(SpreadingFireStates.FILLED, grid.getMyCells().get(1).get(0).getMyState());
//  }
//
//  @Test
//  void getNewStateOfBlockedWithAdjacentFilled() {
//    PropertyReader reader = new PropertyReader("property_lists/SpreadingFire/diagonal.properties");
//    Grid grid = reader.gridFromPropertyFile();
//    assertEquals(SpreadingFireStates.BLOCKED, grid.getMyCells().get(0).get(1).getMyState());
//    grid.updateNewStates();
//    assertEquals(SpreadingFireStates.BLOCKED, grid.getMyCells().get(0).get(1).getMyState());
//  }
//
//  @Test
//  void getNewStateWithAdjacentFilledDiagonal() {
//    PropertyReader reader = new PropertyReader("property_lists/SpreadingFire/diagonal.properties");
//    Grid grid = reader.gridFromPropertyFile();
//    assertEquals(SpreadingFireStates.UNFILLED, grid.getMyCells().get(1).get(1).getMyState());
//    grid.updateNewStates();
//    assertEquals(SpreadingFireStates.FILLED, grid.getMyCells().get(1).get(1).getMyState());
//  }
//
//  @Test
//  void getNewStateOfBlockedWithNoAdjacentFilled() {
//    PropertyReader reader = new PropertyReader("property_lists/SpreadingFire/diagonal.properties");
//    Grid grid = reader.gridFromPropertyFile();
//    assertEquals(SpreadingFireStates.UNFILLED, grid.getMyCells().get(2).get(1).getMyState());
//    grid.updateNewStates();
//    assertEquals(SpreadingFireStates.UNFILLED, grid.getMyCells().get(2).get(1).getMyState());
//  }
//
//  @Test
//  void updateAllBlocked() {
//    SpreadingFireCell[][] expected = {
//        {new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED)},
//        {new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED)},
//        {new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED)},
//        {new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED)},
//        {new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED)}
//    };
//    PropertyReader reader = new PropertyReader("property_lists/SpreadingFire/allBlocked.properties");
//    Grid grid = reader.gridFromPropertyFile();
//    grid.updateNewStates();
//
//    for (int i = 0; i < expected.length; i++) {
//      for (int j = 0; j < expected[i].length; j++) {
//        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
//      }
//    }
//  }
//
//  @Test
//  void updateDiagonal() {
//    SpreadingFireCell[][] expected = {
//        {new SpreadingFireCell(SpreadingFireStates.FILLED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED)},
//        {new SpreadingFireCell(SpreadingFireStates.FILLED), new SpreadingFireCell(SpreadingFireStates.FILLED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED)},
//        {new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.UNFILLED), new SpreadingFireCell(SpreadingFireStates.UNFILLED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED)},
//        {new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.UNFILLED), new SpreadingFireCell(SpreadingFireStates.UNFILLED), new SpreadingFireCell(SpreadingFireStates.BLOCKED)},
//        {new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.UNFILLED), new SpreadingFireCell(SpreadingFireStates.UNFILLED)}
//    };
//    PropertyReader reader = new PropertyReader("property_lists/SpreadingFire/diagonal.properties");
//    Grid grid = reader.gridFromPropertyFile();
//    grid.updateNewStates();
//
//    for (int i = 0; i < expected.length; i++) {
//      for (int j = 0; j < expected[i].length; j++) {
//        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
//      }
//    }
//  }
//
//  @Test
//  void updateStraightLine() {
//    SpreadingFireCell[][] expected = {
//        {new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.FILLED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED)},
//        {new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.FILLED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED)},
//        {new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.UNFILLED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED)},
//        {new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.UNFILLED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED)},
//        {new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.UNFILLED), new SpreadingFireCell(SpreadingFireStates.BLOCKED), new SpreadingFireCell(SpreadingFireStates.BLOCKED)}
//    };
//    PropertyReader reader = new PropertyReader("property_lists/SpreadingFire/straightLine.properties");
//    Grid grid = reader.gridFromPropertyFile();
//    grid.updateNewStates();
//
//    for (int i = 0; i < expected.length; i++) {
//      for (int j = 0; j < expected[i].length; j++) {
//        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
//      }
//    }
//  }
//
//}