package cellsociety.model;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.configuration.Grid;
import cellsociety.configuration.PropertyReader;
import cellsociety.model.Cells.Wator.WatorCell;
import cellsociety.model.Cells.Wator.WatorStates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WatorTest {

  @BeforeEach
  void setRandomSeed() {
  WatorCell.setRandomSeed(10);
}

  @Test
  void getNewStateMove() {
    PropertyReader reader = new PropertyReader("property_lists/Wator/FishSharkAlternation.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(WatorStates.FISH, grid.getMyCells().get(1).get(1).getMyState());
    grid.updateNewStates();
    grid.updateNewStates();
    assertEquals(WatorStates.EMPTY, grid.getMyCells().get(1).get(1).getMyState());
  }

  @Test
  void getNewStateReproduce() {
    PropertyReader reader = new PropertyReader("property_lists/Wator/FishSharkAlternation.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(WatorStates.FISH, grid.getMyCells().get(0).get(0).getMyState());
    for (int i = 0; i < 15; i++) {
      grid.updateNewStates();
    }
    assertEquals(WatorStates.SHARK, grid.getMyCells().get(0).get(0).getMyState());
  }

  @Test
  void getNewStateEaten() {
    PropertyReader reader = new PropertyReader("property_lists/Wator/OneSharkFourFish.properties");
    Grid grid = reader.gridFromPropertyFile();
    assertEquals(WatorStates.FISH, grid.getMyCells().get(2).get(1).getMyState());
    grid.updateNewStates();
    assertEquals(WatorStates.EMPTY, grid.getMyCells().get(2).get(1).getMyState());
  }

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

}