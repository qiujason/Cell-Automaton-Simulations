package cellsociety.model;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.configuration.Grid;
import cellsociety.configuration.PropertyReader;
import cellsociety.model.Cells.Segregation.SegregationCell;
import cellsociety.model.Cells.Wator.WatorCell;
import cellsociety.model.Cells.Wator.WatorStates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WatorTest {

  @Test
  void updateAllFish() {
    PropertyReader reader = new PropertyReader("property_lists/Wator/AllFish.properties");
    WatorCell[][] expected = {
        {new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator"))},
        {new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator"))},
        {new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator"))},
        {new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator"))},
        {new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator"))}
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
  void updateFishSharkAlternation() {
    PropertyReader reader = new PropertyReader("property_lists/Wator/FishSharkAlternation.properties");

    // TODO: update expected
    WatorCell[][] expected = {
        {new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator"))},
        {new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator"))},
        {new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator"))},
        {new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator"))},
        {new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator"))}
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
  void updateOneSharkFourFish() {
    PropertyReader reader = new PropertyReader("property_lists/Wator/OneSharkFourFish.properties");

    // TODO: update expected
    WatorCell[][] expected = {
        {new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator"))},
        {new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator"))},
        {new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator"))},
        {new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator"))},
        {new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator")), new WatorCell(WatorStates.FISH, reader.optionalKeyMap("Wator"))}
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