//package cellsociety.model;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import cellsociety.configuration.Grid;
//import cellsociety.configuration.PropertyReader;
//import cellsociety.model.RPS.RPSCell;
//import cellsociety.model.RPS.RPSStates;
//import org.junit.jupiter.api.Test;
//
//class RPSTest {
//
//  @Test
//  void getNewStateAdjacentFilled() {
//    PropertyReader reader = new PropertyReader("property_lists/RPS/diagonal.properties");
//    Grid grid = reader.gridFromPropertyFile();
//    assertEquals(RPSStates.UNFILLED, grid.getMyCells().get(1).get(0).getMyState());
//    grid.updateNewStates();
//    assertEquals(RPSStates.FILLED, grid.getMyCells().get(1).get(0).getMyState());
//  }
//
//  @Test
//  void getNewStateOfBlockedWithAdjacentFilled() {
//    PropertyReader reader = new PropertyReader("property_lists/RPS/diagonal.properties");
//    Grid grid = reader.gridFromPropertyFile();
//    assertEquals(RPSStates.BLOCKED, grid.getMyCells().get(0).get(1).getMyState());
//    grid.updateNewStates();
//    assertEquals(RPSStates.BLOCKED, grid.getMyCells().get(0).get(1).getMyState());
//  }
//
//  @Test
//  void getNewStateWithAdjacentFilledDiagonal() {
//    PropertyReader reader = new PropertyReader("property_lists/RPS/diagonal.properties");
//    Grid grid = reader.gridFromPropertyFile();
//    assertEquals(RPSStates.UNFILLED, grid.getMyCells().get(1).get(1).getMyState());
//    grid.updateNewStates();
//    assertEquals(RPSStates.FILLED, grid.getMyCells().get(1).get(1).getMyState());
//  }
//
//  @Test
//  void getNewStateOfBlockedWithNoAdjacentFilled() {
//    PropertyReader reader = new PropertyReader("property_lists/RPS/diagonal.properties");
//    Grid grid = reader.gridFromPropertyFile();
//    assertEquals(RPSStates.UNFILLED, grid.getMyCells().get(2).get(1).getMyState());
//    grid.updateNewStates();
//    assertEquals(RPSStates.UNFILLED, grid.getMyCells().get(2).get(1).getMyState());
//  }
//
//  @Test
//  void updateAllBlocked() {
//    RPSCell[][] expected = {
//        {new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED)},
//        {new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED)},
//        {new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED)},
//        {new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED)},
//        {new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED)}
//    };
//    PropertyReader reader = new PropertyReader("property_lists/RPS/allBlocked.properties");
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
//    RPSCell[][] expected = {
//        {new RPSCell(RPSStates.FILLED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED)},
//        {new RPSCell(RPSStates.FILLED), new RPSCell(RPSStates.FILLED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED)},
//        {new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.UNFILLED), new RPSCell(RPSStates.UNFILLED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED)},
//        {new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.UNFILLED), new RPSCell(RPSStates.UNFILLED), new RPSCell(RPSStates.BLOCKED)},
//        {new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.UNFILLED), new RPSCell(RPSStates.UNFILLED)}
//    };
//    PropertyReader reader = new PropertyReader("property_lists/RPS/diagonal.properties");
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
//    RPSCell[][] expected = {
//        {new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.FILLED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED)},
//        {new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.FILLED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED)},
//        {new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.UNFILLED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED)},
//        {new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.UNFILLED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED)},
//        {new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.UNFILLED), new RPSCell(RPSStates.BLOCKED), new RPSCell(RPSStates.BLOCKED)}
//    };
//    PropertyReader reader = new PropertyReader("property_lists/RPS/straightLine.properties");
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