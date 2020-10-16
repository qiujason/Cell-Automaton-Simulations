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

  @BeforeEach
  void resetStaticVariables() {
    SegregationCell.resetUnsatisfiedA();
    SegregationCell.resetUnsatisfiedB();
    SegregationCell.resetNumEmptyCanBeMoved();
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
    int[] initial = getNumOfTypes(grid.getMyCells());

    for (int i = 0; i < 100; i++) {
      grid.updateNewStates();
      getNumOfTypes(grid.getMyCells());
    }

    int[] finalNum = getNumOfTypes(grid.getMyCells());

    assertEquals(initial[0], finalNum[0]);
    assertEquals(initial[1], finalNum[1]);
    assertEquals(initial[2], finalNum[2]);
  }

  private int[] getNumOfTypes(List<List<Cell>> grid) {
    int numA = 0;
    int numB = 0;
    int numEmpty = 0;
    for (List<Cell> cells: grid) {
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
    return new int[]{numA, numB, numEmpty};
  }

}