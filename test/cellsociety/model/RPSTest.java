package cellsociety.model;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.configuration.Grid;
import cellsociety.configuration.PropertyReader;
import cellsociety.model.Cells.RPS.RPSStates;
import org.junit.jupiter.api.Test;

class RPSTest {

  @Test
  void getNewStateWin() {
    PropertyReader reader = new PropertyReader("property_lists/RPS/rockDominant.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(RPSStates.ROCK, grid.getMyCells().get(1).get(1).getMyState());
    grid.updateNewStates();
    assertEquals(RPSStates.ROCK, grid.getMyCells().get(1).get(1).getMyState());
  }

  @Test
  void getNewStateCorner() {
    PropertyReader reader = new PropertyReader("property_lists/RPS/rockDominant.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(RPSStates.PAPER, grid.getMyCells().get(0).get(0).getMyState());
    grid.updateNewStates();
    assertEquals(RPSStates.PAPER, grid.getMyCells().get(0).get(0).getMyState());
  }

  @Test
  void getNewStateEdge() {
    PropertyReader reader = new PropertyReader("property_lists/RPS/even.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(RPSStates.PAPER, grid.getMyCells().get(2).get(0).getMyState());
    grid.updateNewStates();
    assertEquals(RPSStates.PAPER, grid.getMyCells().get(2).get(0).getMyState());
  }

  @Test
  void getNewStateConsistent() {
    PropertyReader reader = new PropertyReader("property_lists/RPS/segregated.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(RPSStates.PAPER, grid.getMyCells().get(2).get(0).getMyState());
    grid.updateNewStates();
    assertEquals(RPSStates.PAPER, grid.getMyCells().get(2).get(0).getMyState());
  }

}