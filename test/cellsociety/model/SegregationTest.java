package cellsociety.model;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.configuration.Grid;
import cellsociety.configuration.PropertyReader;
import cellsociety.model.Segregation.SegregationCell;
import cellsociety.model.Segregation.SegregationStates;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SegregationTest {

  @BeforeEach
  void setRandomSeed() {
    SegregationCell.setRandomSeed(10);
  }

  @Test
  void getNewStateSatisfied() {
    PropertyReader reader = new PropertyReader("property_lists/Segregation/minimal.properties");
    Grid grid = reader.gridFromPropertyFile();

    assertEquals(SegregationStates.UNSATISFIED, ((SegregationCell) grid.getMyCells().get(0).get(0)).getSatisfiedState());
    assertEquals(SegregationStates.A, grid.getMyCells().get(0).get(0).getMyState());
    grid.updateNewStates();

    assertEquals(SegregationStates.SATISFIED, ((SegregationCell) grid.getMyCells().get(0).get(0)).getSatisfiedState());
    assertEquals(SegregationStates.A, grid.getMyCells().get(0).get(0).getMyState());
  }

  @Test
  void getNewStateUnsatisfied() {
    PropertyReader reader = new PropertyReader("property_lists/Segregation/checkerboard.properties");
    Grid grid = reader.gridFromPropertyFile();

    assertEquals(SegregationStates.UNSATISFIED, ((SegregationCell) grid.getMyCells().get(0).get(0)).getSatisfiedState());
    assertEquals(SegregationStates.A, grid.getMyCells().get(0).get(0).getMyState());
    grid.updateNewStates();

    assertEquals(SegregationStates.UNSATISFIED, ((SegregationCell) grid.getMyCells().get(0).get(0)).getSatisfiedState());
    assertEquals(SegregationStates.EMPTY, grid.getMyCells().get(0).get(0).getMyState());
  }

  @Test
  void getNewStateSatisfiedToUnsatisfied() {
    PropertyReader reader = new PropertyReader("property_lists/Segregation/minimal.properties");
    Grid grid = reader.gridFromPropertyFile();

    assertEquals(SegregationStates.UNSATISFIED, ((SegregationCell) grid.getMyCells().get(0).get(0)).getSatisfiedState());
    assertEquals(SegregationStates.A, grid.getMyCells().get(0).get(0).getMyState());
    for (int i = 0; i < 8; i++) {
      grid.updateNewStates();
    }

    assertEquals(SegregationStates.UNSATISFIED, ((SegregationCell) grid.getMyCells().get(0).get(0)).getSatisfiedState());
    assertEquals(SegregationStates.EMPTY, grid.getMyCells().get(0).get(0).getMyState());
  }

  @Test
  void testConsistentNumberOfTypes() {
    PropertyReader reader = new PropertyReader("property_lists/Segregation/minimal.properties");
    Grid grid = reader.gridFromPropertyFile();

    int numA = 0;
    int numB = 0;
    int numEmpty = 0;
    for (List<Cell> cells: grid.getMyCells()) {
      for (Cell cell : cells) {
        Enum<?> state = cell.getMyState();
        if (state == SegregationStates.A) {
          numA++;
        } else if (state == SegregationStates.B) {
          numB++;
        } else if (state == SegregationStates.EMPTY) {
          numEmpty++;
        }
      }
    }

//    for (int i = 0; i < 100; i++) {
      grid.updateNewStates();
//    }

    int newNumA = 0;
    int newNumB = 0;
    int newNumEmpty = 0;
    for (List<Cell> cells: grid.getMyCells()) {
      for (Cell cell : cells) {
        Enum<?> state = cell.getMyState();
        if (state == SegregationStates.A) {
          newNumA++;
        } else if (state == SegregationStates.B) {
          newNumB++;
        } else if (state == SegregationStates.EMPTY) {
          newNumEmpty++;
        }
      }
    }

    assertEquals(numA, newNumA);
    assertEquals(numB, newNumB);
    assertEquals(numEmpty, newNumEmpty);
  }

//  @Test
//  void testEmptyIsUnaffected() {
//    PropertyReader reader = new PropertyReader("property_lists/Segregation/centerOnFireWithSpace.properties");
//    Grid grid = reader.gridFromPropertyFile();
//    List<List<Cell>> initialState = grid.getMyCells();
//    for (int i = 0; i < 100; i++) {
//      grid.updateNewStates();
//    }
//    assertEquals(initialState, grid.getMyCells());
//  }

//  @Test
//  void testBurningBecomesEmpty() {
//    PropertyReader reader = new PropertyReader("property_lists/Segregation/centerOnFire.properties");
//    Grid grid = reader.gridFromPropertyFile();
//
//    assertEquals(SegregationStates., grid.getMyCells().get(2).get(3).getMyState());
//    grid.updateNewStates();
//
//    assertEquals(SegregationStates.ING, grid.getMyCells().get(2).get(3).getMyState());
//    grid.updateNewStates();
//
//    assertEquals(SegregationStates.Y, grid.getMyCells().get(2).get(3).getMyState());
//  }
//
//  @Test
//  void updateCenterOnFire() {
//    SegregationCell[][] expected = {
//        {new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.)},
//        {new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.)},
//        {new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.Y), new SegregationCell(SegregationStates.ING), new SegregationCell(SegregationStates.)},
//        {new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.)},
//        {new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.)}
//    };
//    PropertyReader reader = new PropertyReader("property_lists/Segregation/centerOnFire.properties");
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
//  void updateCenterOnFireWithSpace() {
//    SegregationCell[][] expected = {
//        {new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.)},
//        {new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.Y), new SegregationCell(SegregationStates.Y), new SegregationCell(SegregationStates.Y), new SegregationCell(SegregationStates.)},
//        {new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.Y), new SegregationCell(SegregationStates.Y), new SegregationCell(SegregationStates.Y), new SegregationCell(SegregationStates.)},
//        {new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.Y), new SegregationCell(SegregationStates.Y), new SegregationCell(SegregationStates.Y), new SegregationCell(SegregationStates.)},
//        {new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.)}
//    };
//    PropertyReader reader = new PropertyReader("property_lists/Segregation/centerOnFireWithSpace.properties");
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
//  void updateOneOnFire() {
//    SegregationCell[][] expected = {
//        {new SegregationCell(SegregationStates.Y), new SegregationCell(SegregationStates.Y), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.)},
//        {new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.Y), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.Y)},
//        {new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.)},
//        {new SegregationCell(SegregationStates.Y), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.Y), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.Y)},
//        {new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.Y), new SegregationCell(SegregationStates.), new SegregationCell(SegregationStates.Y), new SegregationCell(SegregationStates.)}
//    };
//    PropertyReader reader = new PropertyReader("property_lists/Segregation/oneOnFire.properties");
//    Grid grid = reader.gridFromPropertyFile();
//    grid.updateNewStates();
//
//    for (int i = 0; i < expected.length; i++) {
//      for (int j = 0; j < expected[i].length; j++) {
//        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
//      }
//    }
//  }

}